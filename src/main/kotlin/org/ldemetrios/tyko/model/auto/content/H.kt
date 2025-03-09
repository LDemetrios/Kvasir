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
    "h",
    ["h", "content"],
    THImpl::class,
)
public interface TH : TContent {
    public val amount: TFractionOrRelative

    public val weak: TBool?

    override fun func(): TElement = TH

    public companion object : TElementImpl("h") {
        internal val amountType: InternalType = UnionType(ConcreteType("fraction"),
                ConcreteType("relative"))

        internal val weakType: InternalType = ConcreteType("bool")
    }
}

internal data class THImpl(
    @TSerialName("amount")
    override val amount: TFractionOrRelative,
    @TSerialName("weak")
    override val weak: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TH {
    override fun format(): String = Representations.elementRepr("h",ArgumentEntry(false, null,
            `amount`),ArgumentEntry(false, "weak", `weak`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TH(
    amount: TFractionOrRelative,
    weak: TBool? = null,
    label: TLabel? = null,
): TH = THImpl(`amount`, `weak`, `label`)

@TSetRuleType(
    "h",
    TSetHImpl::class,
)
public interface TSetH : TSetRule {
    override val elem: String
        get() = "h"

    public val weak: TBool?

    override fun format(): String = Representations.setRepr("h",ArgumentEntry(false, "weak", `weak`),)
}

internal class TSetHImpl(
    @TSerialName("weak")
    override val weak: TBool? = null,
) : TSetH

@TypstOverloads
public fun TSetH(weak: TBool? = null): TSetH = TSetHImpl(`weak`)
