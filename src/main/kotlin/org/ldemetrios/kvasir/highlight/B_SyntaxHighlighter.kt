package org.ldemetrios.kvasir.highlight

import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.firstLeaf
import com.intellij.psi.util.lastLeaf
import org.ldemetrios.kvasir.psi.*
import org.ldemetrios.kvasir.settings.AppSettings
import org.ldemetrios.kvasir.syntax.TYPST_WHITESPACE
import kotlin.random.Random

fun AnnotationHolder.highlight(element: PsiElement?, attributes: TextAttributes?) {
    if (element != null && attributes != null)
        newSilentAnnotation(HighlightInfoType.SYMBOL_TYPE_SEVERITY)
            .range(element.textRange)
            .enforcedTextAttributes(attributes)
            .create()
}

internal class RainbowKey {
    private var rainbowKey: Int = -1
    private var scopeKey: Int = -1

    fun advance(n: Int = 1) {
        rainbowKey += n
        if (scopeKey != -1) scopeKey += n
    }

    fun scope(n: Int = 1) {
        rainbowKey += 1
        scopeKey += 1
    }

    fun scopeIf(condition: Boolean, n: Int = 1) {
        if (condition) scope(n)
        else advance(n)
    }

    operator fun component1() = rainbowKey
    operator fun component2() = scopeKey
}

internal class LazyRandomSeq(val seed: Long) {
    private val rnd = Random(seed)
    private var seq = IntArray(16)
    private var size = 0
    fun get(n: Int): Int {
        if (n >= seq.size) {
            val newSeq = IntArray(n.takeHighestOneBit() * 2)
            seq.copyInto(newSeq)
            seq = newSeq
        }
        while (size <= n) {
            seq[size] = rnd.nextInt()
            size++
        }
        return seq[n]
    }
}

internal val RAINBOW_SEQ = LazyRandomSeq(56623930)

fun resolveRainbowKey(n: Int): Int? {
    if (n == -1) return null
//    val color = RAINBOW_SEQ.get(n)
    val color = n * 6 + 6
    return (color % 11 + 11) % 11
}

class SyntaxHighlighter : Annotator {
    private lateinit var holder: AnnotationHolder
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        this.holder = holder
        if (element !is TypstPsiElement) return
        if (element is RawPsiElement || element is RawPart) return


