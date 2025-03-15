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
    "math.align-point",
    ["math.align-point", "content"],
    TMathAlignPointImpl::class,
)
public interface TMathAlignPoint : TContent {
    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.align-point")
    }
}

internal data class TMathAlignPointImpl(
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathAlignPoint {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TMathAlignPoint(label: TLabel? = null): TMathAlignPoint = TMathAlignPointImpl(`label`)
