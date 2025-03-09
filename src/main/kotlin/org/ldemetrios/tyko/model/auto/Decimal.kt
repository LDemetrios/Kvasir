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
    "decimal",
    ["decimal"],
    TDecimalImpl::class,
)
public interface TDecimal : TValue {
    public val `value`: TStr?

    override fun type(): TType = TDecimal

    public companion object : TTypeImpl("decimal") {
        internal val valueType: InternalType = ConcreteType("str")
    }
}

internal data class TDecimalImpl(
    @TSerialName("value")
    override val `value`: TStr? = null,
) : TDecimal {
    override fun format(): String = Representations.structRepr("decimal",ArgumentEntry(false, null,
            `value`),)
}

@TypstOverloads
public fun TDecimal(`value`: TStr? = null): TDecimal = TDecimalImpl(`value`)
