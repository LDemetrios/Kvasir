@file:Suppress("unused")

package org.ldemetrios.math

class Matrix(val table: List<DoubleArray>) {
    constructor(rows: Int, columns: Int, generator: (Int, Int) -> Number) : this(
        List(rows) { i -> DoubleArray(columns) { j -> generator(i, j).toDouble() } }
    )

    init {
        if (table.map { it.size }.toSet().size != 1) {
            throw IllegalArgumentException("Matrix must have uniform width")
        }
    }

    val rows = table.size
    val columns = table[0].size

    fun get(row: Int, column: Int) = table[row][column]

    fun set(row: Int, column: Int, value: Double) {
        table[row][column] = value
    }

    override fun toString(): String {
        val pad = List(columns) { c -> table.maxOf { it[c].toString().length } }
        return table.joinToString( "\n", "(\n", "\n)",) { row ->
            "\t\t" + row.mapIndexed { i, v ->
                v.toString().padStart(pad[i] + 2)
            }.joinToString(" ")
        }
    }

    operator fun times(other: Matrix): Matrix {
        if (columns != other.rows) {
            throw IllegalArgumentException("Matrix multiplication is not possible")
        }

        return Matrix(rows, other.columns) { i, j ->
            (0 until columns).sumOf { table[i][it] * other.get(it, j) }
        }
    }


    operator fun times(vector: Vector): Vector {
        if (columns != vector.dimension) {
            throw IllegalArgumentException("Matrix multiplication is not possible")
        }

        return Vector(rows) { i ->
            (0 until columns).sumOf { table[i][it] * vector[it] }
        }
    }


    operator fun times(scalar: Double): Matrix {
        return Matrix(rows, columns) { i, j -> table[i][j] * scalar }
    }

    operator fun plus(other: Matrix): Matrix {
        return Matrix(rows, columns) { i, j -> table[i][j] + other.get(i, j) }
    }

    operator fun minus(other: Matrix): Matrix {
        return Matrix(rows, columns) { i, j -> table[i][j] - other.get(i, j) }
    }

    operator fun unaryMinus(): Matrix {
        return Matrix(rows, columns) { i, j -> -table[i][j] }
    }

    fun transpose(): Matrix {
        return Matrix(columns, rows) { i, j -> table[j][i] }
    }

    fun det(): Double {
        if (rows != columns) {
            throw IllegalArgumentException("Determinant can be calculated only for square matrices")
        }
        return det(table)
    }

    //TODO Fix later
    private fun det(table: List<DoubleArray>): Double {
        return if (table.size == 1) {
            table[0][0]
        } else {
            (0 until table.size).sumOf { i ->
                (if (i % 2 == 0) 1 else -1) * table[0][i] * det(table.drop(1).map { (it.take(i) + it.drop(i + 1)).toDoubleArray() })
            }
        }
    }

    operator fun div(scalar: Double): Matrix {
        return Matrix(rows, columns) { i, j -> table[i][j] / scalar }
    }



    companion object {
        fun identity(size: Int): Matrix {
            return Matrix(size, size) { i, j -> if (i == j) 1.0 else 0.0 }
        }

        fun zero(size: Int): Matrix {
            return Matrix(size, size) { _, _ -> 0.0 }
        }
    }
}

fun main(){
    println(
        Matrix(
            listOf(
                doubleArrayOf(11.0, 2.0),
                doubleArrayOf(3.0, 4.0),
                doubleArrayOf(5.0, 6.0)
            )
        )
    )
}
