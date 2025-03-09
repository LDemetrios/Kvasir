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
    "curve",
    ["curve", "content"],
    TCurveImpl::class,
)
public interface TCurve : TContent {
    public val components: TArray<TContent>

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val fillRule: TFillRule?

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    override fun func(): TElement = TCurve

    public companion object : TElementImpl("curve") {
        internal val componentsType: InternalType = ConcreteType("array",
                listOf(ConcreteType("content")))

        internal val fillType: InternalType = UnionType(ConcreteType("color"), ConcreteType("gradient"),
                ConcreteType("none"), ConcreteType("tiling"))

        internal val fillRuleType: InternalType = ConcreteType("fill-rule")

        internal val strokeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))
    }
}

internal data class TCurveImpl(
    @TSerialName("components")
    override val components: TArray<TContent>,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("fill-rule")
    override val fillRule: TFillRule? = null,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? =
            null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCurve {
    override fun format(): String = Representations.elementRepr("curve",ArgumentEntry(true, null,
            `components`),ArgumentEntry(false, "fill", `fill`),ArgumentEntry(false, "fill-rule",
            `fillRule`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TCurve(
    @TVararg components: TArray<TContent>,
    fill: TColorOrGradientOrNoneOrTiling? = null,
    fillRule: TFillRule? = null,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    label: TLabel? = null,
): TCurve = TCurveImpl(`components`, `fill`, `fillRule`, `stroke`, `label`)

@TSetRuleType(
    "curve",
    TSetCurveImpl::class,
)
public interface TSetCurve : TSetRule {
    override val elem: String
        get() = "curve"

    public val fill: TColorOrGradientOrNoneOrTiling?

    public val fillRule: TFillRule?

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    override fun format(): String = Representations.setRepr("curve",ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "fill-rule", `fillRule`),ArgumentEntry(false, "stroke",
            `stroke`),)
}

internal class TSetCurveImpl(
    @TSerialName("fill")
    override val fill: TColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("fill-rule")
    override val fillRule: TFillRule? = null,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? =
            null,
) : TSetCurve

@TypstOverloads
public fun TSetCurve(
    fill: TColorOrGradientOrNoneOrTiling? = null,
    fillRule: TFillRule? = null,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
): TSetCurve = TSetCurveImpl(`fill`, `fillRule`, `stroke`)
