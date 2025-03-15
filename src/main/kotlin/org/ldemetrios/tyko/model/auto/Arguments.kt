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
    "arguments",
    ["arguments"],
    TArgumentsImpl::class,
)
public interface TArguments<out A : TValue> : TValue {
    public val positional: TArray<A>

    public val named: TDictionary<A>

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("arguments")

        internal fun positionalType(A: InternalType): InternalType = ConcreteType("array", listOf(A))

        internal fun namedType(A: InternalType): InternalType = ConcreteType("dictionary", listOf(A))
    }
}

internal data class TArgumentsImpl<out A : TValue>(
    @TSerialName("positional")
    override val positional: TArray<A>,
    @TSerialName("named")
    override val named: TDictionary<A>,
) : TArguments<A> {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun <A : TValue> TArguments(positional: TArray<A>, named: TDictionary<A>): TArguments<A> =
        TArgumentsImpl(`positional`, `named`)
