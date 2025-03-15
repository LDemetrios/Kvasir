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
    "color.hsl",
    ["color.hsl", "color"],
    THslImpl::class,
)
public interface THsl : TColor {
    public val hue: TAngle

    public val saturation: TIntOrRatio

    public val lightness: TIntOrRatio

    public val alpha: TIntOrRatio?

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("color.hsl".t)

        internal val hueType: InternalType = ConcreteType("angle")

        internal val saturationType: InternalType = UnionType(ConcreteType("int"),
                ConcreteType("ratio"))

        internal val lightnessType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))

        internal val alphaType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))
    }
}

internal data class THslImpl(
    @TSerialName("hue")
    override val hue: TAngle,
    @TSerialName("saturation")
    override val saturation: TIntOrRatio,
    @TSerialName("lightness")
    override val lightness: TIntOrRatio,
    @TSerialName("alpha")
    override val alpha: TIntOrRatio? = null,
) : THsl {
    override fun format(): String = Representations.structRepr("color.hsl",ArgumentEntry(false, null,
            `hue`),ArgumentEntry(false, null, `saturation`),ArgumentEntry(false, null,
            `lightness`),ArgumentEntry(false, null, `alpha`),)
}

@TypstOverloads
public fun THsl(
    hue: TAngle,
    saturation: TIntOrRatio,
    lightness: TIntOrRatio,
    alpha: TIntOrRatio? = null,
): THsl = THslImpl(`hue`, `saturation`, `lightness`, `alpha`)
