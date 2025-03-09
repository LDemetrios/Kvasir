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
    "line",
    ["line", "content"],
    TLineImpl::class,
)
public interface TLine : TContent {
    public val start: TArray<TValue>?

    public val end: TArrayOrNone<TValue>?

    public val length: TRelative?

    public val angle: TAngle?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    override fun func(): TElement = TLine

    public companion object : TElementImpl("line") {
        internal val startType: InternalType = ConcreteType("array", listOf(AnyType))

        internal val endType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("none"))

        internal val lengthType: InternalType = ConcreteType("relative")

        internal val angleType: InternalType = ConcreteType("angle")

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("stroke"), ConcreteType("tiling"))
    }
}

internal data class TLineImpl(
    @TSerialName("start")
    override val start: TArray<TValue>? = null,
    @TSerialName("end")
    override val end: TArrayOrNone<TValue>? = null,
    @TSerialName("length")
    override val length: TRelative? = null,
    @TSerialName("angle")
    override val angle: TAngle? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TLine {
    override fun format(): String = Representations.elementRepr("line",ArgumentEntry(false, "start",
            `start`),ArgumentEntry(false, "end", `end`),ArgumentEntry(false, "length",
            `length`),ArgumentEntry(false, "angle", `angle`),ArgumentEntry(false, "stroke",
            `stroke`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TLine(
    start: TArray<TValue>? = null,
    end: TArrayOrNone<TValue>? = null,
    length: TRelative? = null,
    angle: TAngle? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    label: TLabel? = null,
): TLine = TLineImpl(`start`, `end`, `length`, `angle`, `stroke`, `label`)

@TSetRuleType(
    "line",
    TSetLineImpl::class,
)
public interface TSetLine : TSetRule {
    override val elem: String
        get() = "line"

    public val start: TArray<TValue>?

    public val end: TArrayOrNone<TValue>?

    public val length: TRelative?

    public val angle: TAngle?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    override fun format(): String = Representations.setRepr("line",ArgumentEntry(false, "start",
            `start`),ArgumentEntry(false, "end", `end`),ArgumentEntry(false, "length",
            `length`),ArgumentEntry(false, "angle", `angle`),ArgumentEntry(false, "stroke", `stroke`),)
}

internal class TSetLineImpl(
    @TSerialName("start")
    override val start: TArray<TValue>? = null,
    @TSerialName("end")
    override val end: TArrayOrNone<TValue>? = null,
    @TSerialName("length")
    override val length: TRelative? = null,
    @TSerialName("angle")
    override val angle: TAngle? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
) : TSetLine

@TypstOverloads
public fun TSetLine(
    start: TArray<TValue>? = null,
    end: TArrayOrNone<TValue>? = null,
    length: TRelative? = null,
    angle: TAngle? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
): TSetLine = TSetLineImpl(`start`, `end`, `length`, `angle`, `stroke`)
