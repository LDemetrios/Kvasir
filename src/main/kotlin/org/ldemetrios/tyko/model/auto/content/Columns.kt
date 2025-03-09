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
    "columns",
    ["columns", "content"],
    TColumnsImpl::class,
)
public interface TColumns : TContent {
    public val count: TInt?

    public val body: TContent

    public val gutter: TRelative?

    override fun func(): TElement = TColumns

    public companion object : TElementImpl("columns") {
        internal val countType: InternalType = ConcreteType("int")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val gutterType: InternalType = ConcreteType("relative")
    }
}

internal data class TColumnsImpl(
    @TSerialName("count")
    override val count: TInt? = null,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("gutter")
    override val gutter: TRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TColumns {
    override fun format(): String = Representations.elementRepr("columns",ArgumentEntry(false, null,
            `count`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "gutter",
            `gutter`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TColumns(
    count: TInt? = null,
    @TContentBody body: TContent,
    gutter: TRelative? = null,
    label: TLabel? = null,
): TColumns = TColumnsImpl(`count`, `body`, `gutter`, `label`)

@TSetRuleType(
    "columns",
    TSetColumnsImpl::class,
)
public interface TSetColumns : TSetRule {
    override val elem: String
        get() = "columns"

    public val count: TInt?

    public val gutter: TRelative?

    override fun format(): String = Representations.setRepr("columns",ArgumentEntry(false, null,
            `count`),ArgumentEntry(false, "gutter", `gutter`),)
}

internal class TSetColumnsImpl(
    @TSerialName("count")
    override val count: TInt? = null,
    @TSerialName("gutter")
    override val gutter: TRelative? = null,
) : TSetColumns

@TypstOverloads
public fun TSetColumns(count: TInt? = null, gutter: TRelative? = null): TSetColumns =
        TSetColumnsImpl(`count`, `gutter`)
