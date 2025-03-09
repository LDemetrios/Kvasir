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
    "grid.header",
    ["grid.header", "content"],
    TGridHeaderImpl::class,
)
public interface TGridHeader : TContent {
    public val children: TArray<TContent>

    public val repeat: TBool?

    override fun func(): TElement = TGridHeader

    public companion object : TElementImpl("grid.header") {
        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val repeatType: InternalType = ConcreteType("bool")
    }
}

internal data class TGridHeaderImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("repeat")
    override val repeat: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TGridHeader {
    override fun format(): String = Representations.elementRepr("grid.header",ArgumentEntry(true,
            null, `children`),ArgumentEntry(false, "repeat", `repeat`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TGridHeader(
    @TVararg children: TArray<TContent>,
    repeat: TBool? = null,
    label: TLabel? = null,
): TGridHeader = TGridHeaderImpl(`children`, `repeat`, `label`)

@TSetRuleType(
    "grid.header",
    TSetGridHeaderImpl::class,
)
public interface TSetGridHeader : TSetRule {
    override val elem: String
        get() = "grid.header"

    public val repeat: TBool?

    override fun format(): String = Representations.setRepr("grid.header",ArgumentEntry(false,
            "repeat", `repeat`),)
}

internal class TSetGridHeaderImpl(
    @TSerialName("repeat")
    override val repeat: TBool? = null,
) : TSetGridHeader

@TypstOverloads
public fun TSetGridHeader(repeat: TBool? = null): TSetGridHeader = TSetGridHeaderImpl(`repeat`)
