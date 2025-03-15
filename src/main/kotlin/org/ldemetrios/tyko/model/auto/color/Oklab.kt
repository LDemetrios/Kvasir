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
    "oklab",
    ["oklab", "color"],
    TOklabImpl::class,
)
public interface TOklab : TColor {
    public val lightness: TRatio

    public val a: TFloatOrRatio

    public val b: TFloatOrRatio

    public val alpha: TRatio?

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("oklab".t)

        internal val lightnessType: InternalType = ConcreteType("ratio")

        internal val aType: InternalType = UnionType(ConcreteType("float"), ConcreteType("ratio"))

        internal val bType: InternalType = UnionType(ConcreteType("float"), ConcreteType("ratio"))

        internal val alphaType: InternalType = ConcreteType("ratio")
    }
}

internal data class TOklabImpl(
    @TSerialName("lightness")
    override val lightness: TRatio,
    @TSerialName("a")
    override val a: TFloatOrRatio,
    @TSerialName("b")
    override val b: TFloatOrRatio,
    @TSerialName("alpha")
    override val alpha: TRatio? = null,
) : TOklab {
    override fun format(): String = Representations.structRepr("oklab",ArgumentEntry(false, null,
            `lightness`),ArgumentEntry(false, null, `a`),ArgumentEntry(false, null,
            `b`),ArgumentEntry(false, null, `alpha`),)
}

@TypstOverloads
public fun TOklab(
    lightness: TRatio,
    a: TFloatOrRatio,
    b: TFloatOrRatio,
    alpha: TRatio? = null,
): TOklab = TOklabImpl(`lightness`, `a`, `b`, `alpha`)
