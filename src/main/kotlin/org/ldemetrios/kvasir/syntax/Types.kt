package org.ldemetrios.kvasir.syntax

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.tree.IElementType
import org.ldemetrios.kvasir.language.*
import org.ldemetrios.tyko.compiler.SyntaxKind

open class TypstFileCommon(viewProvider: FileViewProvider, language: Language, val fileT: FileType, name: String) : PsiFileBase(viewProvider, language) {
    override fun getFileType(): FileType {
        return fileT
    }

    override fun toString(): String {
        return name
    }
}

class TypstMarkupFile(viewProvider: FileViewProvider) : TypstFileCommon(
    viewProvider, TypstMarkupLanguage.INSTANCE, TypstMarkupFileType.INSTANCE, "Typst"
)

class TypstCodeFile(viewProvider: FileViewProvider) : TypstFileCommon(
    viewProvider, TypstCodeLanguage.INSTANCE, TypstCodeFileType.INSTANCE, "Typst Code"
)


class TypstMathFile(viewProvider: FileViewProvider) : TypstFileCommon(
    viewProvider, TypstMathLanguage.INSTANCE, TypstMathFileType.INSTANCE, "Typst Math"
)

class TypstTokenType(val kind: SyntaxKind?, name: String = kind!!.name) : IElementType(name, TypstMarkupLanguage.INSTANCE) {
    override fun toString(): String {
        return "TypstTokenType." + super.toString()
    }
}

class TypstElementType(val kind: SyntaxKind?, name: String = kind!!.name) : IElementType(name, TypstMarkupLanguage.INSTANCE) {
    override fun toString(): String {
        return "TypstElementType." + super.toString()
    }
}

val TYPST_WHITESPACE = TypstTokenType(null, "Whitespace")
val TYPST_EMBEDDED_CODE = TypstElementType(null, "EmbeddedCode")
val TYPST_RAW_TEXT = TypstElementType(null, "RawText")


private val tokenMap = SyntaxKind.entries.associateWith { TypstTokenType(it) }
private val elementMap = SyntaxKind.entries.associateWith { TypstElementType(it) }

val SyntaxKind.tokenType get() = tokenMap[this]!!
val SyntaxKind.nodeType get() = elementMap[this]!!



