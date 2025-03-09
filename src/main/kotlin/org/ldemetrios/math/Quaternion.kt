@file:Suppress("unused")

package org.ldemetrios.math

data class Quaternion(val scalar: Double, val vector: Vector3D) {
    operator fun unaryPlus(): Quaternion = this
    operator fun unaryMinus(): Quaternion = Quaternion(-scalar, -vector)

    operator fun plus(other: Quaternion): Quaternion = Quaternion(scalar + other.scalar, vector + other.vector)
    operator fun minus(other: Quaternion): Quaternion = Quaternion(scalar - other.scalar, vector - other.vector)
    operator fun times(other: Quaternion): Quaternion = Quaternion(scalar * other.scalar - vector.dot(other.vector), scalar * other.vector + vector * other.scalar + vector.cross(other.vector))
    val conjugated get(): Quaternion = Quaternion(scalar, -vector)
    val norm get(): Double = scalar * scalar + (vector dot vector)
    val inversed get(): Quaternion = conjugated / norm
    operator fun div(other: Quaternion) = this * other.inversed

    operator fun plus(scalar: Double): Quaternion = Quaternion(this.scalar + scalar, vector)
    operator fun minus(scalar: Double): Quaternion = Quaternion(this.scalar - scalar, vector)
    operator fun times(scalar: Number): Quaternion = Quaternion(this.scalar * scalar.toDouble(), vector * scalar.toDouble())
    operator fun div(scalar: Number): Quaternion = Quaternion(this.scalar / scalar.toDouble(), vector / scalar.toDouble())
    operator fun plus(vector: Vector3D) = Quaternion(scalar, this.vector + vector)
    operator fun minus(vector: Vector3D) = Quaternion(scalar, this.vector - vector)
    operator fun times(vector: Vector3D) = this * Quaternion(0.0, vector)
    operator fun div(vector: Vector3D) = this / Quaternion(0.0, vector)

    val normalized get(): Quaternion = this / norm
}

operator fun Number.plus(other: Quaternion) = Quaternion(this.toDouble() + other.scalar, other.vector)
operator fun Number.minus(other: Quaternion) = Quaternion(this.toDouble() - other.scalar, other.vector)
operator fun Number.times(other: Quaternion) = Quaternion(other.scalar * this.toDouble(), other.vector * this.toDouble())
operator fun Number.div(other: Quaternion) = this * other.inversed

operator fun Vector3D.plus(other: Quaternion) = Quaternion(other.scalar, this + other.vector)
operator fun Vector3D.minus(other: Quaternion) = Quaternion(other.scalar, this - other.vector)
operator fun Vector3D.times(other: Quaternion) = Quaternion(0.0, this) * other
operator fun Vector3D.div(other: Quaternion) = Quaternion(.0, this) * other.inversed

