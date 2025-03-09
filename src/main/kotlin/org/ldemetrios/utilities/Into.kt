@file:Suppress("unused")

package org.ldemetrios.utilities

inline fun <reified T> Int.into(): T {
    return when (T::class) {
        String::class -> this.toString()
        Int::class -> this
        Long::class.java -> this.toLong()
        Float::class.java -> this.toFloat()
        Double::class.java -> this.toDouble()
        Boolean::class.java -> this != 0
        else -> throw IllegalArgumentException("Unsupported type")
    } as T
}

inline fun <reified T> String.into(): T {
    return when (T::class) {
        String::class -> this
        Int::class -> this.toInt()
        Long::class.java -> this.toLong()
        Float::class.java -> this.toFloat()
        Double::class.java -> this.toDouble()
        Boolean::class.java -> this != ""
        else -> throw IllegalArgumentException("Unsupported type")
    } as T
}

inline fun <reified T> Boolean.into(): T {
    return when (T::class) {
        String::class -> this.toString()
        Int::class -> if (this) 1 else 0
        Long::class.java -> if (this) 1L else 0L
        Float::class.java -> if (this) 1f else 0f
        Double::class.java -> if (this) 1.0 else 0.0
        Boolean::class.java -> this
        else -> throw IllegalArgumentException("Unsupported type")
    } as T
}

inline fun <reified T> Long.into(): T {
    return when (T::class) {
        String::class -> this.toString()
        Int::class -> this.toInt()
        Long::class.java -> this
        Float::class.java -> this.toFloat()
        Double::class.java -> this.toDouble()
        Boolean::class.java -> this != 0L
        else -> throw IllegalArgumentException("Unsupported type")
    } as T
}

inline fun <reified T> Float.into(): T {
    return when (T::class) {
        String::class -> this.toString()
        Int::class -> this.toInt()
        Long::class.java -> this.toLong()
        Float::class.java -> this
        Double::class.java -> this.toDouble()
        Boolean::class.java -> this != 0f
        else -> throw IllegalArgumentException("Unsupported type")
    } as T
}

inline fun <reified T> Double.into(): T {
    return when (T::class) {
        String::class -> this.toString()
        Int::class -> this.toInt()
        Long::class.java -> this.toLong()
        Float::class.java -> this.toFloat()
        Double::class.java -> this
        Boolean::class.java -> this != 0.0
        else -> throw IllegalArgumentException("Unsupported type")
    } as T
}

inline fun <reified T> Any.into(): T {
    return when (T::class) {
        String::class -> this.toString()
        Int::class -> this.toString().toInt()
        Long::class.java -> this.toString().toLong()
        Float::class.java -> this.toString().toFloat()
        Double::class.java -> this.toString().toDouble()
        Boolean::class.java -> this.toString() != ""
        else -> throw IllegalArgumentException("Unsupported type")
    } as T
}
