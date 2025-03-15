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
    "with",
    ["with", "function"],
    TWithImpl::class,
)
public interface TWith : TFunction {
    public val origin: TFunction

    public val args: TArguments<TValue>

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("with")

        internal val originType: InternalType = ConcreteType("function")

        internal val argsType: InternalType = ConcreteType("arguments", listOf(AnyType))
    }
}

internal data class TWithImpl(
    @TSerialName("origin")
    override val origin: TFunction,
    @TSerialName("args")
    override val args: TArguments<TValue>,
) : TWith {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TWith(origin: TFunction, args: TArguments<TValue>): TWith = TWithImpl(`origin`, `args`)
