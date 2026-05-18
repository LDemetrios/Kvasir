package org.ldemetrios.kvasir.util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.IntFunction

/*
Dear JB team, this is ridiculous...
"
Kvasir 0.4.1 uses deprecated API, which may be removed in future releases leading to binary and source code incompatibilities

Deprecated methods usages (2)
CollectionToArray.toArray(Collection, Object[]) (1)CollectionToArray.toArray(Collection) (1)
"

No it doesn't
 */

// interface ConcurrentSet<E> : MutableSet<E> {
//     val map: ConcurrentMap<E, Unit>
//
//     override fun add(element: E): Boolean = map.put(element, Unit) == null
//
//     override fun addAll(elements: Collection<E>): Boolean = elements.any(this::add)
//
//     override fun clear() = map.clear()
//
//     override fun iterator(): MutableIterator<E> = map.keys.iterator()
//
//     override fun remove(element: E): Boolean = map.remove(element) == Unit
//
//     override fun removeAll(elements: Collection<E>): Boolean = elements.any(this::remove)
//
//     override fun retainAll(elements: Collection<E>): Boolean = map.keys.retainAll(elements)
//
//     override val size: Int get() = map.size
//
//     override fun isEmpty(): Boolean = map.isEmpty()
//
//     override fun contains(element: E): Boolean = map.containsKey(element)
//
//     override fun containsAll(elements: Collection<E>): Boolean = map.keys.containsAll(elements)
// }
//
// class ConcurrentHashSet<E> : ConcurrentSet<E> {
//     override val map: ConcurrentMap<E, Unit> = ConcurrentHashMap()
// }
