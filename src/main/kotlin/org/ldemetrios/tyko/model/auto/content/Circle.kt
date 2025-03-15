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
    "circle",
    ["circle", "content"],
    TCircleImpl::class,
)
public interface TCircle : TContent {
    public val body: TContentOrNone?

    public val radius: TLength?

    public val width: TAutoOrRelative?

    public val height: TAutoOrFractionOrRelative?

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val inset: TDictionaryOrRelative<TValue>?

    public val outset: TDictionaryOrRelative<TValue>?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("circle")

        internal val bodyType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val radiusType: InternalType = ConcreteType("length")

        internal val widthType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("relative"))

        internal val heightType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("fraction"), ConcreteType("relative"))

        internal val fillType: InternalType = UnionType(ConcreteType("color"), ConcreteType("gradient"),
                ConcreteType("none"), ConcreteType("tiling"))

        internal val strokeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val insetType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("relative"))

        internal val outsetType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("relative"))
    }
}

internal data class TCircleImpl(
    @TSerialName("body")
    override val body: TContentOrNone? = null,
    @TSerialName("radius")
    override val radius: TLength? = null,
    @TSerialName("width")
    override val width: TAutoOrRelative? = null,
    @TSerialName("height")
    override val height: TAutoOrFractionOrRelative? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? =
            null,
    @TSerialName("inset")
    override val inset: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("outset")
    override val outset: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCircle {
    override fun format(): String = Representations.elementRepr("circle",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "radius", `radius`),ArgumentEntry(false, "width",
            `width`),ArgumentEntry(false, "height", `height`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "inset",
            `inset`),ArgumentEntry(false, "outset", `outset`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TCircle(
    body: TContentOrNone? = null,
    radius: TLength? = null,
    width: TAutoOrRelative? = null,
    height: TAutoOrFractionOrRelative? = null,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    inset: TDictionaryOrRelative<TValue>? = null,
    outset: TDictionaryOrRelative<TValue>? = null,
    label: TLabel? = null,
): TCircle = TCircleImpl(`body`, `radius`, `width`, `height`, `fill`, `stroke`, `inset`, `outset`,
        `label`)

@TSetRuleType(
    "circle",
    TSetCircleImpl::class,
)
public interface TSetCircle : TSetRule {
    override val elem: String
        get() = "circle"

    public val body: TContentOrNone?

    public val radius: TLength?

    public val width: TAutoOrRelative?

    public val height: TAutoOrFractionOrRelative?

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val inset: TDictionaryOrRelative<TValue>?

    public val outset: TDictionaryOrRelative<TValue>?

    override fun format(): String = Representations.setRepr("circle",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "radius", `radius`),ArgumentEntry(false, "width",
            `width`),ArgumentEntry(false, "height", `height`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "inset",
            `inset`),ArgumentEntry(false, "outset", `outset`),)
}

internal class TSetCircleImpl(
    @TSerialName("body")
    override val body: TContentOrNone? = null,
    @TSerialName("radius")
    override val radius: TLength? = null,
    @TSerialName("width")
    override val width: TAutoOrRelative? = null,
    @TSerialName("height")
    override val height: TAutoOrFractionOrRelative? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? =
            null,
    @TSerialName("inset")
    override val inset: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("outset")
    override val outset: TDictionaryOrRelative<TValue>? = null,
) : TSetCircle

@TypstOverloads
public fun TSetCircle(
    body: TContentOrNone? = null,
    radius: TLength? = null,
    width: TAutoOrRelative? = null,
    height: TAutoOrFractionOrRelative? = null,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    inset: TDictionaryOrRelative<TValue>? = null,
    outset: TDictionaryOrRelative<TValue>? = null,
): TSetCircle = TSetCircleImpl(`body`, `radius`, `width`, `height`, `fill`, `stroke`, `inset`,
        `outset`)
