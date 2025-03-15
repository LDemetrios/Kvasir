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
    "math.vec",
    ["math.vec", "content"],
    TMathVecImpl::class,
)
public interface TMathVec : TContent {
    public val children: TArray<TContent>

    public val delim: TArrayOrNoneOrStr<TNoneOrStr>?

    public val align: TAlignment?

    public val gap: TRelative?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.vec")

        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val delimType: InternalType = UnionType(ConcreteType("array",
                listOf(UnionType(ConcreteType("none"), ConcreteType("str")))), ConcreteType("none"),
                ConcreteType("str"))

        internal val alignType: InternalType = ConcreteType("alignment")

        internal val gapType: InternalType = ConcreteType("relative")
    }
}

internal data class TMathVecImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("delim")
    override val delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    @TSerialName("align")
    override val align: TAlignment? = null,
    @TSerialName("gap")
    override val gap: TRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathVec {
    override fun format(): String = Representations.elementRepr("math.vec",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "delim", `delim`),ArgumentEntry(false, "align",
            `align`),ArgumentEntry(false, "gap", `gap`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathVec(
    @TVararg children: TArray<TContent>,
    delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    align: TAlignment? = null,
    gap: TRelative? = null,
    label: TLabel? = null,
): TMathVec = TMathVecImpl(`children`, `delim`, `align`, `gap`, `label`)

@TSetRuleType(
    "math.vec",
    TSetMathVecImpl::class,
)
public interface TSetMathVec : TSetRule {
    override val elem: String
        get() = "math.vec"

    public val delim: TArrayOrNoneOrStr<TNoneOrStr>?

    public val align: TAlignment?

    public val gap: TRelative?

    override fun format(): String = Representations.setRepr("math.vec",ArgumentEntry(false, "delim",
            `delim`),ArgumentEntry(false, "align", `align`),ArgumentEntry(false, "gap", `gap`),)
}

internal class TSetMathVecImpl(
    @TSerialName("delim")
    override val delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    @TSerialName("align")
    override val align: TAlignment? = null,
    @TSerialName("gap")
    override val gap: TRelative? = null,
) : TSetMathVec

@TypstOverloads
public fun TSetMathVec(
    delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    align: TAlignment? = null,
    gap: TRelative? = null,
): TSetMathVec = TSetMathVecImpl(`delim`, `align`, `gap`)
