package org.ldemetrios.kvasir.highlight

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.ldemetrios.kvasir.syntax.ErroneousTypes.BLOCK_COMMENT
import org.ldemetrios.kvasir.syntax.ErroneousTypes.LINE_COMMENT
import org.ldemetrios.kvasir.syntax.TypstLexerAdapter
import org.ldemetrios.kvasir.syntax.TypstTypes
import org.ldemetrios.kvasir.syntax.TypstTypes.*


class TypstLexicalHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return TypstLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType):  Array<TextAttributesKey> {
        return when (tokenType) {

            TokenType.BAD_CHARACTER -> return BAD_CHARACTER.array
            in keywords -> return KEYWORD.array
            in operators -> return OPERATOR.array
            in numericLiterals -> return NUMERIC_LITERAL.array
            in comments -> return COMMENT.array
            in strings -> return STRINGS.array
            in links -> return LINKS.array
            TypstTypes.ESCAPE -> return ESCAPES.array
            TypstTypes.SHORTHAND -> return SHORTHANDS.array
            TypstTypes.LABEL -> LABELS.array

            else -> EMPTY_KEYS
        }
    }

    companion object {
        val keywords: Set<IElementType> = setOf(
            AND, AS, AUTO, BOOL, BREAK, CONTEXT,
            CONTINUE, ELSE, FOR, IF, IMPORT, IN,
            INCLUDE, LET, NONE, NOT, OR, RETURN,
            SET, SHOW, WHILE,
        )

        val operators : Set<IElementType> = setOf(
            EQ, EQ_EQ, EXCL_EQ, GT, GT_EQ, LT, LT_EQ,
            MINUS, PLUS, SLASH, SLASH_EQ, STAR, STAR_EQ,
        )

        val numericLiterals : Set<IElementType> = setOf(
            INT, FLOAT, NUMERIC
        )

        val comments : Set<IElementType> = setOf(
            LINE_COMMENT, BLOCK_COMMENT
        )

        val strings : Set<IElementType> = setOf(
            STRING
        )

        val links : Set<IElementType>  = setOf(
            LINK
        )

        val EMPTY_KEYS: Array<TextAttributesKey> = arrayOf()

        val INSTANCE = TypstLexicalHighlighter()
    }
}

internal class TypstLexicalHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return TypstLexicalHighlighter.INSTANCE
    }
}