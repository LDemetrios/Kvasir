@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.Long
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
    "int",
    ["int"],
    TIntImpl::class,
)
public interface TInt : TValue, TAutoOrInt, TIntOrNone,
        TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>, TAutoOrFractionOrIntOrRelative,
        TIntOrTextWeight, TArrayOrIntOrNone<TDynamic>, TDictionaryOrIntOrNone<TDynamic>, TIntOrStr,
        TIntOrRatio, TArrayOrFunctionOrInt<TDynamic>, TIntOrLength {
    public val intValue: Long

    override fun type(): TType = Type

    override fun format(): String = Representations.reprOf(intValue)

    public companion object {
        public val Type: TType = TTypeImpl("int")
    }
}

internal data class TIntImpl(
    override val intValue: Long,
) : TInt

public fun TInt(`value`: Long): TInt = TIntImpl(value)
