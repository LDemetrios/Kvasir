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
    "grid",
    ["grid", "content"],
    TGridImpl::class,
)
public interface TGrid : TContent {
    public val children: TArray<TContent>

    public val columns: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>?

    public val align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>?

    public val stroke:
            TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?

    public val inset: TArrayOrFunctionOrRelativeOrSides<TRelative, TNoneOrRelative>?

    override fun func(): TElement = TGrid

    public companion object : TElementImpl("grid") {
        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val columnsType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"), ConcreteType("fraction"), ConcreteType("int"),
                ConcreteType("relative"))

        internal val rowsType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"), ConcreteType("fraction"), ConcreteType("int"),
                ConcreteType("relative"))

        internal val gutterType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"), ConcreteType("fraction"), ConcreteType("int"),
                ConcreteType("relative"))

        internal val columnGutterType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"), ConcreteType("fraction"), ConcreteType("int"),
                ConcreteType("relative"))

        internal val rowGutterType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"), ConcreteType("fraction"), ConcreteType("int"),
                ConcreteType("relative"))

        internal val fillType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("color"), ConcreteType("function"), ConcreteType("gradient"),
                ConcreteType("none"), ConcreteType("tiling"))

        internal val alignType: InternalType = UnionType(ConcreteType("alignment"),
                ConcreteType("array", listOf(ConcreteType("alignment"))), ConcreteType("auto"),
                ConcreteType("function"))

        internal val strokeType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("color"), ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("function"), ConcreteType("gradient"), ConcreteType("length"),
                ConcreteType("none"), ConcreteType("stroke"), ConcreteType("tiling"))

        internal val insetType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("relative"))), ConcreteType("function"), ConcreteType("relative"),
                ConcreteType("sides", listOf(UnionType(ConcreteType("none"), ConcreteType("relative")))))
    }
}

internal data class TGridImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("columns")
    override val columns: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("rows")
    override val rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("gutter")
    override val gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("column-gutter")
    override val columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("row-gutter")
    override val rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("fill")
    override val fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>? = null,
    @TSerialName("align")
    override val align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>? = null,
    @TSerialName("stroke")
    override val stroke:
            TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>? =
            null,
    @TSerialName("inset")
    override val inset: TArrayOrFunctionOrRelativeOrSides<TRelative, TNoneOrRelative>? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TGrid {
    override fun format(): String = Representations.elementRepr("grid",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "columns", `columns`),ArgumentEntry(false, "rows",
            `rows`),ArgumentEntry(false, "gutter", `gutter`),ArgumentEntry(false, "column-gutter",
            `columnGutter`),ArgumentEntry(false, "row-gutter", `rowGutter`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "align", `align`),ArgumentEntry(false, "stroke",
            `stroke`),ArgumentEntry(false, "inset", `inset`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TGrid(
    @TVararg children: TArray<TContent>,
    columns: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>? = null,
    align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>? = null,
    stroke: TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?
            = null,
    inset: TArrayOrFunctionOrRelativeOrSides<TRelative, TNoneOrRelative>? = null,
    label: TLabel? = null,
): TGrid = TGridImpl(`children`, `columns`, `rows`, `gutter`, `columnGutter`, `rowGutter`, `fill`,
        `align`, `stroke`, `inset`, `label`)

@TSetRuleType(
    "grid",
    TSetGridImpl::class,
)
public interface TSetGrid : TSetRule {
    override val elem: String
        get() = "grid"

    public val columns: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>?

    public val align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>?

    public val stroke:
            TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?

    public val inset: TArrayOrFunctionOrRelativeOrSides<TRelative, TNoneOrRelative>?

    override fun format(): String = Representations.setRepr("grid",ArgumentEntry(false, "columns",
            `columns`),ArgumentEntry(false, "rows", `rows`),ArgumentEntry(false, "gutter",
            `gutter`),ArgumentEntry(false, "column-gutter", `columnGutter`),ArgumentEntry(false,
            "row-gutter", `rowGutter`),ArgumentEntry(false, "fill", `fill`),ArgumentEntry(false, "align",
            `align`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "inset", `inset`),)
}

internal class TSetGridImpl(
    @TSerialName("columns")
    override val columns: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("rows")
    override val rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("gutter")
    override val gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("column-gutter")
    override val columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("row-gutter")
    override val rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    @TSerialName("fill")
    override val fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>? = null,
    @TSerialName("align")
    override val align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>? = null,
    @TSerialName("stroke")
    override val stroke:
            TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>? =
            null,
    @TSerialName("inset")
    override val inset: TArrayOrFunctionOrRelativeOrSides<TRelative, TNoneOrRelative>? = null,
) : TSetGrid

@TypstOverloads
public fun TSetGrid(
    columns: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>? = null,
    align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>? = null,
    stroke: TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?
            = null,
    inset: TArrayOrFunctionOrRelativeOrSides<TRelative, TNoneOrRelative>? = null,
): TSetGrid = TSetGridImpl(`columns`, `rows`, `gutter`, `columnGutter`, `rowGutter`, `fill`,
        `align`, `stroke`, `inset`)
