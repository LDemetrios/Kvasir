package org.ldemetrios.utilities

fun <T> List<T>.split(
    limit: Int = Int.MAX_VALUE,
    includeSeparatorAsStart: Boolean = false,
    predicate: (T) -> Boolean,
): List<List<T>> {
    val shift = if (includeSeparatorAsStart) 0 else 1
    val indices = listOf(-shift) +
            this.indices.filter { predicate(this[it]) }.take(limit - 1) +
            listOf(size)

    return List(indices.size - 1) {
        this.subList(indices[it] + shift, indices[it + 1])
    }
}