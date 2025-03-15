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
    "math.op",
    ["math.op", "content"],
    TMathOpImpl::class,
)
public interface TMathOp : TContent {
    public val text: TContent

    public val limits: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.op")

        internal val textType: InternalType = ConcreteType("content")

        internal val limitsType: InternalType = ConcreteType("bool")
    }
}

internal data class TMathOpImpl(
    @TSerialName("text")
    override val text: TContent,
    @TSerialName("limits")
    override val limits: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathOp {
    override fun format(): String = Representations.elementRepr("math.op",ArgumentEntry(false, null,
            `text`),ArgumentEntry(false, "limits", `limits`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathOp(
    text: TContent,
    limits: TBool? = null,
    label: TLabel? = null,
): TMathOp = TMathOpImpl(`text`, `limits`, `label`)

@TSetRuleType(
    "math.op",
    TSetMathOpImpl::class,
)
public interface TSetMathOp : TSetRule {
    override val elem: String
        get() = "math.op"

    public val limits: TBool?

    override fun format(): String = Representations.setRepr("math.op",ArgumentEntry(false, "limits",
            `limits`),)
}

internal class TSetMathOpImpl(
    @TSerialName("limits")
    override val limits: TBool? = null,
) : TSetMathOp

@TypstOverloads
public fun TSetMathOp(limits: TBool? = null): TSetMathOp = TSetMathOpImpl(`limits`)
