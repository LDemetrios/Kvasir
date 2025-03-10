@file:Suppress("ConvertToStringTemplate")

package org.ldemetrios.kvasir.util


fun String.removePrefixOrThrow(prefix: String): String {
    if (!startsWith(prefix)) throw AssertionError("$this doesn't start with $prefix")
    return substring(prefix.length)
}

fun String.removeSuffixOrThrow(suffix: String): String {
    if (!endsWith(suffix)) throw AssertionError("$this doesn't end with $suffix")
    return substring(0, length - suffix.length)
}


val extensions = listOf("typ", "typc", "typm")
