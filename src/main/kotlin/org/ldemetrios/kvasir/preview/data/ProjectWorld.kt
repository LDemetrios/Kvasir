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
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import org.ldemetrios.Pool
import org.ldemetrios.frontendPool
import org.ldemetrios.kvasir.highlight.defaultScheme
import org.ldemetrios.kvasir.preview.ui.TypstPreviewFileEditor
import org.ldemetrios.kvasir.settings.AppSettings
import org.ldemetrios.kvasir.util.ConcurrentHashSet
import org.ldemetrios.kvasir.util.extensions
import org.ldemetrios.tyko.compiler.*
import org.ldemetrios.tyko.driver.chicory_based.ChicoryTypstCore
import org.ldemetrios.tyko.model.TBytes
import org.ldemetrios.tyko.model.TColor
import org.ldemetrios.tyko.model.TDict
import org.ldemetrios.tyko.model.TInt
import org.ldemetrios.tyko.model.TJvmObject
import org.ldemetrios.tyko.model.TStr
import org.ldemetrios.tyko.model.TType
import org.ldemetrios.tyko.model.TValue
import org.ldemetrios.tyko.model.t
import org.ldemetrios.tyko.runtime.CreateSessionMode
import org.ldemetrios.tyko.runtime.LibrarySpecBuilder
import org.ldemetrios.tyko.runtime.TypstRuntime
import org.ldemetrios.tyko.runtime.buildWorldSpecification
import java.io.File
import java.io.IOException
import java.lang.constant.DirectMethodHandleDesc
import java.lang.invoke.MethodHandles
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

class KvasirVFSListener(/*private val project: Project*/val mode: SyntaxMode) : BulkFileListener {
    override fun after(events: MutableList<out VFileEvent>) {
        val changedFiles = events
            .filterIsInstance<VFileContentChangeEvent>()
            .map { it.file }
            .filter { it.extension in extensions }
            .distinct()

        if (changedFiles.isEmpty()) return

        //We'll need something more robust later
        val project = ProjectLocator.getInstance().guessProjectForFile(changedFiles.first())!!

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
    var errors: List<SourceDiagnostic>,
    var warnings: List<SourceDiagnostic>,
)

private val compiled = ConcurrentHashMap<VirtualFile, ConcurrentHashMap<Project, CompiledDoc>>()

fun getCompiled(file: VirtualFile) = compiled[file]?.toMap() ?: mapOf()

private class CompilerRuntimeHolder(val inputs: TDict<TValue>) {
    private val parentRuntime = TypstRuntime(buildWorldSpecification {
        fonts {
            includeSystem = false
            includeEmbedded = true
        }
        library {
            fresh(Feature.all())
            defineUpcall()
        }
    }) { ChicoryTypstCore() }

    var runtime: TypstRuntime? = null

    init {
        checkInputs(inputs)
    }

    private var rememberedInputs = inputs

