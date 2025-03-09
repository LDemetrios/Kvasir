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
    "highlight",
    ["highlight", "content"],
    THighlightImpl::class,
)
public interface THighlight : TContent {
    public val body: TContent

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val topEdge: TLengthOrTextTopEdge?

    public val bottomEdge: TLengthOrTextBottomEdge?

    public val extent: TLength?

    public val radius: TDictionaryOrRelative<TValue>?

    override fun func(): TElement = THighlight

    public companion object : TElementImpl("highlight") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val fillType: InternalType = UnionType(ConcreteType("color"), ConcreteType("gradient"),
                ConcreteType("none"), ConcreteType("tiling"))

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val topEdgeType: InternalType = UnionType(ConcreteType("length"),
                ConcreteType("text-top-edge"))

        internal val bottomEdgeType: InternalType = UnionType(ConcreteType("length"),
                ConcreteType("text-bottom-edge"))

        internal val extentType: InternalType = ConcreteType("length")

        internal val radiusType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("relative"))
    }
}

internal data class THighlightImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("top-edge")
    override val topEdge: TLengthOrTextTopEdge? = null,
    @TSerialName("bottom-edge")
    override val bottomEdge: TLengthOrTextBottomEdge? = null,
    @TSerialName("extent")
    override val extent: TLength? = null,
    @TSerialName("radius")
    override val radius: TDictionaryOrRelative<TValue>? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : THighlight {
    override fun format(): String = Representations.elementRepr("highlight",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "fill", `fill`),ArgumentEntry(false, "stroke",
            `stroke`),ArgumentEntry(false, "top-edge", `topEdge`),ArgumentEntry(false, "bottom-edge",
            `bottomEdge`),ArgumentEntry(false, "extent", `extent`),ArgumentEntry(false, "radius",
            `radius`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun THighlight(
    @TContentBody body: TContent,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    topEdge: TLengthOrTextTopEdge? = null,
    bottomEdge: TLengthOrTextBottomEdge? = null,
    extent: TLength? = null,
    radius: TDictionaryOrRelative<TValue>? = null,
    label: TLabel? = null,
): THighlight = THighlightImpl(`body`, `fill`, `stroke`, `topEdge`, `bottomEdge`, `extent`,
        `radius`, `label`)

@TSetRuleType(
    "highlight",
    TSetHighlightImpl::class,
)
public interface TSetHighlight : TSetRule {
    override val elem: String
        get() = "highlight"

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val topEdge: TLengthOrTextTopEdge?

    public val bottomEdge: TLengthOrTextBottomEdge?

    public val extent: TLength?

    public val radius: TDictionaryOrRelative<TValue>?

    override fun format(): String = Representations.setRepr("highlight",ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "top-edge",
            `topEdge`),ArgumentEntry(false, "bottom-edge", `bottomEdge`),ArgumentEntry(false, "extent",
            `extent`),ArgumentEntry(false, "radius", `radius`),)
}

internal class TSetHighlightImpl(
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("top-edge")
    override val topEdge: TLengthOrTextTopEdge? = null,
    @TSerialName("bottom-edge")
    override val bottomEdge: TLengthOrTextBottomEdge? = null,
    @TSerialName("extent")
    override val extent: TLength? = null,
    @TSerialName("radius")
    override val radius: TDictionaryOrRelative<TValue>? = null,
) : TSetHighlight

@TypstOverloads
public fun TSetHighlight(
    fill: TColorOrGradientOrNoneOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    topEdge: TLengthOrTextTopEdge? = null,
    bottomEdge: TLengthOrTextBottomEdge? = null,
    extent: TLength? = null,
    radius: TDictionaryOrRelative<TValue>? = null,
): TSetHighlight = TSetHighlightImpl(`fill`, `stroke`, `topEdge`, `bottomEdge`, `extent`, `radius`)
