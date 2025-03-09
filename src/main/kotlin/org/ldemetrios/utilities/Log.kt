@file:Suppress("unused")

package org.ldemetrios.utilities

import java.util.*
import java.util.regex.Pattern

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
open class Log {
    private val LINES = Pattern.compile("\n")
    private val scopes: Deque<String> = ArrayDeque()
    protected var indent = 0
        private set

    fun <T> scope(name: String, action: ()->T): T {
        println(name)
        indent++
        return try {
            silentScope(name, action)
        } finally {
            indent--
        }
    }

    fun <T> silentScope(name: String, action: ()->T): T {
        scopes.push(name)
        return try {
            action()
        } finally {
            scopes.pop()
        }
    }

    fun println(value: Any) {
        for (line in LINES.split(value.toString())) {
            kotlin.io.println(indent() + line)
        }
    }

    fun format(format: String?, vararg args: Any?) {
        println(String.format(format!!, *args))
    }

    private fun indent(): String {
        return "    ".repeat(indent)
    }
}
