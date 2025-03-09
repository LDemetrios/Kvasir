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
    "terms.item",
    ["terms.item", "content"],
    TTermsItemImpl::class,
)
public interface TTermsItem : TContent {
    public val term: TContent

    public val description: TContent

    override fun func(): TElement = TTermsItem

    public companion object : TElementImpl("terms.item") {
        internal val termType: InternalType = ConcreteType("content")

        internal val descriptionType: InternalType = ConcreteType("content")
    }
}

internal data class TTermsItemImpl(
    @TSerialName("term")
    override val term: TContent,
    @TSerialName("description")
    override val description: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TTermsItem {
    override fun format(): String = Representations.elementRepr("terms.item",ArgumentEntry(false,
            null, `term`),ArgumentEntry(false, null, `description`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TTermsItem(
    term: TContent,
    description: TContent,
    label: TLabel? = null,
): TTermsItem = TTermsItemImpl(`term`, `description`, `label`)
