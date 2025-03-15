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
    "math.lr",
    ["math.lr", "content"],
    TMathLrImpl::class,
)
public interface TMathLr : TContent {
    public val body: TContent

    public val sz: TAutoOrRelative?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.lr")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val szType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("relative"))
    }
}

internal data class TMathLrImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("size")
    override val sz: TAutoOrRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathLr {
    override fun format(): String = Representations.elementRepr("math.lr",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "size", `sz`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathLr(
    @TContentBody body: TContent,
    sz: TAutoOrRelative? = null,
    label: TLabel? = null,
): TMathLr = TMathLrImpl(`body`, `sz`, `label`)

@TSetRuleType(
    "math.lr",
    TSetMathLrImpl::class,
)
public interface TSetMathLr : TSetRule {
    override val elem: String
        get() = "math.lr"

    public val sz: TAutoOrRelative?

    override fun format(): String = Representations.setRepr("math.lr",ArgumentEntry(false, "size",
            `sz`),)
}

internal class TSetMathLrImpl(
    @TSerialName("size")
    override val sz: TAutoOrRelative? = null,
) : TSetMathLr

@TypstOverloads
public fun TSetMathLr(sz: TAutoOrRelative? = null): TSetMathLr = TSetMathLrImpl(`sz`)
