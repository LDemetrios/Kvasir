//package org.ldemetrios.kvasir.syntax
//
//import com.intellij.psi.tree.IElementType
//import org.ldemetrios.typst4k.Typst
//import org.ldemetrios.typst4k.orm.*
//import org.ldemetrios.typst4k.orm.TContent
//import org.ldemetrios.typst4k.orm.TRaw
//import org.ldemetrios.typst4k.rt.*
//import org.ldemetrios.kvasir.syntax.TypstLexer.*
//import java.awt.Color
//import java.io.File
//
//enum class Supermode {
//    MARKUP, MATH, CODE, GRAY
//}
//
//fun modeName(x: Int): String = when (x) {
//    YYINITIAL -> "YYINITIAL"
//    MARKUP -> "MARKUP"
//    RAW -> "RAW"
//    BLOCK_COMMENT_MODE -> "BLOCK_COMMENT_MODE"
//    BLOCKY_RAW -> "BLOCKY_RAW"
//    MATH -> "MATH"
//    EMBEDDED_CODE_EXPR -> "EMBEDDED_CODE_EXPR"
//    NON_ATOMIC_EXPR -> "NON_ATOMIC_EXPR"
//    IF_ELSE_EXPR -> "IF_ELSE_EXPR"
//    WHILE_EXPR -> "WHILE_EXPR"
//    FOR_EXPR -> "FOR_EXPR"
//    EMBEDDED_STATEMENT -> "EMBEDDED_STATEMENT"
//    CODE_BLOCK -> "CODE_BLOCK"
//    PARENTHESIZED -> "PARENTHESIZED"
//    UNICODE_ESCAPE -> "UNICODE_ESCAPE"
//    LINK_MODE -> "LINK_MODE"
//    LINK_PAR -> "LINK_PAR"
//    LINK_SQ -> "LINK_SQ"
//    LINK_CURLY -> "LINK_CURLY"
//    LINK_ANGLE -> "LINK_ANGLE"
//    LABEL_MODE -> "LABEL_MODE"
//    FOR_EXPR_AFTER_PATTERN -> "FOR_EXPR_AFTER_PATTERN"
//    FOR_EXPR_AFTER_IN -> "FOR_EXPR_AFTER_IN"
//    AFTER_BRANCH -> "AFTER_BRANCH"
//    else -> "UNKNOWN"
//}
//
//fun supermode(stack: List<Int>): Supermode {
//    for (element in stack.asReversed()) {
//        return when (element) {
//            YYINITIAL, MARKUP -> Supermode.MARKUP
//            MATH -> Supermode.MATH
//            EMBEDDED_CODE_EXPR, NON_ATOMIC_EXPR, AFTER_BRANCH, IF_ELSE_EXPR, WHILE_EXPR, FOR_EXPR, FOR_EXPR_AFTER_PATTERN, EMBEDDED_STATEMENT, CODE_BLOCK, PARENTHESIZED, FOR_EXPR_AFTER_IN -> Supermode.CODE
//            BLOCK_COMMENT_MODE, BLOCKY_RAW, RAW -> Supermode.GRAY
//
//            UNICODE_ESCAPE,
//            LINK_MODE,
//            LINK_PAR,
//            LINK_SQ,
//            LINK_CURLY,
//            LINK_ANGLE,
//            LABEL_MODE -> null
//            else -> throw AssertionError(element)
//        } ?: continue
//    }
//    throw AssertionError()
//}
//
//data class TokenData(val token: String, val type: IElementType, val stack: List<Int>) {
//    override fun toString(): String = "${stack.joinToString(" | ", transform = ::modeName)} / $type : $token"
//
//    val color = when (supermode(stack)) {
//        Supermode.MARKUP -> Color(0x00, 0x00, 0xaf)
//        Supermode.MATH -> Color(0x00, 0xaf, 0x00)
//        Supermode.CODE -> Color(0xaf, 0x00, 0x00)
//        Supermode.GRAY -> Color.gray
//    }
//
//    fun coloredBySupermode(): TContent {
//        return TText(body = TRaw(token.t), fill = color.t)
//    }
//
//    fun bracketed(): TContent = TSequence(
//        TText(body = TRaw("[".t), fill = color.t),
//        TRaw(token.t),
//        TText(body = TRaw("]".t), fill = color.t),
//    )
//}
//
//fun testLexer(
//    input: String
//): List<TokenData> {
//    val inner = TypstLexer(null)
//    val lexer = TypstLexerAdapter(inner)
//    lexer.start(input)
//    // start lexing
//    val tokens = mutableListOf<TokenData>()
//    var mode = listOf(YYINITIAL)
//    var wasText = false
//    var latestText = StringBuilder()
//    var textModeWas: List<Int>? = null
//    while (true) {
//        val token = lexer.tokenType ?: break
//        val text = lexer.tokenText
////        print(text)
//        val data = TokenData(text, token, mode)
//        mode = inner.stack + inner.yystate()
//        lexer.advance()
//        if (wasText) {
//            if (token == TypstTypes.TEXT) {
//                latestText.append(text)
//            } else {
//                tokens.add(TokenData(latestText.toString(), TypstTypes.TEXT, textModeWas!!))
//                tokens.add(data)
//                textModeWas = null
//                wasText = false
//            }
//        } else {
//            if (token == TypstTypes.TEXT) {
//                wasText = true
//                textModeWas = mode
//                latestText = StringBuilder(text)
//            } else {
//                tokens.add(data)
//            }
//        }
//    }
//    if (wasText) {
//        tokens.add(TokenData(latestText.toString(), TypstTypes.TEXT, textModeWas!!))
//    }
//    return tokens
//}
//
//
//val typst = Typst()
//
//fun testTypstLexerChaptered() {
//    val tests =
//        File("src/test/resources/lexing.typ").readText().split("\n---")
//            .asSequence()
//            .map {
//                it.split("---", limit = 2)
//            }
//            .filterNot {
//                it.size == 1 && it[0].matches(Regex("((\\s*//[^\n\r]*)?[\n\r])+(\\s*//[^\r\n]*)?"))
//            }
//            .filterNot { it[0].isBlank() }
//            .onEach {
//                if (it.size != 2) {
//                    for (el in it) {
//                        println(el)
//                        println("\n")
//                    }
//                    println("\n\n\n\n")
//                }
//            }
//            .filter { it.size == 2 }
//            .map { it[0].trim() to it[1] }
//            .toList()
//
//    val tmp = File("src/test/output/tmp.typ")
//    File("src/test/output/lexing").run {
//        if (exists()) deleteRecursively()
//        mkdirs()
//    }
//    for ((name, test) in tests) {
//        println("--- $name ---")
//        try {
//            val content = testLexer(test)
////            println("=== out === / ${content.size} / ${content.sumOf { it.toString().length }}")
//            val codeColored = TSequence(*content.map(TokenData::coloredBySupermode).toTypedArray())
//            val rawOutput = content.joinToString("\n")
//            val view = TTable(
//                TArray(codeColored, TRaw(rawOutput.t)),
//                columns = TArray(TAuto, TAuto),
//            )
//            tmp.writeText("#set page(width:auto,height:auto)\n#${view.repr()}")
////            println(tmp.path)
//            typst.compile(tmp.path, "src/test/output/lexing/$name.pdf")
//        } catch (e: Throwable) {
//            e.printStackTrace()
//        }
//    }
//    tmp.delete()
//}
//
//fun <T> T?.orElse(x: T) = this ?: x
//
//fun main() {
//    testTypstLexerChaptered()
//}