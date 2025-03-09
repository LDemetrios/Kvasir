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
    "math.primes",
    ["math.primes", "content"],
    TMathPrimesImpl::class,
)
public interface TMathPrimes : TContent {
    public val count: TInt

    override fun func(): TElement = TMathPrimes

    public companion object : TElementImpl("math.primes") {
        internal val countType: InternalType = ConcreteType("int")
    }
}

internal data class TMathPrimesImpl(
    @TSerialName("count")
    override val count: TInt,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathPrimes {
    override fun format(): String = Representations.elementRepr("math.primes",ArgumentEntry(false,
            null, `count`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathPrimes(count: TInt, label: TLabel? = null): TMathPrimes = TMathPrimesImpl(`count`,
        `label`)
