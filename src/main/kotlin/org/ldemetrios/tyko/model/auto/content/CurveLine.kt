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
    "curve.line",
    ["curve.line", "content"],
    TCurveLineImpl::class,
)
public interface TCurveLine : TContent {
    public val end: TArray<TLength>

    public val relative: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("curve.line")

        internal val endType: InternalType = ConcreteType("array", listOf(ConcreteType("length")))

        internal val relativeType: InternalType = ConcreteType("bool")
    }
}

internal data class TCurveLineImpl(
    @TSerialName("end")
    override val end: TArray<TLength>,
    @TSerialName("relative")
    override val relative: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCurveLine {
    override fun format(): String = Representations.elementRepr("curve.line",ArgumentEntry(false,
            null, `end`),ArgumentEntry(false, "relative", `relative`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TCurveLine(
    end: TArray<TLength>,
    relative: TBool? = null,
    label: TLabel? = null,
): TCurveLine = TCurveLineImpl(`end`, `relative`, `label`)

@TSetRuleType(
    "curve.line",
    TSetCurveLineImpl::class,
)
public interface TSetCurveLine : TSetRule {
    override val elem: String
        get() = "curve.line"

    public val relative: TBool?

    override fun format(): String = Representations.setRepr("curve.line",ArgumentEntry(false,
            "relative", `relative`),)
}

internal class TSetCurveLineImpl(
    @TSerialName("relative")
    override val relative: TBool? = null,
) : TSetCurveLine

@TypstOverloads
public fun TSetCurveLine(relative: TBool? = null): TSetCurveLine = TSetCurveLineImpl(`relative`)
