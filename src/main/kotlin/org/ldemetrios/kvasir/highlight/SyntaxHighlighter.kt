package org.ldemetrios.kvasir.highlight

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.elementType
import org.ldemetrios.kvasir.syntax.TypstFile
import org.ldemetrios.kvasir.syntax.TypstTypes.*
import org.ldemetrios.kvasir.syntax.psi.*
import org.ldemetrios.kvasir.syntax.psiExt.*


fun AnnotationHolder.highlight(element: PsiElement?, attributes: TextAttributeHelper?) {
    if (element != null && attributes != null)
        newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .textAttributes(attributes.key)
            .create()
}

const val NEWLINES = "\n\r\u2028\u2029\u0085\u000B\u000C"

enum class ParType {
    HEADING,
    TERM,
    PLAIN,
}

val paragraphKeys = arrayOf(
    null,
    STRONG,
    EMPH,
    EMPH_STRONG,
    HEADING,
    STRONG_HEADING,
    EMPH_HEADING,
    EMPH_STRONG_HEADING,
    TERM,
    STRONG_TERM,
    EMPH_TERM,
    EMPH_STRONG_TERM,
)

val PLAIN_PAR_TYPE = 0
val HEADING_PAR_TYPE = 4
val TERM_PAR_TYPE = 8

fun Int.andBold() = this or 1
fun Int.andItalic() = this or 2
fun Int.andPlain() = this and 3
fun Int.andHeading() = this and 3 or 4
fun Int.andTerm() = this and 3 or 8

