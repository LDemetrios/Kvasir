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
    "tiling",
    ["tiling"],
    TTilingImpl::class,
)
public interface TTiling : TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<TDynamic>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TAutoOrColorOrGradientOrNoneOrTiling,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TColorOrGradientOrNoneOrTiling,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>, TColorOrGradientOrTiling,
        TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>,
        TAutoOrColorOrGradientOrTiling, TValue {
    public val body: TContent

    public val sz: TArrayOrAuto<TValue>?

    public val spacing: TArray<TLength>?

    public val relative: TAutoOrStr?

    override fun type(): TType = TTiling

    public companion object : TTypeImpl("tiling") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val szType: InternalType = UnionType(ConcreteType("array", listOf(AnyType)),
                ConcreteType("auto"))

        internal val spacingType: InternalType = ConcreteType("array", listOf(ConcreteType("length")))

        internal val relativeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("str"))
    }
}

internal data class TTilingImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("size")
    override val sz: TArrayOrAuto<TValue>? = null,
    @TSerialName("spacing")
    override val spacing: TArray<TLength>? = null,
    @TSerialName("relative")
    override val relative: TAutoOrStr? = null,
) : TTiling {
    override fun format(): String = Representations.structRepr("tiling",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "size", `sz`),ArgumentEntry(false, "spacing",
            `spacing`),ArgumentEntry(false, "relative", `relative`),)
}

@TypstOverloads
public fun TTiling(
    @TContentBody body: TContent,
    sz: TArrayOrAuto<TValue>? = null,
    spacing: TArray<TLength>? = null,
    relative: TAutoOrStr? = null,
): TTiling = TTilingImpl(`body`, `sz`, `spacing`, `relative`)
