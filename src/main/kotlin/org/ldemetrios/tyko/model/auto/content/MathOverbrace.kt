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
    "math.overbrace",
    ["math.overbrace", "content"],
    TMathOverbraceImpl::class,
)
public interface TMathOverbrace : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = TMathOverbrace

    public companion object : TElementImpl("math.overbrace") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathOverbraceImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathOverbrace {
    override fun format(): String = Representations.elementRepr("math.overbrace",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TMathOverbrace(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathOverbrace = TMathOverbraceImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.overbrace",
    TSetMathOverbraceImpl::class,
)
public interface TSetMathOverbrace : TSetRule {
    override val elem: String
        get() = "math.overbrace"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.overbrace",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathOverbraceImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathOverbrace

@TypstOverloads
public fun TSetMathOverbrace(`annotation`: TContentOrNone? = null): TSetMathOverbrace =
        TSetMathOverbraceImpl(`annotation`)
