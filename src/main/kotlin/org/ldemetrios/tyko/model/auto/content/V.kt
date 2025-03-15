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
    "v",
    ["v", "content"],
    TVImpl::class,
)
public interface TV : TContent {
    public val amount: TFractionOrRelative

    public val weak: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("v")

        internal val amountType: InternalType = UnionType(ConcreteType("fraction"),
                ConcreteType("relative"))

        internal val weakType: InternalType = ConcreteType("bool")
    }
}

internal data class TVImpl(
    @TSerialName("amount")
    override val amount: TFractionOrRelative,
    @TSerialName("weak")
    override val weak: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TV {
    override fun format(): String = Representations.elementRepr("v",ArgumentEntry(false, null,
            `amount`),ArgumentEntry(false, "weak", `weak`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TV(
    amount: TFractionOrRelative,
    weak: TBool? = null,
    label: TLabel? = null,
): TV = TVImpl(`amount`, `weak`, `label`)

@TSetRuleType(
    "v",
    TSetVImpl::class,
)
public interface TSetV : TSetRule {
    override val elem: String
        get() = "v"

    public val weak: TBool?

    override fun format(): String = Representations.setRepr("v",ArgumentEntry(false, "weak", `weak`),)
}

internal class TSetVImpl(
    @TSerialName("weak")
    override val weak: TBool? = null,
) : TSetV

@TypstOverloads
public fun TSetV(weak: TBool? = null): TSetV = TSetVImpl(`weak`)
