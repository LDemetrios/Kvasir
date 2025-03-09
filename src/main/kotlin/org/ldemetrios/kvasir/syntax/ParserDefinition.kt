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
import org.ldemetrios.kvasir.language.TypstCodeLanguage
import org.ldemetrios.kvasir.language.TypstMarkupLanguage
import org.ldemetrios.kvasir.language.TypstMathLanguage
import org.ldemetrios.kvasir.psi.EmbeddedCodePsiElement
import org.ldemetrios.kvasir.psi.ErrorPsiElement
import org.ldemetrios.kvasir.psi.RawTextPsiElement
import org.ldemetrios.kvasir.psi.WhitespacePsiElement
import org.ldemetrios.kvasir.psi.constructorsMap
import org.ldemetrios.tyko.compiler.SyntaxKind
import org.ldemetrios.tyko.compiler.SyntaxMode


val COMMENT_TOKENS = TokenSet.create(
    SyntaxKind.LineComment.tokenType,
    SyntaxKind.BlockComment.tokenType,
    SyntaxKind.Shebang.tokenType
)

val WHITESPACE_TOKENS = TokenSet.create()

val MARKUP_FILE_NODE_TYPE: IFileElementType = IFileElementType(TypstMarkupLanguage.INSTANCE)
val CODE_FILE_NODE_TYPE: IFileElementType = IFileElementType(TypstCodeLanguage.INSTANCE)
val MATH_FILE_NODE_TYPE: IFileElementType = IFileElementType(TypstMathLanguage.INSTANCE)

internal abstract class TypstParserDefinitionCommon(val syntaxMode: SyntaxMode, val fileNode : IFileElementType) : ParserDefinition {
    @NotNull
    override fun createLexer(project: Project?): Lexer {
        return TypstLexer(syntaxMode)
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
        return TypstParser(syntaxMode)
    }

    @NotNull
    override fun getFileNodeType(): IFileElementType {
        return fileNode
    }

    override fun createElement(node: ASTNode): PsiElement {
        return when (val el = node.elementType) {
            TYPST_WHITESPACE -> WhitespacePsiElement(node)
            TYPST_EMBEDDED_CODE -> EmbeddedCodePsiElement(node)
            TYPST_RAW_TEXT -> RawTextPsiElement(node)
            is TypstElementType -> constructorsMap[el.kind!!]?.let { it(node) } ?: ErrorPsiElement(node)
            else -> {
                ErrorPsiElement(node)
            }
        }
    }
}

internal class TypstMarkupParserDefinition : TypstParserDefinitionCommon(SyntaxMode.Markup, MARKUP_FILE_NODE_TYPE) {
    @NotNull
    override fun createFile(@NotNull viewProvider: FileViewProvider): PsiFile {
        return TypstMarkupFile(viewProvider)
    }
}
internal class TypstCodeParserDefinition : TypstParserDefinitionCommon(SyntaxMode.Code, CODE_FILE_NODE_TYPE) {
    @NotNull
    override fun createFile(@NotNull viewProvider: FileViewProvider): PsiFile {
        return TypstCodeFile(viewProvider)
    }
}
internal class TypstMathParserDefinition : TypstParserDefinitionCommon(SyntaxMode.Math, MATH_FILE_NODE_TYPE) {
    @NotNull
    override fun createFile(@NotNull viewProvider: FileViewProvider): PsiFile {
        return TypstMathFile(viewProvider)
    }
}
