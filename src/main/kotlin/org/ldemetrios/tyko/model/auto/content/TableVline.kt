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
    "table.vline",
    ["table.vline", "content"],
    TTableVlineImpl::class,
)
public interface TTableVline : TContent {
    public val x: TAutoOrInt?

    public val start: TInt?

    public val end: TIntOrNone?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val position: TAlignment?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("table.vline")

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

internal data class TTableVlineImpl(
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
) : TTableVline {
    override fun format(): String = Representations.elementRepr("table.vline",ArgumentEntry(false,
            "x", `x`),ArgumentEntry(false, "start", `start`),ArgumentEntry(false, "end",
            `end`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "position",
            `position`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TTableVline(
    x: TAutoOrInt? = null,
    start: TInt? = null,
    end: TIntOrNone? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    position: TAlignment? = null,
    label: TLabel? = null,
): TTableVline = TTableVlineImpl(`x`, `start`, `end`, `stroke`, `position`, `label`)

@TSetRuleType(
    "table.vline",
    TSetTableVlineImpl::class,
)
public interface TSetTableVline : TSetRule {
    override val elem: String
        get() = "table.vline"

    public val x: TAutoOrInt?

    public val start: TInt?

    public val end: TIntOrNone?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val position: TAlignment?

    override fun format(): String = Representations.setRepr("table.vline",ArgumentEntry(false, "x",
            `x`),ArgumentEntry(false, "start", `start`),ArgumentEntry(false, "end",
            `end`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "position", `position`),)
}

internal class TSetTableVlineImpl(
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
) : TSetTableVline

@TypstOverloads
public fun TSetTableVline(
    x: TAutoOrInt? = null,
    start: TInt? = null,
    end: TIntOrNone? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    position: TAlignment? = null,
): TSetTableVline = TSetTableVlineImpl(`x`, `start`, `end`, `stroke`, `position`)
