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
    "math.mat",
    ["math.mat", "content"],
    TMathMatImpl::class,
)
public interface TMathMat : TContent {
    public val rows: TArray<TArray<TContent>>

    public val delim: TArrayOrNoneOrStrOrSymbol<TNoneOrStrOrSymbol>?

    public val align: TAlignment?

    public val augment: TDictionaryOrIntOrNone<TValue>?

    public val gap: TRelative?

    public val rowGap: TRelative?

    public val columnGap: TRelative?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.mat")

        internal val rowsType: InternalType = ConcreteType("array", listOf(ConcreteType("array",
                listOf(ConcreteType("content")))))

        internal val delimType: InternalType = UnionType(ConcreteType("array",
                listOf(UnionType(ConcreteType("none"), ConcreteType("str"), ConcreteType("symbol")))),
                ConcreteType("none"), ConcreteType("str"), ConcreteType("symbol"))

        internal val alignType: InternalType = ConcreteType("alignment")

        internal val augmentType: InternalType = UnionType(ConcreteType("dictionary", listOf(AnyType)),
                ConcreteType("int"), ConcreteType("none"))

        internal val gapType: InternalType = ConcreteType("relative")

        internal val rowGapType: InternalType = ConcreteType("relative")

        internal val columnGapType: InternalType = ConcreteType("relative")
    }
}

internal data class TMathMatImpl(
    @TSerialName("rows")
    override val rows: TArray<TArray<TContent>>,
    @TSerialName("delim")
    override val delim: TArrayOrNoneOrStrOrSymbol<TNoneOrStrOrSymbol>? = null,
    @TSerialName("align")
    override val align: TAlignment? = null,
    @TSerialName("augment")
    override val augment: TDictionaryOrIntOrNone<TValue>? = null,
    @TSerialName("gap")
    override val gap: TRelative? = null,
    @TSerialName("row-gap")
    override val rowGap: TRelative? = null,
    @TSerialName("column-gap")
    override val columnGap: TRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathMat {
    override fun format(): String = Representations.elementRepr("math.mat",ArgumentEntry(true, null,
            `rows`),ArgumentEntry(false, "delim", `delim`),ArgumentEntry(false, "align",
            `align`),ArgumentEntry(false, "augment", `augment`),ArgumentEntry(false, "gap",
            `gap`),ArgumentEntry(false, "row-gap", `rowGap`),ArgumentEntry(false, "column-gap",
            `columnGap`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathMat(
    @TVararg rows: TArray<TArray<TContent>>,
    delim: TArrayOrNoneOrStrOrSymbol<TNoneOrStrOrSymbol>? = null,
    align: TAlignment? = null,
    augment: TDictionaryOrIntOrNone<TValue>? = null,
    gap: TRelative? = null,
    rowGap: TRelative? = null,
    columnGap: TRelative? = null,
    label: TLabel? = null,
): TMathMat = TMathMatImpl(`rows`, `delim`, `align`, `augment`, `gap`, `rowGap`, `columnGap`,
        `label`)

@TSetRuleType(
    "math.mat",
    TSetMathMatImpl::class,
)
public interface TSetMathMat : TSetRule {
    override val elem: String
        get() = "math.mat"

    public val delim: TArrayOrNoneOrStrOrSymbol<TNoneOrStrOrSymbol>?

    public val align: TAlignment?

    public val augment: TDictionaryOrIntOrNone<TValue>?

    public val gap: TRelative?

    public val rowGap: TRelative?

    public val columnGap: TRelative?

    override fun format(): String = Representations.setRepr("math.mat",ArgumentEntry(false, "delim",
            `delim`),ArgumentEntry(false, "align", `align`),ArgumentEntry(false, "augment",
            `augment`),ArgumentEntry(false, "gap", `gap`),ArgumentEntry(false, "row-gap",
            `rowGap`),ArgumentEntry(false, "column-gap", `columnGap`),)
}

internal class TSetMathMatImpl(
    @TSerialName("delim")
    override val delim: TArrayOrNoneOrStrOrSymbol<TNoneOrStrOrSymbol>? = null,
    @TSerialName("align")
    override val align: TAlignment? = null,
    @TSerialName("augment")
    override val augment: TDictionaryOrIntOrNone<TValue>? = null,
    @TSerialName("gap")
    override val gap: TRelative? = null,
    @TSerialName("row-gap")
    override val rowGap: TRelative? = null,
    @TSerialName("column-gap")
    override val columnGap: TRelative? = null,
) : TSetMathMat

@TypstOverloads
public fun TSetMathMat(
    delim: TArrayOrNoneOrStrOrSymbol<TNoneOrStrOrSymbol>? = null,
    align: TAlignment? = null,
    augment: TDictionaryOrIntOrNone<TValue>? = null,
    gap: TRelative? = null,
    rowGap: TRelative? = null,
    columnGap: TRelative? = null,
): TSetMathMat = TSetMathMatImpl(`delim`, `align`, `augment`, `gap`, `rowGap`, `columnGap`)
