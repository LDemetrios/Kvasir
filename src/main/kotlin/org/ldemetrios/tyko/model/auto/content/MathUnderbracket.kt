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
    "math.underbracket",
    ["math.underbracket", "content"],
    TMathUnderbracketImpl::class,
)
public interface TMathUnderbracket : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = TMathUnderbracket

    public companion object : TElementImpl("math.underbracket") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathUnderbracketImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathUnderbracket {
    override fun format(): String =
            Representations.elementRepr("math.underbracket",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathUnderbracket(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathUnderbracket = TMathUnderbracketImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.underbracket",
    TSetMathUnderbracketImpl::class,
)
public interface TSetMathUnderbracket : TSetRule {
    override val elem: String
        get() = "math.underbracket"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.underbracket",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathUnderbracketImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathUnderbracket

@TypstOverloads
public fun TSetMathUnderbracket(`annotation`: TContentOrNone? = null): TSetMathUnderbracket =
        TSetMathUnderbracketImpl(`annotation`)
