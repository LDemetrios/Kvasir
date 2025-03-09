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
    "oklch",
    ["oklch", "color"],
    TOklchImpl::class,
)
public interface TOklch : TColor {
    public val lightness: TRatio

    public val chroma: TFloatOrRatio

    public val hue: TAngle

    public val alpha: TRatio?

    override fun func(): TFunction = TOklch

    public companion object : TFunction {
        internal val lightnessType: InternalType = ConcreteType("ratio")

        internal val chromaType: InternalType = UnionType(ConcreteType("float"), ConcreteType("ratio"))

        internal val hueType: InternalType = ConcreteType("angle")

        internal val alphaType: InternalType = ConcreteType("ratio")

        override fun format(): String = "oklch"
    }
}

internal data class TOklchImpl(
    @TSerialName("lightness")
    override val lightness: TRatio,
    @TSerialName("chroma")
    override val chroma: TFloatOrRatio,
    @TSerialName("hue")
    override val hue: TAngle,
    @TSerialName("alpha")
    override val alpha: TRatio? = null,
) : TOklch {
    override fun format(): String = Representations.structRepr("oklch",ArgumentEntry(false, null,
            `lightness`),ArgumentEntry(false, null, `chroma`),ArgumentEntry(false, null,
            `hue`),ArgumentEntry(false, null, `alpha`),)
}

@TypstOverloads
public fun TOklch(
    lightness: TRatio,
    chroma: TFloatOrRatio,
    hue: TAngle,
    alpha: TRatio? = null,
): TOklch = TOklchImpl(`lightness`, `chroma`, `hue`, `alpha`)
