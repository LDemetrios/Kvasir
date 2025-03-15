@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import java.util.Spliterator
import java.util.function.Consumer
import java.util.stream.Stream
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.UnsafeVariance
import kotlin.collections.Collection
import kotlin.collections.Iterator
import kotlin.collections.List
import kotlin.collections.ListIterator
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
    "array",
    ["array"],
    TArrayImpl::class,
)
public interface TArray<out E : TValue> : List<E>, TValue, TArrayOrStr<E>,
        TArrayOrContentOrFunction<E>, TArrayOrEnumItem<E>, TArrayOrAutoOrFractionOrIntOrRelative<E>,
        TArrayOrColorOrFunctionOrGradientOrNoneOrTiling<E>, TAlignmentOrArrayOrAutoOrFunction<E>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<E, TDynamic>,
        TArrayOrFractionOrFunctionOrRelativeOrSides<E, TDynamic>, TArrayOrContent<E>,
        TArrayOrAutoOrDictionaryOrStr<E, TDynamic>, TArrayOrIntOrNone<E>,
        TArrayOrDictionary<E, TDynamic>, TArrayOrNoneOrStr<E>, TArrayOrNoneOrStrOrSymbol<E>,
        TArrayOrFunctionOrRelativeOrSides<E, TDynamic>, TArrayOrNone<E>, TArrayOrColor<E>,
        TArrayOrAuto<E>, TArrayOrAutoOrDictionaryOrNoneOrStr<E, TDynamic>, TArrayOrFunctionOrInt<E>,
        TArrayOrAutoOrNone<E> {
    public val arrayValue: List<E>

    override val size: Int
        get() = arrayValue.size

    override fun type(): TType = Type

    override fun format(): String = Representations.reprOf(arrayValue)

    override fun forEach(action: Consumer<in E>): Unit = arrayValue.forEach(action)

    override fun iterator(): Iterator<E> = arrayValue.iterator()

    override fun spliterator(): Spliterator<@UnsafeVariance E> = arrayValue.spliterator()

    override fun contains(element: @UnsafeVariance E): Boolean = arrayValue.contains(element)

    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean =
            arrayValue.containsAll(elements)

    override fun `get`(index: Int): E = arrayValue.get(index)

    override fun indexOf(element: @UnsafeVariance E): Int = arrayValue.indexOf(element)

    override fun isEmpty(): Boolean = arrayValue.isEmpty()

    override fun lastIndexOf(element: @UnsafeVariance E): Int = arrayValue.lastIndexOf(element)

    override fun listIterator(): ListIterator<E> = arrayValue.listIterator()

    override fun listIterator(index: Int): ListIterator<E> = arrayValue.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<E> = arrayValue.subList(fromIndex,
            toIndex)

    override fun parallelStream(): Stream<@UnsafeVariance E> = arrayValue.parallelStream()

    override fun stream(): Stream<@UnsafeVariance E> = arrayValue.stream()

    public companion object {
        public val Type: TType = TTypeImpl("array")
    }
}

internal data class TArrayImpl<out E : TValue>(
    override val arrayValue: List<E>,
) : TArray<E>

public fun <E : TValue> TArray(`value`: List<E>): TArray<E> = TArrayImpl<E>(value)
