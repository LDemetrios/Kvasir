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
    "grid.cell",
    ["grid.cell", "content"],
    TGridCellImpl::class,
)
public interface TGridCell : TContent {
    public val body: TContent

    public val x: TAutoOrInt?

    public val y: TAutoOrInt?

    public val colspan: TInt?

    public val rowspan: TInt?

    public val fill: TAutoOrColorOrGradientOrNoneOrTiling?

    public val align: TAlignmentOrAuto?

    public val inset: TAutoOrRelativeOrSides<TAutoOrNoneOrRelative>?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val breakable: TAutoOrBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("grid.cell")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val xType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("int"))

        internal val yType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("int"))

        internal val colspanType: InternalType = ConcreteType("int")

        internal val rowspanType: InternalType = ConcreteType("int")

        internal val fillType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("gradient"), ConcreteType("none"), ConcreteType("tiling"))

        internal val alignType: InternalType = UnionType(ConcreteType("alignment"),
                ConcreteType("auto"))

        internal val insetType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("relative"),
                ConcreteType("sides", listOf(UnionType(ConcreteType("auto"), ConcreteType("none"),
                ConcreteType("relative")))))

        internal val strokeType: InternalType = UnionType(ConcreteType("color"),
                ConcreteType("dictionary", listOf(AnyType)), ConcreteType("gradient"),
                ConcreteType("length"), ConcreteType("none"), ConcreteType("stroke"),
                ConcreteType("tiling"))

        internal val breakableType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("bool"))
    }
}

internal data class TGridCellImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("x")
    override val x: TAutoOrInt? = null,
    @TSerialName("y")
    override val y: TAutoOrInt? = null,
    @TSerialName("colspan")
    override val colspan: TInt? = null,
    @TSerialName("rowspan")
    override val rowspan: TInt? = null,
    @TSerialName("fill")
    override val fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("align")
    override val align: TAlignmentOrAuto? = null,
    @TSerialName("inset")
    override val inset: TAutoOrRelativeOrSides<TAutoOrNoneOrRelative>? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("breakable")
    override val breakable: TAutoOrBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TGridCell {
    override fun format(): String = Representations.elementRepr("grid.cell",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "x", `x`),ArgumentEntry(false, "y", `y`),ArgumentEntry(false,
            "colspan", `colspan`),ArgumentEntry(false, "rowspan", `rowspan`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "align", `align`),ArgumentEntry(false, "inset",
            `inset`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "breakable",
            `breakable`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TGridCell(
    @TContentBody body: TContent,
    x: TAutoOrInt? = null,
    y: TAutoOrInt? = null,
    colspan: TInt? = null,
    rowspan: TInt? = null,
    fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    align: TAlignmentOrAuto? = null,
    inset: TAutoOrRelativeOrSides<TAutoOrNoneOrRelative>? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    breakable: TAutoOrBool? = null,
    label: TLabel? = null,
): TGridCell = TGridCellImpl(`body`, `x`, `y`, `colspan`, `rowspan`, `fill`, `align`, `inset`,
        `stroke`, `breakable`, `label`)

@TSetRuleType(
    "grid.cell",
    TSetGridCellImpl::class,
)
public interface TSetGridCell : TSetRule {
    override val elem: String
        get() = "grid.cell"

    public val x: TAutoOrInt?

    public val y: TAutoOrInt?

    public val colspan: TInt?

    public val rowspan: TInt?

    public val fill: TAutoOrColorOrGradientOrNoneOrTiling?

    public val align: TAlignmentOrAuto?

    public val inset: TAutoOrRelativeOrSides<TAutoOrNoneOrRelative>?

    public val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>?

    public val breakable: TAutoOrBool?

    override fun format(): String = Representations.setRepr("grid.cell",ArgumentEntry(false, "x",
            `x`),ArgumentEntry(false, "y", `y`),ArgumentEntry(false, "colspan",
            `colspan`),ArgumentEntry(false, "rowspan", `rowspan`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "align", `align`),ArgumentEntry(false, "inset",
            `inset`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "breakable",
            `breakable`),)
}

internal class TSetGridCellImpl(
    @TSerialName("x")
    override val x: TAutoOrInt? = null,
    @TSerialName("y")
    override val y: TAutoOrInt? = null,
    @TSerialName("colspan")
    override val colspan: TInt? = null,
    @TSerialName("rowspan")
    override val rowspan: TInt? = null,
    @TSerialName("fill")
    override val fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    @TSerialName("align")
    override val align: TAlignmentOrAuto? = null,
    @TSerialName("inset")
    override val inset: TAutoOrRelativeOrSides<TAutoOrNoneOrRelative>? = null,
    @TSerialName("stroke")
    override val stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    @TSerialName("breakable")
    override val breakable: TAutoOrBool? = null,
) : TSetGridCell

@TypstOverloads
public fun TSetGridCell(
    x: TAutoOrInt? = null,
    y: TAutoOrInt? = null,
    colspan: TInt? = null,
    rowspan: TInt? = null,
    fill: TAutoOrColorOrGradientOrNoneOrTiling? = null,
    align: TAlignmentOrAuto? = null,
    inset: TAutoOrRelativeOrSides<TAutoOrNoneOrRelative>? = null,
    stroke: TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue>? = null,
    breakable: TAutoOrBool? = null,
): TSetGridCell = TSetGridCellImpl(`x`, `y`, `colspan`, `rowspan`, `fill`, `align`, `inset`,
        `stroke`, `breakable`)
