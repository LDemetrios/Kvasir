@file:Suppress("unused")

package org.ldemetrios.js

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class JSArray(array: List<JSStuff>) : AbstractMutableList<JSStuff>(),
    JSStuff, JSContainer {
    constructor(vararg entries: JSStuff) : this(entries.toMutableList())
    constructor() : this(mutableListOf())

    val array: MutableList<JSStuff> = array.toMutableList()

    override fun get(ind: Int): JSStuff = if (ind >= 0 && ind < array.size) array[ind] else JSUndefined

    override fun get(ind: String): JSStuff = ind.removeSuffix(".0").toIntOrNull()?.let { this[it] } ?: JSUndefined

    override fun set(ind: Int, value: JSStuff): JSStuff {
        if (ind in array.indices) {
            return array.set(ind, value)
        } else if (ind >= size) {
            while (ind > size) array.add(JSUndefined)
            array.add(value)
        }
        return JSUndefined
    }

    override fun set(ind: String, value: JSStuff) = ind.removeSuffix(".0").toIntOrNull()?.let { this.set(it, value) } ?: JSUndefined

    override fun toBoolean(): Boolean = array.isNotEmpty()
    override fun toDouble(): Double = if (array.isEmpty()) 0.0 else 1.0
    override fun toLong(): Long = if (array.isEmpty()) 0L else 1L

    override fun add(element: JSStuff): Boolean = array.add(element)
    override val size: Int get() = array.size
    override fun iterator(): MutableIterator<JSStuff> = array.iterator()
    override fun toString() = toString(0)
    override fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int) {
        sb.append('[')
        if (indent == -1) {
            val iter = array.iterator()
            if (iter.hasNext()) iter.next().appendTo(sb, -1, 0)
            while (iter.hasNext()) {
                sb.append(",")
                iter.next().appendTo(sb, -1, 0)
            }
        } else if (indent == 0) {
            val iter = array.iterator()
            if (iter.hasNext()) iter.next().appendTo(sb, 0, 0)
            while (iter.hasNext()) {
                sb.append(", ")
                iter.next().appendTo(sb, 0, 0)
            }
        } else {
            if (size == 0) {
                //Skip
            } else if (size == 1 && this[0].isBamboo()) {
                sb.append(" ")
                this[0].appendTo(sb, 1, 0)
                sb.append(" ")
            } else {
                sb.append(System.lineSeparator())
                val newIndent = curIndent + indent
                sb.append(" ".repeat(newIndent))
                val iter = array.iterator()
                iter.next().appendTo(sb, indent, newIndent)
                while (iter.hasNext()) {
                    sb.append(',')
                    sb.append(System.lineSeparator())
                    sb.append(" ".repeat(newIndent))
                    iter.next().appendTo(sb, indent, newIndent)
                }
                sb.append(System.lineSeparator())
                sb.append(" ".repeat(curIndent))
            }
        }
        sb.append(']')
    }

    companion object {
        fun of(iterable: Iterable<JSStuff>) = JSArray(iterable.toMutableList())

        @Deprecated(
            "May cause interesting special effects",
            ReplaceWith("explicit type-based conversion")
        )
        fun ofAnyList(iterable: Iterable<Any?>) = JSArray(iterable.map { it.asJS() })

        @Deprecated(
            "May cause interesting special effects",
            ReplaceWith("explicit type-based conversion")
        )
        fun ofAnys(vararg entries: Any?) = JSArray(entries.mapTo(mutableListOf()) { it.asJS() })
    }

    override fun add(index: Int, element: JSStuff) = array.add(index, element)

    override fun removeAt(index: Int): JSStuff = array.removeAt(index)

    override fun addAll(index: Int, elements: Collection<JSStuff>): Boolean {
        return array.addAll(index, elements)
    }

    override fun clear() {
        array.clear()
    }

    override fun equals(other: Any?): Boolean = other === this || other is JSArray && array == other.array

    override fun hashCode(): Int = array.hashCode()
    override fun indexOf(element: JSStuff): Int = array.indexOf(element)
    override fun lastIndexOf(element: JSStuff): Int = array.lastIndexOf(element)
    override fun listIterator(): MutableListIterator<JSStuff> = array.listIterator()
    override fun listIterator(index: Int): MutableListIterator<JSStuff> = array.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<JSStuff> = array.subList(fromIndex, toIndex)
    override fun addAll(elements: Collection<JSStuff>): Boolean = array.addAll(elements)
    override fun contains(element: JSStuff): Boolean = array.contains(element)
    override fun containsAll(elements: Collection<JSStuff>): Boolean = array.containsAll(elements)
    override fun isEmpty(): Boolean = array.isEmpty()
    override fun remove(element: JSStuff): Boolean = array.remove(element)
    override fun removeAll(elements: Collection<JSStuff>): Boolean = array.removeAll(elements)
    override fun retainAll(elements: Collection<JSStuff>): Boolean = array.retainAll(elements)
}
