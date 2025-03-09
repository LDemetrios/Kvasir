@file:Suppress("unused")

package org.ldemetrios.js

import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import java.util.function.BiFunction


@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class JSObject private constructor(
    var __proto__: JSObject?,
    private val array: MutableMap<String, JSStuff>
) : AbstractMutableMap<String, JSStuff>(), JSContainer, JSStuff {

    companion object {
        val OBJECT_PROTOTYPE = JSObject(null, mutableMapOf())

        const val PROTO_KEY = "__proto__"
        fun of(map: Map<String, JSStuff>) = if (PROTO_KEY in map) {
            JSObject(map[PROTO_KEY].let {
                when (it) {
                    is JSObject -> it
                    is JSNull -> null
                     is JSUndefined -> null
                    null -> null
                    else -> throw IllegalArgumentException()
                }
            }, map.filterTo(mutableMapOf()) { it.key != PROTO_KEY })
        } else {
            JSObject(OBJECT_PROTOTYPE, map.toMutableMap())
        }

        @Deprecated(
            "May cause interesting special effects",
            ReplaceWith("explicit type-based conversion")
        )
        fun ofAny(map: Map<String, Any?>) = of(map.mapValues { it.asJS() })

        fun create(proto: JSObject?) = JSObject(proto, mutableMapOf())
    }

    constructor(vararg entries: Pair<String, JSStuff>) : this(OBJECT_PROTOTYPE, entries.toMap(mutableMapOf()))
    constructor(entries: Map<String, JSStuff>) : this(OBJECT_PROTOTYPE, entries.toMutableMap())
    constructor(proto: JSObject, vararg entries: Pair<String, JSStuff>) : this(proto, entries.toMap(mutableMapOf()))
    constructor() : this(OBJECT_PROTOTYPE, mutableMapOf())

    override val size: Int
        get() {
            var map: JSObject? = this
            var res = 0
            while (map != null) {
                res += map.array.size
                map = map.__proto__
            }
            return res
        }

    override fun get(ind: Int): JSStuff = get(ind.toDouble().toString())
    override fun get(ind: String): JSStuff = array[ind] ?: __proto__?.get(ind) ?: JSUndefined

    override fun set(ind: Int, value: JSStuff) = set(ind.toDouble().toString(), value)
    override fun set(ind: String, value: JSStuff) = array.put(ind, value) ?: JSUndefined

    override fun toBoolean(): Boolean = array.isNotEmpty()
    override fun toDouble(): Double = if (array.isEmpty()) 0.0 else 1.0
    override fun toLong(): Long = if (array.isEmpty()) 0L else 1L

    override fun put(key: String, value: JSStuff): JSStuff? = array.put(key, value)

    override fun toString() = toString(0)

    override fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int) {
        sb.append('{')
        val proto = __proto__ ?: JSNull
        if (proto == OBJECT_PROTOTYPE) {
            if (indent == -1) {
                val iter = array.iterator()
                if (iter.hasNext()) {
                    appendEntry(sb, iter.next(), "\":", -1, 0)
                }
                while (iter.hasNext()) {
                    sb.append(",")
                    val next = iter.next()
                    appendEntry(sb, next, "\":", -1, 0)
                }
            } else if (indent == 0) {
                val iter = array.iterator()
                if (iter.hasNext()) {
                    appendEntry(sb, iter.next(), "\" : ", 0, 0)
                }
                while (iter.hasNext()) {
                    sb.append(", ")
                    val next = iter.next()
                    appendEntry(sb, next, "\" : ", 0, 0)
                }
            } else {
                if (array.size == 0) {
                    //Skip
                } else if (isBamboo()) {
                    sb.append(" ")
                    val next = iterator().next()
                    appendEntry(sb, next, "\" : ", 1, 0)
                    sb.append(" ")
                } else {
                    sb.append(System.lineSeparator())
                    val newIndent = curIndent + indent
                    sb.append(" ".repeat(newIndent))
                    val iter = array.iterator()
                    appendEntry(sb, iter.next(), "\" : ", indent, newIndent)
                    while (iter.hasNext()) {
                        sb.append(',')
                        doIndent(sb, newIndent)
                        appendEntry(sb, iter.next(), "\" : ", indent, newIndent)
                    }
                    doIndent(sb, curIndent)
                }
            }
            sb.append('}')
        } else { // proto != null
            if (indent == -1) {
                appendEntry(sb, PROTO_KEY, proto, "\":", -1, 0)
                val iter = array.iterator()
                while (iter.hasNext()) {
                    sb.append(',')
                    val next = iter.next()
                    appendEntry(sb, next, "\":", -1, 0)
                }
            } else if (indent == 0) {
                appendEntry(sb, PROTO_KEY, proto, "\" : ", 0, 0)
                val iter = array.iterator()
                while (iter.hasNext()) {
                    sb.append(", ")
                    val next = iter.next()
                    appendEntry(sb, next, "\" : ", 0, 0)
                }
            } else {
                if (array.size == 0) {
                    sb.append(" ")
                    appendEntry(sb, PROTO_KEY, proto, "\" : ", 0, 0)
                    sb.append(" ")
                } else {
                    val newIndent = curIndent + indent
                    doIndent(sb, newIndent)
                    appendEntry(sb, PROTO_KEY, proto, "\" : ", indent, newIndent)
                    val iter = array.iterator()
                    while (iter.hasNext()) {
                        sb.append(',')
                        doIndent(sb, newIndent)
                        appendEntry(sb, iter.next(), "\" : ", indent, newIndent)
                    }
                    doIndent(sb, curIndent)
                }
            }
            sb.append('}')
        }
    }

    private fun doIndent(sb: StringBuilder, curIndent: Int) {
        sb.append(System.lineSeparator())
        sb.append(" ".repeat(curIndent))
    }

    private fun appendEntry(
        sb: StringBuilder,
        next: MutableMap.MutableEntry<String, JSStuff>,
        separ: String, indent: Int, curIndent: Int
    ) {
        appendEntry(sb, next.key, next.value, separ, indent, curIndent)
    }

    private fun appendEntry(
        sb: StringBuilder, key: String, value: JSStuff, separ: String, indent: Int, curIndent: Int
    ) {
        sb.append('"')
        sb.append(key.escapeJSON())
        sb.append(separ)
        value.appendTo(sb, indent, curIndent)
    }

    override fun equals(other: Any?): Boolean =
        other === this || other is JSObject && array == other.array && __proto__ == other.__proto__

    override fun clear() = array.clear()

    override fun containsKey(key: String): Boolean = array.containsKey(key) || __proto__?.containsKey(key) ?: false
    override fun containsValue(value: JSStuff): Boolean =
        array.containsValue(value) || __proto__?.containsValue(value) ?: false

    override fun hashCode(): Int = array.hashCode() xor __proto__.hashCode()
    override fun isEmpty(): Boolean = array.isEmpty()
    override fun putIfAbsent(key: String, value: JSStuff): JSStuff? = array.putIfAbsent(key, value)
    override fun remove(key: String): JSStuff? = array.remove(key)
    override fun remove(key: String, value: JSStuff): Boolean = array.remove(key, value)
    override fun replace(key: String, oldValue: JSStuff, newValue: JSStuff): Boolean =
        array.replace(key, oldValue, newValue)

    override fun replace(key: String, value: JSStuff): JSStuff? = array.replace(key, value)
    override fun replaceAll(function: BiFunction<in String, in JSStuff, out JSStuff>) = array.replaceAll(function)

    fun protoChain(): Iterable<JSObject> = Iterable {
        object : Iterator<JSObject> {
            var seed = this@JSObject
            override fun hasNext(): Boolean = seed.__proto__ != null
            override fun next(): JSObject {
                val res = seed
                seed = seed.__proto__!!
                return res
            }
        }
    }

    abstract inner class ChainedSet<T> : AbstractMutableSet<T>() {
        override val size: Int
            get() = this@JSObject.size

        protected fun err(): Nothing = throw UnsupportedOperationException()
        override fun add(element: T): Boolean = err()
        override fun addAll(elements: Collection<T>): Boolean = err()
        override fun clear() = this@JSObject.clear()
        override fun iterator(): MutableIterator<T> {
            val iter = this@JSObject.protoChain().flatMapTo(mutableListOf()) { selector(it) }.iterator()
            return object : MutableIterator<T> {
                var cur: T? = null
                override fun hasNext(): Boolean = iter.hasNext()

                override fun next(): T {
                    cur = iter.next()
                    @Suppress("UNCHECKED_CAST")
                    return cur as T
                }

                override fun remove() {
                    iter.remove()
                    @Suppress("UNCHECKED_CAST")
                    remove(cur as T)
                }
            }
        }

        abstract fun selector(obj: JSObject): Iterable<T>
        abstract override fun contains(element: T): Boolean
        abstract override fun remove(element: T): Boolean
    }

    override val entries: MutableSet<MutableMap.MutableEntry<String, JSStuff>>
        get() = object : ChainedSet<MutableMap.MutableEntry<String, JSStuff>>() {
            override fun selector(obj: JSObject): Iterable<MutableMap.MutableEntry<String, JSStuff>> = obj.array.entries

            override fun contains(element: MutableMap.MutableEntry<String, JSStuff>): Boolean =
                this@JSObject.containsKey(element.key) && this@JSObject[element.key] == element.value

            override fun remove(element: MutableMap.MutableEntry<String, JSStuff>): Boolean =
                this@JSObject.remove(element.key) != null
        }

    override val keys: MutableSet<String>
        get() = object : ChainedSet<String>() {
            override fun selector(obj: JSObject): Iterable<String> = obj.array.keys

            override fun contains(element: String): Boolean =
                this@JSObject.containsKey(element)

            override fun remove(element: String): Boolean =
                this@JSObject.remove(element) != null
        }

    override val values: MutableCollection<JSStuff>
        get() = object : ChainedSet<JSStuff>() {
            override fun selector(obj: JSObject): Iterable<JSStuff> = obj.array.values

            override fun contains(element: JSStuff): Boolean =
                this@JSObject.containsValue(element)

            override fun remove(element: JSStuff): Boolean = err()
        }

    val ownSize get() = array.size
    fun hasOwnProperty(key: String) = key in array
    val ownKeys get() = array.keys
    val ownValues get() = array.values
    val ownEntries get() = array.entries
}
