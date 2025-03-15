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
    "math.overbracket",
    ["math.overbracket", "content"],
    TMathOverbracketImpl::class,
)
public interface TMathOverbracket : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.overbracket")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathOverbracketImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathOverbracket {
    override fun format(): String =
            Representations.elementRepr("math.overbracket",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathOverbracket(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathOverbracket = TMathOverbracketImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.overbracket",
    TSetMathOverbracketImpl::class,
)
public interface TSetMathOverbracket : TSetRule {
    override val elem: String
        get() = "math.overbracket"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.overbracket",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathOverbracketImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathOverbracket

@TypstOverloads
public fun TSetMathOverbracket(`annotation`: TContentOrNone? = null): TSetMathOverbracket =
        TSetMathOverbracketImpl(`annotation`)
