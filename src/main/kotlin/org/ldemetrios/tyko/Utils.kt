package org.ldemetrios.tyko

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createTempDirectory
import kotlin.io.path.readText

fun <T> Iterable<T>.split(keepSeparator: Boolean = false, predicate: (T) -> Boolean): List<List<T>> {
    val result = mutableListOf<List<T>>()
    var current = mutableListOf<T>()
    for (item in this) {
        if (predicate(item)) {
            result.add(current)
            current = mutableListOf()
            if (keepSeparator) current.add(item)
        } else {
            current.add(item)
        }
    }
    result.add(current)
    return result
}

fun String.removePrefixOrThrow(prefix: String): String {
    if (!startsWith(prefix)) throw AssertionError("$this doesn't start with $prefix")
    return substring(prefix.length)
}

fun String.removeSuffixOrThrow(suffix: String): String {
    if (!endsWith(suffix)) throw AssertionError("$this doesn't end with $suffix")
    return substring(0, length - suffix.length)
}

inline fun <T, R> withTmp(func: (Path) -> T, finish: (T, List<String>) -> R): R {
    val tmp = createTempDirectory("tyko")
    try {
        val result = func(tmp)
        return finish(result, Files.list(tmp).use { it.sorted().map(Path::readText).toList() })
    } finally {
        tmp.toFile().deleteRecursively()
    }
}

fun main() { // Quick compilability check
    println("hello")
}

private val utf8CharSizeMap = IntArray(65536) { it.toChar().toString().toByteArray(Charsets.UTF_8).size }

fun mapByteIndicesToCharIndices(text: String, byteIndices: IntArray): IntArray {
    val result = IntArray(byteIndices.size)

    var currentByteOffset = 0
    var charIndex = 0
    var byteIndexPos = 0

    for (char in text) {

        val charByteSize = utf8CharSizeMap[char.code]
        while (byteIndexPos < byteIndices.size && byteIndices[byteIndexPos] < currentByteOffset + charByteSize) {
            result[byteIndexPos] = charIndex
            byteIndexPos++
        }

        if (byteIndexPos >= byteIndices.size) return result // Early exit if all indices are mapped

        currentByteOffset += charByteSize
        charIndex++
    }

    for (i in byteIndexPos until byteIndices.size) {
        result[i] = text.length
    }

    return result
}

