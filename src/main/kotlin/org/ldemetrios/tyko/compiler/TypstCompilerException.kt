@file:JvmName("TypstCompilerExceptionKt")

package org.ldemetrios.tyko.compiler

class TypstCompilerException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}

fun TypstCompilerException(trace: List<SourceDiagnostic>, cause: Throwable? = null): TypstCompilerException {
    val exc = trace.map { TypstCompilerException(it, cause) }
    val res = exc.first()
    for (another in exc.drop(1)) res.addSuppressed(another)
    return res
}

fun Span.toSTE(tracepoint: Tracepoint?) = StackTraceElement(
    file?.pack?.namespace,
    file?.pack?.name,
    file?.pack?.version.toString(),
    file?.path?.toString()?.drop(1) ?: "<detached>",
    when (tracepoint) {
        null -> "<source>"
        is Tracepoint.Call -> tracepoint.name ?: "<Tracepoint.Call>"
        Tracepoint.Import -> "<import>"
        is Tracepoint.Show -> tracepoint.name?.let { "show $it" } ?: "<Tracepoint.Show>"
    },
    file?.path?.toString()?.split("/")?.last() ?: "<detached>",
    startLine.toInt() + 1,
)

private fun TypstCompilerException(diagnostic: SourceDiagnostic, cause: Throwable? = null): TypstCompilerException {
    val exc = TypstCompilerException(diagnostic.message, cause)
    val stack = exc.stackTrace.asList().asReversed().dropLastWhile { it.className == "org.ldemetrios.tyko.compiler.TypstCompilerExceptionKt" }.toMutableList()

    for (idx in diagnostic.trace.indices.reversed()) {
        stack.add(diagnostic.trace[idx].span.toSTE(diagnostic.trace.getOrNull(idx + 1)?.v))
    }
    stack.add(diagnostic.span.toSTE(diagnostic.trace.firstOrNull()?.v))

    exc.stackTrace = stack.asReversed().toTypedArray()
    return exc
}