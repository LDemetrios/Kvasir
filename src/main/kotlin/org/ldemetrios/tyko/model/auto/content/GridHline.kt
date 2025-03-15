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
    "grid.hline",
    ["grid.hline", "content"],
    TGridHlineImpl::class,
)
public interface TGridHline : TContent {
    public val y: TAutoOrInt?

    public val start: TInt?

    public val end: TIntOrNone?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val position: TAlignment?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("grid.hline")

        internal val yType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("int"))

        internal val startType: InternalType = ConcreteType("int")

        internal val endType: InternalType = UnionType(ConcreteType("int"), ConcreteType("none"))

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val positionType: InternalType = ConcreteType("alignment")
    }
}

internal data class TGridHlineImpl(
    @TSerialName("y")
    override val y: TAutoOrInt? = null,
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
) : TGridHline {
    override fun format(): String = Representations.elementRepr("grid.hline",ArgumentEntry(false, "y",
            `y`),ArgumentEntry(false, "start", `start`),ArgumentEntry(false, "end",
            `end`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "position",
            `position`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TGridHline(
    y: TAutoOrInt? = null,
    start: TInt? = null,
    end: TIntOrNone? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    position: TAlignment? = null,
    label: TLabel? = null,
): TGridHline = TGridHlineImpl(`y`, `start`, `end`, `stroke`, `position`, `label`)

@TSetRuleType(
    "grid.hline",
    TSetGridHlineImpl::class,
)
public interface TSetGridHline : TSetRule {
    override val elem: String
        get() = "grid.hline"

    public val y: TAutoOrInt?

    public val start: TInt?

    public val end: TIntOrNone?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val position: TAlignment?

    override fun format(): String = Representations.setRepr("grid.hline",ArgumentEntry(false, "y",
            `y`),ArgumentEntry(false, "start", `start`),ArgumentEntry(false, "end",
            `end`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "position", `position`),)
}

internal class TSetGridHlineImpl(
    @TSerialName("y")
    override val y: TAutoOrInt? = null,
    @TSerialName("start")
    override val start: TInt? = null,
    @TSerialName("end")
    override val end: TIntOrNone? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("position")
    override val position: TAlignment? = null,
) : TSetGridHline

@TypstOverloads
public fun TSetGridHline(
    y: TAutoOrInt? = null,
    start: TInt? = null,
    end: TIntOrNone? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    position: TAlignment? = null,
): TSetGridHline = TSetGridHlineImpl(`y`, `start`, `end`, `stroke`, `position`)
