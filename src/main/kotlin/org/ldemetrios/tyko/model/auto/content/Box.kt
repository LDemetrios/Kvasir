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
    "box",
    ["box", "content"],
    TBoxImpl::class,
)
public interface TBox : TContent {
    public val body: TContentOrNone?

    public val width: TAutoOrFractionOrRelative?

    public val height: TAutoOrRelative?

    public val baseline: TRelative?

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val radius: TDictionaryOrRelative<TValue>?

    public val inset: TDictionaryOrRelative<TValue>?

    public val outset: TDictionaryOrRelative<TValue>?

    public val clip: TBool?

    override fun func(): TElement = TBox

    public companion object : TElementImpl("box") {
        internal val bodyType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val widthType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("fraction"),
                ConcreteType("relative"))

        internal val heightType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("relative"))

        internal val baselineType: InternalType = ConcreteType("relative")

        internal val fillType: InternalType = UnionType(ConcreteType("color"), ConcreteType("gradient"),
                ConcreteType("none"), ConcreteType("tiling"))

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val radiusType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("relative"))

        internal val insetType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("relative"))

        internal val outsetType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("relative"))

        internal val clipType: InternalType = ConcreteType("bool")
    }
}

internal data class TBoxImpl(
    @TSerialName("body")
    override val body: TContentOrNone? = null,
    @TSerialName("width")
    override val width: TAutoOrFractionOrRelative? = null,
    @TSerialName("height")
    override val height: TAutoOrRelative? = null,
    @TSerialName("baseline")
    override val baseline: TRelative? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("radius")
    override val radius: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("inset")
    override val inset: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("outset")
    override val outset: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("clip")
    override val clip: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TBox {
    override fun format(): String = Representations.elementRepr("box",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "width", `width`),ArgumentEntry(false, "height",
            `height`),ArgumentEntry(false, "baseline", `baseline`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "radius",
            `radius`),ArgumentEntry(false, "inset", `inset`),ArgumentEntry(false, "outset",
            `outset`),ArgumentEntry(false, "clip", `clip`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TBox(
    body: TContentOrNone? = null,
    width: TAutoOrFractionOrRelative? = null,
    height: TAutoOrRelative? = null,
    baseline: TRelative? = null,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    radius: TDictionaryOrRelative<TValue>? = null,
    inset: TDictionaryOrRelative<TValue>? = null,
    outset: TDictionaryOrRelative<TValue>? = null,
    clip: TBool? = null,
    label: TLabel? = null,
): TBox = TBoxImpl(`body`, `width`, `height`, `baseline`, `fill`, `stroke`, `radius`, `inset`,
        `outset`, `clip`, `label`)

@TSetRuleType(
    "box",
    TSetBoxImpl::class,
)
public interface TSetBox : TSetRule {
    override val elem: String
        get() = "box"

    public val body: TContentOrNone?

    public val width: TAutoOrFractionOrRelative?

    public val height: TAutoOrRelative?

    public val baseline: TRelative?

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val radius: TDictionaryOrRelative<TValue>?

    public val inset: TDictionaryOrRelative<TValue>?

    public val outset: TDictionaryOrRelative<TValue>?

    public val clip: TBool?

    override fun format(): String = Representations.setRepr("box",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "width", `width`),ArgumentEntry(false, "height",
            `height`),ArgumentEntry(false, "baseline", `baseline`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "radius",
            `radius`),ArgumentEntry(false, "inset", `inset`),ArgumentEntry(false, "outset",
            `outset`),ArgumentEntry(false, "clip", `clip`),)
}

internal class TSetBoxImpl(
    @TSerialName("body")
    override val body: TContentOrNone? = null,
    @TSerialName("width")
    override val width: TAutoOrFractionOrRelative? = null,
    @TSerialName("height")
    override val height: TAutoOrRelative? = null,
    @TSerialName("baseline")
    override val baseline: TRelative? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("radius")
    override val radius: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("inset")
    override val inset: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("outset")
    override val outset: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("clip")
    override val clip: TBool? = null,
) : TSetBox

@TypstOverloads
public fun TSetBox(
    body: TContentOrNone? = null,
    width: TAutoOrFractionOrRelative? = null,
    height: TAutoOrRelative? = null,
    baseline: TRelative? = null,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    radius: TDictionaryOrRelative<TValue>? = null,
    inset: TDictionaryOrRelative<TValue>? = null,
    outset: TDictionaryOrRelative<TValue>? = null,
    clip: TBool? = null,
): TSetBox = TSetBoxImpl(`body`, `width`, `height`, `baseline`, `fill`, `stroke`, `radius`, `inset`,
        `outset`, `clip`)
