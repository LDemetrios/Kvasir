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

public interface TAuto : TAutoOrContentOrNone, TAutoOrLength, TAutoOrBibliographyStyle,
        TAutoOrDatetimeOrNone, TAlignmentOrAutoOrNone, TAutoOrFunctionOrStr,
        TAutoOrContentOrFunctionOrNone, TAutoOrContent, TAutoOrInt, TAutoOrBool,
        TAutoOrBoolOrFunctionOrNoneOrRelative, TAutoOrParLinebreaks, TAlignmentOrAuto,
        TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>, TAutoOrFractionOrIntOrRelative,
        TAlignmentOrArrayOrAutoOrFunction<TDynamic>, TAutoOrColorOrGradientOrNoneOrTiling,
        TAutoOrRelativeOrSides<TDynamic>, TAutoOrNoneOrRelative,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>, TAutoOrNoneOrStr,
        TArrayOrAutoOrDictionaryOrStr<TDynamic, TDynamic>, TAutoOrNone, TAutoOrStr, TAutoOrDirection,
        TAutoOrTextNumberType, TAutoOrTextNumberWidth, TAutoOrRelative, TAngleOrAutoOrFunction,
        TAutoOrFractionOrRelative, TAutoOrMarginOrRelative<TDynamic>, TAutoOrLengthOrRatio,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TAutoOrDictionaryOrImageFormat<TDynamic>, TAutoOrImageScaling, TAutoOrBytesOrStr,
        TArrayOrAuto<TDynamic>, TAutoOrColorOrGradientOrTiling,
        TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, TDynamic>, TAutoOrFloat,
        TArrayOrAutoOrNone<TDynamic>, TValue {
    override fun type(): TType = TAutoType

    public companion object : TAuto {
        override fun format(): String = Representations.reprOf(this)
    }
}

public object TAutoType : TTypeImpl("auto")
