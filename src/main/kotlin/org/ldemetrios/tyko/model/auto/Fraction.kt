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
    "fraction",
    ["fraction"],
    TFractionImpl::class,
)
public interface TFraction : TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>,
        TAutoOrFractionOrIntOrRelative, TArrayOrFractionOrFunctionOrRelativeOrSides<TDynamic, TDynamic>,
        TFractionOrNoneOrRelative, TFractionOrRelative, TAutoOrFractionOrRelative,
        TContentOrFractionOrRelative, TValue {
    public val `value`: TFloat

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("fraction")

        internal val valueType: InternalType = ConcreteType("float")
    }
}

internal data class TFractionImpl(
    @TSerialName("value")
    override val `value`: TFloat,
) : TFraction {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TFraction(`value`: TFloat): TFraction = TFractionImpl(`value`)
