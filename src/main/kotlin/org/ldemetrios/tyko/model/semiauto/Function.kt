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

public interface TFunction : TValue, TArrayOrContentOrFunction<TDynamic>, TAutoOrFunctionOrStr,
        TAutoOrContentOrFunctionOrNone, TFunctionOrNoneOrStr, TFunctionOrStr,
        TFunctionOrLabelOrLocationOrSelector, TAutoOrBoolOrFunctionOrNoneOrRelative,
        TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TDynamic>,
        TAlignmentOrArrayOrAutoOrFunction<TDynamic>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TArrayOrFractionOrFunctionOrRelativeOrSides<TDynamic, TDynamic>, TAngleOrAutoOrFunction,
        TArrayOrFunctionOrRelativeOrSides<TDynamic, TDynamic>,
        TFunctionOrLabelOrLocationOrSelectorOrStr, TArrayOrFunctionOrInt<TDynamic> {
    override fun type(): TType = Type

    override fun format(): String = Representations.reprOf(this)

    public companion object {
        public val Type: TType = TTypeImpl("function")
    }
}
