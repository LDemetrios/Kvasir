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
val LINESEP = System.lineSeparator()
val CODE_PREFIX = "#{" + LINESEP
val CODE_SUFFIX = LINESEP + "}"
val MATH_PREFIX = "$" + LINESEP
val MATH_SUFFIX = LINESEP + "$"
val CODE_PREFIX_BARR = CODE_PREFIX.toByteArray()
val CODE_SUFFIX_BARR = CODE_SUFFIX.toByteArray()
val MATH_PREFIX_BARR = MATH_PREFIX.toByteArray()
val MATH_SUFFIX_BARR = MATH_SUFFIX.toByteArray()