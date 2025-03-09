@file:Suppress("unused")

package org.ldemetrios.utilities

data class Named<T>(val name: String, val value: T) {
    override fun toString(): String = name
}

fun <T> T.withName(name:String) = Named(name, this)

internal fun <T, R> named(name:String, func:(T) -> R) = object : (T) -> R{
    override fun invoke(p1: T): R = func(p1)
    override fun toString(): String = name
}
