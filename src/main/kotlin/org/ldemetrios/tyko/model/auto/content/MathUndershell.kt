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
    "math.undershell",
    ["math.undershell", "content"],
    TMathUndershellImpl::class,
)
public interface TMathUndershell : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.undershell")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathUndershellImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathUndershell {
    override fun format(): String = Representations.elementRepr("math.undershell",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TMathUndershell(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathUndershell = TMathUndershellImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.undershell",
    TSetMathUndershellImpl::class,
)
public interface TSetMathUndershell : TSetRule {
    override val elem: String
        get() = "math.undershell"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.undershell",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathUndershellImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathUndershell

@TypstOverloads
public fun TSetMathUndershell(`annotation`: TContentOrNone? = null): TSetMathUndershell =
        TSetMathUndershellImpl(`annotation`)
