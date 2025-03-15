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
    "quote",
    ["quote", "content"],
    TQuoteImpl::class,
)
public interface TQuote : TContent {
    public val body: TContent

    public val block: TBool?

    public val quotes: TAutoOrBool?

    public val attribution: TContentOrLabelOrNone?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("quote")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val blockType: InternalType = ConcreteType("bool")

        internal val quotesType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("bool"))

        internal val attributionType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("label"), ConcreteType("none"))
    }
}

internal data class TQuoteImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("block")
    override val block: TBool? = null,
    @TSerialName("quotes")
    override val quotes: TAutoOrBool? = null,
    @TSerialName("attribution")
    override val attribution: TContentOrLabelOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TQuote {
    override fun format(): String = Representations.elementRepr("quote",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "block", `block`),ArgumentEntry(false, "quotes",
            `quotes`),ArgumentEntry(false, "attribution", `attribution`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TQuote(
    @TContentBody body: TContent,
    block: TBool? = null,
    quotes: TAutoOrBool? = null,
    attribution: TContentOrLabelOrNone? = null,
    label: TLabel? = null,
): TQuote = TQuoteImpl(`body`, `block`, `quotes`, `attribution`, `label`)

@TSetRuleType(
    "quote",
    TSetQuoteImpl::class,
)
public interface TSetQuote : TSetRule {
    override val elem: String
        get() = "quote"

    public val block: TBool?

    public val quotes: TAutoOrBool?

    public val attribution: TContentOrLabelOrNone?

    override fun format(): String = Representations.setRepr("quote",ArgumentEntry(false, "block",
            `block`),ArgumentEntry(false, "quotes", `quotes`),ArgumentEntry(false, "attribution",
            `attribution`),)
}

internal class TSetQuoteImpl(
    @TSerialName("block")
    override val block: TBool? = null,
    @TSerialName("quotes")
    override val quotes: TAutoOrBool? = null,
    @TSerialName("attribution")
    override val attribution: TContentOrLabelOrNone? = null,
) : TSetQuote

@TypstOverloads
public fun TSetQuote(
    block: TBool? = null,
    quotes: TAutoOrBool? = null,
    attribution: TContentOrLabelOrNone? = null,
): TSetQuote = TSetQuoteImpl(`block`, `quotes`, `attribution`)
