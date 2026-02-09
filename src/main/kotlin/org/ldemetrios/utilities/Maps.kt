@file:Suppress("unused")

package org.ldemetrios.utilities

fun <K, V> Map<K, V>.flip() = toList().map { it.second to it.first }.toMap()

fun <K, V> mapOfNonNull(vararg pairs: Pair<K, V?>): Map<K, V> =
    pairs.filter { it.second != null }.associate { it.first to it.second!! }
