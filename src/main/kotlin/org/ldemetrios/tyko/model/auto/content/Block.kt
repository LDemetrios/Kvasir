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
    "block",
    ["block", "content"],
    TBlockImpl::class,
)
public interface TBlock : TContent {
    public val body: TContentOrNone?

    public val width: TAutoOrRelative?

    public val height: TAutoOrFractionOrRelative?

    public val breakable: TBool?

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val radius: TDictionaryOrRelative<TValue>?

    public val inset: TRelativeOrSides<TNoneOrRelative>?

    public val outset: TRelativeOrSides<TNoneOrRelative>?

    public val spacing: TFractionOrRelative?

    public val above: TAutoOrFractionOrRelative?

    public val below: TAutoOrFractionOrRelative?

    public val clip: TBool?

    public val sticky: TBool?

    override fun func(): TElement = TBlock

    public companion object : TElementImpl("block") {
        internal val bodyType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val widthType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("relative"))

        internal val heightType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("fraction"), ConcreteType("relative"))

        internal val breakableType: InternalType = ConcreteType("bool")

        internal val fillType: InternalType = UnionType(ConcreteType("color"), ConcreteType("gradient"),
                ConcreteType("none"), ConcreteType("tiling"))

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val radiusType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("relative"))

        internal val insetType: InternalType = UnionType(ConcreteType("relative"), ConcreteType("sides",
                listOf(UnionType(ConcreteType("none"), ConcreteType("relative")))))

        internal val outsetType: InternalType = UnionType(ConcreteType("relative"),
                ConcreteType("sides", listOf(UnionType(ConcreteType("none"), ConcreteType("relative")))))

        internal val spacingType: InternalType = UnionType(ConcreteType("fraction"),
                ConcreteType("relative"))

        internal val aboveType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("fraction"),
                ConcreteType("relative"))

        internal val belowType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("fraction"),
                ConcreteType("relative"))

        internal val clipType: InternalType = ConcreteType("bool")

        internal val stickyType: InternalType = ConcreteType("bool")
    }
}

internal data class TBlockImpl(
    @TSerialName("body")
    override val body: TContentOrNone? = null,
    @TSerialName("width")
    override val width: TAutoOrRelative? = null,
    @TSerialName("height")
    override val height: TAutoOrFractionOrRelative? = null,
    @TSerialName("breakable")
    override val breakable: TBool? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("radius")
    override val radius: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("inset")
    override val inset: TRelativeOrSides<TNoneOrRelative>? = null,
    @TSerialName("outset")
    override val outset: TRelativeOrSides<TNoneOrRelative>? = null,
    @TSerialName("spacing")
    override val spacing: TFractionOrRelative? = null,
    @TSerialName("above")
    override val above: TAutoOrFractionOrRelative? = null,
    @TSerialName("below")
    override val below: TAutoOrFractionOrRelative? = null,
    @TSerialName("clip")
    override val clip: TBool? = null,
    @TSerialName("sticky")
    override val sticky: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TBlock {
    override fun format(): String = Representations.elementRepr("block",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "width", `width`),ArgumentEntry(false, "height",
            `height`),ArgumentEntry(false, "breakable", `breakable`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "radius",
            `radius`),ArgumentEntry(false, "inset", `inset`),ArgumentEntry(false, "outset",
            `outset`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false, "above",
            `above`),ArgumentEntry(false, "below", `below`),ArgumentEntry(false, "clip",
            `clip`),ArgumentEntry(false, "sticky", `sticky`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TBlock(
    body: TContentOrNone? = null,
    width: TAutoOrRelative? = null,
    height: TAutoOrFractionOrRelative? = null,
    breakable: TBool? = null,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    radius: TDictionaryOrRelative<TValue>? = null,
    inset: TRelativeOrSides<TNoneOrRelative>? = null,
    outset: TRelativeOrSides<TNoneOrRelative>? = null,
    spacing: TFractionOrRelative? = null,
    above: TAutoOrFractionOrRelative? = null,
    below: TAutoOrFractionOrRelative? = null,
    clip: TBool? = null,
    sticky: TBool? = null,
    label: TLabel? = null,
): TBlock = TBlockImpl(`body`, `width`, `height`, `breakable`, `fill`, `stroke`, `radius`, `inset`,
        `outset`, `spacing`, `above`, `below`, `clip`, `sticky`, `label`)

@TSetRuleType(
    "block",
    TSetBlockImpl::class,
)
public interface TSetBlock : TSetRule {
    override val elem: String
        get() = "block"

    public val body: TContentOrNone?

    public val width: TAutoOrRelative?

    public val height: TAutoOrFractionOrRelative?

    public val breakable: TBool?

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val radius: TDictionaryOrRelative<TValue>?

    public val inset: TRelativeOrSides<TNoneOrRelative>?

    public val outset: TRelativeOrSides<TNoneOrRelative>?

    public val spacing: TFractionOrRelative?

    public val above: TAutoOrFractionOrRelative?

    public val below: TAutoOrFractionOrRelative?

    public val clip: TBool?

    public val sticky: TBool?

    override fun format(): String = Representations.setRepr("block",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "width", `width`),ArgumentEntry(false, "height",
            `height`),ArgumentEntry(false, "breakable", `breakable`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "radius",
            `radius`),ArgumentEntry(false, "inset", `inset`),ArgumentEntry(false, "outset",
            `outset`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false, "above",
            `above`),ArgumentEntry(false, "below", `below`),ArgumentEntry(false, "clip",
            `clip`),ArgumentEntry(false, "sticky", `sticky`),)
}

internal class TSetBlockImpl(
    @TSerialName("body")
    override val body: TContentOrNone? = null,
    @TSerialName("width")
    override val width: TAutoOrRelative? = null,
    @TSerialName("height")
    override val height: TAutoOrFractionOrRelative? = null,
    @TSerialName("breakable")
    override val breakable: TBool? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("radius")
    override val radius: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("inset")
    override val inset: TRelativeOrSides<TNoneOrRelative>? = null,
    @TSerialName("outset")
    override val outset: TRelativeOrSides<TNoneOrRelative>? = null,
    @TSerialName("spacing")
    override val spacing: TFractionOrRelative? = null,
    @TSerialName("above")
    override val above: TAutoOrFractionOrRelative? = null,
    @TSerialName("below")
    override val below: TAutoOrFractionOrRelative? = null,
    @TSerialName("clip")
    override val clip: TBool? = null,
    @TSerialName("sticky")
    override val sticky: TBool? = null,
) : TSetBlock

@TypstOverloads
public fun TSetBlock(
    body: TContentOrNone? = null,
    width: TAutoOrRelative? = null,
    height: TAutoOrFractionOrRelative? = null,
    breakable: TBool? = null,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    radius: TDictionaryOrRelative<TValue>? = null,
    inset: TRelativeOrSides<TNoneOrRelative>? = null,
    outset: TRelativeOrSides<TNoneOrRelative>? = null,
    spacing: TFractionOrRelative? = null,
    above: TAutoOrFractionOrRelative? = null,
    below: TAutoOrFractionOrRelative? = null,
    clip: TBool? = null,
    sticky: TBool? = null,
): TSetBlock = TSetBlockImpl(`body`, `width`, `height`, `breakable`, `fill`, `stroke`, `radius`,
        `inset`, `outset`, `spacing`, `above`, `below`, `clip`, `sticky`)
