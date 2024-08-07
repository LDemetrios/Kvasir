package org.ldemetrios.kvasir.syntax.psiExt

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType


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

inline fun <reified T: PsiElement> PsiElement.nextSiblingOfType(
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

inline fun <reified T: PsiElement> PsiElement.prevSiblingOfType(
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

inline fun <reified T: PsiElement> PsiElement.nextSiblingsWhileIs(
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

inline fun <reified T: PsiElement>  PsiElement.prevSiblingsWhileIs(
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


