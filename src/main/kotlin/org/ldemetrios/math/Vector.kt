@file:Suppress("unused")

package org.ldemetrios.math

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vector3D(val x: Double, val y: Double, val z: Double) {
    constructor(x: Number, y: Number, z: Number) : this(x.toDouble(), y.toDouble(), z.toDouble())
    constructor(list: List<Double>) : this(list.getOrElse(0) { .0 }, list.getOrElse(1) { .0 }, list.getOrElse(2) { .0 })
    constructor(vararg list: Double) : this(list.toList())

    operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)

    operator fun times(scalar: Number) = Vector3D(x * scalar.toDouble(), y * scalar.toDouble(), z * scalar.toDouble())

    operator fun div(scalar: Number) = Vector3D(x / scalar.toDouble(), y / scalar.toDouble(), z / scalar.toDouble())

    operator fun unaryPlus() = Vector3D(x, y, z)

    operator fun unaryMinus() = Vector3D(-x, -y, -z)

    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IndexOutOfBoundsException()
    }

    infix fun dot(other: Vector3D) = x * other.x + y * other.y + z * other.z

    infix fun x(other: Vector3D) = cross(other)
    fun cross(other: Vector3D) = Vector3D(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    val magnitude get() = Math.sqrt(dot(this))

    val normalized get() = this / magnitude

    fun distance(other: Vector3D) = (other - this).magnitude

    fun angle(other: Vector3D) = acos(dot(other) / (magnitude * other.magnitude))

    fun rotate(axis: Vector3D, angle: Double) = this * cos(angle) + axis.cross(this) * sin(angle) + axis * axis.dot(this) * (1 - cos(angle))

    fun reflect(normal: Vector3D) = this - 2 * normal * normal.dot(this)

    fun project(normal: Vector3D) = normal * normal.dot(this)

    fun refract(normal: Vector3D, refractionIndex: Double) = this - normal * normal.dot(this) * 2 - normal * sqrt(1 - refractionIndex * refractionIndex * (1 - normal.dot(this) * normal.dot(this)))
}

operator fun Number.times(v: Vector3D) = v * this

data class Vector(val list: List<Double>) {
    constructor(vararg list: Double) : this(list.toList())
    constructor(n : Int, gen:(Int) -> Double) : this(List(n, gen))

    operator fun plus(other: Vector) = Vector(list.zip(other.list).map { it.first + it.second })

    operator fun minus(other: Vector) = Vector(list.zip(other.list).map { it.first - it.second })

    operator fun times(scalar: Number) = Vector(list.map { it * scalar.toDouble() })

    operator fun div(scalar: Number) = Vector(list.map { it / scalar.toDouble() })

    operator fun unaryPlus() = Vector(list)

    operator fun unaryMinus() = Vector(list.map { -it })

    operator fun get(index: Int) = list[index]

    infix fun dot(other: Vector) = list.zip(other.list).sumOf { it.first * it.second }

    val dimension get() = list.size
}

operator fun Number.times(v: Vector) = v * this
