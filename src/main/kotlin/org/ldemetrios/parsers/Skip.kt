package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either

data class SkipParser<C, T>(val underlying: Parser<C, T>, val name: String? = null, val traceSubTree: Boolean = true) :
    Parser<C, T> {
    override fun parse(content: List<C>): Either<ErrorLevel, Result<T, C>> = underlying.parse(content)
    override fun named(name: String?) = SkipParser(underlying, name, traceSubTree)
    override fun toString(): String = name ?: "-$underlying"
    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<T, C>> {
        return tracer.scope {
            toSummary { "(Skipping) $underlying" }
            val res = if (traceSubTree) underlying.trace(content, tracer) else underlying.parse(content)
            if (res.isRight()) mark(succ) else mark(fail)
            res
        }
    }
}


