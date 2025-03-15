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
    "rgb",
    ["rgb", "color"],
    TRgbImpl::class,
)
public interface TRgb : TColor {
    public val hex: TStr

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("rgb".t)

        internal val hexType: InternalType = ConcreteType("str")
    }
}

internal data class TRgbImpl(
    @TSerialName("hex")
    override val hex: TStr,
) : TRgb {
    override fun format(): String = Representations.structRepr("rgb",ArgumentEntry(false, null,
            `hex`),)
}

@TypstOverloads
public fun TRgb(hex: TStr): TRgb = TRgbImpl(`hex`)
