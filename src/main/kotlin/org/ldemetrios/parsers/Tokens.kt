package org.ldemetrios.parsers

import org.ldemetrios.utilities.Either

data class Literal<C>(val elements: List<C>, val name: String? = null) : NonSequentialParser<C, List<C>> {
    override fun parse(content: List<C>): Either<ErrorLevel, Result<List<C>, C>> {
        if (elements.size > content.size) return unpresent()
        for (i in elements.indices) {
            if (content[i] != elements[i]) {
                return unpresent()
            }
        }
        return success(elements, content.subList(elements.size, content.size))
    }

    override fun named(name: String?) = Literal(elements, name)

    override fun toString(): String = name ?: elements.format()

    override fun trace(content: List<C>, tracer: Tracer): Either<ErrorLevel, Result<List<C>, C>> {
        val what = name ?: elements.format()
        for (i in elements.indices) {
            if (i >= content.size) {
                tracer.line { "$fail EOF reached while parsing $what" }
                return unpresent()
            } else if (content[i] != elements[i]) {
                tracer.line { "$fail missing $i-th element (${elements[i].format()}) from $what, found ${content[i].format()} instead" }
                return unpresent()
            }
        }
        tracer.line { "$succ $what" }
        return success(elements,  content.subList(elements.size, content.size))
    }
}

data class LiteralStr(val elements: String, val name: String? = null) : NonSequentialParser<Char, String> {
    override fun parse(content: List<Char>): Either<ErrorLevel, Result<String, Char>> {
        if (elements.length > content.size) return unpresent()
        for (i in elements.indices) {
            if (content[i] != elements[i]) {
                return unpresent()
            }
        }
        return success(elements,  content.subList(elements.length, content.size))
    }

    override fun named(name: String?) = LiteralStr(elements, name)

    override fun toString(): String = name ?: elements.format()

    override fun trace(content: List<Char>, tracer: Tracer): Either<ErrorLevel, Result<String, Char>> {
        val what = name ?: elements.format()
        for (i in elements.indices) {
            if (i >= content.size) {
                tracer.line { "$fail EOF reached while parsing $what" }
                return unpresent()
            } else if (content[i] != elements[i]) {
                tracer.line { ("$fail missing $i-th element (${elements[i].format()}) from $what, found ${content[i].format()} instead") }
                return unpresent()
            }
        }
        tracer.line { "$succ $what" }
        return success(elements,  content.subList(elements.length, content.size))
    }
}

data class RegexStr(val pattern: Regex, val name: String? = null) : NonSequentialParser<Char, MatchResult> {
    constructor(pattern: String, name: String? = null) : this(Regex(pattern), name)

    override fun parse(content: List<Char>): Either<ErrorLevel, Result<MatchResult, Char>> {
        val result = pattern.matchAt(content.joinToString(""), 0)
        return if (result != null) {
            success(result, content.subList(result.value.length, content.size))
        } else {
            unpresent()
        }
    }

    override fun named(name: String?) = RegexStr(pattern, name)

    override fun toString(): String = name ?: pattern.format()

    override fun trace(content: List<Char>, tracer: Tracer): Either<ErrorLevel, Result<MatchResult, Char>> {
        val result = pattern.matchAt(content.joinToString(""), 0)
        return if (result != null) {
            tracer.line { ("$succ ${pattern.format()}: found ${result.value.format()}") }
            success(result, content.subList(result.value.length, content.size))
        } else {
            tracer.line {
                        "$fail ${pattern.format()}: no matches starting from ${
                            content.take(30).joinToString("").format()
                        }"
            }
            unpresent()
        }
    }
}
