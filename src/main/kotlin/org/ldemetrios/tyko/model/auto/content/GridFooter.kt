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
    "grid.footer",
    ["grid.footer", "content"],
    TGridFooterImpl::class,
)
public interface TGridFooter : TContent {
    public val children: TArray<TContent>

    public val repeat: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("grid.footer")

        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val repeatType: InternalType = ConcreteType("bool")
    }
}

internal data class TGridFooterImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("repeat")
    override val repeat: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TGridFooter {
    override fun format(): String = Representations.elementRepr("grid.footer",ArgumentEntry(true,
            null, `children`),ArgumentEntry(false, "repeat", `repeat`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TGridFooter(
    @TVararg children: TArray<TContent>,
    repeat: TBool? = null,
    label: TLabel? = null,
): TGridFooter = TGridFooterImpl(`children`, `repeat`, `label`)

@TSetRuleType(
    "grid.footer",
    TSetGridFooterImpl::class,
)
public interface TSetGridFooter : TSetRule {
    override val elem: String
        get() = "grid.footer"

    public val repeat: TBool?

    override fun format(): String = Representations.setRepr("grid.footer",ArgumentEntry(false,
            "repeat", `repeat`),)
}

internal class TSetGridFooterImpl(
    @TSerialName("repeat")
    override val repeat: TBool? = null,
) : TSetGridFooter

@TypstOverloads
public fun TSetGridFooter(repeat: TBool? = null): TSetGridFooter = TSetGridFooterImpl(`repeat`)
