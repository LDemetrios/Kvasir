@file:Suppress("unused")

package org.ldemetrios.utilities

fun String.replaceAll(map: Map<String, String>): String {
    var res = this
    for ((key, value) in map) res = res.replace(key, value)
    return res
}

fun String.replaceAll(map: List<Pair<String, String>>): String {
    var res = this
    for ((key, value) in map) {
        res = res.replace(key, value)
    }
    return res
}

fun String.replaceAll(vararg map: Pair<String, String>): String = replaceAll(map.toMap())

fun String.escape() = this.replaceAll(
    "\\" to "\\\\",
    "\n" to "\\n",
    "\r" to "\\r",
    "\t" to "\\t",
    "\"" to "\\\"",
)

fun String.unescape() = this.replaceAll(
    "\\n" to "\n",
    "\\r" to "\r",
    "\\t" to "\t",
    "\\\"" to "\"",
    "\\\\" to "\\",
)

