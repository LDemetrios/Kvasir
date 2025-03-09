package org.ldemetrios.parsers

private data class StringView(val string: String, val from: Int, val to: Int) : List<Char>, RandomAccess {
    init {
        if (from < 0) throw IndexOutOfBoundsException("from < 0")
        if (to > string.length) throw IndexOutOfBoundsException("to > string.length")
    }

    override val size: Int get() = to - from

    override fun isEmpty(): Boolean = size == 0

    override fun contains(element: Char): Boolean = (from until to).any { string[it] == element }

    override fun containsAll(elements: Collection<Char>): Boolean = elements.all { contains(it) }

    override fun get(index: Int): Char = string[from + index]

    override fun indexOf(element: Char): Int = (from until to).indexOfFirst { string[it] == element } - from

    override fun iterator(): Iterator<Char> = listIterator()

    override fun lastIndexOf(element: Char): Int = (from until to).indexOfLast { string[it] == element } - from

    override fun listIterator(): ListIterator<Char> = listIterator(0)

    override fun listIterator(index: Int): ListIterator<Char> = StringViewIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<Char> {
        if (toIndex > size) throw IndexOutOfBoundsException("toIndex > size")
        return StringView(string, from + fromIndex, from + toIndex)
    }

    private inner class StringViewIterator(var index: Int) : ListIterator<Char> {
        override fun hasNext(): Boolean = index < size
        override fun hasPrevious(): Boolean = index > 0
        override fun next(): Char = get(index++)
        override fun nextIndex(): Int = index
        override fun previous(): Char = get(--index)
        override fun previousIndex(): Int = index - 1
    }

    override fun toString() = string.substring(from, to)
}

fun String.asList(): List<Char> = StringView(this, 0, length)





