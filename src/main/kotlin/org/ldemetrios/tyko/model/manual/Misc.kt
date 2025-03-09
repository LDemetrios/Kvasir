package org.ldemetrios.tyko.model

import org.ldemetrios.tyko.model.Representations.reprOf
import org.ldemetrios.tyko.model.pc
import org.ldemetrios.tyko.model.pt
import org.ldemetrios.tyko.model.t

val TRelative.abs: TLength?
    get() = when (this) {
        is TRatio -> 0.pt
        is TLength -> this
        is TRelativeImpl -> this.abs
        else -> throw AssertionError()
    }

val TRelative.rel: TRatio?
    get() = when (this) {
        is TRatio -> this
        is TLength -> 0.pc
        is TRelativeImpl -> this.rel
        else -> throw AssertionError()
    }

val TAlignment.horizontal: THAlignment?
    get() = when (this) {
        is THAlignment -> this
        is TVAlignment -> null
        is TAlignmentImpl -> this.horizontal
        else -> throw AssertionError()
    }

val TAlignment.vertical: TVAlignment?
    get() = when (this) {
        is THAlignment -> null
        is TVAlignment -> this
        is TAlignmentImpl -> this.vertical
        else -> throw AssertionError()
    }

interface TSetRule : TStyle, TContent {
    val elem: String
    override val label: TLabel? get() = null
    override fun type(): TType = TStyle
    override fun func(): TElement {
        TODO("Not yet implemented")
    }

    companion object : TTypeImpl("style") {
        val Elem = TElementImpl("set-rule")
    }
}

@TInterfaceType(
    "element",
    ["element", "selector", "function"],
    TElementImpl::class,
)
interface TElement : TFunction, TSelector {
    val name: TStr
    override fun format(): String = reprOf(this)
    override fun type(): TType = super<TFunction>.type()
}

open class TElementImpl(override val name: TStr) : TElement {
    constructor(name: String) : this(name.t)

    override fun equals(other: Any?): Boolean = other is TElementImpl && other.name == name

    override fun hashCode(): Int = name.hashCode()
}