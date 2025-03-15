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
    "color.linear-rgb",
    ["color.linear-rgb", "color"],
    TLinearRgbImpl::class,
)
public interface TLinearRgb : TColor {
    public val red: TIntOrRatio

    public val green: TIntOrRatio

    public val blue: TIntOrRatio

    public val alpha: TIntOrRatio?

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("color.linear-rgb".t)

        internal val redType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))

        internal val greenType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))

        internal val blueType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))

        internal val alphaType: InternalType = UnionType(ConcreteType("int"), ConcreteType("ratio"))
    }
}

internal data class TLinearRgbImpl(
    @TSerialName("red")
    override val red: TIntOrRatio,
    @TSerialName("green")
    override val green: TIntOrRatio,
    @TSerialName("blue")
    override val blue: TIntOrRatio,
    @TSerialName("alpha")
    override val alpha: TIntOrRatio? = null,
) : TLinearRgb {
    override fun format(): String = Representations.structRepr("color.linear-rgb",ArgumentEntry(false,
            null, `red`),ArgumentEntry(false, null, `green`),ArgumentEntry(false, null,
            `blue`),ArgumentEntry(false, null, `alpha`),)
}

@TypstOverloads
public fun TLinearRgb(
    red: TIntOrRatio,
    green: TIntOrRatio,
    blue: TIntOrRatio,
    alpha: TIntOrRatio? = null,
): TLinearRgb = TLinearRgbImpl(`red`, `green`, `blue`, `alpha`)
