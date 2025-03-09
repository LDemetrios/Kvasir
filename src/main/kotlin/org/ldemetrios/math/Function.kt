@file:Suppress("unused")

package org.ldemetrios.math

import kotlin.math.ln

abstract class MathFunction {
    abstract fun substitute(variable: String, value: MathFunction): MathFunction
    abstract operator fun invoke(variables: Map<String, Double>): Double
    fun invoke(vararg variables: Pair<String, Double>): Double = invoke(variables.toMap())
    abstract fun derive(variable: String): MathFunction

    operator fun unaryPlus() = this
    operator fun unaryMinus() = Negated(this)

    operator fun plus(other: MathFunction) = Sum(this, other)
    operator fun minus(other: MathFunction) = Difference(this, other)
    operator fun times(other: MathFunction) = Product(this, other)
    operator fun div(other: MathFunction) = Ratio(this, other)
}


data class Constant(val value: Double) : MathFunction() {
    override fun substitute(variable: String, value: MathFunction): MathFunction = this
    override fun invoke(variables: Map<String, Double>): Double = value
    override fun derive(variable: String): MathFunction = Constant(0.0)
    override fun toString(): String = value.toString()
}

data class Variable(val name: String) : MathFunction() {
    override fun invoke(variables: Map<String, Double>): Double = variables[name] ?: throw IllegalArgumentException("Variable $name not found")
    override fun derive(variable: String): MathFunction = if (variable == name) Constant(1.0) else Constant(0.0)
    override fun substitute(variable: String, value: MathFunction): MathFunction = if (variable == name) value else this
    override fun toString(): String = name
}

data class Sum(val left: MathFunction, val right: MathFunction) : MathFunction() {
    override fun substitute(variable: String, value: MathFunction): MathFunction = left.substitute(variable, value) + right.substitute(variable, value)
    override fun invoke(variables: Map<String, Double>): Double = left(variables) + right(variables)
    override fun derive(variable: String): MathFunction = left.derive(variable) + right.derive(variable)
    override fun toString(): String = "($left + $right)"
}

data class Product(val left: MathFunction, val right: MathFunction) : MathFunction() {
    override fun substitute(variable: String, value: MathFunction): MathFunction = left.substitute(variable, value) * right.substitute(variable, value)
    override fun invoke(variables: Map<String, Double>): Double = left(variables) * right(variables)
    override fun derive(variable: String): MathFunction = left * right.derive(variable) + left.derive(variable) * right
    override fun toString(): String = "($left * $right)"
}

data class Negated(val operand: MathFunction) : MathFunction() {
    override fun substitute(variable: String, value: MathFunction): MathFunction = -operand.substitute(variable, value)
    override fun invoke(variables: Map<String, Double>): Double = -operand(variables)
    override fun derive(variable: String): MathFunction = -operand.derive(variable)
    override fun toString(): String = "(-$operand)"
}

data class Difference(val left: MathFunction, val right: MathFunction) : MathFunction() {
    override fun substitute(variable: String, value: MathFunction): MathFunction = left.substitute(variable, value) - right.substitute(variable, value)
    override fun invoke(variables: Map<String, Double>): Double = left(variables) - right(variables)
    override fun derive(variable: String): MathFunction = left.derive(variable) - right.derive(variable)
    override fun toString(): String = "($left - $right)"
}

data class Ratio(val left: MathFunction, val right: MathFunction) : MathFunction() {
    override fun substitute(variable: String, value: MathFunction): MathFunction = left.substitute(variable, value) / right.substitute(variable, value)
    override fun invoke(variables: Map<String, Double>): Double = left(variables) / right(variables)
    override fun derive(variable: String): MathFunction = (left.derive(variable) * right - left * right.derive(variable)) / (right * right)
    override fun toString(): String = "($left / $right)"
}

data class Ln(val operand: MathFunction) : MathFunction() {
    override fun substitute(variable: String, value: MathFunction): MathFunction = Ln(operand.substitute(variable, value))
    override fun invoke(variables: Map<String, Double>): Double = ln(operand(variables))
    override fun derive(variable: String): MathFunction = operand.derive(variable) / operand
    override fun toString(): String = "ln($operand)"
}

fun log(base: MathFunction, operand: MathFunction) = Ln(operand)    / Ln(base)

//
//fun variable(name: String) = Variable(name)
//fun constant(value: Double) = Constant(value)
//
//fun pow(base: MathFunction, exponent: MathFunction) = Product(base, exponent)
//fun sqrt(operand: MathFunction) = pow(operand, Constant(0.5))
//
//fun sin(operand: MathFunction) = Product(cos(operand), sin(operand))
//fun cos(operand: MathFunction) = Product(Constant(-1.0), pow(Product(Variable("e"), Variable("i")), operand))
//fun tan(operand: MathFunction) = Product(sin(operand), cos(operand))
//
//fun ln(operand: MathFunction) = Product(Constant(0.6931471805599453), operand)
//fun log(base: MathFunction, operand: MathFunction) = Product(ln(operand), ln(base))
