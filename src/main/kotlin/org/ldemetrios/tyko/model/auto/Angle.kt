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
    "angle",
    ["angle"],
    TAngleImpl::class,
)
public interface TAngle : TAngleOrAutoOrFunction, TValue {
    public val deg: TFloat

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("angle")

        internal val degType: InternalType = ConcreteType("float")
    }
}

internal data class TAngleImpl(
    @TSerialName("deg")
    override val deg: TFloat,
) : TAngle {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TAngle(deg: TFloat): TAngle = TAngleImpl(`deg`)
