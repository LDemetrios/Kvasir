package org.ldemetrios.tyko.model.manual

import org.ldemetrios.tyko.model.TDynamic
import org.ldemetrios.tyko.model.TElement
import org.ldemetrios.tyko.model.TType

data class TDelayedExecution(val repr: String) : TDynamic {
    override val elem: String get() = throw UnsupportedOperationException()
    override val size: Int get() = throw UnsupportedOperationException()

    override fun format(): String = repr

    override fun func(): TElement = TDelayedExecution("($repr).func()")

    override fun fieldAccess(field: String): TDynamic = TDelayedExecution("($repr).$field")


    override fun type(): TType = TDelayedExecution("type($repr)")

    override fun isEmpty(): Boolean = throw UnsupportedOperationException()

    override fun get(index: Int): TDynamic = TDelayedExecution("($repr).at($index)")

    override val strValue: String get() = throw UnsupportedOperationException()
    override val boolValue: Boolean get() = throw UnsupportedOperationException()
    override val intValue: Long get() = throw UnsupportedOperationException()
    override val floatValue: Double get() = throw UnsupportedOperationException()
    override val arrayValue: List<TDynamic> get() = throw UnsupportedOperationException()
    override val bytesValue: ByteArray get() = throw UnsupportedOperationException()
}