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

public interface TNone : TAutoOrContentOrNone, TContentOrNone, TCiteFormOrNone,
        TAutoOrDatetimeOrNone, TAlignmentOrAutoOrNone, TAutoOrContentOrFunctionOrNone,
        TFunctionOrNoneOrStr, TIntOrNone, TAutoOrBoolOrFunctionOrNoneOrRelative, TContentOrLabelOrNone,
        TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TDynamic>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TFractionOrNoneOrRelative, TAutoOrColorOrGradientOrNoneOrTiling, TAutoOrNoneOrRelative,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TColorOrGradientOrNoneOrTiling, TNoneOrStr, TAutoOrNoneOrStr, TAutoOrNone,
        TArrayOrIntOrNone<TDynamic>, TArrayOrNoneOrStr<TDynamic>, TArrayOrNoneOrStrOrSymbol<TDynamic>,
        TNoneOrStrOrSymbol, TDictionaryOrIntOrNone<TDynamic>, TNoneOrRelative, TNoneOrPagebreakTo,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TArrayOrNone<TDynamic>, TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, TDynamic>,
        TNoneOrSelector, TArrayOrAutoOrNone<TDynamic>, TValue {
    override fun type(): TType = TNoneType

    public companion object : TNone {
        override fun format(): String = Representations.reprOf(this)
    }
}

public object TNoneType : TTypeImpl("none")
