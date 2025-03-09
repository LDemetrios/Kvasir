package org.ldemetrios.kvasir.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import org.ldemetrios.tyko.compiler.SyntaxMode


inline fun <T> T.ensuring(message: () -> String = { "Unsatisfied predicate" }, predicate: (T) -> Boolean): T {
    if (!predicate(this)) throw AssertionError(message())
    return this
}

fun PsiElement.ensuringIs(type: IElementType) =
    this.ensuring(message = { "Expected $type, got ${this.node.elementType}" })
    { this.node.elementType == type }

inline fun PsiElement.nextSiblingOf(
    stop: ((PsiElement) -> Boolean) = { false },
    inclusive: Boolean = false,
    condition: (PsiElement) -> Boolean
): PsiElement? {
    var next = if (inclusive) this else this.nextSibling
    while (next != null && !stop(next)) {
        if (condition(next)) return next
        next = next.nextSibling
    }
    return null
}

inline fun PsiElement.prevSiblingOf(
    stop: ((PsiElement) -> Boolean) = { false },
    inclusive: Boolean = false,
    condition: (PsiElement) -> Boolean
): PsiElement? {
    var prev = if (inclusive) this else this.prevSibling
    while (prev != null && !stop(prev)) {
        if (condition(prev)) return prev
        prev = prev.prevSibling
    }
    return null
}

inline fun PsiElement.nextSiblingsWhile(
    stop: (PsiElement) -> Boolean = { false },
    inclusive: Boolean = false,
    condition: (PsiElement) -> Boolean,
): MutableList<PsiElement> {
    val result = mutableListOf<PsiElement>()
    var next = if (inclusive) this else this.nextSibling
    while (next != null && !stop(next)) {
        if (condition(next)) result.add(next)
        next = next.nextSibling
    }
    return result
}

inline fun PsiElement.prevSiblingsWhile(
    stop: ((PsiElement) -> Boolean) = { false },
    inclusive: Boolean = false,
    condition: (PsiElement) -> Boolean
): MutableList<PsiElement> {
    val result = mutableListOf<PsiElement>()
    var prev = if (inclusive) this else this.prevSibling
    while (prev != null && !stop(prev)) {
        if (condition(prev)) result.add(prev)
        prev = prev.prevSibling
    }
    return result.asReversed()
}

// Special options with type safety

inline fun <reified T : PsiElement> PsiElement.nextSiblingOfType(
    stop: ((PsiElement) -> Boolean) = { false },
    inclusive: Boolean = false,
): T? {
    var next = if (inclusive) this else this.nextSibling
    while (next != null && !stop(next)) {
        if (next is T) return next
        next = next.nextSibling
    }
    return null
}

inline fun <reified T : PsiElement> PsiElement.prevSiblingOfType(
    stop: ((PsiElement) -> Boolean) = { false },
    inclusive: Boolean = false,
): PsiElement? {
    var prev = if (inclusive) this else this.prevSibling
    while (prev != null && !stop(prev)) {
        if (prev is T) return prev
        prev = prev.prevSibling
    }
    return null
}

inline fun <reified T : PsiElement> PsiElement.nextSiblingsWhileIs(
    stop: (PsiElement) -> Boolean = { false },
    inclusive: Boolean = false,
): MutableList<T> {
    val result = mutableListOf<T>()
    var next = if (inclusive) this else this.nextSibling
    while (next != null && !stop(next)) {
        if (next is T) result.add(next)
        next = next.nextSibling
    }
    return result
}

inline fun <reified T : PsiElement> PsiElement.prevSiblingsWhileIs(
    stop: ((PsiElement) -> Boolean) = { false },
    inclusive: Boolean = false,
): MutableList<T> {
    val result = mutableListOf<T>()
    var prev = if (inclusive) this else this.prevSibling
    while (prev != null && !stop(prev)) {
        if (prev is T) result.add(prev)
        prev = prev.prevSibling
    }
    return result.asReversed()
}

