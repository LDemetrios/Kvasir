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
    "counter",
    ["counter"],
    TCounterImpl::class,
)
public interface TCounter : TValue {
    public val `value`: TLocationOrSelectorOrStr

    override fun type(): TType = TCounter

    public companion object : TTypeImpl("counter") {
        internal val valueType: InternalType = UnionType(ConcreteType("location"),
                ConcreteType("selector"), ConcreteType("str"))
    }
}

internal data class TCounterImpl(
    @TSerialName("value")
    override val `value`: TLocationOrSelectorOrStr,
) : TCounter {
    override fun format(): String = Representations.structRepr("counter",ArgumentEntry(false, null,
            `value`),)
}

@TypstOverloads
public fun TCounter(`value`: TLocationOrSelectorOrStr): TCounter = TCounterImpl(`value`)
