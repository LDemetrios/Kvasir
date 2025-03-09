package org.ldemetrios.utilities

data class Lines(val lines: List<Pair<Int, String>>) {
    fun map(f: Pair<Int, String>.() -> Pair<Int, String>) = Lines(lines.map { it.f() })

    fun tab(n: Int = 1) = map { Pair(first + n, second) }

    override fun toString() = toString(8)

    fun toString(tabSize: Int) = lines.joinToString("\n") {
        " ".repeat(tabSize * it.first) + it.second
    }

    constructor(vararg lines: Pair<Int, String>) : this(lines.toList())
    constructor(vararg lines: String) : this(lines.map { 0 to it })
    constructor() : this(listOf<Pair<Int, String>>())

    operator fun plus(other: Lines) = Lines(lines + other.lines)
    operator fun plus(other: String) = Lines(lines + (0 to other))
    operator fun plus(other: Pair<Int, String>) = Lines(lines + other)
    operator fun plus(other: Int) = Lines(lines + (other to ""))
}

fun Lines(lines: List<String>) = Lines(lines.map { 0 to it })


@Suppress("NestedLambdaShadowedImplicitParameter")
fun summarize(header: String, entries: List<Lines>) = Lines(
    listOf(0 to header) +
            entries.asSequence().flatMap { it.lines.asSequence().map { it.first + 1 to it.second } }
)
