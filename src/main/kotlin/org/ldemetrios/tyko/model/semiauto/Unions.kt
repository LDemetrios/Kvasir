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

@TUnionType(["array", "str"])
public sealed interface TArrayOrStr<out E : TValue> : TValue,
        TArrayOrAutoOrDictionaryOrStr<E, TDynamic>, TArrayOrNoneOrStr<E>, TArrayOrNoneOrStrOrSymbol<E>,
        TArrayOrAutoOrDictionaryOrNoneOrStr<E, TDynamic>

@TUnionType(["auto", "content", "none"])
public sealed interface TAutoOrContentOrNone : TValue, TAutoOrContentOrFunctionOrNone

@TUnionType(["array", "content", "function"])
public sealed interface TArrayOrContentOrFunction<out E : TValue> : TValue

@TUnionType(["auto", "length"])
public sealed interface TAutoOrLength : TValue,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>, TAutoOrLengthOrRatio,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>

@TUnionType(["content", "none"])
public sealed interface TContentOrNone : TValue, TAutoOrContentOrNone,
        TAutoOrContentOrFunctionOrNone, TContentOrLabelOrNone

@TUnionType(["cite-form", "none"])
public sealed interface TCiteFormOrNone : TValue

@TUnionType(["auto", "bibliography-style"])
public sealed interface TAutoOrBibliographyStyle : TValue

@TUnionType(["auto", "datetime", "none"])
public sealed interface TAutoOrDatetimeOrNone : TValue

@TUnionType(["alignment", "auto", "none"])
public sealed interface TAlignmentOrAutoOrNone : TValue

@TUnionType(["auto", "function", "str"])
public sealed interface TAutoOrFunctionOrStr : TValue

@TUnionType(["auto", "content", "function", "none"])
public sealed interface TAutoOrContentOrFunctionOrNone : TValue

@TUnionType(["function", "none", "str"])
public sealed interface TFunctionOrNoneOrStr : TValue

@TUnionType(["auto", "content"])
public sealed interface TAutoOrContent : TValue, TAutoOrContentOrNone,
        TAutoOrContentOrFunctionOrNone

@TUnionType(["content", "label"])
public sealed interface TContentOrLabel : TValue, TContentOrLabelOrNone

@TUnionType(["function", "str"])
public sealed interface TFunctionOrStr : TValue, TAutoOrFunctionOrStr, TFunctionOrNoneOrStr,
        TFunctionOrLabelOrLocationOrSelectorOrStr

@TUnionType(["auto", "int"])
public sealed interface TAutoOrInt : TValue, TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>,
        TAutoOrFractionOrIntOrRelative

@TUnionType(["auto", "bool"])
public sealed interface TAutoOrBool : TValue, TAutoOrBoolOrFunctionOrNoneOrRelative

@TUnionType(["label", "location", "str"])
public sealed interface TLabelOrLocationOrStr : TValue, TFunctionOrLabelOrLocationOrSelectorOrStr

@TUnionType(["array", "enum.item"])
public sealed interface TArrayOrEnumItem<out E : TValue> : TValue

@TUnionType(["int", "none"])
public sealed interface TIntOrNone : TValue, TArrayOrIntOrNone<TDynamic>,
        TDictionaryOrIntOrNone<TDynamic>

@TUnionType(["function", "label", "location", "selector"])
public sealed interface TFunctionOrLabelOrLocationOrSelector : TValue,
        TFunctionOrLabelOrLocationOrSelectorOrStr

@TUnionType(["auto", "bool", "function", "none", "relative"])
public sealed interface TAutoOrBoolOrFunctionOrNoneOrRelative : TValue

@TUnionType(["auto", "par-linebreaks"])
public sealed interface TAutoOrParLinebreaks : TValue

@TUnionType(["dictionary", "length"])
public sealed interface TDictionaryOrLength<out V : TValue> : TValue,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, V>,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<V>,
        TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<V>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>

@TUnionType(["bool", "length"])
public sealed interface TBoolOrLength : TValue

@TUnionType(["alignment", "auto"])
public sealed interface TAlignmentOrAuto : TValue, TAlignmentOrAutoOrNone,
        TAlignmentOrArrayOrAutoOrFunction<TDynamic>

