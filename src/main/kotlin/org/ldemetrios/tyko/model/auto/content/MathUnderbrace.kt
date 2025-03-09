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
    "math.underbrace",
    ["math.underbrace", "content"],
    TMathUnderbraceImpl::class,
)
public interface TMathUnderbrace : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = TMathUnderbrace

    public companion object : TElementImpl("math.underbrace") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathUnderbraceImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathUnderbrace {
    override fun format(): String = Representations.elementRepr("math.underbrace",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TMathUnderbrace(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathUnderbrace = TMathUnderbraceImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.underbrace",
    TSetMathUnderbraceImpl::class,
)
public interface TSetMathUnderbrace : TSetRule {
    override val elem: String
        get() = "math.underbrace"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.underbrace",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathUnderbraceImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathUnderbrace

@TypstOverloads
public fun TSetMathUnderbrace(`annotation`: TContentOrNone? = null): TSetMathUnderbrace =
        TSetMathUnderbraceImpl(`annotation`)
