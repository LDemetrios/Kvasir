package org.ldemetrios.parsers

import org.ldemetrios.utilities.*

data class Transforming<C, T, R>(
    val underlying: Parser<C, T>,
    val transformer: ((T) -> R)? = null,
    val verifier: ((R) -> Boolean)? = null,
    val greedy: Boolean = false,
    val name: String? = null,
) : NonSequentialParser<C, R> {
    override fun named(name: String?) = Transforming(underlying, transformer, verifier, greedy, name)

    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<R, C>> = tracer.scope {
        toSummary { this@Transforming.toString() }
        val result = underlying.trace(content, tracer)
        if (result.isLeft()) {
            mark(fail)
            return@scope Either.Left(result.left())
        }
        val (value, rest) = result.right()
        val transformed = if (transformer != null) {
            val it = transformer.invoke(value)
            line { "$info transformed to ${it.format()}" }
            it
        } else value as R
        if (verifier != null) {
            val verified = verifier.invoke(transformed)
            if (verified) {
                mark(succ)
                line { "$succ Passed verification $verifier" }
                return@scope success(transformed, rest)
            } else {
                mark(fail)
                line { "$fail Failed verification $verifier" }
                return@scope if (greedy) malformed() else unpresent()
            }
        } else {
            mark(succ)
            toSummary { ", found $transformed" }
            return@scope success(transformed, rest)
        }
    }

    override fun toString(): String =
        name
            ?: "Transforming($underlying, ${transformer?.let { "$it, " } ?: ""}${verifier?.let { "$it, " } ?: ""}${if (greedy) "" else "non-"}greedy)"
}

infix fun <C, T, R> Parser<C, T>.use(func: T.() -> R) = Transforming(
    this, func, null, greedy = false
)

infix fun <C, T, R> Parser<C, T>.with(func: (T) -> R) = Transforming(
    this, func, null, greedy = false
)

infix fun <C, T, R> Parser<C, T>.useNN(func: T.() -> R?) = Transforming(
    this, func, { it != null }, greedy = false
)

infix fun <C, T, R> Parser<C, T>.withNN(func: (T) -> R?) = Transforming(
    this, func, { it != null }, greedy = false
)

infix fun <C, T> Parser<C, T>.onlyIf(predicate: (T) -> Boolean) = Transforming<C, T, T>(
    this, null, predicate, greedy = false
)

infix fun <C, T> Parser<C, T>.requiring(predicate: (T) -> Boolean) = Transforming<C, T, T>(
    this, null, predicate, greedy = true
)

fun <C, T, R> Parser<C, T>.use(func: T.() -> R, greedy: Boolean) = Transforming(
    this, func, null, greedy = greedy
)

fun <C, T, R> Parser<C, T>.with(func: (T) -> R, greedy: Boolean) = Transforming(
    this, func, null, greedy = greedy
)

fun <C, T, R> Parser<C, T>.useNN(func: T.() -> R?, greedy: Boolean) = Transforming(
    this, func, { it != null }, greedy = greedy
)

fun <C, T, R> Parser<C, T>.withNN(func: (T) -> R?, greedy: Boolean) = Transforming(
    this, func, { it != null }, greedy = greedy
)


fun <C, T, R> Parser<C, T>.useNamed(name: String, func: T.() -> R) = use(named(name, func))
fun <C, T, R> Parser<C, T>.withNamed(name: String, func: (T) -> R) = with(named(name, func))
fun <C, T, R> Parser<C, T>.useNNNamed(name: String, func: T.() -> R?) = useNN(named(name, func))
fun <C, T, R> Parser<C, T>.withNNNamed(name: String, func: (T) -> R?) = withNN(named(name, func))
fun <C, T> Parser<C, T>.onlyIfNamed(name: String, predicate: (T) -> Boolean) = onlyIf(named(name, predicate))
fun <C, T> Parser<C, T>.requiringNamed(name: String, predicate: (T) -> Boolean) = requiring(named(name, predicate))
fun <C, T, R> Parser<C, T>.useNamed(name: String, func: T.() -> R, greedy: Boolean) = use(named(name, func), greedy)
fun <C, T, R> Parser<C, T>.withNamed(name: String, func: (T) -> R, greedy: Boolean) = with(named(name, func), greedy)
fun <C, T, R> Parser<C, T>.useNNNamed(name: String, func: T.() -> R?, greedy: Boolean) =
    useNN(named(name, func), greedy)

fun <C, T, R> Parser<C, T>.withNNNamed(name: String, func: (T) -> R?, greedy: Boolean) =
    withNN(named(name, func), greedy)