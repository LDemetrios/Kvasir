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
    "grid.vline",
    ["grid.vline", "content"],
    TGridVlineImpl::class,
)
public interface TGridVline : TContent {
    public val x: TAutoOrInt?

    public val start: TInt?

    public val end: TIntOrNone?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val position: TAlignment?

    override fun func(): TElement = TGridVline

    public companion object : TElementImpl("grid.vline") {
        internal val xType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("int"))

        internal val startType: InternalType = ConcreteType("int")

        internal val endType: InternalType = UnionType(ConcreteType("int"), ConcreteType("none"))

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val positionType: InternalType = ConcreteType("alignment")
    }
}

internal data class TGridVlineImpl(
    @TSerialName("x")
    override val x: TAutoOrInt? = null,
    @TSerialName("start")
    override val start: TInt? = null,
    @TSerialName("end")
    override val end: TIntOrNone? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("position")
    override val position: TAlignment? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TGridVline {
    override fun format(): String = Representations.elementRepr("grid.vline",ArgumentEntry(false, "x",
            `x`),ArgumentEntry(false, "start", `start`),ArgumentEntry(false, "end",
            `end`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "position",
            `position`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TGridVline(
    x: TAutoOrInt? = null,
    start: TInt? = null,
    end: TIntOrNone? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    position: TAlignment? = null,
    label: TLabel? = null,
): TGridVline = TGridVlineImpl(`x`, `start`, `end`, `stroke`, `position`, `label`)

@TSetRuleType(
    "grid.vline",
    TSetGridVlineImpl::class,
)
public interface TSetGridVline : TSetRule {
    override val elem: String
        get() = "grid.vline"

    public val x: TAutoOrInt?

    public val start: TInt?

    public val end: TIntOrNone?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val position: TAlignment?

    override fun format(): String = Representations.setRepr("grid.vline",ArgumentEntry(false, "x",
            `x`),ArgumentEntry(false, "start", `start`),ArgumentEntry(false, "end",
            `end`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "position", `position`),)
}

internal class TSetGridVlineImpl(
    @TSerialName("x")
    override val x: TAutoOrInt? = null,
    @TSerialName("start")
    override val start: TInt? = null,
    @TSerialName("end")
    override val end: TIntOrNone? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("position")
    override val position: TAlignment? = null,
) : TSetGridVline

@TypstOverloads
public fun TSetGridVline(
    x: TAutoOrInt? = null,
    start: TInt? = null,
    end: TIntOrNone? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    position: TAlignment? = null,
): TSetGridVline = TSetGridVlineImpl(`x`, `start`, `end`, `stroke`, `position`)
