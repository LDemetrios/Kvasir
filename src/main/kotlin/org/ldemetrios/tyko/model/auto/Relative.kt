@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.reflect.KType
import kotlin.reflect.typeOf
import org.ldemetrios.js.JSBoolean
import org.ldemetrios.js.JSNumber
import org.ldemetrios.js.JSObject
import org.ldemetrios.js.JSString
import org.ldemetrios.js.JSUndefined
import org.ldemetrios.utilities.cast
import org.ldemetrios.utilities.castUnchecked

public interface TRelative : TAutoOrBoolOrFunctionOrNoneOrRelative,
        TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>, TAutoOrFractionOrIntOrRelative,
        TArrayOrFractionOrFunctionOrRelativeOrSides<TDynamic, TDynamic>, TFractionOrNoneOrRelative,
        TFractionOrRelative, TAutoOrRelativeOrSides<TDynamic>, TAutoOrNoneOrRelative,
        TDictionaryOrRelative<TDynamic>, TAutoOrRelative, TAutoOrFractionOrRelative,
        TRelativeOrSides<TDynamic>, TNoneOrRelative,
        TArrayOrFunctionOrRelativeOrSides<TDynamic, TDynamic>, TAutoOrMarginOrRelative<TDynamic>,
        TContentOrFractionOrRelative, TValue {
    override fun type(): TType = TRelative

    public companion object : TTypeImpl("relative")
}
