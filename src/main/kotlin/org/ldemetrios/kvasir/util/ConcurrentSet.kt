package org.ldemetrios.kvasir.util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

interface ConcurrentSet<E> : MutableSet<E> {
    val map: ConcurrentMap<E, Unit>

    override fun add(element: E): Boolean = map.put(element, Unit) == null

    override fun addAll(elements: Collection<E>): Boolean = elements.any(this::add)

    override fun clear() = map.clear()

    override fun iterator(): MutableIterator<E> = map.keys.iterator()

    override fun remove(element: E): Boolean = map.remove(element) == Unit

    override fun removeAll(elements: Collection<E>): Boolean = elements.any(this::remove)

    override fun retainAll(elements: Collection<E>): Boolean = map.keys.retainAll(elements)

    override val size: Int get() = map.size

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun contains(element: E): Boolean = map.containsKey(element)

    override fun containsAll(elements: Collection<E>): Boolean = map.keys.containsAll(elements)
}

//default V getOrDefault(Object key, V defaultValue)
//default void forEach(BiConsumer<? super K, ? super V> action)
//V putIfAbsent(K var1, V var2);
//
//boolean remove(Object var1, Object var2);
//
//boolean replace(K var1, V var2, V var3);
//
//V replace(K var1, V var2);
//
//default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)
//
//default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
//default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
//
//default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
//
//default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)

class ConcurrentHashSet<E> : ConcurrentSet<E> {
    override val map: ConcurrentMap<E, Unit> = ConcurrentHashMap()
}