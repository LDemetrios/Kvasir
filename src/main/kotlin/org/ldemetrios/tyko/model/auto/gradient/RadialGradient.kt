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
    "gradient.radial",
    ["gradient.radial", "gradient"],
    TRadialGradientImpl::class,
)
public interface TRadialGradient : TGradient {
    public val stops: TArray<TArrayOrColor<TColorOrRatio>>

    public val space: TValue?

    public val relative: TAutoOrStr?

    public val center: TArray<TRatio>?

    public val radius: TRatio?

    public val focalCenter: TArrayOrAuto<TRatio>?

    public val focalRadius: TRatio?

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("gradient.radial".t)

        internal val stopsType: InternalType = ConcreteType("array",
                listOf(UnionType(ConcreteType("array", listOf(UnionType(ConcreteType("color"),
                ConcreteType("ratio")))), ConcreteType("color"))))

        internal val spaceType: InternalType = AnyType

        internal val relativeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("str"))

        internal val centerType: InternalType = ConcreteType("array", listOf(ConcreteType("ratio")))

        internal val radiusType: InternalType = ConcreteType("ratio")

        internal val focalCenterType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("ratio"))), ConcreteType("auto"))

        internal val focalRadiusType: InternalType = ConcreteType("ratio")
    }
}

internal data class TRadialGradientImpl(
    @TSerialName("stops")
    override val stops: TArray<TArrayOrColor<TColorOrRatio>>,
    @TSerialName("space")
    override val space: TValue? = null,
    @TSerialName("relative")
    override val relative: TAutoOrStr? = null,
    @TSerialName("center")
    override val center: TArray<TRatio>? = null,
    @TSerialName("radius")
    override val radius: TRatio? = null,
    @TSerialName("focal-center")
    override val focalCenter: TArrayOrAuto<TRatio>? = null,
    @TSerialName("focal-radius")
    override val focalRadius: TRatio? = null,
) : TRadialGradient {
    override fun format(): String = Representations.structRepr("gradient.radial",ArgumentEntry(true,
            null, `stops`),ArgumentEntry(false, "space", `space`),ArgumentEntry(false, "relative",
            `relative`),ArgumentEntry(false, "center", `center`),ArgumentEntry(false, "radius",
            `radius`),ArgumentEntry(false, "focal-center", `focalCenter`),ArgumentEntry(false,
            "focal-radius", `focalRadius`),)
}

@TypstOverloads
public fun TRadialGradient(
    @TVararg stops: TArray<TArrayOrColor<TColorOrRatio>>,
    space: TValue? = null,
    relative: TAutoOrStr? = null,
    center: TArray<TRatio>? = null,
    radius: TRatio? = null,
    focalCenter: TArrayOrAuto<TRatio>? = null,
    focalRadius: TRatio? = null,
): TRadialGradient = TRadialGradientImpl(`stops`, `space`, `relative`, `center`, `radius`,
        `focalCenter`, `focalRadius`)
