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
    "cite",
    ["cite", "content"],
    TCiteImpl::class,
)
public interface TCite : TContent {
    public val key: TLabel

    public val supplement: TContentOrNone?

    public val form: TCiteFormOrNone?

    public val style: TAutoOrBibliographyStyle?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("cite")

        internal val keyType: InternalType = ConcreteType("label")

        internal val supplementType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))

        internal val formType: InternalType = UnionType(ConcreteType("cite-form"), ConcreteType("none"))

        internal val styleType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("bibliography-style"))
    }
}

internal data class TCiteImpl(
    @TSerialName("key")
    override val key: TLabel,
    @TSerialName("supplement")
    override val supplement: TContentOrNone? = null,
    @TSerialName("form")
    override val form: TCiteFormOrNone? = null,
    @TSerialName("style")
    override val style: TAutoOrBibliographyStyle? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCite {
    override fun format(): String = Representations.elementRepr("cite",ArgumentEntry(false, null,
            `key`),ArgumentEntry(false, "supplement", `supplement`),ArgumentEntry(false, "form",
            `form`),ArgumentEntry(false, "style", `style`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TCite(
    key: TLabel,
    supplement: TContentOrNone? = null,
    form: TCiteFormOrNone? = null,
    style: TAutoOrBibliographyStyle? = null,
    label: TLabel? = null,
): TCite = TCiteImpl(`key`, `supplement`, `form`, `style`, `label`)

@TSetRuleType(
    "cite",
    TSetCiteImpl::class,
)
public interface TSetCite : TSetRule {
    override val elem: String
        get() = "cite"

    public val supplement: TContentOrNone?

    public val form: TCiteFormOrNone?

    public val style: TAutoOrBibliographyStyle?

    override fun format(): String = Representations.setRepr("cite",ArgumentEntry(false, "supplement",
            `supplement`),ArgumentEntry(false, "form", `form`),ArgumentEntry(false, "style", `style`),)
}

internal class TSetCiteImpl(
    @TSerialName("supplement")
    override val supplement: TContentOrNone? = null,
    @TSerialName("form")
    override val form: TCiteFormOrNone? = null,
    @TSerialName("style")
    override val style: TAutoOrBibliographyStyle? = null,
) : TSetCite

@TypstOverloads
public fun TSetCite(
    supplement: TContentOrNone? = null,
    form: TCiteFormOrNone? = null,
    style: TAutoOrBibliographyStyle? = null,
): TSetCite = TSetCiteImpl(`supplement`, `form`, `style`)
