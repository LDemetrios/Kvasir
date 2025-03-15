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
    "place.flush",
    ["place.flush", "content"],
    TFlushImpl::class,
)
public interface TFlush : TContent {
    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("place.flush")
    }
}

internal data class TFlushImpl(
    @TSerialName("label")
    override val label: TLabel? = null,
) : TFlush {
    override fun format(): String = Representations.elementRepr("place.flush",ArgumentEntry(false,
            "label", `label`),)
}

@TypstOverloads
public fun TFlush(label: TLabel? = null): TFlush = TFlushImpl(`label`)
