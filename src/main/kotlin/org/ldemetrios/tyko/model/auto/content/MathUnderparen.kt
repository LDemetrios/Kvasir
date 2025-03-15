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
    "math.underparen",
    ["math.underparen", "content"],
    TMathUnderparenImpl::class,
)
public interface TMathUnderparen : TContent {
    public val body: TContent

    public val `annotation`: TContentOrNone?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.underparen")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val annotationType: InternalType = UnionType(ConcreteType("content"),
                ConcreteType("none"))
    }
}

internal data class TMathUnderparenImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathUnderparen {
    override fun format(): String = Representations.elementRepr("math.underparen",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, null, `annotation`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TMathUnderparen(
    @TContentBody body: TContent,
    `annotation`: TContentOrNone? = null,
    label: TLabel? = null,
): TMathUnderparen = TMathUnderparenImpl(`body`, `annotation`, `label`)

@TSetRuleType(
    "math.underparen",
    TSetMathUnderparenImpl::class,
)
public interface TSetMathUnderparen : TSetRule {
    override val elem: String
        get() = "math.underparen"

    public val `annotation`: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.underparen",ArgumentEntry(false,
            null, `annotation`),)
}

internal class TSetMathUnderparenImpl(
    @TSerialName("annotation")
    override val `annotation`: TContentOrNone? = null,
) : TSetMathUnderparen

@TypstOverloads
public fun TSetMathUnderparen(`annotation`: TContentOrNone? = null): TSetMathUnderparen =
        TSetMathUnderparenImpl(`annotation`)
