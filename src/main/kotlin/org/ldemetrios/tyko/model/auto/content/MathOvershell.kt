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
    "math.overshell",
    ["math.overshell", "content"],
    TMathOvershellImpl::class,
)
public interface TMathOvershell : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = TMathOvershell

    public companion object : TElementImpl("math.overshell") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathOvershellImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathOvershell {
    override fun format(): String = Representations.elementRepr("math.overshell",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TMathOvershell(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathOvershell = TMathOvershellImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.overshell",
    TSetMathOvershellImpl::class,
)
public interface TSetMathOvershell : TSetRule {
    override val elem: String
        get() = "math.overshell"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.overshell",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathOvershellImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathOvershell

@TypstOverloads
public fun TSetMathOvershell(`annotation`: TContentOrNone? = null): TSetMathOvershell =
        TSetMathOvershellImpl(`annotation`)
