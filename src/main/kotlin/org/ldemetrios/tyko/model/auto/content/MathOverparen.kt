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
    "math.overparen",
    ["math.overparen", "content"],
    TMathOverparenImpl::class,
)
public interface TMathOverparen : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.overparen")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathOverparenImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathOverparen {
    override fun format(): String = Representations.elementRepr("math.overparen",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TMathOverparen(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathOverparen = TMathOverparenImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.overparen",
    TSetMathOverparenImpl::class,
)
public interface TSetMathOverparen : TSetRule {
    override val elem: String
        get() = "math.overparen"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.overparen",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathOverparenImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathOverparen

@TypstOverloads
public fun TSetMathOverparen(`annotation`: TContentOrNone? = null): TSetMathOverparen =
        TSetMathOverparenImpl(`annotation`)
