package org.ldemetrios.kvasir.preview.watch

import org.ldemetrios.kvasir.util.removePrefixOrThrow
import org.ldemetrios.kvasir.util.removeSuffixOrThrow
import java.io.File


/*
Upon success:
watching main.typ
writing to main.pdf

[18:16:58] compiling ...
watching main.typ
writing to main.pdf

[18:16:58] compiled successfully in 1.90ms
*/
/*
Upon failure:
watching main.typ
writing to main.pdf

[18:24:30] compiling ...
watching main.typ
writing to main.pdf

[18:24:30] compiled with errors

error: number must be at least zero
  ┌─ main.typ:1:18
  │
1 │ #let g(x) = lorem(x)
  │                   ^

help: error occurred in this call of function `g`
  ┌─ main.typ:3:12
  │
3 │ #let f(x) = g(x)
  │             ^^^^

help: error occurred in this call of function `f`
  ┌─ main.typ:5:1
  │
5 │   #f(
  │ ╭──^
6 │ │ -1
7 │ │ )
  │ ╰─^
 */

enum class ErrorSeverity {
    INFO, WARNING, ERROR
}

class TypstStacktrace(val severity: ErrorSeverity, val entries: List<TraceEntry>) {
    fun format(): String {
        val sb = StringBuilder()
        for (entry in entries) {
            sb.append("  at ${entry.file}:${entry.startLine + 1}:${entry.startColumn + 1}...${entry.endLine + 1}:${entry.endColumn + 1}\n")
        }
        return sb.toString()
    }

    fun extend(e: Throwable): Throwable {
        val stacktrace = e.stackTrace.asList().asReversed().toMutableList()
        for (entry in entries.asReversed()) {

            val new = StackTraceElement(
                entry.file,
                "",
                File(entry.file).nameWithoutExtension + ".typ",
                entry.startLine
            )
            stacktrace.add(new)
        }
        e.stackTrace = stacktrace.asReversed().toTypedArray()
        return e
    }
}

data class TraceEntry(
    val severity: ErrorSeverity,
    val file: String,
    val startLine: Int,
    val startColumn: Int,
    val endLine: Int,
    val endColumn: Int,
    val message: String
)

fun String.severity(): ErrorSeverity? = when {
    this.startsWith("error") -> ErrorSeverity.ERROR
    this.startsWith("warning") -> ErrorSeverity.WARNING
    this.startsWith("help") -> ErrorSeverity.INFO
    else -> null
}

fun parseTypstStacktrace(input: File, root: File, log: List<String>): List<TypstStacktrace> {
    val relative = root.toPath().relativize(input.toPath()).toString()

    val starts = mutableListOf<Pair<ErrorSeverity, Int>>()
    for ((i, line) in log.withIndex()) {
        val severity = line.severity()
        if (severity != null) starts.add(severity to i)
    }
    starts.add(ErrorSeverity.INFO to log.size)
    val chunks = (0 until starts.size - 1).map { i ->
        starts[i].first to log.subList(starts[i].second, starts[i + 1].second)
    }
        .map { it.first to it.second.filter(String::isNotBlank) }
        .filter { it.second.isNotEmpty() }

    if (chunks.isEmpty()) throw AssertionError("Empty??")

    val traceStarts = chunks.indices.filter { chunks[it].first != ErrorSeverity.INFO } + chunks.size
    val traces = (0 until traceStarts.size - 1).map { i ->
        chunks.subList(traceStarts[i], traceStarts[i + 1])
    }

    val pathPrefix =
        traces.firstOrNull { it.first().first == ErrorSeverity.ERROR }?.let { findPrefix(relative, it[0].second) }

    return traces.map {
        val part = it.map { parseTraceEntry(pathPrefix, input.relativeTo(root).path, it.second) }
        TypstStacktrace(part.first().severity, part)
    }
}

fun findPrefix(relative: String, part: List<String>): String {
    val declaration = part[1].dropWhile { it in " ┌─" }.split(":")[0]
    return declaration.removeSuffixOrThrow(relative)
}

fun parseTraceEntry(pathPrefix: String?, realFile: String, part: List<String>): TraceEntry {
    val (severity, message) = part[0].split(":", limit = 2).map { it.trim() }
    if (severity == "warning") return TraceEntry(ErrorSeverity.WARNING, realFile, 0, 0, 0, 0, message)

    val (file, line, char) = part[1].dropWhile { it in " ┌─" }.split(":").also { require(it.size == 3) }

    val last = Regex(" *[\\│|] ( *\\^*) *").matchEntire(part.last())
    if (/*oneline*/ last != null) {
        val endColumn = last.groupValues[1].length
        return TraceEntry(
            severity = parseSeverity(severity),
            file = file.removePrefixOrThrow(pathPrefix!!),
            startLine = line.toInt() - 1,
            startColumn = char.toInt(),
            endLine = line.toInt() - 1,
            endColumn = endColumn,
            message
        )
    } else {
        val penultimate = part[part.size - 2]
        val last = Regex(" *[\\│|] ╰─(-*\\^)").matchEntire(part.last())
        return TraceEntry(
            severity = parseSeverity(severity),
            file = file.removePrefixOrThrow(pathPrefix!!),
            startLine = line.toInt() - 1,
            startColumn = char.toInt(),
            endLine = penultimate.split(" ").filter { it.isNotBlank() }[0].toInt() - 1,
            endColumn = last!!.groupValues[1].length,
            message
        )
    }
}

fun parseSeverity(severity: String): ErrorSeverity {
    return when (severity) {
        "help" -> ErrorSeverity.INFO
        "warning" -> ErrorSeverity.WARNING
        "error" -> ErrorSeverity.ERROR
        else -> throw AssertionError(severity)
    }
}