@TUnionType(["content", "label", "none"])
public sealed interface TContentOrLabelOrNone : TValue

@TUnionType(["array", "auto", "fraction", "int", "relative"])
public sealed interface TArrayOrAutoOrFractionOrIntOrRelative<out E : TValue> : TValue

@TUnionType(["auto", "fraction", "int", "relative"])
public sealed interface TAutoOrFractionOrIntOrRelative : TValue,
        TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>

@TUnionType(["array", "color", "function", "gradient", "none", "tiling"])
public sealed interface TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<out E : TValue> : TValue,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<E, TDynamic>

@TUnionType(["alignment", "array", "auto", "function"])
public sealed interface TAlignmentOrArrayOrAutoOrFunction<out E : TValue> : TValue

@TUnionType(["array", "color", "dictionary", "function", "gradient", "length", "none", "stroke",
        "tiling"])
public sealed interface
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<out E : TValue, out
        V : TValue> : TValue

@TUnionType(["array", "fraction", "function", "relative", "sides"])
public sealed interface TArrayOrFractionOrFunctionOrRelativeOrSides<out E : TValue, out S : TValue>
        : TValue

@TUnionType(["fraction", "none", "relative"])
public sealed interface TFractionOrNoneOrRelative : TValue

@TUnionType(["fraction", "relative"])
public sealed interface TFractionOrRelative : TValue,
        TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>, TAutoOrFractionOrIntOrRelative,
        TArrayOrFractionOrFunctionOrRelativeOrSides<TDynamic, TDynamic>, TFractionOrNoneOrRelative,
        TAutoOrFractionOrRelative, TContentOrFractionOrRelative

@TUnionType(["auto", "color", "gradient", "none", "tiling"])
public sealed interface TAutoOrColorOrGradientOrNoneOrTiling : TValue,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>

@TUnionType(["auto", "relative", "sides"])
public sealed interface TAutoOrRelativeOrSides<out S : TValue> : TValue

@TUnionType(["auto", "none", "relative"])
public sealed interface TAutoOrNoneOrRelative : TValue, TAutoOrBoolOrFunctionOrNoneOrRelative

@TUnionType(["color", "dictionary", "gradient", "length", "none", "stroke", "tiling"])
public sealed interface TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<out V : TValue> :
        TValue,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, V>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>

@TUnionType(["array", "content"])
public sealed interface TArrayOrContent<out E : TValue> : TValue, TArrayOrContentOrFunction<E>

@TUnionType(["color", "gradient", "none", "tiling"])
public sealed interface TColorOrGradientOrNoneOrTiling : TValue,
        TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TDynamic>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TAutoOrColorOrGradientOrNoneOrTiling,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>

@TUnionType(["length", "text-top-edge"])
public sealed interface TLengthOrTextTopEdge : TValue

@TUnionType(["length", "text-bottom-edge"])
public sealed interface TLengthOrTextBottomEdge : TValue

@TUnionType(["dictionary", "relative"])
public sealed interface TDictionaryOrRelative<out V : TValue> : TValue

@TUnionType(["auto", "color", "dictionary", "gradient", "length", "stroke", "tiling"])
public sealed interface TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<out V : TValue> :
        TValue, TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>

@TUnionType(["none", "str"])
public sealed interface TNoneOrStr : TValue, TFunctionOrNoneOrStr, TAutoOrNoneOrStr,
        TArrayOrNoneOrStr<TDynamic>, TArrayOrNoneOrStrOrSymbol<TDynamic>, TNoneOrStrOrSymbol,
        TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, TDynamic>

@TUnionType(["auto", "none", "str"])
public sealed interface TAutoOrNoneOrStr : TValue,
        TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, TDynamic>

@TUnionType(["array", "auto", "dictionary", "str"])
public sealed interface TArrayOrAutoOrDictionaryOrStr<out E : TValue, out V : TValue> : TValue,
        TArrayOrAutoOrDictionaryOrNoneOrStr<E, V>

@TUnionType(["int", "text-weight"])
public sealed interface TIntOrTextWeight : TValue

