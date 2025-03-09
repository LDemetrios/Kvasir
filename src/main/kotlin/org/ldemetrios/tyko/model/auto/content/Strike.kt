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
    "strike",
    ["strike", "content"],
    TStrikeImpl::class,
)
public interface TStrike : TContent {
    public val body: TContent

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    public val offset: TAutoOrLength?

    public val extent: TLength?

    public val background: TBool?

    override fun func(): TElement = TStrike

    public companion object : TElementImpl("strike") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val strokeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("stroke"), ConcreteType("tiling"))

        internal val offsetType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"))

        internal val extentType: InternalType = ConcreteType("length")

        internal val backgroundType: InternalType = ConcreteType("bool")
    }
}

internal data class TStrikeImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    @TSerialName("offset")
    override val offset: TAutoOrLength? = null,
    @TSerialName("extent")
    override val extent: TLength? = null,
    @TSerialName("background")
    override val background: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TStrike {
    override fun format(): String = Representations.elementRepr("strike",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "offset",
            `offset`),ArgumentEntry(false, "extent", `extent`),ArgumentEntry(false, "background",
            `background`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TStrike(
    @TContentBody body: TContent,
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    offset: TAutoOrLength? = null,
    extent: TLength? = null,
    background: TBool? = null,
    label: TLabel? = null,
): TStrike = TStrikeImpl(`body`, `stroke`, `offset`, `extent`, `background`, `label`)

@TSetRuleType(
    "strike",
    TSetStrikeImpl::class,
)
public interface TSetStrike : TSetRule {
    override val elem: String
        get() = "strike"

    public val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>?

    public val offset: TAutoOrLength?

    public val extent: TLength?

    public val background: TBool?

    override fun format(): String = Representations.setRepr("strike",ArgumentEntry(false, "stroke",
            `stroke`),ArgumentEntry(false, "offset", `offset`),ArgumentEntry(false, "extent",
            `extent`),ArgumentEntry(false, "background", `background`),)
}

internal class TSetStrikeImpl(
    @TSerialName("stroke")
    override val stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    @TSerialName("offset")
    override val offset: TAutoOrLength? = null,
    @TSerialName("extent")
    override val extent: TLength? = null,
    @TSerialName("background")
    override val background: TBool? = null,
) : TSetStrike

@TypstOverloads
public fun TSetStrike(
    stroke: TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TValue>? = null,
    offset: TAutoOrLength? = null,
    extent: TLength? = null,
    background: TBool? = null,
): TSetStrike = TSetStrikeImpl(`stroke`, `offset`, `extent`, `background`)
