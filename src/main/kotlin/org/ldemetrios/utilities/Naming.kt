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
