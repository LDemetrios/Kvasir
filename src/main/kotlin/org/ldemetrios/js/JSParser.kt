@file:Suppress("unused")

package org.ldemetrios.js
import org.ldemetrios.utilities.cast
import java.lang.StringBuilder
import java.math.BigDecimal
import java.text.ParseException
import kotlin.math.min

class JSParser(val string: String) {
    var pos = 0

    private fun checkNotEnd() {
        if (pos >= string.length) throw ParseException("Unexpected EOF", pos)
    }

    private fun expect(c: Char) {
        if (pop() != c) throw ParseException("Expected $c", pos - 1)
    }

    private fun expect(string: String) {
        var index = pos
        for (c in string) {
            if (this.string[index] != c) throw ParseException("Expected $string, not ${string.take(index)}$c...", pos)
            index++
        }
        pos = index
    }

    private fun <T> expectAs(string: String, value: T): T {
        expect(string)
        return value
    }

    private fun peek(): Char {
        checkNotEnd()
        return string[pos]
    }

    private fun pop(): Char {
        checkNotEnd()
        return string[pos++]
    }

    private fun skipWs() {
        while (pos < string.length && Character.isWhitespace(peek())) pop()
    }

    private fun parseString(): String {
        val sb = StringBuilder()
        while (peek() != '"') {
            val next = pop()
            if (next == '\\') {
                sb.append(
                    when (val c = pop()) {
                        '\\' -> '\\'
                        '/' -> '/'
                        '"' -> '\"'
                        'b' -> '\b'
                        'f' -> '\u000C'
                        'n' -> '\n'
                        'r' -> '\r'
                        't' -> '\t'
                        else -> throw ParseException("Illegal escape '\\$c'", pos - 1)
                    }
                )
            } else sb.append(next)
        }
        return sb.toString()
    }

    private fun parseValue(): JSStuff {
        skipWs()
        val ret = when (peek()) {
            'n' -> expectAs("null", JSNull)
            't' -> expectAs("true", JSTrue)
            'f' -> expectAs("false", JSFalse)
            'u' -> expectAs("undefined", JSUndefined)
            '"' -> {
                pop()
                val s = parseString()
                expect('"')
                JSString(s)
            }

            '{' -> {
                pop()
                val o = parseObject()
                expect('}')
                o
            }

            '[' -> {
                pop()
                val o = parseArray()
                expect(']')
                o
            }

            '-', '.', in '0'..'9' -> JSNumber(parseNumber())
            else -> throw ParseException("Unexpected symbol ${peek()}", pos)
        }
        skipWs()
        return ret
    }

    private fun parseArray(): JSArray {
        val res = JSArray()
        skipWs()
        if (peek() == ']') return res
        res.add(parseValue())
        while (peek() != ']') {
            expect(',')
            res.add(parseValue())
        }
        return res
    }

    private fun parseObject(): JSObject {
        val res = JSObject()
        skipWs()
        if (peek() == '}') return res
        expect('"')
        val key = parseString()
        expect('"')
        skipWs()
        expect(':')
        val value = parseValue()
        if (key == JSObject.PROTO_KEY) {
            res.__proto__ = value as JSObject
        } else {
            res[key] = value
        }
        while (peek() != '}') {
            expect(',')
            skipWs()
            expect('"')
            @Suppress("NAME_SHADOWING") val key = parseString()
            expect('"')
            skipWs()
            expect(':')
            @Suppress("NAME_SHADOWING") val value = parseValue()
            if (key == JSObject.PROTO_KEY) {
                res.__proto__ = value as JSObject
            } else {
                res[key] = value
            }
        }
        return res
    }

    private fun parseNumber(): Number {
        val sb = StringBuilder()
        if (peek() == '-') sb.append(pop())
        while (pos < string.length && (peek() in '0'..'9')) sb.append(pop())
        if (pos < string.length && peek() == '.') {
            sb.append(pop())
            while (pos < string.length && (peek() in '0'..'9')) sb.append(pop())
        }
        if (pos < string.length && (peek() == 'e' || peek() == 'E')) {
            sb.append(pop())
            if (peek() == '-' || peek() == '+') sb.append(pop())
            while (pos < string.length && (peek() in '0'..'9')) sb.append(pop())
        }
        return sb.toString().run { toBigIntegerOrNull() ?: toBigDecimal() }
    }

    companion object {
        fun parseValue(str: String): JSStuff {
            val p = JSParser(str)
            val value = p.parseValue()
            p.skipWs()
            if (p.pos < p.string.length) throw ParseException("Expected EOF", p.pos)
            return value
        }

        fun parseObject(str: String) = parseValue(str).cast<JSObject>()
        fun parseArray(str: String) = parseValue(str).cast<JSArray>()
        fun parseBoolean(str: String) = parseValue(str).cast<JSBoolean>()
        fun parseNumber(str: String) = parseValue(str).cast<JSNumber>()
        fun parseString(str: String) = parseValue(str).cast<JSString>()
    }

    override fun toString(): String {
        return string.substring(pos, min(pos + 15, string.length))
    }
}