@TUnionType(["color", "gradient", "tiling"])
public sealed interface TColorOrGradientOrTiling : TValue,
        TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TDynamic>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TAutoOrColorOrGradientOrNoneOrTiling,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TColorOrGradientOrNoneOrTiling,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrGradientOrTiling

@TUnionType(["auto", "none"])
public sealed interface TAutoOrNone : TValue, TAutoOrContentOrNone, TAutoOrDatetimeOrNone,
        TAlignmentOrAutoOrNone, TAutoOrContentOrFunctionOrNone, TAutoOrBoolOrFunctionOrNoneOrRelative,
        TAutoOrColorOrGradientOrNoneOrTiling, TAutoOrNoneOrRelative, TAutoOrNoneOrStr,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, TDynamic>, TArrayOrAutoOrNone<TDynamic>

@TUnionType(["auto", "str"])
public sealed interface TAutoOrStr : TValue, TAutoOrFunctionOrStr, TAutoOrNoneOrStr,
        TArrayOrAutoOrDictionaryOrStr<TDynamic, TDynamic>, TAutoOrBytesOrStr,
        TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, TDynamic>

@TUnionType(["auto", "direction"])
public sealed interface TAutoOrDirection : TValue

@TUnionType(["array", "int", "none"])
public sealed interface TArrayOrIntOrNone<out E : TValue> : TValue

@TUnionType(["auto", "text-number-type"])
public sealed interface TAutoOrTextNumberType : TValue

@TUnionType(["auto", "text-number-width"])
public sealed interface TAutoOrTextNumberWidth : TValue

@TUnionType(["array", "dictionary"])
public sealed interface TArrayOrDictionary<out E : TValue, out V : TValue> : TValue,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<E, V>,
        TArrayOrAutoOrDictionaryOrStr<E, V>, TArrayOrAutoOrDictionaryOrNoneOrStr<E, V>

@TUnionType(["content", "str"])
public sealed interface TContentOrStr : TValue

@TUnionType(["auto", "relative"])
public sealed interface TAutoOrRelative : TValue, TAutoOrBoolOrFunctionOrNoneOrRelative,
        TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>, TAutoOrFractionOrIntOrRelative,
        TAutoOrRelativeOrSides<TDynamic>, TAutoOrNoneOrRelative, TAutoOrFractionOrRelative,
        TAutoOrMarginOrRelative<TDynamic>

@TUnionType(["angle", "auto", "function"])
public sealed interface TAngleOrAutoOrFunction : TValue

@TUnionType(["color", "dictionary", "gradient", "length", "stroke", "tiling"])
public sealed interface TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<out V : TValue> :
        TValue,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, V>,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<V>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>

@TUnionType(["array", "none", "str"])
public sealed interface TArrayOrNoneOrStr<out E : TValue> : TValue, TArrayOrNoneOrStrOrSymbol<E>,
        TArrayOrAutoOrDictionaryOrNoneOrStr<E, TDynamic>

@TUnionType(["array", "none", "str", "symbol"])
public sealed interface TArrayOrNoneOrStrOrSymbol<out E : TValue> : TValue

@TUnionType(["none", "str", "symbol"])
public sealed interface TNoneOrStrOrSymbol : TValue, TArrayOrNoneOrStrOrSymbol<TDynamic>

@TUnionType(["dictionary", "int", "none"])
public sealed interface TDictionaryOrIntOrNone<out V : TValue> : TValue

@TUnionType(["auto", "fraction", "relative"])
public sealed interface TAutoOrFractionOrRelative : TValue,
        TArrayOrAutoOrFractionOrIntOrRelative<TDynamic>, TAutoOrFractionOrIntOrRelative

@TUnionType(["relative", "sides"])
public sealed interface TRelativeOrSides<out S : TValue> : TValue,
        TArrayOrFractionOrFunctionOrRelativeOrSides<TDynamic, S>, TAutoOrRelativeOrSides<S>,
        TArrayOrFunctionOrRelativeOrSides<TDynamic, S>

@TUnionType(["none", "relative"])
public sealed interface TNoneOrRelative : TValue, TAutoOrBoolOrFunctionOrNoneOrRelative,
        TFractionOrNoneOrRelative, TAutoOrNoneOrRelative

