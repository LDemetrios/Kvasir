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
    "sequence",
    ["sequence", "content"],
    TSequenceImpl::class,
)
public interface TSequence : TContent {
    public val children: TArray<TContent>

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("sequence")

        internal val childrenType: InternalType = ConcreteType("array", listOf(ConcreteType("content")))
    }
}

internal data class TSequenceImpl(
    @TSerialName("children")
    override val children: TArray<TContent>,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TSequence {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TSequence(children: TArray<TContent>, label: TLabel? = null): TSequence =
        TSequenceImpl(`children`, `label`)
