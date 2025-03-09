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
    "curve.cubic",
    ["curve.cubic", "content"],
    TCurveCubicImpl::class,
)
public interface TCurveCubic : TContent {
    public val controlStart: TArrayOrAutoOrNone<TLength>

    public val controlEnd: TArrayOrNone<TLength>

    public val end: TArray<TLength>

    public val relative: TBool?

    override fun func(): TElement = TCurveCubic

    public companion object : TElementImpl("curve.cubic") {
        internal val controlStartType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("length"))), ConcreteType("auto"), ConcreteType("none"))

        internal val controlEndType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("length"))), ConcreteType("none"))

        internal val endType: InternalType = ConcreteType("array", listOf(ConcreteType("length")))

        internal val relativeType: InternalType = ConcreteType("bool")
    }
}

internal data class TCurveCubicImpl(
    @TSerialName("control-start")
    override val controlStart: TArrayOrAutoOrNone<TLength>,
    @TSerialName("control-end")
    override val controlEnd: TArrayOrNone<TLength>,
    @TSerialName("end")
    override val end: TArray<TLength>,
    @TSerialName("relative")
    override val relative: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCurveCubic {
    override fun format(): String = Representations.elementRepr("curve.cubic",ArgumentEntry(false,
            null, `controlStart`),ArgumentEntry(false, null, `controlEnd`),ArgumentEntry(false, null,
            `end`),ArgumentEntry(false, "relative", `relative`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TCurveCubic(
    controlStart: TArrayOrAutoOrNone<TLength>,
    controlEnd: TArrayOrNone<TLength>,
    end: TArray<TLength>,
    relative: TBool? = null,
    label: TLabel? = null,
): TCurveCubic = TCurveCubicImpl(`controlStart`, `controlEnd`, `end`, `relative`, `label`)

@TSetRuleType(
    "curve.cubic",
    TSetCurveCubicImpl::class,
)
public interface TSetCurveCubic : TSetRule {
    override val elem: String
        get() = "curve.cubic"

    public val relative: TBool?

    override fun format(): String = Representations.setRepr("curve.cubic",ArgumentEntry(false,
            "relative", `relative`),)
}

internal class TSetCurveCubicImpl(
    @TSerialName("relative")
    override val relative: TBool? = null,
) : TSetCurveCubic

@TypstOverloads
public fun TSetCurveCubic(relative: TBool? = null): TSetCurveCubic = TSetCurveCubicImpl(`relative`)
