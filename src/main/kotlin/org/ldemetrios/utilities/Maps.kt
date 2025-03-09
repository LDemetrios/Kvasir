@file:Suppress("unused")

package org.ldemetrios.utilities

fun <K, V> Map<K, V>.flip() = toList().map { it.second to it.first }.toMap()
