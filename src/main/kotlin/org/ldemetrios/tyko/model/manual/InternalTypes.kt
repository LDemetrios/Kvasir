package org.ldemetrios.tyko.model

import kotlin.reflect.KClass
import kotlin.text.get

internal sealed interface InternalType {
    fun optionOf(ident: String): ConcreteType?
}

internal data object AnyType : InternalType {
    override fun optionOf(ident: String): ConcreteType = ConcreteType(
        ident,
        List(genericTypes[ident] ?: 0) { AnyType }
    )

    override fun toString(): String = "any"
}

internal data class ConcreteType(val ident: String, val params: List<InternalType>? = null) : InternalType {
    override fun toString(): String = StringBuilder().apply {
        append(ident)
        if (!params.isNullOrEmpty()) {
            append("<")
            append(params.joinToString(", "))
            append(">")
        }
    }.toString()

    override fun optionOf(ident: String): ConcreteType? = if (ident == this.ident) this else null
}

internal fun UnionType(vararg options: ConcreteType) = UnionType(options.toList())

internal class UnionType(val options: List<ConcreteType>) : InternalType {
    override fun optionOf(ident: String): ConcreteType? = options.find { it.ident == ident }
    override fun toString(): String = options.joinToString("|")
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class TUnionType(val options: Array<String>)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class TSyntheticType(val options: Array<String>)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class TEnumType(val options: Array<String>)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class TSetRuleType(val owner: String, val impl : KClass<*>)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class TInterfaceType(val name: String, val chain: Array<String>, val impl: KClass<*>)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class TSerialName(val name: String)

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class TVararg

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class TContentBody()

@Target(AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
internal annotation class TypstOverloads(
    val generateFullyKotlin: Boolean = true,
    val generateFullyTypst: Boolean = true,
    val exclude: IntArray = [],
)
