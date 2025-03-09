package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either

data class Choice<C, out T>(
    val choice: List<Parser<C, T>>,
    val name: String? = null,
    val keepGreediness: Boolean = true
) : NonSequentialParser<C, T> {
    override fun named(name: String?) = Choice(choice, name, keepGreediness)

    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<T, C>> = tracer.scope {
        toSummary { this@Choice.toString() }
        for (variant in choice) {
            val result: Either<ErrorLevel, Result<T, C>> = variant.trace(content, tracer)
            if (result is Either.Right) {
                mark(succ)
                toSummary { ", found ${result.right().parsed.format()}"}
                return@scope result
            }
            if (result.left() == ErrorLevel.Malformed) {
                mark(fail)
                line {"$info Malformed element"}
                return@scope if (keepGreediness) malformed() else unpresent()
            }
        }
        line {"$info Ran out of variants"}
        mark(fail)
        unpresent()
    }

    override fun toString(): String = name ?: "Choice(${if (keepGreediness) "kg" else "!kg"})$choice"
}


