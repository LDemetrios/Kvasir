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
    "state.update",
    ["state.update", "content"],
    TStateUpdateImpl::class,
)
public interface TStateUpdate : TContent {
    public val key: TStr

    public val update: TValue

    override fun func(): TElement = TStateUpdate

    public companion object : TElementImpl("state.update") {
        internal val keyType: InternalType = ConcreteType("str")

        internal val updateType: InternalType = AnyType
    }
}

internal data class TStateUpdateImpl(
    @TSerialName("key")
    override val key: TStr,
    @TSerialName("update")
    override val update: TValue,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TStateUpdate {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TStateUpdate(
    key: TStr,
    update: TValue,
    label: TLabel? = null,
): TStateUpdate = TStateUpdateImpl(`key`, `update`, `label`)
