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
    "luma",
    ["luma", "color"],
    TLumaImpl::class,
)
public interface TLuma : TColor {
    public val lightness: TIntOrRatio

    public val alpha: TRatio?

    override fun func(): TFunction = TLuma

    public companion object : TFunction {
        internal val lightnessType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))

        internal val alphaType: InternalType = ConcreteType("ratio")

        override fun format(): String = "luma"
    }
}

internal data class TLumaImpl(
    @TSerialName("lightness")
    override val lightness: TIntOrRatio,
    @TSerialName("alpha")
    override val alpha: TRatio? = null,
) : TLuma {
    override fun format(): String = Representations.structRepr("luma",ArgumentEntry(false, null,
            `lightness`),ArgumentEntry(false, null, `alpha`),)
}

@TypstOverloads
public fun TLuma(lightness: TIntOrRatio, alpha: TRatio? = null): TLuma = TLumaImpl(`lightness`,
        `alpha`)
