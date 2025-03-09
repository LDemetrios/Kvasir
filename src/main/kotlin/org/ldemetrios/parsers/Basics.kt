package org.ldemetrios.parsers

import org.ldemetrios.utilities.*

data class Result<out T, out C>(val parsed: T, val rest: List<C>) {
    override fun toString(): String = "$parsed | $rest"
}

sealed interface Parser<C, out T> {
    fun parse(content: List<C>): Either<ErrorLevel, Result<T, C>> = trace(content, SuppressingTracer)
    infix fun named(name: String?): Parser<C, T>
    fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<T, C>>
}

sealed interface MeaningfulParser<C, out T> : Parser<C, T>
open interface NonSequentialParser<C, out T> : MeaningfulParser<C, T>

enum class ErrorLevel {
    Malformed, Unpresent
}

fun <T, C, E> success(parsed: T, rest: List<C>): Either<E, Result<T, C>> = Either.Right(Result(parsed, rest))
fun <R> unpresent(): Either<ErrorLevel, R> = Either.Left(ErrorLevel.Unpresent)
fun <R> malformed(): Either<ErrorLevel, R> = Either.Left(ErrorLevel.Malformed)

const val fail = "-"
const val succ = "+"
const val info = "*"


