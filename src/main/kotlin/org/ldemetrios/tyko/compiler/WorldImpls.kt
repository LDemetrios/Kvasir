package org.ldemetrios.tyko.compiler

import org.ldemetrios.tyko.ffi.NativeWorld
import org.ldemetrios.tyko.ffi.TyKoFFIEntity
import org.ldemetrios.tyko.ffi.TypstSharedLibrary
import org.ldemetrios.tyko.model.*

@OptIn(TyKoFFIEntity::class)
class WorldBasedTypstCompiler(val owner: TypstSharedLibrary, world: World) : TypstCompiler {
    private val nativeDelegate = lazy { owner.NativeWorld(world) }

    private val native by nativeDelegate

    override fun evalDetachedRaw(source: String): RResult<String, List<SourceDiagnostic>> {
        return native.evalDetachedRaw(source)
    }

    override fun queryRaw(selector: String, format: SerialFormat): Warned<RResult<String, List<SourceDiagnostic>>> {
        return native.queryRaw(selector, format)
    }

    override fun compileHtmlRaw(): Warned<RResult<String, List<SourceDiagnostic>>> {
        return native.compileHtmlRaw()
    }

    override fun compileSvgRaw(fromPage: Int, toPage: Int): Warned<RResult<List<String>, List<SourceDiagnostic>>> {
        return native.compileSvgRaw(fromPage, toPage)
    }

    override fun compilePngRaw(
        fromPage: Int,
        toPage: Int,
        ppi: Float
    ): Warned<RResult<List<ByteArray>, List<SourceDiagnostic>>> {
        return native.compilePngRaw(fromPage, toPage, ppi)
    }

    override fun close() = if (nativeDelegate.isInitialized()) native.close() else Unit

    override fun reset() = if (nativeDelegate.isInitialized()) native.reset() else Unit
}

open class SingleFileWorld(
    val source: String,
    val features: List<Feature> = listOf(),
    val inputs: TDictionary<TValue> = TEmptyDictionaryImpl,
    val time: WorldTime? = WorldTime.System,
) : World {
    override fun library(): StdlibProvider = object : StdlibProvider.Standard {
        override val inputs: TDictionary<TValue> get() = this@SingleFileWorld.inputs
        override val features: List<Feature> get() = this@SingleFileWorld.features
    }

    override fun mainFile(): FileDescriptor = FileDescriptor(null, "/main.typ")

    override fun file(file: FileDescriptor): RResult<ByteArray, FileError> {
        if (file.pack == null && file.path == "/main.typ") return RResult.Ok(source.encodeToByteArray())
        return RResult.Err(FileError.NotFound(file.path))
    }

    override fun now(): WorldTime? = time

    override val autoManageCentral: Boolean get() = true
}

class DetachedWorld(val features: List<Feature> = listOf()) : World {
    override fun library(): StdlibProvider = object : StdlibProvider.Standard {
        override val inputs: TDictionary<TValue> get() = TDictionary()
        override val features: List<Feature> get() = this@DetachedWorld.features
    }

    override fun mainFile(): FileDescriptor = FileDescriptor(null, "/main.typ")

    override fun file(file: FileDescriptor): RResult<ByteArray, FileError> {
        return RResult.Err(FileError.NotFound(file.path))
    }

    override fun now(): WorldTime? = null

    override val autoManageCentral: Boolean get() = false
}