        val children = element.children
        if (children.isNotEmpty()) {
            return
            // TODO Handle scopes later
            // highlight spaces
            val spaces = generateSequence(element.firstChild) { it.nextSibling }
                .filter { it is LeafPsiElement && it.tokenType === TYPST_WHITESPACE }.toList()
            if (spaces.isEmpty()) return
            val (rainbowKey, scopeKey) = computeRainbowKey(element)
            val attr = resolveRainbowKey(scopeKey)?.let { RAINBOW_BACK_WEAK[it].key.resolve() } ?: return
            spaces.forEach {
                holder.newSilentAnnotation(HighlightInfoType.SYMBOL_TYPE_SEVERITY)
                    .range(it.textRange)
                    .enforcedTextAttributes(attr)
                    .create()
            }
        } else {
            val colorize = when (element) {
                is HashPsiElement -> element.nextSibling.firstLeaf().parent
                is SemicolonPsiElement -> {
                    element.prevSibling.takeIf { it is EmbeddedCodePsiElement }?.lastLeaf()?.parent ?: element
                }

                else -> element
            }

            val lexemeKey = computeLexemeKey(colorize)
            val textKey = computeTextKey(colorize)
            val (rainbowKey, scopeKey) = computeRainbowKey(colorize)

            val scopeAttr = resolveRainbowKey(scopeKey)?.let { RAINBOW_BACK_WEAK[it].key.resolve() }
            val rainbowAttr = if (!AppSettings.instance.state.rainbow) null else  resolveRainbowKey(rainbowKey)?.takeIf {
                when (colorize) {
                    is LeftBracePsiElement, is LeftBracketPsiElement, is LeftParenPsiElement,
                    is RightBracePsiElement, is RightBracketPsiElement, is RightParenPsiElement,
                    is EnumMarkerPsiElement, is ListMarkerPsiElement -> true

                    is ColonPsiElement -> {
                        val parent = colorize.parent
                        parent is KeyedPairPsiElement || parent is NamedPairPsiElement
                    }

                    is CommaPsiElement -> {
//                      val parent = colorize.parent
//                      parent is KeyedPairPsiElement || parent is NamedPairPsiElement
                        true
                    }
                    is ArrowPsiElement -> true

                    is MathTextPsiElement -> colorize.parent is MathDelimitedPsiElement
                    else -> false
                }
            }?.let { RAINBOW[it].key.resolve() }
            val textAttr = textKey?.let { mergeAttributes(*it.map { it.resolve() }.toTypedArray()) }
            val lexemeAttr = lexemeKey?.resolve()

            val attr = mergeAttributesNoMix(
                *listOfNotNull(rainbowAttr, textAttr, lexemeAttr/*, scopeAttr*/).toTypedArray()
            )
            if (attr != null)
                holder.newSilentAnnotation(HighlightInfoType.SYMBOL_TYPE_SEVERITY)
                    .range(element.textRange)
                    .enforcedTextAttributes(attr)
                    .create()
        }
    }

    private fun computeRainbowKey(element: PsiElement): RainbowKey {
        val result = RainbowKey()
        var prev: PsiElement? = null
        var element: PsiElement = element
        val settings = AppSettings.instance.state
        while (element is ATypstPsiElement) {
            when (element) {
                is Keyword, is UnOp, is BinOp, is AssignOp, is Ignored, is NumericLiteral -> Unit
                is ArgsPsiElement -> result.scopeIf(settings.scopeParenthesis)
                is ArrayPsiElement -> result.scopeIf(settings.scopeParenthesis)
                is ArrowPsiElement -> result.advance()
                is BinaryPsiElement -> Unit
                is ClosurePsiElement -> Unit
                is CodeBlockPsiElement -> {
                    val constructionBody = when (val parent = element.parent) {
                        is ForLoopPsiElement, is WhileLoopPsiElement -> element == parent.lastChild
                        is ConditionalPsiElement -> element != parent.firstChild.nextSibling
                        else -> false
                    }
                    result.scopeIf(settings.scopeCodeBlocks, if (constructionBody) 4 else 1)
                }

                is CodePsiElement -> Unit
                is ColonPsiElement -> Unit
                is CommaPsiElement -> Unit
                is ConditionalPsiElement -> Unit
                is ContentBlockPsiElement -> {
                    if (element.parent !is RefPsiElement) {
                        result.scopeIf(settings.scopeContentBlocks)
                    } else {
                        val constructionBody = when (val parent = element.parent) {
                            is ForLoopPsiElement, is WhileLoopPsiElement -> element == parent.lastChild
                            is ConditionalPsiElement -> element != parent.firstChild.nextSibling
                            else -> false
                        }
                        result.advance(if (constructionBody) 4 else 1)
                    }
                }

                is ContextualPsiElement -> Unit
                is DestructAssignmentPsiElement -> Unit
                is DestructuringPsiElement -> result.advance()
                is DictPsiElement -> result.scopeIf(settings.scopeParenthesis)
                is DollarPsiElement -> Unit
                is DotPsiElement -> Unit
                is DotsPsiElement -> Unit
                is EmbeddedCodePsiElement -> Unit
                is EmphPsiElement -> Unit
                is EnumItemPsiElement -> result.advance(3)
                is EnumMarkerPsiElement -> Unit
                is EquationPsiElement -> Unit
                is ErrorPsiElement -> Unit
                is EscapePsiElement -> Unit
                is FieldAccessPsiElement -> Unit
                is ForLoopPsiElement -> Unit
                is FuncCallPsiElement -> Unit
                is FuncReturnPsiElement -> Unit
                is HashPsiElement -> Unit
                is HatPsiElement -> Unit
                is HeadingMarkerPsiElement -> Unit
                is HeadingPsiElement -> Unit
                is IdentPsiElement -> Unit
                is ImportItemPathPsiElement -> Unit
                is ImportItemsPsiElement -> Unit
                is KeyedPairPsiElement -> Unit
                is LabelPsiElement -> Unit
                is LeftBracePsiElement -> Unit
                is LeftBracketPsiElement -> Unit
                is LeftParenPsiElement -> Unit
                is LetBindingPsiElement -> Unit
                is LinebreakPsiElement -> Unit
                is LinkPsiElement -> Unit
                is ListItemPsiElement -> result.advance(3)
                is ListMarkerPsiElement -> Unit
                is LoopBreakPsiElement -> Unit
                is LoopContinuePsiElement -> Unit
                is MarkupPsiElement -> Unit
                is MathAlignPointPsiElement -> Unit
                is MathAttachPsiElement -> Unit
                is MathDelimitedPsiElement -> result.scopeIf(settings.scopeDelimited)
                is MathFracPsiElement -> Unit
                is MathIdentPsiElement -> Unit
                is MathPrimesPsiElement -> Unit
                is MathPsiElement -> Unit
                is MathRootPsiElement -> Unit
                is MathShorthandPsiElement -> Unit
                is MathTextPsiElement -> Unit
                is ModuleImportPsiElement -> Unit
                is ModuleIncludePsiElement -> Unit
                is NamedPairPsiElement -> Unit
                is ParamsPsiElement -> result.advance()
                is ParbreakPsiElement -> Unit
                is ParenthesizedPsiElement -> result.scopeIf(settings.scopeParenthesis)
                is PrimePsiElement -> Unit
                is RawDelimPsiElement -> Unit
                is RawLangPsiElement -> Unit
                is RawPsiElement -> Unit
                is RawTextPsiElement -> Unit
                is RawTrimmedPsiElement -> Unit
                is RefMarkerPsiElement -> if (element.parent.lastChild !is RefMarkerPsiElement) result.advance()
                is RefPsiElement -> Unit
                is RenamedImportItemPsiElement -> Unit
                is RightBracePsiElement -> Unit
                is RightBracketPsiElement -> Unit
                is RightParenPsiElement -> Unit
                is RootPsiElement -> Unit
                is SemicolonPsiElement -> Unit
                is SetRulePsiElement -> Unit
                is ShorthandPsiElement -> Unit
                is ShowRulePsiElement -> Unit
                is SmartQuotePsiElement -> Unit
                is SpacePsiElement -> Unit
                is SpreadPsiElement -> Unit
                is StrPsiElement -> Unit
                is StrongPsiElement -> Unit
                is TermItemPsiElement -> Unit
                is TermMarkerPsiElement -> Unit
                is TextPsiElement -> Unit
                is UnaryPsiElement -> Unit
                is UnderscorePsiElement -> Unit
                is WhileLoopPsiElement -> Unit
                is WhitespacePsiElement -> Unit
            }
            prev = element
            element = element.parent
        }

        return result
    }

    private fun computeTextKey(element: PsiElement): List<TextAttributesKey>? {
        val result = mutableListOf<TextAttributesKey>()
        var prev: PsiElement? = null
        var element: PsiElement = element
        while (element is ATypstPsiElement) {
            when (element) {
                is Keyword, is UnOp, is BinOp, is AssignOp, is Ignored, is NumericLiteral -> if (element !is StarPsiElement) return null
                is EquationPsiElement -> result.add(MATHS.key)
                is RawPart -> Unit
                is ContentBlockPsiElement -> {
                    if (element.parent is RefPsiElement) {
                        result.add(REFERENCES.key)
                    } else return result
                }
                is StrPsiElement -> Unit
                is FuncCallPsiElement, is FieldAccessPsiElement -> Unit
                is CodePart -> return result
                is Param -> if (element !is UnderscorePsiElement) return result
                is ArgsPsiElement -> Unit
                is ArrowPsiElement -> Unit
                is CodePsiElement -> Unit
                is ColonPsiElement -> Unit
                is CommaPsiElement -> Unit
                is DestructuringPsiElement -> Unit
                is DollarPsiElement -> Unit
                is DotPsiElement -> Unit
                is DotsPsiElement -> Unit
                is EmbeddedCodePsiElement -> return result
                is EmphPsiElement -> result.add(EMPH.key)
                is EnumItemPsiElement -> Unit
                is EnumMarkerPsiElement -> Unit
                is ErrorPsiElement -> Unit
                is EscapePsiElement -> Unit
                is HashPsiElement -> Unit
                is HatPsiElement -> Unit
                is HeadingMarkerPsiElement -> Unit
                is HeadingPsiElement -> result.add(HEADING.key)
                is IdentPsiElement -> Unit
                is ImportItemPathPsiElement -> Unit
                is ImportItemsPsiElement -> Unit
                is LabelPsiElement -> Unit
                is LeftBracePsiElement -> Unit
                is LeftBracketPsiElement -> Unit
                is LeftParenPsiElement -> Unit
                is LinebreakPsiElement -> Unit
                is LinkPsiElement -> Unit
                is ListItemPsiElement -> Unit
                is ListMarkerPsiElement -> Unit
                is MarkupPsiElement -> Unit
                is MathAlignPointPsiElement -> Unit
                is MathAttachPsiElement -> Unit
                is MathDelimitedPsiElement -> Unit
                is MathFracPsiElement -> Unit
                is MathIdentPsiElement -> Unit
                is MathPrimesPsiElement -> Unit
                is MathPsiElement -> Unit
                is MathRootPsiElement -> Unit
                is MathShorthandPsiElement -> Unit
                is MathTextPsiElement -> Unit
                is ParamsPsiElement -> Unit
                is ParbreakPsiElement -> Unit
                is PrimePsiElement -> Unit
                is RefMarkerPsiElement -> Unit
                is RefPsiElement -> Unit
                is RenamedImportItemPsiElement -> Unit
                is RightBracePsiElement -> Unit
                is RightBracketPsiElement -> Unit
                is RightParenPsiElement -> Unit
                is RootPsiElement -> Unit
                is SemicolonPsiElement -> Unit
                is ShorthandPsiElement -> Unit
                is SmartQuotePsiElement -> Unit
                is SpacePsiElement -> Unit
                is StrongPsiElement -> result.add(STRONG.key)
                is TermItemPsiElement -> {
                    if (prev?.nextSiblingOf(inclusive = true) { it is ColonPsiElement } != null) {
                        result.add(TERM.key)
                    }
                }

                is TermMarkerPsiElement -> Unit
                is TextPsiElement -> Unit
            }
            prev = element
            element = element.parent
        }

        return result
    }

    private fun computeLexemeKey(element: PsiElement): TextAttributesKey? {
        return when (element) {
            is KeywordLiteral -> KEYWORD_LITERAL
            is Keyword -> KEYWORD
            is NumericLiteral -> NUMERIC_LITERAL
            is StrPsiElement -> STRINGS
            is ShorthandPsiElement, is MathShorthandPsiElement, is LinebreakPsiElement
                -> SHORTHANDS

            is LinkPsiElement -> LINKS
            is RefMarkerPsiElement -> REFERENCES
            is EscapePsiElement -> ESCAPES
            is BinOp, is UnOp, is AssignOp -> OPERATOR
            is RawPart -> RAWS
            else -> null
        }?.key
    }
}