internal class TypstAnnotator : TypstVisitor(), Annotator {
    private lateinit var holder: AnnotationHolder
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        this.holder = holder
        element.accept(this)
    }

    fun PsiElement.computeRainbowKey(): Int {
        var result = 0
        var cur: PsiElement? = this
        while (cur != null) {
            val shift = when (cur) {
                is TypstClosure, is TypstSingleArgClosure -> 2
                is TypstCodeBlock -> {
                    when (val par = cur.parent) {
                        is TypstForExpr -> if (cur == par.lastChild) 3 else 2
                        is TypstConditional -> if (cur != par.expr) 3 else 2
                        else -> 3
                    }
                }
                is TypstCodeExpr -> 0
                is TypstContentBlock -> {
                    when (val par = cur.parent) {
                        is TypstForExpr -> if (cur == par.lastChild) 3 else 2
                        is TypstConditional -> if (cur != par.expr) 3 else 2
                        is TypstMethodCall, is TypstFunctionCall -> {
                            par.parent.children.indexOf(par) * 2 - 2
                        }
                        else -> 2
                    }
                }
                is TypstParenthesized -> {
                    var lookahead: PsiElement? = cur.parent
                    var res = -1
                    var private_was: PsiElement? = null
                    while (lookahead != null) {
                        res = when (lookahead) {
                            is TypstLetBinding, is TypstForBinding -> 0
                            is TypstSetStmt -> if (private_was == lookahead.expr) 3 else 2
                            is TypstClosure -> 0
                            is TypstMethodCall, is TypstFunctionCall -> {
                                lookahead.parent.children.indexOf(lookahead) * 2 - 2
                            }
                            is TypstExpr -> 2
                            else -> -1
                        }
                        if (res != -1) break
                        private_was = lookahead
                        lookahead = lookahead.parent
                    }
                    if (lookahead == null) 0 else res
                }
                else -> 0
            }
            result += shift
            cur = cur.parent
        }
        return result
    }

    fun PsiElement.computeTextKey(): TextAttributeHelper? {
        var x: Int = 0
        var was: PsiElement? = null
        var parent: PsiElement? = this
        while (parent != null) {
            when (parent) {
                is TypstParagraph -> {
                    when (parent.marker.elementType) {
                        HEADING_MARKER -> {
                            x = x.andHeading()
                        }
                        TERM_MARKER -> {
                            if (was?.nextSiblingOf { it.elementType == COLON } != null) {
                                x = x.andTerm()
                            }
                        }
                    }
                    break
                }
                is TypstEmph -> x = x.andItalic()
                is TypstStrong -> x = x.andBold()
                is TypstJustTextImpl, is LeafPsiElement -> Unit
                else -> println(parent.javaClass.simpleName + " is unaccounted for")
            }
            was = parent
            parent = parent.parent
        }
        return paragraphKeys[x]
    }

    override fun visitClosure(o: TypstClosure) {
        val key = o.parenthesized.computeRainbowKey()
        holder.highlight(o.arrow, RAINBOW(key))
//        holder.highlight(o.expr, RAINBOW_BACK_WEAK(key))
    }

    override fun visitCodeBlock(o: TypstCodeBlock) {
        val key = o.computeRainbowKey()
        holder.highlight(o.firstChild, RAINBOW(key))
        holder.highlight(o.lastChild, RAINBOW(key))
    }

    override fun visitContentBlock(o: TypstContentBlock) {
        val key = o.computeRainbowKey()
        holder.highlight(o.firstChild, RAINBOW(key))
        holder.highlight(o.lastChild, RAINBOW(key))
    }

    override fun visitEmbeddedCode(o: TypstEmbeddedCode) {
        val attr = when (val line = o.line.firstChild) {
            is TypstStmt -> KEYWORD
            is TypstAtomic -> {
                when (val primary = line.primary) {
                    is TypstCodeBlock, is TypstContentBlock -> {
                        val key = primary.computeRainbowKey()
                        RAINBOW(key)
                    }
                    is TypstClosure -> {
                        val key = primary.parenthesized.computeRainbowKey()
                        RAINBOW(key)
                    }
                    is TypstParenthesized -> {
                        val key = primary.computeRainbowKey()
                        RAINBOW(key)
                    }
                    is TypstWhileExpr, is TypstForExpr, is TypstConditional -> KEYWORD
                    else -> null
                }
            }
            else -> null
        }
        attr?.let { holder.highlight(o.hash, attr) }
    }


    override fun visitEmptyDict(o: TypstEmptyDict) {
        val key = o.computeRainbowKey()
        holder.highlight(o.firstChild, RAINBOW(key))
        holder.highlight(o.lastChild, RAINBOW(key))
    }

    override fun visitEmptyParens(o: TypstEmptyParens) {
        val key = o.computeRainbowKey()
        holder.highlight(o, RAINBOW(key))
    }

    override fun visitEquation(o: TypstEquation) {
        holder.highlight(o.firstChild, MATHS)
        holder.highlight(o.lastChild, MATHS)
    }

    override fun visitEmph(o: TypstEmph) {
        val textKey = o.computeTextKey()
        holder.highlight(o.opening, textKey)
        o.closing?.let { holder.highlight(it, textKey) }
    }

    override fun visitJustText(o: TypstJustText) {
        holder.highlight(o, o.computeTextKey())
    }

    override fun visitMarkupSequence(o: TypstMarkupSequence) {
        var rainbow = o.computeRainbowKey()
        var indents: ArrayList<Int> = ArrayList()
        indents.add(0)
        var wasList = false
        var maxRainbowWas = 0
        for (paragraph in o.paragraphs) {
            val marker = paragraph.marker

            // Rainbow list marker
            val isList = marker?.elementType == LIST_MARKER || marker?.elementType == ENUM_MARKER
            if (isList) {
                val indent = marker!!.text.takeWhile { it.isWhitespace() }.length
                while (indent < indents.last()) {
                    try {
                        indents.removeAt(indents.size - 1)
                    } catch (t: Throwable) {
                        t.printStackTrace()
                    }
                }
                if (indent != indents.last()) {
                    indents.add(indent)
                }
                val key = rainbow + 1 +  indents.size * 3
                wasList = true
                holder.highlight(marker, RAINBOW(key ))
                maxRainbowWas = maxOf(maxRainbowWas, key)
            } else if (wasList) {
                wasList = false
                indents = ArrayList()
                indents.add(0)
                rainbow += maxRainbowWas
            }
        }
    }

    override fun visitMathExpressionLiteral(o: TypstMathExpressionLiteral) {
        holder.highlight(o, MATHS)
    }

    override fun visitParenthesized(o: TypstParenthesized) {
        val key = o.computeRainbowKey()
        holder.highlight(o.firstChild, RAINBOW(key))
        holder.highlight(o.lastChild, RAINBOW(key))
    }

    override fun visitRaw(o: TypstRaw) {
        holder.highlight(o, RAWS)
    }

    override fun visitReference(o: TypstReference) {
        holder.highlight(o, REFERENCES)
    }

    override fun visitStrong(o: TypstStrong) {
        val textKey = o.computeTextKey()
        holder.highlight(o.opening, textKey)
        o.closing?.let { holder.highlight(it, textKey) }
    }

    override fun visitElement(element: PsiElement) {
        when (element.elementType) {
            SPACE, HEADING_MARKER, TERM_MARKER, SMART_QUOTE -> holder.highlight(element, element.computeTextKey())
            COLON -> if (element.parent.elementType == PARAGRAPH) holder.highlight(element, TERM) else Unit
        }
    }
}
