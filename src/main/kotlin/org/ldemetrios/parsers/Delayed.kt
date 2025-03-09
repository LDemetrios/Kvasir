package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either

data class Delayed<C, T>(val underlying: () -> Parser<C, T>, val name: String? = null) :
    NonSequentialParser<C, T> {
    override fun parse(content: List<C>): Either<ErrorLevel, Result<T, C>> = underlying().parse(content)
    override fun named(name: String?) = Delayed(underlying, name)
    override fun toString(): String = name ?: "Delayed(${underlying})"
    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<T, C>> =
        underlying().trace(content, tracer)
}

