@file:Suppress("PackageDirectoryMismatch", "unused")
//@file:JvmName("Delayed")

package org.ldemetrios.utilities

abstract class Delayed<T> {
    private var value: T? = null
    private var initialized = false
    fun get(): T {
        if (!initialized) {
            value = compute()
            initialized = true
        }
        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    abstract fun compute(): T

    override fun toString(): String = if(initialized) {
        "Delayed[$value]"
    } else {
        "Uninitialized Delayed 0x" + Integer.toHexString(this.hashCode())
    }
}

fun <T> delayed(func: () -> T): Delayed<T> = object : Delayed<T>() {
    override fun compute(): T = func()
}