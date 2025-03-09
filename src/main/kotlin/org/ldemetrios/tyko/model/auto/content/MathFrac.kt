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
    "math.frac",
    ["math.frac", "content"],
    TMathFracImpl::class,
)
public interface TMathFrac : TContent {
    public val num: TContent

    public val denom: TContent

    override fun func(): TElement = TMathFrac

    public companion object : TElementImpl("math.frac") {
        internal val numType: InternalType = ConcreteType("content")

        internal val denomType: InternalType = ConcreteType("content")
    }
}

internal data class TMathFracImpl(
    @TSerialName("num")
    override val num: TContent,
    @TSerialName("denom")
    override val denom: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathFrac {
    override fun format(): String = Representations.elementRepr("math.frac",ArgumentEntry(false, null,
            `num`),ArgumentEntry(false, null, `denom`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathFrac(
    num: TContent,
    denom: TContent,
    label: TLabel? = null,
): TMathFrac = TMathFracImpl(`num`, `denom`, `label`)
