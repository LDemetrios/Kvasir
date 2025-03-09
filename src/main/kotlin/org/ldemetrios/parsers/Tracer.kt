package org.ldemetrios.parsers

import java.io.PrintStream
import java.io.PrintWriter

@PublishedApi
internal val suppressingScope = Scope(true)
val SuppressingTracer = Tracer(true)

class Tracer(val suppressing: Boolean = false) {
    @PublishedApi
    internal var scopes = mutableListOf(Scope(suppressing))

    inline fun <R> scope(func: Scope.() -> R): R {
        if (suppressing) return suppressingScope.func() else {
            val scope = Scope(false)
            scopes.add(scope)
            val r = scope.func()
            scopes.removeLast()
            scopes.last().run {
                line{ (scope.mark?.let { "$it " } ?: "") + scope.builder.toString() }
                for (line in scope.list) {
                    line(line.indent + 1) { line.text }
                }
            }
            return r
        }
    }

    inline fun line(indent: Int = 0,  line: () -> String) {
        if (!suppressing) scopes.last().line(indent, line)
    }

    fun printTo(out: PrintStream = System.out, spaces: Int = -1) {
        if (!suppressing) scopes.last().run {
            val tab = if (spaces == -1) "\t" else " ".repeat(spaces)
            for (line in list) {
                out.println(tab.repeat(line.indent) + line.text)
            }
        }
    }
}


class Scope(val suppressing: Boolean) {
    @PublishedApi
    internal val list: MutableList<Line> = mutableListOf<Line>()
    @PublishedApi
    internal val builder = StringBuilder()
    @PublishedApi
    internal var mark: String? = null

    fun mark(text: String) {
        mark = text
    }

   inline fun line(indent: Int = 0, line: () -> String) {
        if (!suppressing) list.add(Line(indent, line()))
    }

   inline fun toSummary(text: () -> String) {
        if (!suppressing) builder.append(text())
    }
}

@PublishedApi
internal data class Line(val indent: Int, val text: String) {
//    fun tab() = Line(indent + 1, text)
}

//fun main() {
//    Tracer().apply {
//        scope {
//            line { "Hello" }
//            scope {
//                line { "World" }
//                toSummary { "World printed" }
//                mark("+")
//            }
//            toSummary { "Summary" }
//        }
//        printTo(System.out)
//    }
//}