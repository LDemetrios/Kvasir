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
    "parbreak",
    ["parbreak", "content"],
    TParbreakImpl::class,
)
public interface TParbreak : TContent {
    override fun func(): TElement = TParbreak

    public companion object : TElementImpl("parbreak")
}

internal data class TParbreakImpl(
    @TSerialName("label")
    override val label: TLabel? = null,
) : TParbreak {
    override fun format(): String = Representations.elementRepr("parbreak",ArgumentEntry(false,
            "label", `label`),)
}

@TypstOverloads
public fun TParbreak(label: TLabel? = null): TParbreak = TParbreakImpl(`label`)
