package org.ldemetrios.kvasir.syntax

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.jetbrains.annotations.NotNull
import org.ldemetrios.kvasir.language.TypstLanguage

val COMMENT_TOKENS = TokenSet.create(ErroneousTypes.LINE_COMMENT, ErroneousTypes.BLOCK_COMMENT)

val WHITESPACE_TOKENS = TokenSet.create()

val FILE_NODE_TYPE: IFileElementType = IFileElementType(TypstLanguage.INSTANCE)

internal class TypstParserDefinition : ParserDefinition {
    @NotNull
    override fun createLexer(project: Project?): Lexer {
        return TypstLexerAdapter()
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITESPACE_TOKENS
    }

    @NotNull
    override fun getCommentTokens(): TokenSet {
        return COMMENT_TOKENS
    }

    @NotNull
    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    @NotNull
    override fun createParser(project: Project?): PsiParser {
        return TypstParser()
    }

    @NotNull
    override fun getFileNodeType(): IFileElementType {
        return FILE_NODE_TYPE
    }

    @NotNull
    override fun createFile(@NotNull viewProvider: FileViewProvider): PsiFile {
        return TypstFile(viewProvider)
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return TypstTypes.Factory.createElement(node)
    }
}