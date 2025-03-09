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
    "space",
    ["space", "content"],
    TSpaceImpl::class,
)
public interface TSpace : TContent {
    override fun func(): TElement = TSpace

    public companion object : TElementImpl("space")
}

internal data class TSpaceImpl(
    @TSerialName("label")
    override val label: TLabel? = null,
) : TSpace {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TSpace(label: TLabel? = null): TSpace = TSpaceImpl(`label`)
