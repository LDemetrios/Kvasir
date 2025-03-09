package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either
import org.ldemetrios.utilities.Lines

data class NothingParser<Ch, T>(val result: T) : NonSequentialParser<Ch, T> {
    override fun parse(content: List<Ch>): Either<Nothing, Result<T, Ch>> = success(result, content)

    override fun named(name: String?) = this

    override fun trace(content: List<Ch>, tracer: Tracer): Either<ErrorLevel, Result<T, Ch>> {
        tracer.line { "$succ Nothing parsed, ${result.format()} returned" }
        return parse(content)
    }

    override fun toString(): String = "Nothing(${result.format()})"
}