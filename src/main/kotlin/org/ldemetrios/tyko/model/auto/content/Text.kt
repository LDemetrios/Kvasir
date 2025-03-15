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
    "text",
    ["text", "content"],
    TTextImpl::class,
)
public interface TText : TContent {
    public val body: TContent?

    public val text: TStr?

    public val font: TArrayOrStr<TStr>?

    public val fallback: TBool?

    public val style: TTextStyle?

    public val weight: TIntOrTextWeight?

    public val stretch: TRatio?

    public val sz: TLength?

    public val fill: TColorOrGradientOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val tracking: TLength?

    public val spacing: TRelative?

    public val cjkLatinSpacing: TAutoOrNone?

    public val baseline: TLength?

    public val overhang: TBool?

    public val topEdge: TLengthOrTextTopEdge?

    public val bottomEdge: TLengthOrTextBottomEdge?

    public val lang: TStr?

    public val region: TNoneOrStr?

    public val script: TAutoOrStr?

    public val dir: TAutoOrDirection?

    public val hyphenate: TAutoOrBool?

    public val costs: TDictionary<TValue>?

    public val kerning: TBool?

    public val alternates: TBool?

    public val stylisticSet: TArrayOrIntOrNone<TInt>?

    public val ligatures: TBool?

    public val discretionaryLigatures: TBool?

    public val historicalLigatures: TBool?

    public val numberType: TAutoOrTextNumberType?

    public val numberWidth: TAutoOrTextNumberWidth?

    public val slashedZero: TBool?

    public val fractions: TBool?

    public val features: TArrayOrDictionary<TStr, TInt>?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("text")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val textType: InternalType = ConcreteType("str")

        internal val fontType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("str"))), ConcreteType("str"))

        internal val fallbackType: InternalType = ConcreteType("bool")

        internal val styleType: InternalType = ConcreteType("text-style")

        internal val weightType: InternalType = UnionType(ConcreteType("int"),
                ConcreteType("text-weight"))

        internal val stretchType: InternalType = ConcreteType("ratio")

        internal val szType: InternalType = ConcreteType("length")

        internal val fillType: InternalType = UnionType(ConcreteType("color"), ConcreteType("gradient"),
                ConcreteType("tiling"))

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val trackingType: InternalType = ConcreteType("length")

        internal val spacingType: InternalType = ConcreteType("relative")

        internal val cjkLatinSpacingType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("none"))

        internal val baselineType: InternalType = ConcreteType("length")

        internal val overhangType: InternalType = ConcreteType("bool")

        internal val topEdgeType: InternalType = UnionType(ConcreteType("length"),
                ConcreteType("text-top-edge"))

        internal val bottomEdgeType: InternalType = UnionType(ConcreteType("length"),
                ConcreteType("text-bottom-edge"))

        internal val langType: InternalType = ConcreteType("str")

        internal val regionType: InternalType = UnionType(ConcreteType("none"), ConcreteType("str"))

        internal val scriptType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("str"))

        internal val dirType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("direction"))

        internal val hyphenateType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("bool"))

        internal val costsType: InternalType = ConcreteType("dictionary", listOf(AnyType))

        internal val kerningType: InternalType = ConcreteType("bool")

        internal val alternatesType: InternalType = ConcreteType("bool")

        internal val stylisticSetType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("int"))), ConcreteType("int"), ConcreteType("none"))

        internal val ligaturesType: InternalType = ConcreteType("bool")

        internal val discretionaryLigaturesType: InternalType = ConcreteType("bool")

        internal val historicalLigaturesType: InternalType = ConcreteType("bool")

        internal val numberTypeType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("text-number-type"))

        internal val numberWidthType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("text-number-width"))

        internal val slashedZeroType: InternalType = ConcreteType("bool")

        internal val fractionsType: InternalType = ConcreteType("bool")

        internal val featuresType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("str"))), ConcreteType("dictionary", listOf(ConcreteType("int"))))
    }
}

