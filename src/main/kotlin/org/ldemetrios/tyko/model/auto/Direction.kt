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
    "direction",
    ["direction"],
    TDirectionImpl::class,
)
public interface TDirection : TAutoOrDirection, TValue {
    public val `value`: TStr

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("direction")

        internal val valueType: InternalType = ConcreteType("str")
    }
}

internal data class TDirectionImpl(
    @TSerialName("value")
    override val `value`: TStr,
) : TDirection {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TDirection(`value`: TStr): TDirection = TDirectionImpl(`value`)
