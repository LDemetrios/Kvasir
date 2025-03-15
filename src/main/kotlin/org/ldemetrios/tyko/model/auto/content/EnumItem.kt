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
    "enum.item",
    ["enum.item", "content"],
    TEnumItemImpl::class,
)
public interface TEnumItem : TArrayOrEnumItem<TDynamic>, TContent {
    public val number: TIntOrNone?

    public val body: TContent

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("enum.item")

        internal val numberType: InternalType = UnionType(ConcreteType("int"), ConcreteType("none"))

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TEnumItemImpl(
    @TSerialName("number")
    override val number: TIntOrNone? = null,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TEnumItem {
    override fun format(): String = Representations.elementRepr("enum.item",ArgumentEntry(false, null,
            `number`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TEnumItem(
    number: TIntOrNone? = null,
    @TContentBody body: TContent,
    label: TLabel? = null,
): TEnumItem = TEnumItemImpl(`number`, `body`, `label`)

@TSetRuleType(
    "enum.item",
    TSetEnumItemImpl::class,
)
public interface TSetEnumItem : TSetRule {
    override val elem: String
        get() = "enum.item"

    public val number: TIntOrNone?

    override fun format(): String = Representations.setRepr("enum.item",ArgumentEntry(false, null,
            `number`),)
}

internal class TSetEnumItemImpl(
    @TSerialName("number")
    override val number: TIntOrNone? = null,
) : TSetEnumItem

@TypstOverloads
public fun TSetEnumItem(number: TIntOrNone? = null): TSetEnumItem = TSetEnumItemImpl(`number`)
