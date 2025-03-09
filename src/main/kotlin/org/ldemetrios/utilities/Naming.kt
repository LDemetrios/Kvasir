@file:Suppress("unused")

package org.ldemetrios.utilities

fun ordinal(i: Int): String {
    require(i >= 0) { "i expected to be non-negative" }
    return if (i % 100 / 10 == 1) {
        "$i-th"
    } else if (i % 10 == 1) {
        "$i-st"
    } else if (i % 10 == 2) {
        "$i-nd"
    } else if (i % 10 == 3) {
        "$i-rd"
    } else {
        "$i-th"
    }
}

fun naturallyJoin(vararg args: Any): String {
    if (args.size == 0) return "none"
    if (args.size == 1) return args[0].toString()
    val until = args.size - 2
    val b = StringBuilder()
    for (i in 0 until until) {
        b.append(args[i])
        b.append(", ")
    }
    b.append(args[until])
    b.append(" and ")
    b.append(args[until + 1])
    return b.toString()
}

fun Any?.toCodeRepr(): String = when (this) {
    null -> "null"
    is Int -> this.toString()
    is String -> "\"$this\""
    is Boolean -> this.toString()
    is Byte -> "$this.toByte()"
    is Short -> "$this.toShort()"
    is Long -> "${this}L"
    is Float -> "${this}f"
    is Double -> this.toString()
    is Char -> "'\\u" + code.toString(16).let { "0".repeat(4 - it.length) + it } + "'"
    is List<*> -> "listOf(" + joinToString(", ") { it.toCodeRepr() } + ")"
    is Map<*, *> -> "mapOf(" + toList().joinToString(", ") { it.toCodeRepr() } + ")"
    is Set<*> -> "setOf(" + joinToString(", ") { it.toCodeRepr() } + ")"
    is Pair<*, *> -> "Pair(" + first.toCodeRepr() + ", " + second.toCodeRepr() + ")"
    is Triple<*, *, *> -> "Triple(" + first.toCodeRepr() + ", " + second.toCodeRepr() + ", " + third.toCodeRepr() + ")"
    else -> throw IllegalArgumentException("Cannot convert $this to code representation")
}

fun Any?.toCodeReprSep(): String = when (this) {
    null -> "null"
    is Int -> this.toString()
    is String -> "\"$this\""
    is Boolean -> this.toString()
    is Byte -> "$this.toByte()"
    is Short -> "$this.toShort()"
    is Long -> "${this}L"
    is Float -> "${this}f"
    is Double -> this.toString()
    is Char -> "'\\u" + code.toString(16).let { "0".repeat(4 - it.length) + it } + "'"
    is List<*> -> "listOf(\n" + joinToString(", \n") { it.toCodeReprSep() } + ")"
    is Map<*, *> -> "mapOf(\n" + toList().joinToString(", \n") { it.toCodeReprSep() } + ")"
    is Set<*> -> "setOf(\n" + joinToString(", \n") { it.toCodeReprSep() } + ")"
    is Pair<*, *> -> "Pair(\n" + first.toCodeReprSep() + ", \n" + second.toCodeReprSep() + ")"
    is Triple<*, *, *> -> "Triple(\n" + first.toCodeReprSep() + ", \n" + second.toCodeReprSep() + ",\n " + third.toCodeReprSep() + ")"
    else -> throw IllegalArgumentException("Cannot convert $this to code representation")
}