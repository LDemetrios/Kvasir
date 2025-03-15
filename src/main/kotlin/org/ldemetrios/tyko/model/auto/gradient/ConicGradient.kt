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
    "gradient.conic",
    ["gradient.conic", "gradient"],
    TConicGradientImpl::class,
)
public interface TConicGradient : TGradient {
    public val stops: TArray<TArrayOrColor<TColorOrRatio>>

    public val angle: TAngle?

    public val space: TValue?

    public val relative: TAutoOrStr?

    public val center: TArray<TRatio>?

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("gradient.conic".t)

        internal val stopsType: InternalType = ConcreteType("array",
                listOf(UnionType(ConcreteType("array", listOf(UnionType(ConcreteType("color"),
                ConcreteType("ratio")))), ConcreteType("color"))))

        internal val angleType: InternalType = ConcreteType("angle")

        internal val spaceType: InternalType = AnyType

        internal val relativeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("str"))

        internal val centerType: InternalType = ConcreteType("array", listOf(ConcreteType("ratio")))
    }
}

internal data class TConicGradientImpl(
    @TSerialName("stops")
    override val stops: TArray<TArrayOrColor<TColorOrRatio>>,
    @TSerialName("angle")
    override val angle: TAngle? = null,
    @TSerialName("space")
    override val space: TValue? = null,
    @TSerialName("relative")
    override val relative: TAutoOrStr? = null,
    @TSerialName("center")
    override val center: TArray<TRatio>? = null,
) : TConicGradient {
    override fun format(): String = Representations.structRepr("gradient.conic",ArgumentEntry(true,
            null, `stops`),ArgumentEntry(false, "angle", `angle`),ArgumentEntry(false, "space",
            `space`),ArgumentEntry(false, "relative", `relative`),ArgumentEntry(false, "center",
            `center`),)
}

@TypstOverloads
public fun TConicGradient(
    @TVararg stops: TArray<TArrayOrColor<TColorOrRatio>>,
    angle: TAngle? = null,
    space: TValue? = null,
    relative: TAutoOrStr? = null,
    center: TArray<TRatio>? = null,
): TConicGradient = TConicGradientImpl(`stops`, `angle`, `space`, `relative`, `center`)
