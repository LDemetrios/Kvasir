package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either
import org.ldemetrios.utilities.Tuple

data class Sequential<C, T, out Tup : Tuple<T>>(
    val parsers: List<Parser<C, *>>, val composer: (List<T>) -> Tup, val name: String? = null
) : MeaningfulParser<C, Tup> {
    override fun named(name: String?) = Sequential(parsers, composer, name)

    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<Tup, C>> = tracer.scope {
        toSummary { this@Sequential.toString() }
        val results = mutableListOf<Any?>()
        var wasCut = false
        var rest = content
        for (parser in parsers) {
            val result = parser.trace(rest, tracer)
            if (result.isLeft()) {
                mark(fail)
                if (wasCut) {
                    line { "$info `!` passed, element is malformed" }
                    return@scope malformed()
                } else {
                    line { "$info `!` not passed, element is absent" }
                    return@scope unpresent()
                }
            }
            val (value, newRest) = result.right()
            when (parser) {
                is CutParserT<*> -> wasCut = true
                is SkipParser<*, *> -> Unit
                else -> results.add(value)
            }
            rest = newRest
        }
        toSummary { ", found ${results.format()}" }
        mark(succ)
        success(composer(results as List<T>), rest)
    }

    override fun toString(): String = name ?: "Sequential$parsers"
}
