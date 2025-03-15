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
    "page",
    ["page", "content"],
    TPageImpl::class,
)
public interface TPage : TContent {
    public val body: TContent

    public val paper: TPagePaper?

    public val width: TAutoOrLength?

    public val height: TAutoOrLength?

    public val flipped: TBool?

    public val margin: TAutoOrMarginOrRelative<TAutoOrNoneOrRelative>?

    public val binding: TAlignmentOrAuto?

    public val columns: TInt?

    public val fill: TAutoOrColorOrGradientOrNoneOrTiling?

    public val numbering: TFunctionOrNoneOrStr?

    public val numberAlign: TAlignment?

    public val `header`: TAutoOrContentOrNone?

    public val headerAscent: TRelative?

    public val footer: TAutoOrContentOrNone?

    public val footerDescent: TRelative?

    public val background: TContentOrNone?

    public val foreground: TContentOrNone?

    public val supplement: TContent?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("page")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val paperType: InternalType = ConcreteType("page-paper")

        internal val widthType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"))

        internal val heightType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"))

        internal val flippedType: InternalType = ConcreteType("bool")

        internal val marginType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("margin",
                listOf(UnionType(ConcreteType("auto"), ConcreteType("none"), ConcreteType("relative")))),
                ConcreteType("relative"))

        internal val bindingType: InternalType = UnionType(ConcreteType("alignment"),
                ConcreteType("auto"))

        internal val columnsType: InternalType = ConcreteType("int")

        internal val fillType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("gradient"), ConcreteType("none"), ConcreteType("tiling"))

        internal val numberingType: InternalType = UnionType(ConcreteType("function"),
                ConcreteType("none"), ConcreteType("str"))

        internal val numberAlignType: InternalType = ConcreteType("alignment")

        internal val headerType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("content"),
                ConcreteType("none"))

        internal val headerAscentType: InternalType = ConcreteType("relative")

        internal val footerType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("content"),
                ConcreteType("none"))

        internal val footerDescentType: InternalType = ConcreteType("relative")

        internal val backgroundType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))

        internal val foregroundType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))

        internal val supplementType: InternalType = ConcreteType("content")
    }
}

