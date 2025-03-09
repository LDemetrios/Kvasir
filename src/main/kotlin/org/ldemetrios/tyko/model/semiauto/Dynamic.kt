@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.Boolean
import kotlin.Int
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

public interface TDynamic : TValue, DynamicEnum, TSetBibliography, TBibliography, TSetList, TList,
        TListItem, TSetCite, TCite, TSetDocument, TDocument, TEmph, TSetFigure, TFigure, TSetCaption,
        TCaption, TSetFootnote, TFootnote, TSetFootnoteEntry, TFootnoteEntry, TSetHeading, THeading,
        TLink, TSetEnum, TEnum, TSetEnumItem, TEnumItem, TSetOutline, TOutline, TSetPar, TPar,
        TSetParLine, TParLine, TParbreak, TSetQuote, TQuote, TSetRef, TRef, TSetStrong, TStrong,
        TSetTable, TTable, TSetTableCell, TTableCell, TSetTableHline, TTableHline, TSetTableVline,
        TTableVline, TSetTableHeader, TTableHeader, TSetTableFooter, TTableFooter, TSetTerms, TTerms,
        TTermsItem, TSetHighlight, THighlight, TSetLinebreak, TLinebreak, TSetOverline, TOverline,
        TSetRaw, TRaw, TRawLine, TSetSmallcaps, TSmallcaps, TSetSmartquote, TSmartquote, TSetStrike,
        TStrike, TSetSub, TSub, TSetSuper, TSuper, TSetText, TText, TSetUnderline, TUnderline,
        TSetMathAccent, TMathAccent, TMathBinom, TSetMathCancel, TMathCancel, TSetMathCases, TMathCases,
        TMathClass, TSetMathEquation, TMathEquation, TMathFrac, TSetMathMat, TMathMat, TMathPrimes,
        TSetMathStretch, TMathStretch, TSetMathOp, TMathOp, TSetMathVec, TMathVec, TSetAlign, TAlign,
        TSetBlock, TBlock, TSetBox, TBox, TSetColbreak, TColbreak, TSetColumns, TColumns, TSetGrid,
        TGrid, TSetGridCell, TGridCell, TSetGridHline, TGridHline, TSetGridVline, TGridVline,
        TSetGridHeader, TGridHeader, TSetGridFooter, TGridFooter, THide, TSetMove, TMove, TSetPad, TPad,
        TSetPage, TPage, TSetPagebreak, TPagebreak, TSetPlace, TPlace, TFlush, TSetRepeat, TRepeat,
        TSetRotate, TRotate, TSetScale, TScale, TSetSkew, TSkew, TSetH, TH, TSetV, TV, TSetStack,
        TStack, TSetCircle, TCircle, TSetEllipse, TEllipse, TSetImage, TImage, TSetLine, TLine,
        TSetPath, TPath, TSetPolygon, TPolygon, TSetRect, TRect, TSetSquare, TSquare,
        TMetadata<TDynamic>, TSetMathAttach, TMathAttach, TMathScripts, TSetMathLimits, TMathLimits,
        TSetMathLr, TMathLr, TMathMid, TSetMathRoot, TMathRoot, TMathUnderline, TMathOverline,
        TSetMathUnderbrace, TMathUnderbrace, TSetMathOverbrace, TMathOverbrace, TSetMathUnderbracket,
        TMathUnderbracket, TSetMathOverbracket, TMathOverbracket, TSetMathUnderparen, TMathUnderparen,
        TSetMathOverparen, TMathOverparen, TSetMathUndershell, TMathUndershell, TSetMathOvershell,
        TMathOvershell, TNone, TAuto, TBool, TInt, TFloat, TStr, TArray<TDynamic>,
        TDictionary<TDynamic>, TBytes, TArguments<TDynamic>, TContent, TAngle, TRelative, TAlignment,
        TLength, TRatio, TRelativeImpl, TFraction, TColor, TGradient, TLuma, TOklab, TOklch, TLinearRgb,
        TRgb, TCmyk, THsl, THsv, TLinearGradient, TRadialGradient, TConicGradient, TTiling, TVersion,
        TLabel, TDatetime, TDuration, TType, TModule, TPlugin, TAlignmentImpl, TDirection, TRegex,
        TMathAlignPoint, TStroke, TSelector, TElementSelector, TLabelSelector, TRegexSelector,
        TBeforeSelector, TAfterSelector, TAndSelector, TOrSelector, TCounter, TState, TDecimal, TStyled,
        TSequence, TSpace, TFunction, TClosure, TWith, TNativeFunc, TStyle, TShowRule, TContext,
        TStyleDeprecated, TLayout, TLocate, TCounterUpdate, TCounterStep, TStateUpdate, TSymbol,
        TSetCurve, TCurve, TSetCurveMove, TCurveMove, TSetCurveLine, TCurveLine, TSetCurveQuad,
        TCurveQuad, TSetCurveCubic, TCurveCubic, TSetCurveClose, TCurveClose, TSetOutlineEntry,
        TOutlineEntry, TSetEmbed, TEmbed, TSetElem, TElem, TFrame, TSymbolElem, TMargin<TDynamic>,
        TSides<TDynamic>, TElement {
    override val elem: String

    override val size: Int

    override val path: TDynamic
        get() = fieldAccess("path")

    override val title: TDynamic
        get() = fieldAccess("title")

    override val full: TDynamic
        get() = fieldAccess("full")

    override val style: TDynamic
        get() = fieldAccess("style")

    override val children: TDynamic
        get() = fieldAccess("children")

    override val tight: TDynamic
        get() = fieldAccess("tight")

    override val marker: TDynamic
        get() = fieldAccess("marker")

    override val indent: TDynamic
        get() = fieldAccess("indent")

    override val bodyIndent: TDynamic
        get() = fieldAccess("bodyIndent")

    override val spacing: TDynamic
        get() = fieldAccess("spacing")

    override val body: TDynamic
        get() = fieldAccess("body")

    override val key: TDynamic
        get() = fieldAccess("key")

    override val supplement: TDynamic
        get() = fieldAccess("supplement")

    override val form: TDynamic
        get() = fieldAccess("form")

    override val author: TDynamic
        get() = fieldAccess("author")

    override val keywords: TDynamic
        get() = fieldAccess("keywords")

    override val date: TDynamic
        get() = fieldAccess("date")

    override val placement: TDynamic
        get() = fieldAccess("placement")

    override val scope: TDynamic
        get() = fieldAccess("scope")

    override val caption: TDynamic
        get() = fieldAccess("caption")

    override val kind: TDynamic
        get() = fieldAccess("kind")

    override val numbering: TDynamic
        get() = fieldAccess("numbering")

    override val gap: TDynamic
        get() = fieldAccess("gap")

    override val outlined: TDynamic
        get() = fieldAccess("outlined")

    override val position: TDynamic
        get() = fieldAccess("position")

    override val separator: TDynamic
        get() = fieldAccess("separator")

    override val note: TDynamic
        get() = fieldAccess("note")

    override val clearance: TDynamic
        get() = fieldAccess("clearance")

    override val level: TDynamic
        get() = fieldAccess("level")

    override val depth: TDynamic
        get() = fieldAccess("depth")

    override val offset: TDynamic
        get() = fieldAccess("offset")

    override val bookmarked: TDynamic
        get() = fieldAccess("bookmarked")

    override val hangingIndent: TDynamic
        get() = fieldAccess("hangingIndent")

    override val dest: TDynamic
        get() = fieldAccess("dest")

    override val start: TDynamic
        get() = fieldAccess("start")

    override val numberAlign: TDynamic
        get() = fieldAccess("numberAlign")

    override val reversed: TDynamic
        get() = fieldAccess("reversed")

    override val number: TDynamic
        get() = fieldAccess("number")

    override val target: TDynamic
        get() = fieldAccess("target")

    override val fill: TDynamic
        get() = fieldAccess("fill")

    override val leading: TDynamic
        get() = fieldAccess("leading")

    override val justify: TDynamic
        get() = fieldAccess("justify")

    override val linebreaks: TDynamic
        get() = fieldAccess("linebreaks")

    override val firstLineIndent: TDynamic
        get() = fieldAccess("firstLineIndent")

    override val numberMargin: TDynamic
        get() = fieldAccess("numberMargin")

    override val numberClearance: TDynamic
        get() = fieldAccess("numberClearance")

    override val numberingScope: TDynamic
        get() = fieldAccess("numberingScope")

    override val block: TDynamic
        get() = fieldAccess("block")

    override val quotes: TDynamic
        get() = fieldAccess("quotes")

    override val attribution: TDynamic
        get() = fieldAccess("attribution")

    override val delta: TDynamic
        get() = fieldAccess("delta")

    override val columns: TDynamic
        get() = fieldAccess("columns")

    override val rows: TDynamic
        get() = fieldAccess("rows")

    override val gutter: TDynamic
        get() = fieldAccess("gutter")

    override val columnGutter: TDynamic
        get() = fieldAccess("columnGutter")

    override val rowGutter: TDynamic
        get() = fieldAccess("rowGutter")

    override val align: TDynamic
        get() = fieldAccess("align")

    override val stroke: TDynamic
        get() = fieldAccess("stroke")

    override val inset: TDynamic
        get() = fieldAccess("inset")

    override val top: TDynamic
        get() = fieldAccess("top")

    override val right: TDynamic
        get() = fieldAccess("right")

    override val bottom: TDynamic
        get() = fieldAccess("bottom")

    override val left: TDynamic
        get() = fieldAccess("left")

    override val x: TDynamic
        get() = fieldAccess("x")

    override val y: TDynamic
        get() = fieldAccess("y")

    override val colspan: TDynamic
        get() = fieldAccess("colspan")

    override val rowspan: TDynamic
        get() = fieldAccess("rowspan")

    override val breakable: TDynamic
        get() = fieldAccess("breakable")

    override val end: TDynamic
        get() = fieldAccess("end")

    override val repeat: TDynamic
        get() = fieldAccess("repeat")

    override val term: TDynamic
        get() = fieldAccess("term")

    override val description: TDynamic
        get() = fieldAccess("description")

    override val topEdge: TDynamic
        get() = fieldAccess("topEdge")

    override val bottomEdge: TDynamic
        get() = fieldAccess("bottomEdge")

    override val extent: TDynamic
        get() = fieldAccess("extent")

    override val radius: TDynamic
        get() = fieldAccess("radius")

    override val evade: TDynamic
        get() = fieldAccess("evade")

    override val background: TDynamic
        get() = fieldAccess("background")

    override val text: TDynamic
        get() = fieldAccess("text")

    override val lang: TDynamic
        get() = fieldAccess("lang")

    override val syntaxes: TDynamic
        get() = fieldAccess("syntaxes")

    override val theme: TDynamic
        get() = fieldAccess("theme")

    override val tabSize: TDynamic
        get() = fieldAccess("tabSize")

    override val count: TDynamic
        get() = fieldAccess("count")

    override val all: TDynamic
        get() = fieldAccess("all")

    override val double: TDynamic
        get() = fieldAccess("double")

    override val enabled: TDynamic
        get() = fieldAccess("enabled")

    override val alternative: TDynamic
        get() = fieldAccess("alternative")

    override val typographic: TDynamic
        get() = fieldAccess("typographic")

    override val baseline: TDynamic
        get() = fieldAccess("baseline")

    override val sz: TDynamic
        get() = fieldAccess("sz")

    override val font: TDynamic
        get() = fieldAccess("font")

    override val fallback: TDynamic
        get() = fieldAccess("fallback")

    override val weight: TDynamic
        get() = fieldAccess("weight")

    override val stretch: TDynamic
        get() = fieldAccess("stretch")

    override val tracking: TDynamic
        get() = fieldAccess("tracking")

    override val cjkLatinSpacing: TDynamic
        get() = fieldAccess("cjkLatinSpacing")

    override val overhang: TDynamic
        get() = fieldAccess("overhang")

    override val region: TDynamic
        get() = fieldAccess("region")

    override val script: TDynamic
        get() = fieldAccess("script")

    override val dir: TDynamic
        get() = fieldAccess("dir")

    override val hyphenate: TDynamic
        get() = fieldAccess("hyphenate")

    override val costs: TDynamic
        get() = fieldAccess("costs")

    override val kerning: TDynamic
        get() = fieldAccess("kerning")

    override val alternates: TDynamic
        get() = fieldAccess("alternates")

    override val stylisticSet: TDynamic
        get() = fieldAccess("stylisticSet")

    override val ligatures: TDynamic
        get() = fieldAccess("ligatures")

    override val discretionaryLigatures: TDynamic
        get() = fieldAccess("discretionaryLigatures")

    override val historicalLigatures: TDynamic
        get() = fieldAccess("historicalLigatures")

    override val numberType: TDynamic
        get() = fieldAccess("numberType")

    override val numberWidth: TDynamic
        get() = fieldAccess("numberWidth")

    override val slashedZero: TDynamic
        get() = fieldAccess("slashedZero")

    override val fractions: TDynamic
        get() = fieldAccess("fractions")

    override val features: TDynamic
        get() = fieldAccess("features")

    override val base: TDynamic
        get() = fieldAccess("base")

    override val accent: TDynamic
        get() = fieldAccess("accent")

    override val upper: TDynamic
        get() = fieldAccess("upper")

    override val lower: TDynamic
        get() = fieldAccess("lower")

    override val length: TDynamic
        get() = fieldAccess("length")

    override val inverted: TDynamic
        get() = fieldAccess("inverted")

    override val cross: TDynamic
        get() = fieldAccess("cross")

    override val angle: TDynamic
        get() = fieldAccess("angle")

    override val delim: TDynamic
        get() = fieldAccess("delim")

    override val reverse: TDynamic
        get() = fieldAccess("reverse")

    override val `class`: TDynamic
        get() = fieldAccess("class")

    override val num: TDynamic
        get() = fieldAccess("num")

    override val denom: TDynamic
        get() = fieldAccess("denom")

    override val augment: TDynamic
        get() = fieldAccess("augment")

    override val rowGap: TDynamic
        get() = fieldAccess("rowGap")

    override val columnGap: TDynamic
        get() = fieldAccess("columnGap")

    override val limits: TDynamic
        get() = fieldAccess("limits")

    override val alignment: TDynamic
        get() = fieldAccess("alignment")

    override val width: TDynamic
        get() = fieldAccess("width")

    override val height: TDynamic
        get() = fieldAccess("height")

    override val outset: TDynamic
        get() = fieldAccess("outset")

    override val above: TDynamic
        get() = fieldAccess("above")

    override val below: TDynamic
        get() = fieldAccess("below")

    override val clip: TDynamic
        get() = fieldAccess("clip")

    override val sticky: TDynamic
        get() = fieldAccess("sticky")

    override val weak: TDynamic
        get() = fieldAccess("weak")

    override val dx: TDynamic
        get() = fieldAccess("dx")

    override val dy: TDynamic
        get() = fieldAccess("dy")

    override val rest: TDynamic
        get() = fieldAccess("rest")

    override val paper: TDynamic
        get() = fieldAccess("paper")

    override val flipped: TDynamic
        get() = fieldAccess("flipped")

    override val margin: TDynamic
        get() = fieldAccess("margin")

    override val binding: TDynamic
        get() = fieldAccess("binding")

    override val `header`: TDynamic
        get() = fieldAccess("header")

    override val headerAscent: TDynamic
        get() = fieldAccess("headerAscent")

    override val footer: TDynamic
        get() = fieldAccess("footer")

    override val footerDescent: TDynamic
        get() = fieldAccess("footerDescent")

    override val foreground: TDynamic
        get() = fieldAccess("foreground")

    override val inside: TDynamic
        get() = fieldAccess("inside")

    override val outside: TDynamic
        get() = fieldAccess("outside")

    override val to: TDynamic
        get() = fieldAccess("to")

    override val float: TDynamic
        get() = fieldAccess("float")

    override val origin: TDynamic
        get() = fieldAccess("origin")

    override val reflow: TDynamic
        get() = fieldAccess("reflow")

    override val factor: TDynamic
        get() = fieldAccess("factor")

    override val ax: TDynamic
        get() = fieldAccess("ax")

    override val ay: TDynamic
        get() = fieldAccess("ay")

    override val amount: TDynamic
        get() = fieldAccess("amount")

    override val source: TDynamic
        get() = fieldAccess("source")

    override val format: TDynamic
        get() = fieldAccess("format")

    override val alt: TDynamic
        get() = fieldAccess("alt")

    override val fit: TDynamic
        get() = fieldAccess("fit")

    override val scaling: TDynamic
        get() = fieldAccess("scaling")

    override val icc: TDynamic
        get() = fieldAccess("icc")

    override val vertices: TDynamic
        get() = fieldAccess("vertices")

    override val fillRule: TDynamic
        get() = fieldAccess("fillRule")

    override val closed: TDynamic
        get() = fieldAccess("closed")

    override val `value`: TDynamic
        get() = fieldAccess("value")

    override val t: TDynamic
        get() = fieldAccess("t")

    override val b: TDynamic
        get() = fieldAccess("b")

    override val tl: TDynamic
        get() = fieldAccess("tl")

    override val bl: TDynamic
        get() = fieldAccess("bl")

    override val tr: TDynamic
        get() = fieldAccess("tr")

    override val br: TDynamic
        get() = fieldAccess("br")

    override val `inline`: TDynamic
        get() = fieldAccess("inline")

    override val index: TDynamic
        get() = fieldAccess("index")

    override val radicand: TDynamic
        get() = fieldAccess("radicand")

    override val `annotation`: TDynamic
        get() = fieldAccess("annotation")

    override val positional: TDynamic
        get() = fieldAccess("positional")

    override val named: TDynamic
        get() = fieldAccess("named")

    override val label: TDynamic
        get() = fieldAccess("label")

    override val deg: TDynamic
        get() = fieldAccess("deg")

    override val pt: TDynamic
        get() = fieldAccess("pt")

    override val em: TDynamic
        get() = fieldAccess("em")

    override val rel: TDynamic
        get() = fieldAccess("rel")

    override val abs: TDynamic
        get() = fieldAccess("abs")

    override val lightness: TDynamic
        get() = fieldAccess("lightness")

    override val alpha: TDynamic
        get() = fieldAccess("alpha")

    override val a: TDynamic
        get() = fieldAccess("a")

    override val chroma: TDynamic
        get() = fieldAccess("chroma")

    override val hue: TDynamic
        get() = fieldAccess("hue")

    override val red: TDynamic
        get() = fieldAccess("red")

    override val green: TDynamic
        get() = fieldAccess("green")

    override val blue: TDynamic
        get() = fieldAccess("blue")

    override val hex: TDynamic
        get() = fieldAccess("hex")

    override val cyan: TDynamic
        get() = fieldAccess("cyan")

    override val magenta: TDynamic
        get() = fieldAccess("magenta")

    override val yellow: TDynamic
        get() = fieldAccess("yellow")

    override val saturation: TDynamic
        get() = fieldAccess("saturation")

    override val stops: TDynamic
        get() = fieldAccess("stops")

    override val space: TDynamic
        get() = fieldAccess("space")

    override val relative: TDynamic
        get() = fieldAccess("relative")

    override val center: TDynamic
        get() = fieldAccess("center")

    override val focalCenter: TDynamic
        get() = fieldAccess("focalCenter")

    override val focalRadius: TDynamic
        get() = fieldAccess("focalRadius")

    override val name: TDynamic
        get() = fieldAccess("name")

    override val year: TDynamic
        get() = fieldAccess("year")

    override val month: TDynamic
        get() = fieldAccess("month")

    override val day: TDynamic
        get() = fieldAccess("day")

    override val hour: TDynamic
        get() = fieldAccess("hour")

    override val minute: TDynamic
        get() = fieldAccess("minute")

    override val second: TDynamic
        get() = fieldAccess("second")

    override val seconds: TDynamic
        get() = fieldAccess("seconds")

    override val minutes: TDynamic
        get() = fieldAccess("minutes")

    override val hours: TDynamic
        get() = fieldAccess("hours")

    override val days: TDynamic
        get() = fieldAccess("days")

    override val weeks: TDynamic
        get() = fieldAccess("weeks")

    override val horizontal: TDynamic
        get() = fieldAccess("horizontal")

    override val vertical: TDynamic
        get() = fieldAccess("vertical")

    override val regex: TDynamic
        get() = fieldAccess("regex")

    override val paint: TDynamic
        get() = fieldAccess("paint")

    override val thickness: TDynamic
        get() = fieldAccess("thickness")

    override val cap: TDynamic
        get() = fieldAccess("cap")

    override val join: TDynamic
        get() = fieldAccess("join")

    override val dash: TDynamic
        get() = fieldAccess("dash")

    override val miterLimit: TDynamic
        get() = fieldAccess("miterLimit")

    override val element: TDynamic
        get() = fieldAccess("element")

    override val `where`: TDynamic
        get() = fieldAccess("where")

    override val selector: TDynamic
        get() = fieldAccess("selector")

    override val inclusive: TDynamic
        get() = fieldAccess("inclusive")

    override val variants: TDynamic
        get() = fieldAccess("variants")

    override val `init`: TDynamic
        get() = fieldAccess("init")

    override val styles: TDynamic
        get() = fieldAccess("styles")

    override val child: TDynamic
        get() = fieldAccess("child")

    override val node: TDynamic
        get() = fieldAccess("node")

    override val defaults: TDynamic
        get() = fieldAccess("defaults")

    override val captured: TDynamic
        get() = fieldAccess("captured")

    override val numPosParams: TDynamic
        get() = fieldAccess("numPosParams")

    override val args: TDynamic
        get() = fieldAccess("args")

    override val transform: TDynamic
        get() = fieldAccess("transform")

    override val func: TDynamic
        get() = fieldAccess("func")

    override val update: TDynamic
        get() = fieldAccess("update")

    override val components: TDynamic
        get() = fieldAccess("components")

    override val control: TDynamic
        get() = fieldAccess("control")

    override val controlStart: TDynamic
        get() = fieldAccess("controlStart")

    override val controlEnd: TDynamic
        get() = fieldAccess("controlEnd")

    override val mode: TDynamic
        get() = fieldAccess("mode")

    override val `data`: TDynamic
        get() = fieldAccess("data")

    override val relationship: TDynamic
        get() = fieldAccess("relationship")

    override val mimeType: TDynamic
        get() = fieldAccess("mimeType")

    override val tag: TDynamic
        get() = fieldAccess("tag")

    override val attrs: TDynamic
        get() = fieldAccess("attrs")

    public fun fieldAccess(`field`: String): TDynamic

    override fun type(): TType

    override fun format(): String

    override fun isEmpty(): Boolean

    override fun func(): TElement

    override fun `get`(index: Int): TDynamic
}
