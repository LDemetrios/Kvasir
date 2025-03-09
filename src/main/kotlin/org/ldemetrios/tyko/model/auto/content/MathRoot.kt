@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.String
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import org.ldemetrios.js.JSBoolean
import org.ldemetrios.js.JSNumber
import org.ldemetrios.js.JSObject
import org.ldemetrios.js.JSString
import org.ldemetrios.js.JSUndefined
import org.ldemetrios.utilities.cast
import org.ldemetrios.utilities.castUnchecked

@TInterfaceType(
    "math.root",
    ["math.root", "content"],
    TMathRootImpl::class,
)
public interface TMathRoot : TContent {
    public val index: TContentOrNone?

    public val radicand: TContent

    override fun func(): TElement = TMathRoot

    public companion object : TElementImpl("math.root") {
        internal val indexType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val radicandType: InternalType = ConcreteType("content")
    }
}

internal data class TMathRootImpl(
    @TSerialName("index")
    override val index: TContentOrNone? = null,
    @TSerialName("radicand")
    override val radicand: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathRoot {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TMathRoot(
    index: TContentOrNone? = null,
    radicand: TContent,
    label: TLabel? = null,
): TMathRoot = TMathRootImpl(`index`, `radicand`, `label`)

@TSetRuleType(
    "math.root",
    TSetMathRootImpl::class,
)
public interface TSetMathRoot : TSetRule {
    override val elem: String
        get() = "math.root"

    public val index: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.root",ArgumentEntry(false, null,
            `index`),)
}

internal class TSetMathRootImpl(
    @TSerialName("index")
    override val index: TContentOrNone? = null,
) : TSetMathRoot

@TypstOverloads
public fun TSetMathRoot(index: TContentOrNone? = null): TSetMathRoot = TSetMathRootImpl(`index`)
