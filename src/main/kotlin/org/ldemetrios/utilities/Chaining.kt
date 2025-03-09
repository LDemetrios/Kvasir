@file:Suppress("unused")

package org.ldemetrios.utilities

operator fun <T, R> ((T) -> R).unaryPlus(): T.() -> R = { this@unaryPlus(this) }
operator fun <T, R> (T.() -> R).unaryMinus(): (T) -> R = { it.this() }

@Suppress("USELESS_CAST")
inline fun <reified R> Any?.cast(): R = if (this is R) this as R else throw ClassCastException("Cannot cast $this to ${R::class.java.simpleName}")
@Suppress("UNCHECKED_CAST")
fun <R> Any?.castUnchecked(): R = this as R
inline fun <reified R> Any?.castOrNull(): R? = if (this is R) this else null

fun <T> T.alsoPrint(): T {
    print(this)
    return this
}

fun <T> T.alsoPrintln(): T {
    println(this)
    return this
}

infix fun <A, B, C> ((A) -> B).then(other: (B) -> C): (A) -> C = { other(this(it)) }

fun <T, A : T, B : T> A.applyIf(boolean: Boolean, func: (A) -> B): T =
    if (boolean) func(this) else this
