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
    "table",
    ["table", "content"],
    TTableImpl::class,
)
public interface TTable : TContent {
    public val children: TArray<TContent>

    public val columns: TArrayOrAutoOrFractionOrIntOrRelative<TAutoOrFractionOrIntOrRelative>?

    public val rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>?

    public val align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>?

    public val stroke:
            TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?

    public val inset:
            TArrayOrFractionOrFunctionOrRelativeOrSides<TFractionOrRelative, TFractionOrNoneOrRelative>?

    override fun func(): TElement = TTable

    public companion object : TElementImpl("table") {
        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val columnsType: InternalType = UnionType(ConcreteType("array",
                listOf(UnionType(ConcreteType("auto"), ConcreteType("fraction"), ConcreteType("int"),
                ConcreteType("relative")))), ConcreteType("auto"), ConcreteType("fraction"),
                ConcreteType("int"), ConcreteType("relative"))

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
                listOf(UnionType(ConcreteType("fraction"), ConcreteType("relative")))),
                ConcreteType("fraction"), ConcreteType("function"), ConcreteType("relative"),
                ConcreteType("sides", listOf(UnionType(ConcreteType("fraction"), ConcreteType("none"),
                ConcreteType("relative")))))
    }
}

internal data class TTableImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("columns")
    override val columns: TArrayOrAutoOrFractionOrIntOrRelative<TAutoOrFractionOrIntOrRelative>? =
            null,
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
    override val inset:
            TArrayOrFractionOrFunctionOrRelativeOrSides<TFractionOrRelative, TFractionOrNoneOrRelative>? =
            null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TTable {
    override fun format(): String = Representations.elementRepr("table",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "columns", `columns`),ArgumentEntry(false, "rows",
            `rows`),ArgumentEntry(false, "gutter", `gutter`),ArgumentEntry(false, "column-gutter",
            `columnGutter`),ArgumentEntry(false, "row-gutter", `rowGutter`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "align", `align`),ArgumentEntry(false, "stroke",
            `stroke`),ArgumentEntry(false, "inset", `inset`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TTable(
    @TVararg children: TArray<TContent>,
    columns: TArrayOrAutoOrFractionOrIntOrRelative<TAutoOrFractionOrIntOrRelative>? = null,
    rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>? = null,
    align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>? = null,
    stroke: TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?
            = null,
    inset: TArrayOrFractionOrFunctionOrRelativeOrSides<TFractionOrRelative, TFractionOrNoneOrRelative>?
            = null,
    label: TLabel? = null,
): TTable = TTableImpl(`children`, `columns`, `rows`, `gutter`, `columnGutter`, `rowGutter`, `fill`,
        `align`, `stroke`, `inset`, `label`)

@TSetRuleType(
    "table",
    TSetTableImpl::class,
)
public interface TSetTable : TSetRule {
    override val elem: String
        get() = "table"

    public val columns: TArrayOrAutoOrFractionOrIntOrRelative<TAutoOrFractionOrIntOrRelative>?

    public val rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>?

    public val fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>?

    public val align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>?

    public val stroke:
            TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?

    public val inset:
            TArrayOrFractionOrFunctionOrRelativeOrSides<TFractionOrRelative, TFractionOrNoneOrRelative>?

    override fun format(): String = Representations.setRepr("table",ArgumentEntry(false, "columns",
            `columns`),ArgumentEntry(false, "rows", `rows`),ArgumentEntry(false, "gutter",
            `gutter`),ArgumentEntry(false, "column-gutter", `columnGutter`),ArgumentEntry(false,
            "row-gutter", `rowGutter`),ArgumentEntry(false, "fill", `fill`),ArgumentEntry(false, "align",
            `align`),ArgumentEntry(false, "stroke", `stroke`),ArgumentEntry(false, "inset", `inset`),)
}

internal class TSetTableImpl(
    @TSerialName("columns")
    override val columns: TArrayOrAutoOrFractionOrIntOrRelative<TAutoOrFractionOrIntOrRelative>? =
            null,
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
    override val inset:
            TArrayOrFractionOrFunctionOrRelativeOrSides<TFractionOrRelative, TFractionOrNoneOrRelative>? =
            null,
) : TSetTable

@TypstOverloads
public fun TSetTable(
    columns: TArrayOrAutoOrFractionOrIntOrRelative<TAutoOrFractionOrIntOrRelative>? = null,
    rows: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    gutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    columnGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    rowGutter: TArrayOrAutoOrFractionOrIntOrRelative<TValue>? = null,
    fill: TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TValue>? = null,
    align: TAlignmentOrArrayOrAutoOrFunction<TAlignment>? = null,
    stroke: TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TValue, TValue>?
            = null,
    inset: TArrayOrFractionOrFunctionOrRelativeOrSides<TFractionOrRelative, TFractionOrNoneOrRelative>?
            = null,
): TSetTable = TSetTableImpl(`columns`, `rows`, `gutter`, `columnGutter`, `rowGutter`, `fill`,
        `align`, `stroke`, `inset`)
