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
    "color.hsv",
    ["color.hsv", "color"],
    THsvImpl::class,
)
public interface THsv : TColor {
    public val hue: TAngle

    public val saturation: TIntOrRatio

    public val `value`: TIntOrRatio

    public val alpha: TIntOrRatio?

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("color.hsv".t)

        internal val hueType: InternalType = ConcreteType("angle")

        internal val saturationType: InternalType = UnionType(ConcreteType("int"),
                ConcreteType("ratio"))

        internal val valueType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))

        internal val alphaType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))
    }
}

internal data class THsvImpl(
    @TSerialName("hue")
    override val hue: TAngle,
    @TSerialName("saturation")
    override val saturation: TIntOrRatio,
    @TSerialName("value")
    override val `value`: TIntOrRatio,
    @TSerialName("alpha")
    override val alpha: TIntOrRatio? = null,
) : THsv {
    override fun format(): String = Representations.structRepr("color.hsv",ArgumentEntry(false, null,
            `hue`),ArgumentEntry(false, null, `saturation`),ArgumentEntry(false, null,
            `value`),ArgumentEntry(false, null, `alpha`),)
}

@TypstOverloads
public fun THsv(
    hue: TAngle,
    saturation: TIntOrRatio,
    `value`: TIntOrRatio,
    alpha: TIntOrRatio? = null,
): THsv = THsvImpl(`hue`, `saturation`, `value`, `alpha`)