@TUnionType(["array", "function", "relative", "sides"])
public sealed interface TArrayOrFunctionOrRelativeOrSides<out E : TValue, out S : TValue> : TValue,
        TArrayOrFractionOrFunctionOrRelativeOrSides<E, S>

@TUnionType(["auto", "margin", "relative"])
public sealed interface TAutoOrMarginOrRelative<out M : TValue> : TValue

@TUnionType(["none", "pagebreak-to"])
public sealed interface TNoneOrPagebreakTo : TValue

@TUnionType(["auto", "length", "ratio"])
public sealed interface TAutoOrLengthOrRatio : TValue

@TUnionType(["content", "fraction", "relative"])
public sealed interface TContentOrFractionOrRelative : TValue

@TUnionType(["auto", "color", "dictionary", "gradient", "length", "none", "stroke", "tiling"])
public sealed interface TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<out V :
        TValue> : TValue

@TUnionType(["bytes", "str"])
public sealed interface TBytesOrStr : TValue, TAutoOrBytesOrStr

@TUnionType(["auto", "dictionary", "image-format"])
public sealed interface TAutoOrDictionaryOrImageFormat<out V : TValue> : TValue

@TUnionType(["int", "str"])
public sealed interface TIntOrStr : TValue

@TUnionType(["auto", "image-scaling"])
public sealed interface TAutoOrImageScaling : TValue

@TUnionType(["auto", "bytes", "str"])
public sealed interface TAutoOrBytesOrStr : TValue

@TUnionType(["array", "none"])
public sealed interface TArrayOrNone<out E : TValue> : TValue,
        TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<E>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<E, TDynamic>,
        TArrayOrIntOrNone<E>, TArrayOrNoneOrStr<E>, TArrayOrNoneOrStrOrSymbol<E>,
        TArrayOrAutoOrDictionaryOrNoneOrStr<E, TDynamic>, TArrayOrAutoOrNone<E>

@TUnionType(["int", "ratio"])
public sealed interface TIntOrRatio : TValue

@TUnionType(["float", "ratio"])
public sealed interface TFloatOrRatio : TValue

@TUnionType(["array", "color"])
public sealed interface TArrayOrColor<out E : TValue> : TValue,
        TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<E>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<E, TDynamic>

@TUnionType(["color", "ratio"])
public sealed interface TColorOrRatio : TValue

@TUnionType(["array", "auto"])
public sealed interface TArrayOrAuto<out E : TValue> : TValue,
        TArrayOrAutoOrFractionOrIntOrRelative<E>, TAlignmentOrArrayOrAutoOrFunction<E>,
        TArrayOrAutoOrDictionaryOrStr<E, TDynamic>, TArrayOrAutoOrDictionaryOrNoneOrStr<E, TDynamic>,
        TArrayOrAutoOrNone<E>

@TUnionType(["auto", "color", "gradient", "tiling"])
public sealed interface TAutoOrColorOrGradientOrTiling : TValue,
        TAutoOrColorOrGradientOrNoneOrTiling,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>

@TUnionType(["array", "auto", "dictionary", "none", "str"])
public sealed interface TArrayOrAutoOrDictionaryOrNoneOrStr<out E : TValue, out V : TValue> : TValue

@TUnionType(["auto", "float"])
public sealed interface TAutoOrFloat : TValue

@TUnionType(["location", "selector", "str"])
public sealed interface TLocationOrSelectorOrStr : TValue, TFunctionOrLabelOrLocationOrSelectorOrStr

@TUnionType(["none", "selector"])
public sealed interface TNoneOrSelector : TValue

@TUnionType(["function", "label", "location", "selector", "str"])
public sealed interface TFunctionOrLabelOrLocationOrSelectorOrStr : TValue

@TUnionType(["array", "function", "int"])
public sealed interface TArrayOrFunctionOrInt<out E : TValue> : TValue

@TUnionType(["array", "auto", "none"])
public sealed interface TArrayOrAutoOrNone<out E : TValue> : TValue,
        TArrayOrAutoOrDictionaryOrNoneOrStr<E, TDynamic>

@TUnionType(["int", "length"])
public sealed interface TIntOrLength : TValue
