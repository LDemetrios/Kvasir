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
    "state",
    ["state"],
    TStateImpl::class,
)
public interface TState : TValue {
    public val key: TStr

    public val `init`: TValue?

    override fun type(): TType = TState

    public companion object : TTypeImpl("state") {
        internal val keyType: InternalType = ConcreteType("str")

        internal val initType: InternalType = AnyType
    }
}

internal data class TStateImpl(
    @TSerialName("key")
    override val key: TStr,
    @TSerialName("init")
    override val `init`: TValue? = null,
) : TState {
    override fun format(): String = Representations.structRepr("state",ArgumentEntry(false, null,
            `key`),ArgumentEntry(false, null, `init`),)
}

@TypstOverloads
public fun TState(key: TStr, `init`: TValue? = null): TState = TStateImpl(`key`, `init`)
