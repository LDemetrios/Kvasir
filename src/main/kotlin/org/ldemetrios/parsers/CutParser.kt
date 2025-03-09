package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either
import org.ldemetrios.utilities.Lines

val CutParser = CutParserT<Any?>()

class CutParserT<in C> : Parser<@UnsafeVariance C, Nothing?> {
    override fun parse(content: List<C>): Either<Nothing, Result<Nothing?, @UnsafeVariance C>> = success(null, content)

    override fun named(name: String?) = this

    override fun equals(other: Any?): Boolean = other is CutParserT<*>

    override fun hashCode(): Int = 566

    override fun toString(): String = "!"

    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<Nothing?, @UnsafeVariance  C>> {
        tracer.line { "!" }
        return parse(content)
    }
}

object Cut
