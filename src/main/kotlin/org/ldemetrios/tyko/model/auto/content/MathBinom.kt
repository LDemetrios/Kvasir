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
    "math.binom",
    ["math.binom", "content"],
    TMathBinomImpl::class,
)
public interface TMathBinom : TContent {
    public val upper: TContent

    public val lower: TArray<TContent>

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.binom")

        internal val upperType: InternalType = ConcreteType("content")

        internal val lowerType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))
    }
}

internal data class TMathBinomImpl(
    @TSerialName("upper")
    override val upper: TContent,
    @TSerialName("lower")
    override val lower: TArray<TContent>,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathBinom {
    override fun format(): String = Representations.elementRepr("math.binom",ArgumentEntry(false,
            null, `upper`),ArgumentEntry(true, null, `lower`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathBinom(
    upper: TContent,
    @TVararg lower: TArray<TContent>,
    label: TLabel? = null,
): TMathBinom = TMathBinomImpl(`upper`, `lower`, `label`)
