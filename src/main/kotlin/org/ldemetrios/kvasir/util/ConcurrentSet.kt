package org.ldemetrios.kvasir.util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.IntFunction

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

    @Suppress("OVERRIDE_DEPRECATION")
    override fun <T : Any?> toArray(generator: IntFunction<Array<T>>): Array<T> {
        val r = generator.apply(size)
        for ((idx, el) in this.withIndex()) r[idx] = el as T
        return r
    }
}

class ConcurrentHashSet<E> : ConcurrentSet<E> {
    override val map: ConcurrentMap<E, Unit> = ConcurrentHashMap()
}
