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
    "str",
    ["str"],
    TStrImpl::class,
)
public interface TStr : TValue, TArrayOrStr<TDynamic>, TAutoOrFunctionOrStr, TFunctionOrNoneOrStr,
        TFunctionOrStr, TLabelOrLocationOrStr, TNoneOrStr, TAutoOrNoneOrStr,
        TArrayOrAutoOrDictionaryOrStr<TDynamic, TDynamic>, TAutoOrStr, TContentOrStr,
        TArrayOrNoneOrStr<TDynamic>, TArrayOrNoneOrStrOrSymbol<TDynamic>, TNoneOrStrOrSymbol,
        TBytesOrStr, TIntOrStr, TAutoOrBytesOrStr,
        TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, TDynamic>, TLocationOrSelectorOrStr,
        TFunctionOrLabelOrLocationOrSelectorOrStr {
    public val strValue: String

    override fun type(): TType = Type

    override fun format(): String = Representations.reprOf(strValue)

    public companion object {
        public val Type: TType = TTypeImpl("str")
    }
}

internal data class TStrImpl(
    override val strValue: String,
) : TStr

public fun TStr(`value`: String): TStr = allVariants[value] ?: TStrImpl(value)