fun PsiElement.modeAt(offset: Int): SyntaxMode? {
    var element: PsiElement? = this.findElementAt(offset) ?: return null
    while (element != null) {
        when (element) {
            is Keyword -> return SyntaxMode.Code
            is ArgsPsiElement -> Unit
            is ArrayPsiElement -> Unit
            is ArrowPsiElement -> return SyntaxMode.Code
            is BinaryPsiElement -> return SyntaxMode.Code
            is BlockCommentPsiElement -> Unit
            is ClosurePsiElement -> return SyntaxMode.Code
            is CodeBlockPsiElement -> Unit
            is CodePsiElement -> return SyntaxMode.Code
            is ColonPsiElement -> Unit
            is CommaPsiElement -> Unit
            is ConditionalPsiElement -> return SyntaxMode.Code
            is ContentBlockPsiElement -> return SyntaxMode.Markup // TODO check
            is ContextualPsiElement -> return SyntaxMode.Code
            is DestructAssignmentPsiElement -> return SyntaxMode.Code
            is DestructuringPsiElement -> return SyntaxMode.Code
            is DictPsiElement -> Unit
            is DollarPsiElement -> return SyntaxMode.Math
            is DotPsiElement -> Unit
            is DotsPsiElement -> Unit
            is EmbeddedCodePsiElement -> return SyntaxMode.Code
            is EmphPsiElement -> return SyntaxMode.Markup
            is EnumItemPsiElement -> return SyntaxMode.Markup
            is EnumMarkerPsiElement -> return SyntaxMode.Markup
            is EqEqPsiElement -> return SyntaxMode.Code
            is AssignOp -> Unit
            is EquationPsiElement -> return SyntaxMode.Math // TODO check
            is ErrorPsiElement -> return null
            is EscapePsiElement -> Unit
            is ExclEqPsiElement -> return SyntaxMode.Code
            is FieldAccessPsiElement -> Unit
            is FloatPsiElement -> return SyntaxMode.Code
            is ForLoopPsiElement -> return SyntaxMode.Code
            is FuncCallPsiElement -> Unit
            is FuncReturnPsiElement -> return SyntaxMode.Code
            is GtEqPsiElement -> return SyntaxMode.Code
            is GtPsiElement -> return SyntaxMode.Code
            is HashPsiElement -> return SyntaxMode.Markup // TODO Assume caret is before?
            is HatPsiElement -> return SyntaxMode.Math
            is HeadingMarkerPsiElement -> return SyntaxMode.Markup
            is HeadingPsiElement -> return SyntaxMode.Markup
            is IdentPsiElement -> Unit
            is ImportItemPathPsiElement -> return SyntaxMode.Code
            is ImportItemsPsiElement -> return SyntaxMode.Code
            is IntPsiElement -> Unit
            is KeyedPairPsiElement -> Unit
            is LabelPsiElement -> return SyntaxMode.Markup
            is LeftBracePsiElement -> Unit
            is LeftBracketPsiElement -> Unit
            is LeftParenPsiElement -> Unit
            is LetBindingPsiElement -> return SyntaxMode.Code
            is LineCommentPsiElement -> Unit
            is LinebreakPsiElement -> Unit
            is LinkPsiElement -> return SyntaxMode.Markup
            is ListItemPsiElement -> return SyntaxMode.Markup
            is ListMarkerPsiElement -> return SyntaxMode.Markup
            is LoopBreakPsiElement -> return SyntaxMode.Code
            is LoopContinuePsiElement -> return SyntaxMode.Code
            is LtEqPsiElement -> return SyntaxMode.Code
            is LtPsiElement -> return SyntaxMode.Code
            is MarkupPsiElement -> return SyntaxMode.Markup
            is MathAlignPointPsiElement -> return SyntaxMode.Math
            is MathAttachPsiElement -> return SyntaxMode.Math
            is MathDelimitedPsiElement -> return SyntaxMode.Math
            is MathFracPsiElement -> return SyntaxMode.Math
            is MathIdentPsiElement -> return SyntaxMode.Math
            is MathPrimesPsiElement -> return SyntaxMode.Math
            is MathPsiElement -> return SyntaxMode.Math
            is MathRootPsiElement -> return SyntaxMode.Math
            is MathShorthandPsiElement -> return SyntaxMode.Math
            is MathTextPsiElement -> return SyntaxMode.Math
            is MinusPsiElement -> Unit
            is ModuleImportPsiElement -> return SyntaxMode.Code
            is ModuleIncludePsiElement -> return SyntaxMode.Code
            is NamedPairPsiElement -> Unit
            is NumericPsiElement -> return SyntaxMode.Code
            is ParamsPsiElement -> Unit
            is ParbreakPsiElement -> Unit
            is ParenthesizedPsiElement -> return SyntaxMode.Code
            is PlusPsiElement -> return SyntaxMode.Code
            is PrimePsiElement -> return SyntaxMode.Math
            is RawPart -> Unit
            is RawPsiElement -> Unit
            is RefMarkerPsiElement -> return SyntaxMode.Markup
            is RefPsiElement -> return SyntaxMode.Markup
            is RenamedImportItemPsiElement -> return SyntaxMode.Code
            is RightBracePsiElement -> Unit
            is RightBracketPsiElement -> Unit
            is RightParenPsiElement -> Unit
            is RootPsiElement -> return SyntaxMode.Math
            is SemicolonPsiElement -> Unit
            is SetRulePsiElement -> return SyntaxMode.Code
            is ShebangPsiElement -> Unit
            is ShorthandPsiElement -> return SyntaxMode.Markup
            is ShowRulePsiElement -> return SyntaxMode.Code
            is SlashPsiElement -> Unit
            is SmartQuotePsiElement -> return SyntaxMode.Markup
            is SpacePsiElement -> Unit
            is SpreadPsiElement -> Unit
            is StarPsiElement -> Unit
            is StrPsiElement -> Unit
            is StrongPsiElement -> return SyntaxMode.Markup
            is TermItemPsiElement -> return SyntaxMode.Markup
            is TermMarkerPsiElement -> return SyntaxMode.Markup
            is TextPsiElement -> Unit
            is UnaryPsiElement -> return SyntaxMode.Code
            is UnderscorePsiElement -> Unit
            is WhileLoopPsiElement -> return SyntaxMode.Code
            is WhitespacePsiElement -> return SyntaxMode.Code
        }
        element = element.parent
    }
    return null
}
