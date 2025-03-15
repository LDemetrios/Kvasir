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
    "counter.update",
    ["counter.update", "content"],
    TCounterUpdateImpl::class,
)
public interface TCounterUpdate : TContent {
    public val key: TFunctionOrLabelOrLocationOrSelectorOrStr

    public val update: TArrayOrFunctionOrInt<TInt>

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("counter.update")

        internal val keyType: InternalType = UnionType(ConcreteType("function"), ConcreteType("label"),
                ConcreteType("location"), ConcreteType("selector"), ConcreteType("str"))

        internal val updateType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("int"))), ConcreteType("function"), ConcreteType("int"))
    }
}

internal data class TCounterUpdateImpl(
    @TSerialName("key")
    override val key: TFunctionOrLabelOrLocationOrSelectorOrStr,
    @TSerialName("update")
    override val update: TArrayOrFunctionOrInt<TInt>,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCounterUpdate {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TCounterUpdate(
    key: TFunctionOrLabelOrLocationOrSelectorOrStr,
    update: TArrayOrFunctionOrInt<TInt>,
    label: TLabel? = null,
): TCounterUpdate = TCounterUpdateImpl(`key`, `update`, `label`)
