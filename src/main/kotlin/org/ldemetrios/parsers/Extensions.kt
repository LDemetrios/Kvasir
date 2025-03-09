package org.ldemetrios.parsers

fun literally(str: String) = LiteralStr(str)
fun regex(pattern: String) = regex(Regex(pattern))
fun regex(pattern: Regex) = RegexStr(pattern)
fun <T> just(value: T) = Literal(listOf(value))
fun <T> literallyAs(str: String, value: T) = LiteralStr(str) use { value } named "${str.format()} -> ${value.format()}"
fun <T> regexAs(pattern: String, value: T) = regex(pattern) use { value } named "${pattern.format()} -> ${value.format()}"
fun <T> regexAsStr(pattern: String, value: T) = regex(pattern) use { value } named pattern.format()
fun <T> regexAs(pattern: Regex, value: T) = regex(pattern) use { value } named "${pattern.format()} -> ${value.format()}"
fun <T> regexAsStr(pattern: Regex, value: T) = regex(pattern) use { value } named pattern.format()

fun <C, T> Parser<C, T>.repeat(from: Long, to: Long, ifLess: ErrorLevel? = null) = Repeating(this, from, to)
fun <C, T> star(it: Parser<C, T>, ifLess: ErrorLevel? = null) = Repeating(it, 0, Long.MAX_VALUE, "Star($it)")
fun <C, T> plus(it: Parser<C, T>, ifLess: ErrorLevel? = null) = Repeating(it, 1, Long.MAX_VALUE, "Plus($it)")
fun <C, T> opt(it: Parser<C, T>) = Repeating(it, 0, 1).useNamed("singleOrNull()") { singleOrNull() } named "Opt($it)"

operator fun <C, T> Parser<C, T>.unaryMinus(): SkipParser<C, *> = SkipParser(this)
operator fun String.unaryMinus() = (-literally(this)).notrace() named "-${this.format()}"
operator fun Regex.unaryMinus() = (-regex(this)).notrace() named "-${this.format()}"

infix fun <C, T> Parser<C, T>.or(other: Parser<C, T>) = Choice(listOf(this, other))
infix fun <C, T> Choice<C, T>.or(other: Parser<C, T>) = Choice(choice + other)
operator fun <C, T> Parser<C, T>.plus(other: Parser<C, T>) = Choice(listOf(this, other))
operator fun <C, T> Choice<C, T>.plus(other: Parser<C, T>) = Choice(choice + other)

fun <C, T> NonSequentialParser<C, T>.notrace() = NoTrace(this)
fun <C, T> SkipParser<C, T>.notrace() = SkipParser(underlying, name , false)
