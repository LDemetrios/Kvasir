@file:Suppress("PackageDirectoryMismatch", "unused", "NAME_SHADOWING", "KotlinRedundantDiagnosticSuppress", "UNUSED_PARAMETER", "UNUSED_PARAMETER", "ObjectPropertyName", "NonAsciiCharacters", "RedundantLambdaArrow")

package org.ldemetrios.functional

fun <T> cons(x: T, list: Iterable<T>) = listOf(x) + list
fun <T> first(list: Iterable<T>) = list.first()
fun <T> next(list: Iterable<T>) = list.run { if (any()) this else null }?.drop(1)?.ifEmpty { null }
fun <T> rest(list: Iterable<T>) = list.drop(1)
fun <T> conj() = listOf<T>()
fun <T> conj(it: Iterable<T>) = it

fun <T> second(it: Iterable<T>): T? {
    val iter = it.iterator()
    if (!iter.hasNext()) return null
    iter.next()
    if (!iter.hasNext()) return null
    return iter.next()
}

fun <T> ffirst(it: Iterable<Iterable<T>>) = it.first().first()
fun <T> nfirst(it: Iterable<Iterable<T>>) = next(it.first())
fun <T> fnext(it: Iterable<Iterable<T>>) = next(it)?.first()
fun <T> nnext(it: Iterable<Iterable<T>>) = next(it)?.run { next(this) }

fun <C, T> instance7(c: Class<C>, x: T) = c.isInstance(x)

fun <T> char7(it: T) = it is Char
fun <T> string7(it: T) = it is String
fun <T> map7(it: T) = it is Map<*, *>

fun <T> last(it: Iterable<T>) = it.lastOrNull()
fun <T> butlast(it: Iterable<T>): T? {
    when (it) {
        is List -> return if (it.size < 2) null else it[it.size - 2]
        else -> {
            val iterator = it.iterator()
            if (!iterator.hasNext())
                return null
            var last = iterator.next()
            if (!iterator.hasNext())
                return null
            var butlast = last
            last = iterator.next()
            while (iterator.hasNext()) {
                butlast = last
                last = iterator.next()
            }
            return butlast
        }
    }
}

fun <T> nil7(it: T) = it == null
@Suppress("KotlinConstantConditions")
fun <T> false7(it: T) = it == false
@Suppress("KotlinConstantConditions")
fun <T> true7(it: T) = it == true
fun <T> boolean7(it: T) = it is Boolean
fun <T> some7(it: T) = it != null
fun <T> any7(it: T) = true
fun not(it: Boolean) = !it

@Suppress("UNCHECKED_CAST")
fun <T, U> identity() : (T) -> U = { it as U }

fun <T, U, R> ((T, U) -> R).asMono(): (Pair<T, U>) -> R = { this(it.first, it.second) }
fun <T, U, R> ((Pair<T, U>) -> R).asBi(): (T, U) -> R = { t, u -> this(t to u) }