internal data class TTextImpl(
    @TSerialName("body")
    override val body: TContent? = null,
    @TSerialName("text")
    override val text: TStr? = null,
    @TSerialName("font")
    override val font: TArrayOrStr<TStr>? = null,
    @TSerialName("fallback")
    override val fallback: TBool? = null,
    @TSerialName("style")
    override val style: TTextStyle? = null,
    @TSerialName("weight")
    override val weight: TIntOrTextWeight? = null,
    @TSerialName("stretch")
    override val stretch: TRatio? = null,
    @TSerialName("size")
    override val sz: TLength? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("tracking")
    override val tracking: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TRelative? = null,
    @TSerialName("cjk-latin-spacing")
    override val cjkLatinSpacing: TAutoOrNone? = null,
    @TSerialName("baseline")
    override val baseline: TLength? = null,
    @TSerialName("overhang")
    override val overhang: TBool? = null,
    @TSerialName("top-edge")
    override val topEdge: TLengthOrTextTopEdge? = null,
    @TSerialName("bottom-edge")
    override val bottomEdge: TLengthOrTextBottomEdge? = null,
    @TSerialName("lang")
    override val lang: TStr? = null,
    @TSerialName("region")
    override val region: TNoneOrStr? = null,
    @TSerialName("script")
    override val script: TAutoOrStr? = null,
    @TSerialName("dir")
    override val dir: TAutoOrDirection? = null,
    @TSerialName("hyphenate")
    override val hyphenate: TAutoOrBool? = null,
    @TSerialName("costs")
    override val costs: TDictionary<TValue>? = null,
    @TSerialName("kerning")
    override val kerning: TBool? = null,
    @TSerialName("alternates")
    override val alternates: TBool? = null,
    @TSerialName("stylistic-set")
    override val stylisticSet: TArrayOrIntOrNone<TInt>? = null,
    @TSerialName("ligatures")
    override val ligatures: TBool? = null,
    @TSerialName("discretionary-ligatures")
    override val discretionaryLigatures: TBool? = null,
    @TSerialName("historical-ligatures")
    override val historicalLigatures: TBool? = null,
    @TSerialName("number-type")
    override val numberType: TAutoOrTextNumberType? = null,
    @TSerialName("number-width")
    override val numberWidth: TAutoOrTextNumberWidth? = null,
    @TSerialName("slashed-zero")
    override val slashedZero: TBool? = null,
    @TSerialName("fractions")
    override val fractions: TBool? = null,
    @TSerialName("features")
    override val features: TArrayOrDictionary<TStr, TInt>? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TText {
    override fun format(): String = Representations.elementRepr("text",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, null, `text`),ArgumentEntry(false, "font",
            `font`),ArgumentEntry(false, "fallback", `fallback`),ArgumentEntry(false, "style",
            `style`),ArgumentEntry(false, "weight", `weight`),ArgumentEntry(false, "stretch",
            `stretch`),ArgumentEntry(false, "size", `sz`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "tracking",
            `tracking`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false,
            "cjk-latin-spacing", `cjkLatinSpacing`),ArgumentEntry(false, "baseline",
            `baseline`),ArgumentEntry(false, "overhang", `overhang`),ArgumentEntry(false, "top-edge",
            `topEdge`),ArgumentEntry(false, "bottom-edge", `bottomEdge`),ArgumentEntry(false, "lang",
            `lang`),ArgumentEntry(false, "region", `region`),ArgumentEntry(false, "script",
            `script`),ArgumentEntry(false, "dir", `dir`),ArgumentEntry(false, "hyphenate",
            `hyphenate`),ArgumentEntry(false, "costs", `costs`),ArgumentEntry(false, "kerning",
            `kerning`),ArgumentEntry(false, "alternates", `alternates`),ArgumentEntry(false,
            "stylistic-set", `stylisticSet`),ArgumentEntry(false, "ligatures",
            `ligatures`),ArgumentEntry(false, "discretionary-ligatures",
            `discretionaryLigatures`),ArgumentEntry(false, "historical-ligatures",
            `historicalLigatures`),ArgumentEntry(false, "number-type", `numberType`),ArgumentEntry(false,
            "number-width", `numberWidth`),ArgumentEntry(false, "slashed-zero",
            `slashedZero`),ArgumentEntry(false, "fractions", `fractions`),ArgumentEntry(false, "features",
            `features`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TText(
    @TContentBody body: TContent? = null,
    text: TStr? = null,
    font: TArrayOrStr<TStr>? = null,
    fallback: TBool? = null,
    style: TTextStyle? = null,
    weight: TIntOrTextWeight? = null,
    stretch: TRatio? = null,
    sz: TLength? = null,
    fill: TColorOrGradientOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    tracking: TLength? = null,
    spacing: TRelative? = null,
    cjkLatinSpacing: TAutoOrNone? = null,
    baseline: TLength? = null,
    overhang: TBool? = null,
    topEdge: TLengthOrTextTopEdge? = null,
    bottomEdge: TLengthOrTextBottomEdge? = null,
    lang: TStr? = null,
    region: TNoneOrStr? = null,
    script: TAutoOrStr? = null,
    dir: TAutoOrDirection? = null,
    hyphenate: TAutoOrBool? = null,
    costs: TDictionary<TValue>? = null,
    kerning: TBool? = null,
    alternates: TBool? = null,
    stylisticSet: TArrayOrIntOrNone<TInt>? = null,
    ligatures: TBool? = null,
    discretionaryLigatures: TBool? = null,
    historicalLigatures: TBool? = null,
    numberType: TAutoOrTextNumberType? = null,
    numberWidth: TAutoOrTextNumberWidth? = null,
    slashedZero: TBool? = null,
    fractions: TBool? = null,
    features: TArrayOrDictionary<TStr, TInt>? = null,
    label: TLabel? = null,
): TText = TTextImpl(`body`, `text`, `font`, `fallback`, `style`, `weight`, `stretch`, `sz`, `fill`,
        `stroke`, `tracking`, `spacing`, `cjkLatinSpacing`, `baseline`, `overhang`, `topEdge`,
        `bottomEdge`, `lang`, `region`, `script`, `dir`, `hyphenate`, `costs`, `kerning`, `alternates`,
        `stylisticSet`, `ligatures`, `discretionaryLigatures`, `historicalLigatures`, `numberType`,
        `numberWidth`, `slashedZero`, `fractions`, `features`, `label`)

@TSetRuleType(
    "text",
    TSetTextImpl::class,
)
public interface TSetText : TSetRule {
    override val elem: String
        get() = "text"

    public val font: TArrayOrStr<TStr>?

    public val fallback: TBool?

    public val style: TTextStyle?

    public val weight: TIntOrTextWeight?

    public val stretch: TRatio?

    public val sz: TLength?

    public val fill: TColorOrGradientOrTiling?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val tracking: TLength?

    public val spacing: TRelative?

    public val cjkLatinSpacing: TAutoOrNone?

    public val baseline: TLength?

    public val overhang: TBool?

    public val topEdge: TLengthOrTextTopEdge?

    public val bottomEdge: TLengthOrTextBottomEdge?

    public val lang: TStr?

    public val region: TNoneOrStr?

    public val script: TAutoOrStr?

    public val dir: TAutoOrDirection?

    public val hyphenate: TAutoOrBool?

    public val costs: TDictionary<TValue>?

    public val kerning: TBool?

    public val alternates: TBool?

    public val stylisticSet: TArrayOrIntOrNone<TInt>?

    public val ligatures: TBool?

    public val discretionaryLigatures: TBool?

    public val historicalLigatures: TBool?

    public val numberType: TAutoOrTextNumberType?

    public val numberWidth: TAutoOrTextNumberWidth?

    public val slashedZero: TBool?

    public val fractions: TBool?

    public val features: TArrayOrDictionary<TStr, TInt>?

    override fun format(): String = Representations.setRepr("text",ArgumentEntry(false, "font",
            `font`),ArgumentEntry(false, "fallback", `fallback`),ArgumentEntry(false, "style",
            `style`),ArgumentEntry(false, "weight", `weight`),ArgumentEntry(false, "stretch",
            `stretch`),ArgumentEntry(false, "size", `sz`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "tracking",
            `tracking`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false,
            "cjk-latin-spacing", `cjkLatinSpacing`),ArgumentEntry(false, "baseline",
            `baseline`),ArgumentEntry(false, "overhang", `overhang`),ArgumentEntry(false, "top-edge",
            `topEdge`),ArgumentEntry(false, "bottom-edge", `bottomEdge`),ArgumentEntry(false, "lang",
            `lang`),ArgumentEntry(false, "region", `region`),ArgumentEntry(false, "script",
            `script`),ArgumentEntry(false, "dir", `dir`),ArgumentEntry(false, "hyphenate",
            `hyphenate`),ArgumentEntry(false, "costs", `costs`),ArgumentEntry(false, "kerning",
            `kerning`),ArgumentEntry(false, "alternates", `alternates`),ArgumentEntry(false,
            "stylistic-set", `stylisticSet`),ArgumentEntry(false, "ligatures",
            `ligatures`),ArgumentEntry(false, "discretionary-ligatures",
            `discretionaryLigatures`),ArgumentEntry(false, "historical-ligatures",
            `historicalLigatures`),ArgumentEntry(false, "number-type", `numberType`),ArgumentEntry(false,
            "number-width", `numberWidth`),ArgumentEntry(false, "slashed-zero",
            `slashedZero`),ArgumentEntry(false, "fractions", `fractions`),ArgumentEntry(false, "features",
            `features`),)
}

internal class TSetTextImpl(
    @TSerialName("font")
    override val font: TArrayOrStr<TStr>? = null,
    @TSerialName("fallback")
    override val fallback: TBool? = null,
    @TSerialName("style")
    override val style: TTextStyle? = null,
    @TSerialName("weight")
    override val weight: TIntOrTextWeight? = null,
    @TSerialName("stretch")
    override val stretch: TRatio? = null,
    @TSerialName("size")
    override val sz: TLength? = null,
    @TSerialName("fill")
    override val fill: TColorOrGradientOrTiling? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("tracking")
    override val tracking: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TRelative? = null,
    @TSerialName("cjk-latin-spacing")
    override val cjkLatinSpacing: TAutoOrNone? = null,
    @TSerialName("baseline")
    override val baseline: TLength? = null,
    @TSerialName("overhang")
    override val overhang: TBool? = null,
    @TSerialName("top-edge")
    override val topEdge: TLengthOrTextTopEdge? = null,
    @TSerialName("bottom-edge")
    override val bottomEdge: TLengthOrTextBottomEdge? = null,
    @TSerialName("lang")
    override val lang: TStr? = null,
    @TSerialName("region")
    override val region: TNoneOrStr? = null,
    @TSerialName("script")
    override val script: TAutoOrStr? = null,
    @TSerialName("dir")
    override val dir: TAutoOrDirection? = null,
    @TSerialName("hyphenate")
    override val hyphenate: TAutoOrBool? = null,
    @TSerialName("costs")
    override val costs: TDictionary<TValue>? = null,
    @TSerialName("kerning")
    override val kerning: TBool? = null,
    @TSerialName("alternates")
    override val alternates: TBool? = null,
    @TSerialName("stylistic-set")
    override val stylisticSet: TArrayOrIntOrNone<TInt>? = null,
    @TSerialName("ligatures")
    override val ligatures: TBool? = null,
    @TSerialName("discretionary-ligatures")
    override val discretionaryLigatures: TBool? = null,
    @TSerialName("historical-ligatures")
    override val historicalLigatures: TBool? = null,
    @TSerialName("number-type")
    override val numberType: TAutoOrTextNumberType? = null,
    @TSerialName("number-width")
    override val numberWidth: TAutoOrTextNumberWidth? = null,
    @TSerialName("slashed-zero")
    override val slashedZero: TBool? = null,
    @TSerialName("fractions")
    override val fractions: TBool? = null,
    @TSerialName("features")
    override val features: TArrayOrDictionary<TStr, TInt>? = null,
) : TSetText

@TypstOverloads
public fun TSetText(
    font: TArrayOrStr<TStr>? = null,
    fallback: TBool? = null,
    style: TTextStyle? = null,
    weight: TIntOrTextWeight? = null,
    stretch: TRatio? = null,
    sz: TLength? = null,
    fill: TColorOrGradientOrTiling? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    tracking: TLength? = null,
    spacing: TRelative? = null,
    cjkLatinSpacing: TAutoOrNone? = null,
    baseline: TLength? = null,
    overhang: TBool? = null,
    topEdge: TLengthOrTextTopEdge? = null,
    bottomEdge: TLengthOrTextBottomEdge? = null,
    lang: TStr? = null,
    region: TNoneOrStr? = null,
    script: TAutoOrStr? = null,
    dir: TAutoOrDirection? = null,
    hyphenate: TAutoOrBool? = null,
    costs: TDictionary<TValue>? = null,
    kerning: TBool? = null,
    alternates: TBool? = null,
    stylisticSet: TArrayOrIntOrNone<TInt>? = null,
    ligatures: TBool? = null,
    discretionaryLigatures: TBool? = null,
    historicalLigatures: TBool? = null,
    numberType: TAutoOrTextNumberType? = null,
    numberWidth: TAutoOrTextNumberWidth? = null,
    slashedZero: TBool? = null,
    fractions: TBool? = null,
    features: TArrayOrDictionary<TStr, TInt>? = null,
): TSetText = TSetTextImpl(`font`, `fallback`, `style`, `weight`, `stretch`, `sz`, `fill`, `stroke`,
        `tracking`, `spacing`, `cjkLatinSpacing`, `baseline`, `overhang`, `topEdge`, `bottomEdge`,
        `lang`, `region`, `script`, `dir`, `hyphenate`, `costs`, `kerning`, `alternates`,
        `stylisticSet`, `ligatures`, `discretionaryLigatures`, `historicalLigatures`, `numberType`,
        `numberWidth`, `slashedZero`, `fractions`, `features`)
