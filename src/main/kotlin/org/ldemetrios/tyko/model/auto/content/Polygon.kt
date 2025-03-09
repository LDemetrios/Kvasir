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
    "polygon",
    ["polygon", "content"],
    TPolygonImpl::class,
)
public interface TPolygon : TContent {
    public val vertices: TArray<TArray<TValue>>

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val fillRule: TFillRule?

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    override fun func(): TElement = TPolygon

    public companion object : TElementImpl("polygon") {
        internal val verticesType: InternalType = ConcreteType("array", listOf(ConcreteType("array",
                listOf(AnyType))))

        internal val fillType: InternalType = UnionType(ConcreteType("color"), ConcreteType("gradient"),
                ConcreteType("none"), ConcreteType("tiling"))

        internal val fillRuleType: InternalType = ConcreteType("fill-rule")

        internal val strokeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))
    }
}

internal data class TPolygonImpl(
    @TSerialName("vertices")
    override val vertices: TArray<TArray<TValue>>,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("fill-rule")
    override val fillRule: TFillRule? = null,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? =
            null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TPolygon {
    override fun format(): String = Representations.elementRepr("polygon",ArgumentEntry(true, null,
            `vertices`),ArgumentEntry(false, "fill", `fill`),ArgumentEntry(false, "fill-rule",
            `fillRule`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TPolygon(
    @TVararg vertices: TArray<TArray<TValue>>,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    fillRule: TFillRule? = null,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    label: TLabel? = null,
): TPolygon = TPolygonImpl(`vertices`, `fill`, `fillRule`, `stroke`, `label`)

@TSetRuleType(
    "polygon",
    TSetPolygonImpl::class,
)
public interface TSetPolygon : TSetRule {
    override val elem: String
        get() = "polygon"

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val fillRule: TFillRule?

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    override fun format(): String = Representations.setRepr("polygon",ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "fill-rule", `fillRule`),ArgumentEntry(false, "stroke",
            `stroke`),)
}

internal class TSetPolygonImpl(
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("fill-rule")
    override val fillRule: TFillRule? = null,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? =
            null,
) : TSetPolygon

@TypstOverloads
public fun TSetPolygon(
    fill: TColorOrGradientOrNoneOrTiling? = null,
    fillRule: TFillRule? = null,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
): TSetPolygon = TSetPolygonImpl(`fill`, `fillRule`, `stroke`)
