package org.ldemetrios.parsers

import org.ldemetrios.utilities.*
import org.ldemetrios.utilities.named


fun Any?.format(): String = when (this) {
    null -> "`null`"
    is Char -> "'$this'"
    is Boolean -> if (this) "`true`" else "`false`"
    is Number -> "`$this`"
    is String -> when {
        length < 10 -> "\"" + escape() + "\""
        else -> "\"" + substring(0, 8).escape() + "...\""
    }

    is Regex -> "/" + pattern.escape() + "/"
    is List<*> -> "[" +joinFormatted() +"]"
    is Map.Entry<*, *> -> "${key.format()}: ${value.format()}"
    is Map<*, *> -> "{" + map{it}.joinFormatted() + "}"
    is Pair<*, *> -> "(${first.format()}, ${second.format()})"
    is Triple<*, *, *> -> "(${first.format()}, ${second.format()}, ${third.format()})"
    is Nullad<*> -> "()"
    is Monad<*> -> "(${first.format()},)"
    is Tuple<*> -> "(" + reify().joinFormatted() + ")"
    is Set<*> -> "{" + joinFormatted() + "}"
    is Iterable<*> -> "[" + joinFormatted() + "]"
    is Array<*> -> "[" + asList().joinFormatted() + "]"

    else -> "`" + toString().escape() + "`"
}

private fun Iterable<*>.joinFormatted() = when {
    count() < 5 -> joinToString(", ", "", "") { it.format() }
    else -> take(3).joinToString(", ") { it.format() } + ", ..., ${last().format()}"
}

fun <T> unpack() = named("unpacking", Monad<T>::first)
