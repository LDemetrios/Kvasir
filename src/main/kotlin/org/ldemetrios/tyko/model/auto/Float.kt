@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.Double
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
    "float",
    ["float"],
    TFloatImpl::class,
)
public interface TFloat : TValue, TFloatOrRatio, TAutoOrFloat {
    public val floatValue: Double

    override fun type(): TType = TFloat

    override fun format(): String = Representations.reprOf(floatValue)

    public companion object : TTypeImpl("float")
}

internal data class TFloatImpl(
    override val floatValue: Double,
) : TFloat

public fun TFloat(`value`: Double): TFloat = TFloatImpl(value)
