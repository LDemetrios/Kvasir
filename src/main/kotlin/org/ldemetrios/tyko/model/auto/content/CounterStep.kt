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
    "counter.step",
    ["counter.step", "content"],
    TCounterStepImpl::class,
)
public interface TCounterStep : TContent {
    public val key: TFunctionOrLabelOrLocationOrSelectorOrStr

    public val level: TInt

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("counter.step")

        internal val keyType: InternalType = UnionType(ConcreteType("function"), ConcreteType("label"),
                ConcreteType("location"), ConcreteType("selector"), ConcreteType("str"))

        internal val levelType: InternalType = ConcreteType("int")
    }
}

internal data class TCounterStepImpl(
    @TSerialName("key")
    override val key: TFunctionOrLabelOrLocationOrSelectorOrStr,
    @TSerialName("level")
    override val level: TInt,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCounterStep {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TCounterStep(
    key: TFunctionOrLabelOrLocationOrSelectorOrStr,
    level: TInt,
    label: TLabel? = null,
): TCounterStep = TCounterStepImpl(`key`, `level`, `label`)
