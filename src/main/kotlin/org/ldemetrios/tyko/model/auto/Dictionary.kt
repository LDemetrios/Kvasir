@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import java.util.function.BiConsumer
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.UnsafeVariance
import kotlin.collections.Collection
import kotlin.collections.Map
import kotlin.collections.Set
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
    "dictionary",
    ["dictionary"],
    TDictionaryImpl::class,
)
public interface TDictionary<out V : TValue> : Map<String, V>, TValue, TDictionaryOrLength<V>,
        TArrayOrColorOrDictionaryOrFunctionOrGradientOrLengthOrNoneOrStrokeOrTiling<TDynamic, V>,
        TColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>, TDictionaryOrRelative<V>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<V>,
        TArrayOrAutoOrDictionaryOrStr<TDynamic, V>, TArrayOrDictionary<TDynamic, V>,
        TColorOrDictionaryOrGradientOrLengthOrStrokeOrTiling<V>, TDictionaryOrIntOrNone<V>,
        TAutoOrColorOrDictionaryOrGradientOrLengthOrNoneOrStrokeOrTiling<V>,
        TAutoOrDictionaryOrImageFormat<V>, TArrayOrAutoOrDictionaryOrNoneOrStr<TDynamic, V> {
    public val dictionaryValue: Map<String, V>

    override val entries: Set<Map.Entry<String, V>>
        get() = dictionaryValue.entries

    override val keys: Set<String>
        get() = dictionaryValue.keys

    override val size: Int
        get() = dictionaryValue.size

    override val values: Collection<V>
        get() = dictionaryValue.values

    override fun type(): TType = TDictionary

    override fun format(): String = Representations.reprOf(dictionaryValue)

    override fun containsKey(key: String): Boolean = dictionaryValue.containsKey(key)

    override fun containsValue(`value`: @UnsafeVariance V): Boolean =
            this.dictionaryValue.containsValue(value)

    override fun `get`(key: String): V? = dictionaryValue.get(key)

    override fun isEmpty(): Boolean = dictionaryValue.isEmpty()

    override fun forEach(action: BiConsumer<in String, in V>): Unit = dictionaryValue.forEach(action)

    override fun getOrDefault(key: String, defaultValue: @UnsafeVariance V): V =
            dictionaryValue.getOrDefault(key, defaultValue)

    public companion object : TTypeImpl("dictionary")
}

internal data class TDictionaryImpl<out V : TValue>(
    override val dictionaryValue: Map<String, V>,
) : TDictionary<V> {
    override fun equals(other: Any?): Boolean = this === other || other is TDictionary<*> &&
            this.dictionaryValue == other.dictionaryValue

    override fun hashCode(): Int = dictionaryValue.hashCode()
}
