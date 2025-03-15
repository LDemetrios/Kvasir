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
    "math.equation",
    ["math.equation", "content"],
    TMathEquationImpl::class,
)
public interface TMathEquation : TContent {
    public val body: TContent

    public val block: TBool?

    public val numbering: TFunctionOrNoneOrStr?

    public val numberAlign: TAlignment?

    public val supplement: TAutoOrContentOrFunctionOrNone?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.equation")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val blockType: InternalType = ConcreteType("bool")

        internal val numberingType: InternalType = UnionType(ConcreteType("function"),
                ConcreteType("none"), ConcreteType("str"))

        internal val numberAlignType: InternalType = ConcreteType("alignment")

        internal val supplementType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("content"), ConcreteType("function"), ConcreteType("none"))
    }
}

internal data class TMathEquationImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("block")
    override val block: TBool? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignment? = null,
    @TSerialName("supplement")
    override val supplement: TAutoOrContentOrFunctionOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathEquation {
    override fun format(): String = Representations.elementRepr("math.equation",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "block", `block`),ArgumentEntry(false, "numbering",
            `numbering`),ArgumentEntry(false, "number-align", `numberAlign`),ArgumentEntry(false,
            "supplement", `supplement`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathEquation(
    @TContentBody body: TContent,
    block: TBool? = null,
    numbering: TFunctionOrNoneOrStr? = null,
    numberAlign: TAlignment? = null,
    supplement: TAutoOrContentOrFunctionOrNone? = null,
    label: TLabel? = null,
): TMathEquation = TMathEquationImpl(`body`, `block`, `numbering`, `numberAlign`, `supplement`,
        `label`)

@TSetRuleType(
    "math.equation",
    TSetMathEquationImpl::class,
)
public interface TSetMathEquation : TSetRule {
    override val elem: String
        get() = "math.equation"

    public val block: TBool?

    public val numbering: TFunctionOrNoneOrStr?

    public val numberAlign: TAlignment?

    public val supplement: TAutoOrContentOrFunctionOrNone?

    override fun format(): String = Representations.setRepr("math.equation",ArgumentEntry(false,
            "block", `block`),ArgumentEntry(false, "numbering", `numbering`),ArgumentEntry(false,
            "number-align", `numberAlign`),ArgumentEntry(false, "supplement", `supplement`),)
}

internal class TSetMathEquationImpl(
    @TSerialName("block")
    override val block: TBool? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignment? = null,
    @TSerialName("supplement")
    override val supplement: TAutoOrContentOrFunctionOrNone? = null,
) : TSetMathEquation

@TypstOverloads
public fun TSetMathEquation(
    block: TBool? = null,
    numbering: TFunctionOrNoneOrStr? = null,
    numberAlign: TAlignment? = null,
    supplement: TAutoOrContentOrFunctionOrNone? = null,
): TSetMathEquation = TSetMathEquationImpl(`block`, `numbering`, `numberAlign`, `supplement`)
