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
    "outline.entry",
    ["outline.entry", "content"],
    TOutlineEntryImpl::class,
)
public interface TOutlineEntry : TContent {
    public val level: TInt

    public val element: TContent

    public val fill: TContentOrNone?

    override fun func(): TElement = TOutlineEntry

    public companion object : TElementImpl("outline.entry") {
        internal val levelType: InternalType = ConcreteType("int")

        internal val elementType: InternalType = ConcreteType("content")

        internal val fillType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))
    }
}

internal data class TOutlineEntryImpl(
    @TSerialName("level")
    override val level: TInt,
    @TSerialName("element")
    override val element: TContent,
    @TSerialName("fill")
    override val fill: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TOutlineEntry {
    override fun format(): String = Representations.elementRepr("outline.entry",ArgumentEntry(false,
            null, `level`),ArgumentEntry(false, null, `element`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TOutlineEntry(
    level: TInt,
    element: TContent,
    fill: TContentOrNone? = null,
    label: TLabel? = null,
): TOutlineEntry = TOutlineEntryImpl(`level`, `element`, `fill`, `label`)

@TSetRuleType(
    "outline.entry",
    TSetOutlineEntryImpl::class,
)
public interface TSetOutlineEntry : TSetRule {
    override val elem: String
        get() = "outline.entry"

    public val fill: TContentOrNone?

    override fun format(): String = Representations.setRepr("outline.entry",ArgumentEntry(false,
            "fill", `fill`),)
}

internal class TSetOutlineEntryImpl(
    @TSerialName("fill")
    override val fill: TContentOrNone? = null,
) : TSetOutlineEntry

@TypstOverloads
public fun TSetOutlineEntry(fill: TContentOrNone? = null): TSetOutlineEntry =
        TSetOutlineEntryImpl(`fill`)
