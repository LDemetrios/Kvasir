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
    "curve.quad",
    ["curve.quad", "content"],
    TCurveQuadImpl::class,
)
public interface TCurveQuad : TContent {
    public val control: TArrayOrAutoOrNone<TLength>

    public val end: TArray<TLength>

    public val relative: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("curve.quad")

        internal val controlType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("length"))), ConcreteType("auto"), ConcreteType("none"))

        internal val endType: InternalType = ConcreteType("array", listOf(ConcreteType("length")))

        internal val relativeType: InternalType = ConcreteType("bool")
    }
}

internal data class TCurveQuadImpl(
    @TSerialName("control")
    override val control: TArrayOrAutoOrNone<TLength>,
    @TSerialName("end")
    override val end: TArray<TLength>,
    @TSerialName("relative")
    override val relative: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCurveQuad {
    override fun format(): String = Representations.elementRepr("curve.quad",ArgumentEntry(false,
            null, `control`),ArgumentEntry(false, null, `end`),ArgumentEntry(false, "relative",
            `relative`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TCurveQuad(
    control: TArrayOrAutoOrNone<TLength>,
    end: TArray<TLength>,
    relative: TBool? = null,
    label: TLabel? = null,
): TCurveQuad = TCurveQuadImpl(`control`, `end`, `relative`, `label`)

@TSetRuleType(
    "curve.quad",
    TSetCurveQuadImpl::class,
)
public interface TSetCurveQuad : TSetRule {
    override val elem: String
        get() = "curve.quad"

    public val relative: TBool?

    override fun format(): String = Representations.setRepr("curve.quad",ArgumentEntry(false,
            "relative", `relative`),)
}

internal class TSetCurveQuadImpl(
    @TSerialName("relative")
    override val relative: TBool? = null,
) : TSetCurveQuad

@TypstOverloads
public fun TSetCurveQuad(relative: TBool? = null): TSetCurveQuad = TSetCurveQuadImpl(`relative`)
