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
    "curve.move",
    ["curve.move", "content"],
    TCurveMoveImpl::class,
)
public interface TCurveMove : TContent {
    public val start: TArray<TLength>

    public val relative: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("curve.move")

        internal val startType: InternalType = ConcreteType("array", listOf(ConcreteType("length")))

        internal val relativeType: InternalType = ConcreteType("bool")
    }
}

internal data class TCurveMoveImpl(
    @TSerialName("start")
    override val start: TArray<TLength>,
    @TSerialName("relative")
    override val relative: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCurveMove {
    override fun format(): String = Representations.elementRepr("curve.move",ArgumentEntry(false,
            null, `start`),ArgumentEntry(false, "relative", `relative`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TCurveMove(
    start: TArray<TLength>,
    relative: TBool? = null,
    label: TLabel? = null,
): TCurveMove = TCurveMoveImpl(`start`, `relative`, `label`)

@TSetRuleType(
    "curve.move",
    TSetCurveMoveImpl::class,
)
public interface TSetCurveMove : TSetRule {
    override val elem: String
        get() = "curve.move"

    public val relative: TBool?

    override fun format(): String = Representations.setRepr("curve.move",ArgumentEntry(false,
            "relative", `relative`),)
}

internal class TSetCurveMoveImpl(
    @TSerialName("relative")
    override val relative: TBool? = null,
) : TSetCurveMove

@TypstOverloads
public fun TSetCurveMove(relative: TBool? = null): TSetCurveMove = TSetCurveMoveImpl(`relative`)
