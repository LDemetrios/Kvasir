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

public interface TGradient : TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TDynamic>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TAutoOrColorOrGradientOrNoneOrTiling,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TColorOrGradientOrNoneOrTiling,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>, TColorOrGradientOrTiling,
        TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrGradientOrTiling, TValue {
    override fun type(): TType = Type

    public fun func(): TFunction

    public companion object {
        public val Type: TType = TTypeImpl("gradient")
    }
}
