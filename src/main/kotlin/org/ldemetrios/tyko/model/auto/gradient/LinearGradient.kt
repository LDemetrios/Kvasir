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
    "gradient.linear",
    ["gradient.linear", "gradient"],
    TLinearGradientImpl::class,
)
public interface TLinearGradient : TGradient {
    public val stops: TArray<TArrayOrColor<TColorOrRatio>>

    public val space: TValue?

    public val relative: TAutoOrStr?

    public val dir: TDirection?

    public val angle: TAngle?

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("gradient.linear".t)

        internal val stopsType: InternalType = ConcreteType("array",
                listOf(UnionType(ConcreteType("array", listOf(UnionType(ConcreteType("color"),
                ConcreteType("ratio")))), ConcreteType("color"))))

        internal val spaceType: InternalType = AnyType

        internal val relativeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("str"))

        internal val dirType: InternalType = ConcreteType("direction")

        internal val angleType: InternalType = ConcreteType("angle")
    }
}

internal data class TLinearGradientImpl(
    @TSerialName("stops")
    override val stops: TArray<TArrayOrColor<TColorOrRatio>>,
    @TSerialName("space")
    override val space: TValue? = null,
    @TSerialName("relative")
    override val relative: TAutoOrStr? = null,
    @TSerialName("dir")
    override val dir: TDirection? = null,
    @TSerialName("angle")
    override val angle: TAngle? = null,
) : TLinearGradient {
    override fun format(): String = Representations.structRepr("gradient.linear",ArgumentEntry(true,
            null, `stops`),ArgumentEntry(false, "space", `space`),ArgumentEntry(false, "relative",
            `relative`),ArgumentEntry(false, "dir", `dir`),ArgumentEntry(false, "angle", `angle`),)
}

@TypstOverloads
public fun TLinearGradient(
    @TVararg stops: TArray<TArrayOrColor<TColorOrRatio>>,
    space: TValue? = null,
    relative: TAutoOrStr? = null,
    dir: TDirection? = null,
    angle: TAngle? = null,
): TLinearGradient = TLinearGradientImpl(`stops`, `space`, `relative`, `dir`, `angle`)
