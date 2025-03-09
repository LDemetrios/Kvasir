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
    "overline",
    ["overline", "content"],
    TOverlineImpl::class,
)
public interface TOverline : TContent {
    public val body: TContent

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    public val offset: TAutoOrLength?

    public val extent: TLength?

    public val evade: TBool?

    public val background: TBool?

    override fun func(): TElement = TOverline

    public companion object : TElementImpl("overline") {
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

internal data class TOverlineImpl(
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
) : TOverline {
    override fun format(): String = Representations.elementRepr("overline",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "offset",
            `offset`),ArgumentEntry(false, "extent", `extent`),ArgumentEntry(false, "evade",
            `evade`),ArgumentEntry(false, "background", `background`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TOverline(
    @TContentBody body: TContent,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    offset: TAutoOrLength? = null,
    extent: TLength? = null,
    evade: TBool? = null,
    background: TBool? = null,
    label: TLabel? = null,
): TOverline = TOverlineImpl(`body`, `stroke`, `offset`, `extent`, `evade`, `background`, `label`)

@TSetRuleType(
    "overline",
    TSetOverlineImpl::class,
)
public interface TSetOverline : TSetRule {
    override val elem: String
        get() = "overline"

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    public val offset: TAutoOrLength?

    public val extent: TLength?

    public val evade: TBool?

    public val background: TBool?

    override fun format(): String = Representations.setRepr("overline",ArgumentEntry(false, "stroke",
            `stroke`),ArgumentEntry(false, "offset", `offset`),ArgumentEntry(false, "extent",
            `extent`),ArgumentEntry(false, "evade", `evade`),ArgumentEntry(false, "background",
            `background`),)
}

internal class TSetOverlineImpl(
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
) : TSetOverline

@TypstOverloads
public fun TSetOverline(
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    offset: TAutoOrLength? = null,
    extent: TLength? = null,
    evade: TBool? = null,
    background: TBool? = null,
): TSetOverline = TSetOverlineImpl(`stroke`, `offset`, `extent`, `evade`, `background`)
