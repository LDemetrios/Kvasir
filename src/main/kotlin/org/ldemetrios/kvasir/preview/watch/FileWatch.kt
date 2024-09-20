package org.ldemetrios.kvasir.preview.watch

import org.ldemetrios.kvasir.util.InputStreamNBReader
import java.io.Closeable
import java.io.File
import kotlin.io.path.createTempDirectory


data class Switchable<T>(val a: T, val b: T, var selected: Boolean = true) {
    val current: T get() = if (selected) a else b
    internal val waiting: T get() = if (selected) b else a

    fun switch() {
        selected = !selected
    }
}

class FileWatch(private val input: File, private val root: File) : Closeable {
    private val onSuccess = mutableListOf<(FileWatch) -> Unit>()
    private val onError = mutableListOf<(FileWatch) -> Unit>()

    fun onSuccessDo(action: (FileWatch) -> Unit) {
        onSuccess.add(action)
    }
    fun onErrorDo(action: (FileWatch) -> Unit) {
        onError.add(action)
    }

    private val closed: String = createTempDirectory(prefix = "kvasir-preview-" + input.nameWithoutExtension).toString()

    private val opened = Switchable(
        createTempDirectory(prefix = "kvasir-preview-" + input.nameWithoutExtension).toString(),
        createTempDirectory(prefix = "kvasir-preview-" + input.nameWithoutExtension).toString(),
    )

    private val watchProcess = ProcessBuilder(
        "typst", "watch",
        "--root", root.absolutePath,
        input.absolutePath,
        "$closed/out-{n}.svg",
        "--format", "svg"
    ).start()

    val svgFolder get() = File(opened.current)

    val pageNumber get() = svgFolder.listFiles()?.size ?: 0

    val svgPages get() = svgFolder.listFiles()?.asList()?.sortedBy { it.name } ?: emptyList()

    @Volatile
    var lastCompilationReport: CompilationReport? = null
        private set(value) {
            value?.trace?.firstOrNull()?.extend(RuntimeException())?.printStackTrace()
            field = value
        }

    private val pendingReport: MutableList<String> = mutableListOf()

    private val stream = InputStreamNBReader(watchProcess.errorStream)

    private var pendingSucceeded: Boolean? = null

    fun update() {
        val changes = stream.readAvailable {
            println("line: $it")
            if (it.isBlank()) return@readAvailable

            when {
                it.matches(filler) -> {
                    println("Filler, pendingReport is " + (if (pendingReport.isEmpty()) "empty" else "not empty, reparsing, success: $pendingSucceeded"))
                    if (pendingReport.isNotEmpty()) {
                        println("Report\n" + pendingReport.joinToString("\n") { "\t$it" })
                        reparseReport()
                    }
                }
                it.matches(compiledSuccessfully) -> {
                    println("Compiled successfully")
                    pendingSucceeded = true
                    pendingReport.add(it)
                }
                it.matches(compiledWithErrors) -> {
                    println("Compiled with errors")
                    pendingSucceeded = false
                    pendingReport.add(it)
                }
                else -> {
                    println("Just adding")
                    pendingReport.add(it)
                }
            }
        }

        if (changes && pendingSucceeded != null && pendingReport.isNotEmpty()) {
            println("Finished reading, changes present, success: $pendingSucceeded")
            println("Report\n" + pendingReport.joinToString("\n") { "\t$it" })

            val success = pendingSucceeded
            reparseReport()
            when(success){
                true -> onSuccess.forEach { it(this) }
                false -> onError.forEach { it(this) }
                null -> {}
            }
        }
    }

    private fun reparseReport() {
        val report = pendingSucceeded!!.let {
            CompilationReport(
                input,
                root,
                if (it) null else parseTypstStacktrace(input, root, pendingReport),
                it
            )
        }
        if (pendingSucceeded == true) {
            val switch = File(opened.current).listFiles()!!.isNotEmpty()
            println("Reloading $input, switch: $switch")
            val cl = File(closed)
            val to = if (switch) File(opened.waiting) else File(opened.current)
            for (page in cl.listFiles()!!) {
                page.renameTo(to.resolve(page.relativeTo(cl)))
            }
            if (switch) opened.switch()
        }
        pendingReport.clear()
        lastCompilationReport = report
        pendingSucceeded = null
    }

    private fun printState() {
        println("lastSucceeded: $pendingSucceeded\npendingReport:\n" + pendingReport.joinToString("\n") { "\t$it" } + "\n")
    }

    override fun close() {
        watchProcess.destroy()
        File(opened.waiting).deleteRecursively()
        File(opened.waiting).deleteRecursively()
        File(closed).deleteRecursively()
    }
}

val filler = Regex("watching .*|writing to .*|\\[..:..:..] compiling \\.\\.\\.")
val compiledSuccessfully = Regex("\\[..:..:..] compiled successfully in .*")
val compiledWithErrors = Regex("\\[..:..:..] compiled with errors")


data class CompilationReport(
    val input: File,
    val root: File,
    val trace: List<TypstStacktrace>?,
    val success: Boolean
)
