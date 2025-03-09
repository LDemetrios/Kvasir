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
    "smartquote",
    ["smartquote", "content"],
    TSmartquoteImpl::class,
)
public interface TSmartquote : TContent {
    public val double: TBool?

    public val enabled: TBool?

    public val alternative: TBool?

    public val quotes: TArrayOrAutoOrDictionaryOrStr<TValue, TValue>?

    override fun func(): TElement = TSmartquote

    public companion object : TElementImpl("smartquote") {
        internal val doubleType: InternalType = ConcreteType("bool")

        internal val enabledType: InternalType = ConcreteType("bool")

        internal val alternativeType: InternalType = ConcreteType("bool")

        internal val quotesType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"), ConcreteType("dictionary", listOf(AnyType)), ConcreteType("str"))
    }
}

internal data class TSmartquoteImpl(
    @TSerialName("double")
    override val double: TBool? = null,
    @TSerialName("enabled")
    override val enabled: TBool? = null,
    @TSerialName("alternative")
    override val alternative: TBool? = null,
    @TSerialName("quotes")
    override val quotes: TArrayOrAutoOrDictionaryOrStr<TValue, TValue>? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TSmartquote {
    override fun format(): String = Representations.elementRepr("smartquote",ArgumentEntry(false,
            "double", `double`),ArgumentEntry(false, "enabled", `enabled`),ArgumentEntry(false,
            "alternative", `alternative`),ArgumentEntry(false, "quotes", `quotes`),ArgumentEntry(false,
            "label", `label`),)
}

@TypstOverloads
public fun TSmartquote(
    double: TBool? = null,
    enabled: TBool? = null,
    alternative: TBool? = null,
    quotes: TArrayOrAutoOrDictionaryOrStr<TValue, TValue>? = null,
    label: TLabel? = null,
): TSmartquote = TSmartquoteImpl(`double`, `enabled`, `alternative`, `quotes`, `label`)

@TSetRuleType(
    "smartquote",
    TSetSmartquoteImpl::class,
)
public interface TSetSmartquote : TSetRule {
    override val elem: String
        get() = "smartquote"

    public val double: TBool?

    public val enabled: TBool?

    public val alternative: TBool?

    public val quotes: TArrayOrAutoOrDictionaryOrStr<TValue, TValue>?

    override fun format(): String = Representations.setRepr("smartquote",ArgumentEntry(false,
            "double", `double`),ArgumentEntry(false, "enabled", `enabled`),ArgumentEntry(false,
            "alternative", `alternative`),ArgumentEntry(false, "quotes", `quotes`),)
}

internal class TSetSmartquoteImpl(
    @TSerialName("double")
    override val double: TBool? = null,
    @TSerialName("enabled")
    override val enabled: TBool? = null,
    @TSerialName("alternative")
    override val alternative: TBool? = null,
    @TSerialName("quotes")
    override val quotes: TArrayOrAutoOrDictionaryOrStr<TValue, TValue>? = null,
) : TSetSmartquote

@TypstOverloads
public fun TSetSmartquote(
    double: TBool? = null,
    enabled: TBool? = null,
    alternative: TBool? = null,
    quotes: TArrayOrAutoOrDictionaryOrStr<TValue, TValue>? = null,
): TSetSmartquote = TSetSmartquoteImpl(`double`, `enabled`, `alternative`, `quotes`)
