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
    "length",
    ["length", "relative"],
    TLengthImpl::class,
)
public interface TLength : TAutoOrLength, TDictionaryOrLength<TDynamic>, TBoolOrLength,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, TDynamic>,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>, TLengthOrTextTopEdge,
        TLengthOrTextBottomEdge, TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>,
        TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<TDynamic>, TAutoOrLengthOrRatio,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic>, TIntOrLength,
        TRelative {
    public val pt: TFloat?

    public val em: TFloat?

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("length")

        internal val ptType: InternalType = ConcreteType("float")

        internal val emType: InternalType = ConcreteType("float")
    }
}

internal data class TLengthImpl(
    @TSerialName("pt")
    override val pt: TFloat? = null,
    @TSerialName("em")
    override val em: TFloat? = null,
) : TLength {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TLength(pt: TFloat? = null, em: TFloat? = null): TLength = TLengthImpl(`pt`, `em`)
