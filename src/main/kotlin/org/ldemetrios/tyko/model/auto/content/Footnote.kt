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
    "footnote",
    ["footnote", "content"],
    TFootnoteImpl::class,
)
public interface TFootnote : TContent {
    public val body: TContentOrLabel

    public val numbering: TFunctionOrStr?

    override fun func(): TElement = TFootnote

    public companion object : TElementImpl("footnote") {
        internal val bodyType: InternalType = UnionType(ConcreteType("content"), ConcreteType("label"))

        internal val numberingType: InternalType = UnionType(ConcreteType("function"),
                ConcreteType("str"))
    }
}

internal data class TFootnoteImpl(
    @TSerialName("body")
    override val body: TContentOrLabel,
    @TSerialName("numbering")
    override val numbering: TFunctionOrStr? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TFootnote {
    override fun format(): String = Representations.elementRepr("footnote",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "numbering", `numbering`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TFootnote(
    body: TContentOrLabel,
    numbering: TFunctionOrStr? = null,
    label: TLabel? = null,
): TFootnote = TFootnoteImpl(`body`, `numbering`, `label`)

@TSetRuleType(
    "footnote",
    TSetFootnoteImpl::class,
)
public interface TSetFootnote : TSetRule {
    override val elem: String
        get() = "footnote"

    public val numbering: TFunctionOrStr?

    override fun format(): String = Representations.setRepr("footnote",ArgumentEntry(false,
            "numbering", `numbering`),)
}

internal class TSetFootnoteImpl(
    @TSerialName("numbering")
    override val numbering: TFunctionOrStr? = null,
) : TSetFootnote

@TypstOverloads
public fun TSetFootnote(numbering: TFunctionOrStr? = null): TSetFootnote =
        TSetFootnoteImpl(`numbering`)
