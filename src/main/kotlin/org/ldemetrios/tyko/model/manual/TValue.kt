package org.ldemetrios.tyko.model

sealed interface TValue {
    fun type(): TType
    fun format(): String
}

