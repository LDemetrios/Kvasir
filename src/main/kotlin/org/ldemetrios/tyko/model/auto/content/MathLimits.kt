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
    "math.limits",
    ["math.limits", "content"],
    TMathLimitsImpl::class,
)
public interface TMathLimits : TContent {
    public val body: TContent

    public val `inline`: TBool?

    override fun func(): TElement = TMathLimits

    public companion object : TElementImpl("math.limits") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val inlineType: InternalType = ConcreteType("bool")
    }
}

internal data class TMathLimitsImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("inline")
    override val `inline`: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathLimits {
    override fun format(): String = Representations.elementRepr("math.limits",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "inline", `inline`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TMathLimits(
    @TContentBody body: TContent,
    `inline`: TBool? = null,
    label: TLabel? = null,
): TMathLimits = TMathLimitsImpl(`body`, `inline`, `label`)

@TSetRuleType(
    "math.limits",
    TSetMathLimitsImpl::class,
)
public interface TSetMathLimits : TSetRule {
    override val elem: String
        get() = "math.limits"

    public val `inline`: TBool?

    override fun format(): String = Representations.setRepr("math.limits",ArgumentEntry(false,
            "inline", `inline`),)
}

internal class TSetMathLimitsImpl(
    @TSerialName("inline")
    override val `inline`: TBool? = null,
) : TSetMathLimits

@TypstOverloads
public fun TSetMathLimits(`inline`: TBool? = null): TSetMathLimits = TSetMathLimitsImpl(`inline`)
