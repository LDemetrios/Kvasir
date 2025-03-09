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
    "table.footer",
    ["table.footer", "content"],
    TTableFooterImpl::class,
)
public interface TTableFooter : TContent {
    public val children: TArray<TContent>

    public val repeat: TBool?

    override fun func(): TElement = TTableFooter

    public companion object : TElementImpl("table.footer") {
        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val repeatType: InternalType = ConcreteType("bool")
    }
}

internal data class TTableFooterImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("repeat")
    override val repeat: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TTableFooter {
    override fun format(): String = Representations.elementRepr("table.footer",ArgumentEntry(true,
            null, `children`),ArgumentEntry(false, "repeat", `repeat`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TTableFooter(
    @TVararg children: TArray<TContent>,
    repeat: TBool? = null,
    label: TLabel? = null,
): TTableFooter = TTableFooterImpl(`children`, `repeat`, `label`)

@TSetRuleType(
    "table.footer",
    TSetTableFooterImpl::class,
)
public interface TSetTableFooter : TSetRule {
    override val elem: String
        get() = "table.footer"

    public val repeat: TBool?

    override fun format(): String = Representations.setRepr("table.footer",ArgumentEntry(false,
            "repeat", `repeat`),)
}

internal class TSetTableFooterImpl(
    @TSerialName("repeat")
    override val repeat: TBool? = null,
) : TSetTableFooter

@TypstOverloads
public fun TSetTableFooter(repeat: TBool? = null): TSetTableFooter = TSetTableFooterImpl(`repeat`)