    fun checkInputs(newInputs: TDict<TValue>) {
        if (newInputs != rememberedInputs) {
            runtime?.close()
            runtime = parentRuntime.inherit {
                library {
                    fromDefault()
                    inputs(newInputs)
                    defineUpcall()
                }
                fonts {
                    includeEmbedded = true
                }
            }
            rememberedInputs = newInputs
        }
    }
}

private fun LibrarySpecBuilder.defineUpcall() {
    func("define-class") {
        title = "Define Class"

        param("breakglass") {
            docs = "Break-glass ticket"
            input = NativeCastInfo.type("str")
            positional = false
            named = true
            required = false
        }

        param("name") {
            docs = "Class name"
            input = NativeCastInfo.type("str")
            positional = true
            named = false
            required = true
        }

        param("content") {
            docs = "Classfile bytes"
            input = NativeCastInfo.type("bytes")
            positional = true
            named = false
            required = true
        }

        returns(TType("jvm-object"))

        pureCatching {
            val name = it.positional[0] as TStr
            val content = it.positional[1] as TBytes
            val breakglass = it.named["breakglass"] as TStr?
            if (breakglass == null ||
                breakglass.value.isBlank() ||
                breakglass.value != AppSettings.instance.state.breakGlassTicket ||
                !AppSettings.instance.state.breakGlassTicketEnabled
            ) {
                throw AssertionError("Break-glass access is forbidden (incorrect or missing ticket)")
            }
            val clazz = BytesClassLoader.define(name.value, content.value)
            TJvmObject(clazz)
        }
    }

    func("lookup-in") {
        title = "Lookup in particular Class"

        param("breakglass") {
            docs = "Break-glass ticket"
            input = NativeCastInfo.type("str")
            positional = false
            named = true
            required = false
        }

        param("class") {
            docs = "The class"
            input = NativeCastInfo.type("jvm-object")
            positional = true
            named = false
            required = true
        }

        returns(TType("jvm-object"))

        pureCatching {
            val clazz = it.positional[0] as TJvmObject
            val breakglass = it.named["breakglass"] as TStr?
            if (breakglass == null || breakglass.value != AppSettings.instance.state.breakGlassTicket) {
                throw AssertionError("Break-glass access is forbidden (incorrect or missing ticket)")
            }
            val lookup = MethodHandles.privateLookupIn(clazz.value as Class<*>, MethodHandles.lookup())
            TJvmObject(lookup)
        }
    }

    func("upcall") {
        title = "Upcall"
        docs = """
                    Resolve and invoke a JVM member through method handles.
                    
                    Signature format: `owner#member:descriptor`
                    Examples:
                    - `java.lang.Integer#sum:(II)I`
                    - `java.awt.Point#x:I`
                    - `java.awt.Point#<init>:(II)V`
                    
                    Refkind values:
                    - `1` getter
                    - `2` static getter
                    - `3` setter
                    - `4` static setter
                    - `5` virtual
                    - `6` static
                    - `7` special
                    - `8` constructor
                    - `9` interface virtual
                """.trimIndent()
        keywords("java", "jvm", "ffi", "reflection", "method-handle")

        param("lookup") {
            docs = "Optional `MethodHandles.Lookup` to resolve the handle with."
            input = NativeCastInfo.type("jvm-object")
            positional = false
            named = true
            required = false
        }

        param("breakglass") {
            docs = "Break-glass ticket"
            input = NativeCastInfo.type("str")
            positional = false
            named = true
            required = false
        }
        param("refkind") {
            docs = "The JVM reference kind number."
            input = NativeCastInfo.type("int")
        }
        param("signature") {
            docs = "The JVM member signature in `owner#member:descriptor` format."
            input = NativeCastInfo.type("str")
        }

        param("args") {
            docs = "Arguments for the receiver/member invocation."
            input = NativeCastInfo.any()
            variadic = true
            required = false
        }

        returnsAny()

        pureCatching {
            val lookup = it.named["lookup"] as TJvmObject?
            val breakglass = it.named["breakglass"] as TStr?
            if (breakglass == null || breakglass.value != AppSettings.instance.state.breakGlassTicket) {
                throw AssertionError("Break-glass access is forbidden (incorrect or missing ticket)")
            }
            val refkind = it.positional[0] as TInt
            val signature = it.positional[1] as TStr
            val params = it.positional.drop(2)
            reflectiveCall(
                lookup?.value?.let { it as MethodHandles.Lookup } ?: MethodHandles.publicLookup(),
                signature.value,
                DirectMethodHandleDesc.Kind.valueOf(refkind.value.toInt()),
                params
            )
        }
    }
}

@Service(Service.Level.PROJECT)
class ProjectCompilerService(val project: Project) : Disposable {
    private val pool = Pool { CompilerRuntimeHolder(colorsInput()) }

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
                frontendPool.withResource(true, true) {
                    resolvePackage(file)
                }
            }
        }
    }

    fun scheduleRecompile(file: VirtualFile, notify: TypstPreviewFileEditor, mode: SyntaxMode) {
        if (AppSettings.instance.state.suppressPreview) return
        val added = scheduled.add(file)
        if (!added) {
            reschedule.add(file)
            return
        }
        ApplicationManager.getApplication().executeOnPooledThread {
            val currentMain =
                FileDescriptor(null, File.separator + Path.of(project.basePath).relativize(file.toNioPath()).toString())
            pool.withResource(true, true) {
                checkInputs(colorsInput())
                val fs = runtime!!.fileContextOf {
                    resolveFile(currentMain, mode, it)
                }
                val result = try {
                    runtime!!.compileSvg(
                        fs, currentMain,
                        sessionMode = if (
                            AppSettings.instance.state.breakGlassTicket.isBlank() ||
                            !AppSettings.instance.state.breakGlassTicketEnabled
                        ) CreateSessionMode.No else CreateSessionMode.Yes,
                    )
                } finally {
                    fs.release()
                }
                val warnings = result.warnings // cheap

                val errors = when (val output = result.output) {
                    is RResult.Ok -> {
                        ApplicationManager.getApplication().invokeLater { // on another thread
                            notify.RENDERER.reload(output.value)
                            notify.component.repaint()
                        }
                        listOf()
                    }

                    is RResult.Err -> output.error // TypstCompilerException(result.error).printStackTrace()
                }
                compiled.compute(file) { _, map -> // cheap
                    val map = map ?: ConcurrentHashMap()
                    map.put(project, CompiledDoc(errors, warnings))
                    map
                }
                scheduled.remove(file) // cheap
                if (reschedule.remove(file)) {
                    scheduleRecompile(file, notify, mode) // schedules to another thread
                } else {
                    runtime!!.evictCache(AppSettings.instance.state.cacheAge.toLong())
                }
            }
        }
    }

    override fun dispose() {
        compiled.values.forEach { it.remove(project) }
    }

    private var storedInputs = colorsInput()

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
