package org.ldemetrios.tyko.model

import org.ldemetrios.tyko.model.Representations.reprOf
import org.ldemetrios.tyko.model.pc
import org.ldemetrios.tyko.model.pt
import org.ldemetrios.tyko.model.t

public interface TStyle : TValue, TContent {
    override fun type(): TType = Type
    override val label: TLabel? get() = null
    override fun func(): TElement {
        TODO("Not yet implemented")
    }

    public companion object {
        public val Type: TType = TTypeImpl("style")
    }
}

interface TSetRule : TStyle {
    val elem: String

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