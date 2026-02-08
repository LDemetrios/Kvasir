package org.ldemetrios.kvasir.preview.data

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditorWithPreview
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectLocator
import com.intellij.openapi.vfs.*
import org.ldemetrios.kvasir.highlight.defaultScheme
import org.ldemetrios.kvasir.preview.ui.TypstPreviewFileEditor
import org.ldemetrios.kvasir.util.*
import org.ldemetrios.withCompilerRuntime
import org.ldemetrios.tyko.compiler.*
import org.ldemetrios.tyko.model.TColor
import org.ldemetrios.tyko.model.TDict
import org.ldemetrios.tyko.model.TValue
import org.ldemetrios.tyko.model.repr
import org.ldemetrios.tyko.model.t
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.iterator
import kotlin.concurrent.withLock

class KvasirVFSListener(/*private val project: Project*/val mode: SyntaxMode) : VirtualFileListener {
    override fun contentsChanged(event: VirtualFileEvent) {
        if (event.file.extension !in extensions) return

        //We'll need something more robust later
        val project = ProjectLocator.getInstance().guessProjectForFile(event.file)!!

        val editorManager = FileEditorManager.getInstance(project)
        val openEditors = editorManager.allEditors

        val files = FileEditorManager.getInstance(project).selectedFiles.filter { it.extension in extensions }
            .mapNotNull { f -> openEditors.singleOrNull { it.file == f }?.let { f to it } }
            .toMap()

        if (files.isEmpty()) return
        val service: ProjectCompilerService = ProjectCompilerService.getInstance(project)

        for ((file, editor) in files) {
            val actualEditor = when (editor) {
                is TextEditorWithPreview -> editor.previewEditor as? TypstPreviewFileEditor
                is TypstPreviewFileEditor -> editor
                else -> null
            } ?: continue
            service.scheduleRecompile(file, actualEditor, mode)
        }
    }
}

data class CompiledDoc(
//    var lastSvg : List<String>,
    var errors: List<SourceDiagnostic>,
    var warnings: List<SourceDiagnostic>,
)

private val compiled = ConcurrentHashMap<VirtualFile, ConcurrentHashMap<Project, CompiledDoc>>()

fun getCompiled(file: VirtualFile) = compiled[file]?.toMap() ?: mapOf()

@Service(Service.Level.PROJECT)
class ProjectCompilerService(val project: Project) : Disposable {
//    private val fonts = runtime.fontCollection(includeSystem = true, includeEmbedded = true, listOf())


    private var library = withCompilerRuntime {
        library(
            features = setOf(Feature.Html, Feature.A11yExtras),
            inputs = colorsInput().repr()
        )
    }
    private var currentMain: String? = null
    private val lock = ReentrantLock()

    private val edited = ConcurrentHashMap<String, Document>()
    fun registerDoc(path: String, doc: Document) = edited.put(path, doc)
    fun unregisterDoc(path: String) = edited.remove(path)

    private val scheduled = ConcurrentHashSet<VirtualFile>()
    private val reschedule = ConcurrentHashSet<VirtualFile>()

    fun resolveFile(
        mainFile: FileDescriptor,
        mode: SyntaxMode,
        file: FileDescriptor
    ): RResult<ByteArray, FileError> {
        return when (file.packageSpec?.namespace) {
            null -> {
                // File in project
                val cached = edited[file.virtualPath]
                if (cached != null) {
                    val text = cached.text
                    RResult.Ok(
                        if (file == mainFile) {
                            when (mode) {
                                SyntaxMode.Markup -> text
                                SyntaxMode.Code -> "#{\n$text\n}"
                                SyntaxMode.Math -> "$\n$text\n$"
                            }
                        } else {
                            text
                        }.toByteArray()
                    )
                } else {
                    val absolutePath: String = project.basePath + file.virtualPath
                    val projectFile = LocalFileSystem.getInstance().findFileByPath(absolutePath)
                    when {
                        projectFile == null || !projectFile.exists() -> {
                            RResult.Err(FileError.NotFound(file.virtualPath))
                        }

                        projectFile.isDirectory -> {
                            RResult.Err(FileError.IsDirectory)
                        }

                        else -> try {
                            val text = projectFile.readBytes()
                            RResult.Ok(
                                if (file == mainFile) {
                                    when (mode) {
                                        SyntaxMode.Markup -> text
                                        SyntaxMode.Code -> ("#{\n").toByteArray() + text + ("\n}").toByteArray()
                                        SyntaxMode.Math -> ("$\n").toByteArray() + text + ("\n$").toByteArray()
                                    }
                                } else {
                                    projectFile.readBytes()
                                }
                            )
                        } catch (e: IOException) {
                            e.printStackTrace()
                            RResult.Err(FileError.Other(e.message))
                        }
                    }
                }
            }

            else -> {
                // Probably add some built-in files
                RResult.Err(FileError.Package(PackageError.NotFound(file.packageSpec!!)))
            }
        }
    }

    fun scheduleRecompile(file: VirtualFile, notify: TypstPreviewFileEditor, mode: SyntaxMode) {
        val added = scheduled.add(file)
        if (!added) {
            reschedule.add(file)
            return
        }
        ApplicationManager.getApplication().executeOnPooledThread {

            val currentMain =
                FileDescriptor(null, File.separator + Path.of(project.basePath).relativize(file.toNioPath()).toString())
            val result = withCompilerRuntime {
                fileContext {
                    resolveFile(currentMain, mode, it).map { Base64Bytes(it) }
                }.use { fsContext ->
                    compileSvgRaw(
                        fsContext, currentMain, stdlib = library,
                        now = Now.System
                    )
                }
            }
            val warnings = result.warnings

            val errors = when (val output = result.output) {
                is RResult.Ok -> {
                    ApplicationManager.getApplication().invokeLater {
                        notify.RENDERER.reload(output.value)
                        notify.component.repaint()
                    }
                    listOf()
                }

                is RResult.Err -> output.error // TypstCompilerException(result.error).printStackTrace()
            }
            compiled.compute(file) { _, map ->
                val map = map ?: ConcurrentHashMap()
                map.put(project, CompiledDoc(errors, warnings))
                map
            }
            scheduled.remove(file)
            if (reschedule.remove(file)) {
                scheduleRecompile(file, notify, mode)
            } else {
                withCompilerRuntime { evictCache(2) }
            }
        }
    }

    override fun dispose() {
//        compiler.close() TODO Ensure safety
        compiled.values.forEach { it.remove(project) }
    }

    fun colorsInput(): TDict<TColor> {
        return TDict(
            mapOf(
                "kvasir-preview-background" to defaultScheme.defaultBackground.t,
                "kvasir-preview-foreground" to defaultScheme.defaultForeground.t,
            )
        )
    }

    companion object {
        fun getInstance(project: Project): ProjectCompilerService {
            return project.getService<ProjectCompilerService>(ProjectCompilerService::class.java)
        }
    }
}
