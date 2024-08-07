package org.ldemetrios.kvasir.syntax.psiExt

import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.ldemetrios.kvasir.syntax.TypstTypes.*
import org.ldemetrios.kvasir.syntax.psi.*


val TypstMarkupSequence.paragraphs get() = paragraphList

var TypstParagraph.marker: PsiElement?
    get() = firstChild.takeIf {
        when (it.elementType) {
            HEADING_MARKER, ENUM_MARKER, LIST_MARKER, TERM_MARKER -> true
            else -> false
        }
    }
    set(value) {
        val now = marker
        listOf(1).joinToString()
        if (value == null && now == null) return
        else if (value == null) this.firstChild.delete()
        else if (now == null) this.addBefore(value, firstChild)
        else now.replace(value)
    }

val TypstParagraph.colon get() = firstChild?.nextSiblingOf(inclusive = true) { it.elementType == COLON }

val TypstParagraph.content
    get() = (colon ?: firstChild)?.nextSiblingsWhileIs<TypstMarkupExpression>(inclusive = true) ?: listOf()

val TypstParagraph.term
    get() = firstChild?.nextSiblingsWhileIs<TypstMarkupExpression>(
        inclusive = true,
        stop = { it.elementType == COLON })

val TypstEmph.opening get() = firstChild!!
val TypstEmph.body get() = opening.nextSiblingsWhile { it.elementType != UNDERSCORE }
val TypstEmph.closing get() = lastChild?.takeIf { it.elementType == UNDERSCORE }

val TypstStrong.opening get() = firstChild!!
val TypstStrong.body get() = opening.nextSiblingsWhile { it.elementType != STAR }
val TypstStrong.closing get() = lastChild?.takeIf { it.elementType == STAR }

val TypstReference.ref get() = firstChild!!
val TypstReference.body get() = lastChild.takeIf { it.elementType == CONTENT_BLOCK }
val TypstReference.refText get() = ref.text.drop(1)

val TypstEmbeddedCode.hash get() = firstChild!!
val TypstEmbeddedCode.line get() = lastChild!! as TypstCodeLine

val TypstCodeSequence.exprs get() = firstChild.nextSiblingsWhileIs<TypstCodeLine>(inclusive = true)

val TypstCodeLine.sep get() = lastChild.takeIf { it.elementType == CODE_SPACE || it.elementType == CODE_NEW_LINE_SPACE }
val TypstCodeLine.expression
    get() = firstChild.nextSiblingsWhile(inclusive = true) {
        when (it.elementType) {
            WSPACE, CODE_NEW_LINE_SPACE, SEMICOLON -> false
            else -> true
        }
    }

val TypstStmt.keyword get() = firstChild!!

val TypstClosure.arrow get() = firstChild.nextSiblingOf { it.elementType == ARROW}!!

val TypstAtomic.primary get() = firstChild!! as TypstPrimary

