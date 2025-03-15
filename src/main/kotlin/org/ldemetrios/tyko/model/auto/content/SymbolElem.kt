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
    "symbol-elem",
    ["symbol-elem", "content"],
    TSymbolElemImpl::class,
)
public interface TSymbolElem : TContent {
    public val text: TStr

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("symbol-elem")

        internal val textType: InternalType = ConcreteType("str")
    }
}

internal data class TSymbolElemImpl(
    @TSerialName("text")
    override val text: TStr,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TSymbolElem {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TSymbolElem(text: TStr, label: TLabel? = null): TSymbolElem = TSymbolElemImpl(`text`,
        `label`)
