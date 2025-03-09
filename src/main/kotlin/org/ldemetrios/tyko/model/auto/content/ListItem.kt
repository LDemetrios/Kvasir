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
    "list.item",
    ["list.item", "content"],
    TListItemImpl::class,
)
public interface TListItem : TContent {
    public val body: TContent

    override fun func(): TElement = TListItem

    public companion object : TElementImpl("list.item") {
        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TListItemImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TListItem {
    override fun format(): String = Representations.elementRepr("list.item",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TListItem(@TContentBody body: TContent, label: TLabel? = null): TListItem =
        TListItemImpl(`body`, `label`)
