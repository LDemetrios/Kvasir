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
    "table.header",
    ["table.header", "content"],
    TTableHeaderImpl::class,
)
public interface TTableHeader : TContent {
    public val children: TArray<TContent>

    public val repeat: TBool?

    override fun func(): TElement = TTableHeader

    public companion object : TElementImpl("table.header") {
        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val repeatType: InternalType = ConcreteType("bool")
    }
}

internal data class TTableHeaderImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("repeat")
    override val repeat: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TTableHeader {
    override fun format(): String = Representations.elementRepr("table.header",ArgumentEntry(true,
            null, `children`),ArgumentEntry(false, "repeat", `repeat`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TTableHeader(
    @TVararg children: TArray<TContent>,
    repeat: TBool? = null,
    label: TLabel? = null,
): TTableHeader = TTableHeaderImpl(`children`, `repeat`, `label`)

@TSetRuleType(
    "table.header",
    TSetTableHeaderImpl::class,
)
public interface TSetTableHeader : TSetRule {
    override val elem: String
        get() = "table.header"

    public val repeat: TBool?

    override fun format(): String = Representations.setRepr("table.header",ArgumentEntry(false,
            "repeat", `repeat`),)
}

internal class TSetTableHeaderImpl(
    @TSerialName("repeat")
    override val repeat: TBool? = null,
) : TSetTableHeader

@TypstOverloads
public fun TSetTableHeader(repeat: TBool? = null): TSetTableHeader = TSetTableHeaderImpl(`repeat`)
