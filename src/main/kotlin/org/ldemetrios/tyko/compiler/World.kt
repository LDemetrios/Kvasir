package org.ldemetrios.tyko.compiler

import org.ldemetrios.tyko.model.TDictionary
import org.ldemetrios.tyko.model.TValue
import java.time.Instant

sealed interface StdlibProvider {
    val features: List<Feature>

//    interface Custom : StdlibProvider {
//        // TODO After refining what exactly module is
////    val global: TModule
////    val math: TModule
////    val styles: List<TStyle>
////    val std: Binding
//    }

    interface Standard : StdlibProvider {
        val inputs: TDictionary<TValue>
    }
}

enum class Feature {
    Html,
    ;

    companion object {
        val ALL = entries.toList()
    }
}

interface World {
    fun library(): StdlibProvider

//    fun fontBook(): FontBookProvider

    fun mainFile(): FileDescriptor

    fun file(file: FileDescriptor): RResult<ByteArray, FileError>

//    fun font(index: Int): Font?

    fun now(): WorldTime?

    val autoManageCentral: Boolean
}

sealed interface WorldTime {
    data class Fixed(val time: Instant) : WorldTime
    data object System : WorldTime
}


