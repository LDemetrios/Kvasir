package org.ldemetrios.kvasir.highlight

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType
import org.ldemetrios.kvasir.syntax.TypstTokenType
import org.ldemetrios.kvasir.syntax.TypstLexer
import org.ldemetrios.tyko.compiler.SyntaxKind
import org.ldemetrios.tyko.compiler.SyntaxKind.*
import org.ldemetrios.tyko.compiler.SyntaxMode

class TypstLexicalHighlighter(val mode: SyntaxMode) : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return TypstLexer(mode)
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when ((tokenType as? TypstTokenType)?.kind) {
            in keywords -> return KEYWORD.array
            in operators -> return OPERATOR.array
            in numericLiterals -> return NUMERIC_LITERAL.array
            in comments -> return COMMENT.array
            in strings -> return STRINGS.array
            in links -> return LINKS.array
            Escape -> return ESCAPES.array
            Shorthand, MathShorthand, Linebreak -> return SHORTHANDS.array
            Label -> LABELS.array
            else -> EMPTY_KEYS
        }
    }

    companion object {
        val keywords: Set<SyntaxKind> = setOf(
            And, As, Auto, Bool, Break, Context,
            Continue, Else, For, If, Import, In,
            Include, Let, None, Not, Or, Return,
            Set, Show, While,
        )

        val operators: Set<SyntaxKind> = setOf(
            Eq, EqEq, ExclEq, Gt, GtEq, Lt, LtEq,
            Minus, Plus, Slash, SlashEq, Star, StarEq,
        )

        val numericLiterals: Set<SyntaxKind> = setOf(
            SyntaxKind.Int, SyntaxKind.Float, Numeric
        )

        val comments: Set<SyntaxKind> = setOf(
            LineComment, BlockComment, Shebang
        )

        val strings: Set<SyntaxKind> = setOf(
            Str
        )

        val links: Set<SyntaxKind> = setOf(
            Link
        )

        val EMPTY_KEYS: Array<TextAttributesKey> = arrayOf()

        val MARKUP_INSTANCE = TypstLexicalHighlighter(SyntaxMode.Markup)
        val CODE_INSTANCE = TypstLexicalHighlighter(SyntaxMode.Code)
        val MATH_INSTANCE = TypstLexicalHighlighter(SyntaxMode.Math)
    }
}

internal class TypstMarkupLexicalHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return TypstLexicalHighlighter.MARKUP_INSTANCE
    }
}

internal class TypstCodeLexicalHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return TypstLexicalHighlighter.CODE_INSTANCE
    }
}

internal class TypstMathLexicalHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return TypstLexicalHighlighter.MATH_INSTANCE
    }
}