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
    "stroke",
    ["stroke"],
    TStrokeImpl::class,
)
public interface TStroke :
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>, TValue {
    public val paint: TAutoOrColorOrGradientOrTiling?

    public val thickness: TAutoOrLength?

    public val cap: TAutoOrStr?

    public val join: TAutoOrStr?

    public val dash: TArrayOrAutoOrDictionaryOrNoneOrStr<TValue, TValue>?

    public val miterLimit: TAutoOrFloat?

    override fun type(): TType = TStroke

    public companion object : TTypeImpl("stroke") {
        internal val paintType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("color"),
                ConcreteType("gradient"), ConcreteType("tiling"))

        internal val thicknessType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("length"))

        internal val capType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("str"))

        internal val joinType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("str"))

        internal val dashType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"), ConcreteType("dictionary", listOf(AnyType)), ConcreteType("none"),
                ConcreteType("str"))

        internal val miterLimitType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("float"))
    }
}

internal data class TStrokeImpl(
    @TSerialName("paint")
    override val paint: TAutoOrColorOrGradientOrTiling? = null,
    @TSerialName("thickness")
    override val thickness: TAutoOrLength? = null,
    @TSerialName("cap")
    override val cap: TAutoOrStr? = null,
    @TSerialName("join")
    override val join: TAutoOrStr? = null,
    @TSerialName("dash")
    override val dash: TArrayOrAutoOrDictionaryOrNoneOrStr<TValue, TValue>? = null,
    @TSerialName("miter-limit")
    override val miterLimit: TAutoOrFloat? = null,
) : TStroke {
    override fun format(): String = Representations.structRepr("stroke",ArgumentEntry(false, "paint",
            `paint`),ArgumentEntry(false, "thickness", `thickness`),ArgumentEntry(false, "cap",
            `cap`),ArgumentEntry(false, "join", `join`),ArgumentEntry(false, "dash",
            `dash`),ArgumentEntry(false, "miter-limit", `miterLimit`),)
}

@TypstOverloads
public fun TStroke(
    paint: TAutoOrColorOrGradientOrTiling? = null,
    thickness: TAutoOrLength? = null,
    cap: TAutoOrStr? = null,
    join: TAutoOrStr? = null,
    dash: TArrayOrAutoOrDictionaryOrNoneOrStr<TValue, TValue>? = null,
    miterLimit: TAutoOrFloat? = null,
): TStroke = TStrokeImpl(`paint`, `thickness`, `cap`, `join`, `dash`, `miterLimit`)
