package org.ldemetrios.kvasir.preview.watch

import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI.CurrentTheme
import org.ldemetrios.kvasir.util.InputStreamNBReader
import java.awt.Color
import java.io.Closeable
import java.io.File
import java.io.IOException
import kotlin.io.path.createTempDirectory


data class Switchable<T>(val a: T, val b: T, var selected: Boolean = true) {
    val current: T get() = if (selected) a else b
    internal val waiting: T get() = if (selected) b else a

    fun switch() {
        selected = !selected
    }
}

fun colorAsTypst(c: Color) = listOf(
    c.red, c.green, c.blue, c.alpha
).joinToString("", "#") { it.toString(16).run { "0".repeat(2 - length) + this } }

val foregroundColorAsTypst get() = colorAsTypst(CurrentTheme.List.FOREGROUND)
val backgroundColorAsTypst get() = colorAsTypst(CurrentTheme.List.BACKGROUND)

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

    private val watchProcess = try {
        ProcessBuilder(
            "typst", "watch",
            "--root", root.absolutePath,
            input.absolutePath,
            "$closed/out-{n}.svg",
            "--format", "svg",
            "--input", "kvasir-preview-background=$backgroundColorAsTypst",
            "--input", "kvasir-preview-foreground=$foregroundColorAsTypst",
        ).start()
    } catch (e: IOException) {
        null
    }

    val svgFolder get() = File(opened.current)

    val pageNumber get() = svgFolder.listFiles()?.size ?: 0

    val svgPages get() = svgFolder.listFiles()?.asList()?.sortedBy { it.name } ?: emptyList()

    @Volatile
    var lastCompilationReport: CompilationReport? = null
        private set(value) {
//            value?.trace?.firstOrNull()?.extend(RuntimeException())?.printStackTrace()
            field = value
        }

    private val pendingReport: MutableList<String> = mutableListOf()

    private val stream = watchProcess?.let { InputStreamNBReader(it.errorStream) }

    private var pendingSucceeded: State? = null

    fun update() {
        if (stream == null) return
        val changes = stream.readAvailable {
//            println("line: $it")
            if (it.isBlank()) return@readAvailable

            when {
                it.matches(filler) -> {
//                    println("Filler, pendingReport is " + (if (pendingReport.isEmpty()) "empty" else "not empty, reparsing, success: $pendingSucceeded"))
                    if (pendingReport.isNotEmpty()) {
                        println("Report\n" + pendingReport.joinToString("\n") { "\t$it" })
                        reparseReport()
                    }
                }
                it.matches(compiledSuccessfully) -> {
                    println("Compiled successfully")
                    pendingSucceeded = State.Success
                    pendingReport.add(it)
                }
                it.matches(compiledWithErrors) -> {
                    println("Compiled with errors")
                    pendingSucceeded = State.Error
                    pendingReport.add(it)
                }
                it.matches(compiledWithWarnings) -> {
                    println("Compiled with warnings")
                    pendingSucceeded = State.Warnings
                    pendingReport.add(it)
                }
                else -> {
//                    println("Just adding")
                    pendingReport.add(it)
                }
            }
        }

        if (changes && pendingSucceeded != null && pendingReport.isNotEmpty()) {
//            println("Finished reading, changes present, success: $pendingSucceeded")
//            println("Report\n" + pendingReport.joinToString("\n") { "\t$it" })

            val success = pendingSucceeded
            reparseReport()
            when (success) {
                State.Success, State.Warnings -> onSuccess.forEach { it(this) }
                State.Error -> onError.forEach { it(this) }
                null -> {}
            }
        }
    }

    private fun reparseReport() {
        try {
            val pendingSucceeded = pendingSucceeded
            val report = pendingSucceeded!!.let {
                CompilationReport(
                    input,
                    root,
                    if (it == State.Success) null else parseTypstStacktrace(input, root, pendingReport),
                    it
                )
            }
            if (pendingSucceeded != State.Error) {
                val switch = File(opened.current).listFiles()!!.isNotEmpty()
//            println("Reloading $input, switch: $switch")
                val cl = File(closed)
                val to = if (switch) File(opened.waiting) else File(opened.current)
                for (page in cl.listFiles()!!) {
                    page.renameTo(to.resolve(page.relativeTo(cl)))
                }
                if (switch) opened.switch()
                File(opened.waiting).listFiles()!!.forEach(File::delete)
            }
            lastCompilationReport = report
        } finally {
            pendingReport.clear()
            pendingSucceeded = null
        }
    }

    override fun close() {
        watchProcess?.destroy()
        File(opened.waiting).deleteRecursively()
        File(opened.waiting).deleteRecursively()
        File(closed).deleteRecursively()
    }
}

val filler = Regex("watching .*|writing to .*|\\[..:..:..] compiling \\.\\.\\.")
val compiledSuccessfully = Regex("\\[..:..:..] compiled successfully in .*")
val compiledWithErrors = Regex("\\[..:..:..] compiled with errors")
val compiledWithWarnings = Regex("\\[..:..:..] compiled with warnings in .*")

enum class State { Success, Warnings, Error }

data class CompilationReport(
    val input: File,
    val root: File,
    val trace: List<TypstStacktrace>?,
    val success: State
)
