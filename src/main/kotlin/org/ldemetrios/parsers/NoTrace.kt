package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either

data class NoTrace<C, T>(val underlying: Parser<C, T>, val name: String? = null) :
    NonSequentialParser<C, T> {
    override fun parse(content: List<C>): Either<ErrorLevel, Result<T, C>> = underlying.parse(content)
    override fun named(name: String?) = SkipParser(underlying, name)
    override fun toString(): String = name ?: underlying.toString()
    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<T, C>> {
        val result = underlying.parse(content)
        if (result.isRight()) {
            tracer.line { "$succ $underlying: ${result.right().parsed.format()}" }
        } else {
            tracer.line { "$fail $underlying" }
        }
        return result
    }
}