internal data class TPageImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("paper")
    override val paper: TPagePaper? = null,
    @TSerialName("width")
    override val width: TAutoOrLength? = null,
    @TSerialName("height")
    override val height: TAutoOrLength? = null,
    @TSerialName("flipped")
    override val flipped: TBool? = null,
    @TSerialName("margin")
    override val margin: TAutoOrMarginOrRelative<TAutoOrNoneOrRelative>? = null,
    @TSerialName("binding")
    override val binding: TAlignmentOrAuto? = null,
    @TSerialName("columns")
    override val columns: TInt? = null,
    @TSerialName("fill")
    override val fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignment? = null,
    @TSerialName("header")
    override val `header`: TAutoOrContentOrNone? = null,
    @TSerialName("header-ascent")
    override val headerAscent: TRelative? = null,
    @TSerialName("footer")
    override val footer: TAutoOrContentOrNone? = null,
    @TSerialName("footer-descent")
    override val footerDescent: TRelative? = null,
    @TSerialName("background")
    override val background: TContentOrNone? = null,
    @TSerialName("foreground")
    override val foreground: TContentOrNone? = null,
    @TSerialName("supplement")
    override val supplement: TContent? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TPage {
    override fun format(): String = Representations.elementRepr("page",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "paper", `paper`),ArgumentEntry(false, "width",
            `width`),ArgumentEntry(false, "height", `height`),ArgumentEntry(false, "flipped",
            `flipped`),ArgumentEntry(false, "margin", `margin`),ArgumentEntry(false, "binding",
            `binding`),ArgumentEntry(false, "columns", `columns`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "numbering", `numbering`),ArgumentEntry(false, "number-align",
            `numberAlign`),ArgumentEntry(false, "header", `header`),ArgumentEntry(false, "header-ascent",
            `headerAscent`),ArgumentEntry(false, "footer", `footer`),ArgumentEntry(false,
            "footer-descent", `footerDescent`),ArgumentEntry(false, "background",
            `background`),ArgumentEntry(false, "foreground", `foreground`),ArgumentEntry(false,
            "supplement", `supplement`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TPage(
    @TContentBody body: TContent,
    paper: TPagePaper? = null,
    width: TAutoOrLength? = null,
    height: TAutoOrLength? = null,
    flipped: TBool? = null,
    margin: TAutoOrMarginOrRelative<TAutoOrNoneOrRelative>? = null,
    binding: TAlignmentOrAuto? = null,
    columns: TInt? = null,
    fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    numbering: TFunctionOrNoneOrStr? = null,
    numberAlign: TAlignment? = null,
    `header`: TAutoOrContentOrNone? = null,
    headerAscent: TRelative? = null,
    footer: TAutoOrContentOrNone? = null,
    footerDescent: TRelative? = null,
    background: TContentOrNone? = null,
    foreground: TContentOrNone? = null,
    supplement: TContent? = null,
    label: TLabel? = null,
): TPage = TPageImpl(`body`, `paper`, `width`, `height`, `flipped`, `margin`, `binding`, `columns`,
        `fill`, `numbering`, `numberAlign`, `header`, `headerAscent`, `footer`, `footerDescent`,
        `background`, `foreground`, `supplement`, `label`)

@TSetRuleType(
    "page",
    TSetPageImpl::class,
)
public interface TSetPage : TSetRule {
    override val elem: String
        get() = "page"

    public val paper: TPagePaper?

    public val width: TAutoOrLength?

    public val height: TAutoOrLength?

    public val flipped: TBool?

    public val margin: TAutoOrMarginOrRelative<TAutoOrNoneOrRelative>?

    public val binding: TAlignmentOrAuto?

    public val columns: TInt?

    public val fill: TAutoOrColorOrGradientOrNoneOrTiling?

    public val numbering: TFunctionOrNoneOrStr?

    public val numberAlign: TAlignment?

    public val `header`: TAutoOrContentOrNone?

    public val headerAscent: TRelative?

    public val footer: TAutoOrContentOrNone?

    public val footerDescent: TRelative?

    public val background: TContentOrNone?

    public val foreground: TContentOrNone?

    public val supplement: TContent?

    override fun format(): String = Representations.setRepr("page",ArgumentEntry(false, "paper",
            `paper`),ArgumentEntry(false, "width", `width`),ArgumentEntry(false, "height",
            `height`),ArgumentEntry(false, "flipped", `flipped`),ArgumentEntry(false, "margin",
            `margin`),ArgumentEntry(false, "binding", `binding`),ArgumentEntry(false, "columns",
            `columns`),ArgumentEntry(false, "fill", `fill`),ArgumentEntry(false, "numbering",
            `numbering`),ArgumentEntry(false, "number-align", `numberAlign`),ArgumentEntry(false,
            "header", `header`),ArgumentEntry(false, "header-ascent", `headerAscent`),ArgumentEntry(false,
            "footer", `footer`),ArgumentEntry(false, "footer-descent",
            `footerDescent`),ArgumentEntry(false, "background", `background`),ArgumentEntry(false,
            "foreground", `foreground`),ArgumentEntry(false, "supplement", `supplement`),)
}

internal class TSetPageImpl(
    @TSerialName("paper")
    override val paper: TPagePaper? = null,
    @TSerialName("width")
    override val width: TAutoOrLength? = null,
    @TSerialName("height")
    override val height: TAutoOrLength? = null,
    @TSerialName("flipped")
    override val flipped: TBool? = null,
    @TSerialName("margin")
    override val margin: TAutoOrMarginOrRelative<TAutoOrNoneOrRelative>? = null,
    @TSerialName("binding")
    override val binding: TAlignmentOrAuto? = null,
    @TSerialName("columns")
    override val columns: TInt? = null,
    @TSerialName("fill")
    override val fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignment? = null,
    @TSerialName("header")
    override val `header`: TAutoOrContentOrNone? = null,
    @TSerialName("header-ascent")
    override val headerAscent: TRelative? = null,
    @TSerialName("footer")
    override val footer: TAutoOrContentOrNone? = null,
    @TSerialName("footer-descent")
    override val footerDescent: TRelative? = null,
    @TSerialName("background")
    override val background: TContentOrNone? = null,
    @TSerialName("foreground")
    override val foreground: TContentOrNone? = null,
    @TSerialName("supplement")
    override val supplement: TContent? = null,
) : TSetPage

@TypstOverloads
public fun TSetPage(
    paper: TPagePaper? = null,
    width: TAutoOrLength? = null,
    height: TAutoOrLength? = null,
    flipped: TBool? = null,
    margin: TAutoOrMarginOrRelative<TAutoOrNoneOrRelative>? = null,
    binding: TAlignmentOrAuto? = null,
    columns: TInt? = null,
    fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    numbering: TFunctionOrNoneOrStr? = null,
    numberAlign: TAlignment? = null,
    `header`: TAutoOrContentOrNone? = null,
    headerAscent: TRelative? = null,
    footer: TAutoOrContentOrNone? = null,
    footerDescent: TRelative? = null,
    background: TContentOrNone? = null,
    foreground: TContentOrNone? = null,
    supplement: TContent? = null,
): TSetPage = TSetPageImpl(`paper`, `width`, `height`, `flipped`, `margin`, `binding`, `columns`,
        `fill`, `numbering`, `numberAlign`, `header`, `headerAscent`, `footer`, `footerDescent`,
        `background`, `foreground`, `supplement`)
