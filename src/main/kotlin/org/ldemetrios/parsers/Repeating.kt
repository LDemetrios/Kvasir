package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either

data class Repeating<C, T>(
    val underlying: Parser<C, T>,
    val from: Long = 0,
    val to: Long = Long.MAX_VALUE,
    val name: String? = null,
    val ifLess: ErrorLevel? = null,
) : NonSequentialParser<C, List<T>> {
    init {
        require(from >= 0) { "from must be greater or equal to 0" }
        require(to >= from) { "to must be greater or equal to from" }
    }

    override fun named(name: String?) = Repeating(underlying, from, to, name)

    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<List<T>, C>> = tracer.scope {
        toSummary { this@Repeating.toString() }
        var rest = content
        val successes = mutableListOf<T>() // TODO change to long list
        var size = 0L
        while (true) {
            if (size == to) {
                line { "$info limit ($to) reached" }
                mark(succ)
                toSummary { ", found $size elements" }
                return@scope success(successes, rest)
            }
            val next = underlying.trace(rest, tracer)
            if (next.isLeft()) {
                toSummary { ", found $size elements" }
                return@scope if (successes.size >= from) {
                    mark(succ)
                    line { "$succ $size in [$from, $to] elements already found" }
                    success(successes, rest)
                } else {
                    mark(fail)
                    line { "$fail $size !in [$from, $to] elements already found" }
                    Either.Left(ifLess ?: next.left())
                }
            }
            val (value, newRest) = next.right()
            successes.add(value)
            size++
            rest = newRest
        }
        throw AssertionError()
    }

    override fun toString() = name ?: "Repeating[$from, ${if (to == 9223372036854775807L) "inf" else to}]($underlying)"
}
