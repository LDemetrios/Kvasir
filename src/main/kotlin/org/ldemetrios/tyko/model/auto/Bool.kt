@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.Boolean
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
    "bool",
    ["bool"],
    TBoolImpl::class,
)
public interface TBool : TValue, TAutoOrBool, TAutoOrBoolOrFunctionOrNoneOrRelative, TBoolOrLength {
    public val boolValue: Boolean

    override fun type(): TType = TBool

    override fun format(): String = Representations.reprOf(boolValue)

    public companion object : TTypeImpl("bool")
}

internal data class TBoolImpl(
    override val boolValue: Boolean,
) : TBool

public fun TBool(`value`: Boolean): TBool = TBoolImpl(value)
