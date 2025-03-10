package org.ldemetrios.kvasir.preview.data

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditorWithPreview
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectLocator
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileEvent
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.readBytes
import org.ldemetrios.instance
import org.ldemetrios.kvasir.highlight.defaultScheme
import org.ldemetrios.kvasir.preview.ui.TypstPreviewFileEditor
import org.ldemetrios.kvasir.util.*
import org.ldemetrios.tyko.compiler.*
import org.ldemetrios.tyko.model.TDictionary
import org.ldemetrios.tyko.model.TValue
import org.ldemetrios.tyko.model.t
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
class ProjectCompilerService(val project: Project) : World, Disposable {
    val compiler = instance?.let { WorldBasedTypstCompiler(it, this) }

    private var currentMain: String? = null
    private val lock = ReentrantLock()
    private lateinit var mode: SyntaxMode

    private val edited = ConcurrentHashMap<String, Document>()
    fun registerDoc(path: String, doc: Document) = edited.put(path, doc)
    fun unregisterDoc(path: String) = edited.remove(path)

    private val scheduled = ConcurrentHashSet<VirtualFile>()

    fun scheduleRecompile(file: VirtualFile, notify: TypstPreviewFileEditor, mode: SyntaxMode) {
        val added = scheduled.add(file)
        if (!added) return
        ApplicationManager.getApplication().executeOnPooledThread {
            val result = lock.withLock {
                this.mode = mode
                compiler?.reset()
                currentMain = "/" + Path.of(project.basePath).relativize(file.toNioPath()).toString()
                compiler?.compileSvgRaw(0, Int.MAX_VALUE)
            } ?: return@executeOnPooledThread
            val warnings = result.warnings

            val errors = when (val output = result.output) {
                is RResult.Ok -> {
                    notify.RENDERER.reload(output.value)
                    notify.component.repaint()
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
        }
    }

    override fun dispose() {
//        compiler.close() TODO Ensure safety
        compiled.values.forEach { it.remove(project) }
    }


    override fun file(file: FileDescriptor): RResult<ByteArray, FileError> {
        return when (file.pack?.namespace) {
            null -> {
                // File in project
                val cached = edited[file.path]
                if (cached != null) {
                    val text = cached.text
                    RResult.Ok(
                        if (file == this.mainFile()) {
                            when (mode) {
                                SyntaxMode.Markup -> text
                                SyntaxMode.Code -> "#{\n" + text + "\n}"
                                SyntaxMode.Math -> "$\n" + text + "\n$"
                            }
                        } else {
                            text
                        }.toByteArray()
                    )
                } else {
                    val absolutePath: String = project.basePath + file.path
                    val projectFile = LocalFileSystem.getInstance().findFileByPath(absolutePath)
                    when {
                        projectFile == null || !projectFile.exists() -> {
                            RResult.Err(FileError.NotFound(file.path))
                        }

                        projectFile.isDirectory -> {
                            RResult.Err(FileError.IsDirectory)
                        }

                        else -> try {
                            val text = projectFile.readBytes()
                            RResult.Ok(
                                if (file == this.mainFile()) {
                                    when (mode) {
                                        SyntaxMode.Markup -> text
                                        SyntaxMode.Code -> CODE_PREFIX_BARR + text + CODE_SUFFIX_BARR
                                        SyntaxMode.Math -> MATH_PREFIX_BARR + text + MATH_SUFFIX_BARR
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

            "preview" -> {
                packageFromCentral(file)
            }

            else -> {
                // Probably add some built-in files
                RResult.Err(FileError.Package(PackageError.NotFound(file.pack!!)))
            }
        }
    }

    override fun library(): StdlibProvider = object : StdlibProvider.Standard {
        override val inputs: TDictionary<TValue>
            get() = TDictionary(
                "kvasir-preview-background" to defaultScheme.defaultBackground.t,
                "kvasir-preview-foreground" to defaultScheme.defaultForeground.t,
            )
        override val features: List<Feature> get() = listOf(Feature.Html)
    }

    override fun mainFile(): FileDescriptor {
        return FileDescriptor(null, currentMain ?: "___________invalid")
    }

    override fun now(): WorldTime = WorldTime.System

    override val autoManageCentral: Boolean get() = true

    companion object {
        fun getInstance(project: Project): ProjectCompilerService {
            return project.getService<ProjectCompilerService>(ProjectCompilerService::class.java)
        }
    }
}