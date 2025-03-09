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
    "underline",
    ["underline", "content"],
    TUnderlineImpl::class,
)
public interface TUnderline : TContent {
    public val body: TContent

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    public val offset: TAutoOrLength?

    public val extent: TLength?

    public val evade: TBool?

    public val background: TBool?

    override fun func(): TElement = TUnderline

    public companion object : TElementImpl("underline") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val strokeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("stroke"), ConcreteType("tiling"))

        internal val offsetType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"))

        internal val extentType: InternalType = ConcreteType("length")

        internal val evadeType: InternalType = ConcreteType("bool")

        internal val backgroundType: InternalType = ConcreteType("bool")
    }
}

internal data class TUnderlineImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    @TSerialName("offset")
    override val offset: TAutoOrLength? = null,
    @TSerialName("extent")
    override val extent: TLength? = null,
    @TSerialName("evade")
    override val evade: TBool? = null,
    @TSerialName("background")
    override val background: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TUnderline {
    override fun format(): String = Representations.elementRepr("underline",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "offset",
            `offset`),ArgumentEntry(false, "extent", `extent`),ArgumentEntry(false, "evade",
            `evade`),ArgumentEntry(false, "background", `background`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TUnderline(
    @TContentBody body: TContent,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    offset: TAutoOrLength? = null,
    extent: TLength? = null,
    evade: TBool? = null,
    background: TBool? = null,
    label: TLabel? = null,
): TUnderline = TUnderlineImpl(`body`, `stroke`, `offset`, `extent`, `evade`, `background`, `label`)

@TSetRuleType(
    "underline",
    TSetUnderlineImpl::class,
)
public interface TSetUnderline : TSetRule {
    override val elem: String
        get() = "underline"

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    public val offset: TAutoOrLength?

    public val extent: TLength?

    public val evade: TBool?

    public val background: TBool?

    override fun format(): String = Representations.setRepr("underline",ArgumentEntry(false, "stroke",
            `stroke`),ArgumentEntry(false, "offset", `offset`),ArgumentEntry(false, "extent",
            `extent`),ArgumentEntry(false, "evade", `evade`),ArgumentEntry(false, "background",
            `background`),)
}

internal class TSetUnderlineImpl(
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    @TSerialName("offset")
    override val offset: TAutoOrLength? = null,
    @TSerialName("extent")
    override val extent: TLength? = null,
    @TSerialName("evade")
    override val evade: TBool? = null,
    @TSerialName("background")
    override val background: TBool? = null,
) : TSetUnderline

@TypstOverloads
public fun TSetUnderline(
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    offset: TAutoOrLength? = null,
    extent: TLength? = null,
    evade: TBool? = null,
    background: TBool? = null,
): TSetUnderline = TSetUnderlineImpl(`stroke`, `offset`, `extent`, `evade`, `background`)
