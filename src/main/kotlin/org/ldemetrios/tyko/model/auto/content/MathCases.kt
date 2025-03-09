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
    "math.cases",
    ["math.cases", "content"],
    TMathCasesImpl::class,
)
public interface TMathCases : TContent {
    public val children: TArray<TContent>

    public val delim: TArrayOrNoneOrStr<TNoneOrStr>?

    public val reverse: TBool?

    public val gap: TRelative?

    override fun func(): TElement = TMathCases

    public companion object : TElementImpl("math.cases") {
        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))

        internal val delimType: InternalType = UnionType(ConcreteType("array",
                listOf(UnionType(ConcreteType("none"), ConcreteType("str")))), ConcreteType("none"),
                ConcreteType("str"))

        internal val reverseType: InternalType = ConcreteType("bool")

        internal val gapType: InternalType = ConcreteType("relative")
    }
}

internal data class TMathCasesImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("delim")
    override val delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    @TSerialName("reverse")
    override val reverse: TBool? = null,
    @TSerialName("gap")
    override val gap: TRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathCases {
    override fun format(): String = Representations.elementRepr("math.cases",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "delim", `delim`),ArgumentEntry(false, "reverse",
            `reverse`),ArgumentEntry(false, "gap", `gap`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathCases(
    @TVararg children: TArray<TContent>,
    delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    reverse: TBool? = null,
    gap: TRelative? = null,
    label: TLabel? = null,
): TMathCases = TMathCasesImpl(`children`, `delim`, `reverse`, `gap`, `label`)

@TSetRuleType(
    "math.cases",
    TSetMathCasesImpl::class,
)
public interface TSetMathCases : TSetRule {
    override val elem: String
        get() = "math.cases"

    public val delim: TArrayOrNoneOrStr<TNoneOrStr>?

    public val reverse: TBool?

    public val gap: TRelative?

    override fun format(): String = Representations.setRepr("math.cases",ArgumentEntry(false, "delim",
            `delim`),ArgumentEntry(false, "reverse", `reverse`),ArgumentEntry(false, "gap", `gap`),)
}

internal class TSetMathCasesImpl(
    @TSerialName("delim")
    override val delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    @TSerialName("reverse")
    override val reverse: TBool? = null,
    @TSerialName("gap")
    override val gap: TRelative? = null,
) : TSetMathCases

@TypstOverloads
public fun TSetMathCases(
    delim: TArrayOrNoneOrStr<TNoneOrStr>? = null,
    reverse: TBool? = null,
    gap: TRelative? = null,
): TSetMathCases = TSetMathCasesImpl(`delim`, `reverse`, `gap`)
