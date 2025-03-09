@file:Suppress("unused")

package org.ldemetrios.utilities

interface WindowList<out T> : List<T> {
    override fun subList(fromIndex: Int, toIndex: Int): WindowList<T>
    fun subList(fromIndex: Int): WindowList<T> = subList(fromIndex, size)


}

abstract class WindowListImpl<out E>(
    protected val fromIndex: Int = 0, /*protected val*/
    toIndex: Int,
    internalSize: Int
) :
    WindowList<E>, AbstractList<E>() {
    private var _size: Int = 0

    init {
        if (fromIndex < 0 || toIndex > internalSize) {
            throw IndexOutOfBoundsException("fromIndex: $fromIndex, toIndex: $toIndex, size: $internalSize")
        }
        if (fromIndex > toIndex) {
            throw IllegalArgumentException("fromIndex: $fromIndex > toIndex: $toIndex")
        }
        this._size = toIndex - fromIndex
    }

    override val size: Int get() = _size

    override fun get(index: Int): E {
        if (index < 0 || index >= _size) {
            throw IndexOutOfBoundsException("index: $index, size: $_size")
        }

        return internalGet(fromIndex + index)
    }

    protected abstract fun internalGet(index: Int): E

    companion object {
        fun checkRangeIndices(fromIndex: Int, toIndex: Int, size: Int) {
            if (fromIndex < 0 || toIndex > size) {
                throw IndexOutOfBoundsException("fromIndex: $fromIndex, toIndex: $toIndex, size: $size")
            }
            if (fromIndex > toIndex) {
                throw IllegalArgumentException("fromIndex: $fromIndex > toIndex: $toIndex")
            }
        }
    }

    abstract override fun subList(fromIndex: Int, toIndex: Int): WindowList<E>
}

class WindowListListImpl<out E>(private val list: List<E>, fromIndex: Int = 0, toIndex: Int = list.size) :
    WindowListImpl<E>(fromIndex, toIndex, list.size) {
    override fun internalGet(index: Int): E = list[index]

    override fun subList(fromIndex: Int, toIndex: Int): WindowList<E> {
        checkRangeIndices(fromIndex, toIndex, size)
        return WindowListListImpl(list, this.fromIndex + fromIndex, this.fromIndex + toIndex)
    }
}

class WindowListStringImpl(val string: String, fromIndex: Int = 0, toIndex: Int = string.length) :
    WindowListImpl<Char>(fromIndex, toIndex, string.length) {
    override fun internalGet(index: Int): Char = string[index]

    override fun subList(fromIndex: Int, toIndex: Int): WindowList<Char> {
        checkRangeIndices(fromIndex, toIndex, size)
        return WindowListStringImpl(string, this.fromIndex + fromIndex, this.fromIndex + toIndex)
    }

    override fun toString(): String = string.substring(fromIndex, fromIndex + size)
}

fun <T> WindowList(list: List<T>): WindowList<T> = WindowListListImpl<T>(list)
fun WindowList(list: String): WindowList<Char> = WindowListStringImpl(list)
