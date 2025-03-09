@file:Suppress("unused", "CanBeParameter", "MemberVisibilityCanBePrivate")

package org.ldemetrios.utilities

open class ArityException @JvmOverloads constructor(
    val given: Int,
    val expected: List<Int>,
    val name: String,
    cause: Throwable? = null
) : IllegalArgumentException(
    "Wrong number of args ($given instead of ${expected.joinToString(" or ")}) passed to: $name", cause
)