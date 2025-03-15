package org.ldemetrios.tyko.ffi

import com.sun.jna.Memory
import com.sun.jna.Pointer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.ldemetrios.tyko.compiler.*
import org.ldemetrios.tyko.model.repr
import java.io.Closeable
import java.time.Instant

@Serializable
internal sealed interface Now {
    @Serializable
    @SerialName("Fixed")
    data class Fixed(val millis: Long, val nanos: Int) : Now

    @Serializable
    @SerialName("System")
    data object System : Now
}

internal fun Now.toWorldTime() = when (this) {
    is Now.Fixed -> WorldTime.Fixed(Instant.ofEpochSecond(millis / 1000, (millis % 1000) * 1000000 + nanos.toLong()))
    is Now.System -> WorldTime.System
}

internal fun WorldTime.toNow(): Now = when (this) {
    is WorldTime.Fixed -> Now.Fixed(time.epochSecond * 1000 + time.nano / 1000000, time.nano % 1000000)
    is WorldTime.System -> Now.System
}

@Serializable(with = Base16ByteArraySerializer::class)
data class Base16ByteArray(val arr: ByteArray) {
    override fun equals(other: Any?): Boolean {
        return (this === other) || (javaClass == other?.javaClass) && arr.contentEquals((other as Base16ByteArray).arr)
    }

    override fun hashCode(): Int {
        return arr.contentHashCode()
    }
}

object Base16ByteArraySerializer : KSerializer<Base16ByteArray> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Base16ByteArray", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Base16ByteArray) {
        val hexString = value.arr.joinToString("") { "%02x".format(it) }
        encoder.encodeString(hexString)
    }

    override fun deserialize(decoder: Decoder): Base16ByteArray {
        val hexString = decoder.decodeString()
        val byteArray = hexString.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        return Base16ByteArray(byteArray)
    }
}


@TyKoFFIEntity
internal fun TypstSharedLibrary.nativeLibrary(library: StdlibProvider): Pointer = when (library) {
    is StdlibProvider.Custom -> throw UnsupportedOperationException("Custom library is not yet supported")
    is StdlibProvider.Standard -> {
        val features = library.features.toSet().map { it.ordinal }.sumOf { 1 shl it }
        val inputs = library.inputs.repr()
        val thickBytePtr = ThickBytePtr.fromString(inputs)
        val stdlib = create_stdlib(features, thickBytePtr)
        thickBytePtr.close()
        stdlib
    }
}

private val json = Json {
    encodeDefaults = false
    classDiscriminator = "type"
}

@TyKoFFIEntity
fun TypstSharedLibrary.NativeWorld(world: World): NativeWorld {
    val library = world.library()

    val stdlib = UniquePtr(nativeLibrary(library)) { free_library(it) }
    try {
        val mainCallback = MainCallback {
            val file = world.mainFile()
            val str = json.encodeToString(file)
            JavaResult.fromString(str)
        }
        val fileCallback = FileCallback { file ->
            val descriptor = json.decodeFromString<FileDescriptor>(file.readString())
            val result = json.encodeToString(world.file(descriptor).map { Base16ByteArray(it) })
            JavaResult.fromString(result)
        }
        val now = world.now()?.toNow()
        val str = now?.let { json.encodeToString(it) } ?: "null"
        val now_jr = JavaResult.fromString(str)
        val exceptWorld =
            new_world(stdlib.release()!!, mainCallback, fileCallback, now_jr, if (world.autoManageCentral) 1 else 0)
        (now_jr.value!!.ptr!! as Memory).close()
        if (exceptWorld.ptr == null) {
            val comment = exceptWorld.comment!!.readString()
            free_thick_byte_ptr(exceptWorld.comment!!)
            TODO(comment)
        } else {
            val c = UniquePtr(exceptWorld.ptr!!) { free_world(it) }
            val d = NativeWorld(this, world, c, mainCallback, fileCallback)
            return d
        }
    } finally {
        stdlib.tryClose()
    }
}

@TyKoFFIEntity
class NativeWorld(
    val owner: TypstSharedLibrary,
    val delegate: World,
    val ptr: UniquePtr,
    private val mainTicket: MainCallback,
    private val fileTicket: FileCallback
) : Closeable {
    override fun close() {
        ptr.close()
    }

    fun evalDetachedRaw(source: String): RResult<String, List<SourceDiagnostic>> {
        val thick_result = this@NativeWorld.ptr.map {
            val thick_source = ThickBytePtr.fromString(source)
            val res = owner.detached_eval(it, thick_source)
            // drop thick_source
            (thick_source.ptr!! as Memory).close()
            res
        }.value!!
        // Result<String, Vec<ExtendedSourceDiagnostic>>
        val result = thick_result.readString()
        owner.free_thick_byte_ptr(thick_result)

        val decoded = json.decodeFromString<RResult<String, List<SourceDiagnostic>>>(result)

//        val result = UniquePtr(ptr, owner::free_eval_result).use {
//            it.map { ptr -> owner.obtainResult(delegate, ptr, source).map { it.map { it.decodeToString() } } }
//        }

        assertNoLocalPins()
        return decoded
    }

    fun queryRaw(string: String, format: SerialFormat): Warned<RResult<String, List<SourceDiagnostic>>> {
        /*
         world_ptr: *mut JavaWorld,
         selector_thick: ThickBytePtr,
         fmt_type: i32,
         */
        val selector_thick = ThickBytePtr.fromString(string)
        return try {
            val result = ptr.map {
                owner.query(it, selector_thick, format.ordinal)
            }.value!! // <Warned<Result<String, EcoVec<SourceDiagnostic>>>>
            val result_str = result.readString()
            owner.free_thick_byte_ptr(result)
            json.decodeFromString<Warned<RResult<String, List<SourceDiagnostic>>>>(result_str)
        } finally {
            selector_thick.close()
        }.also {
            assertNoLocalPins()
        }
    }

    fun compileHtmlRaw(): Warned<RResult<String, List<SourceDiagnostic>>> {
        val result = ptr.map { owner.compile_html(it) }.value!! // <Warned<Result<String, EcoVec<SourceDiagnostic>>>>
        val result_str = result.readString()
        owner.free_thick_byte_ptr(result)
        return json.decodeFromString<Warned<RResult<String, List<SourceDiagnostic>>>>(result_str).also {
            assertNoLocalPins()
        }
    }

    fun compileSvgRaw(fromPage: Int, toPage: Int): Warned<RResult<List<String>, List<SourceDiagnostic>>> {
        val result = ptr.map {
            owner.compile_svg(
                it,
                fromPage,
                toPage
            )
        }.value!! // <Warned<Result<String, EcoVec<SourceDiagnostic>>>>
        val result_str = result.readString()
        owner.free_thick_byte_ptr(result)
        return json.decodeFromString<Warned<RResult<List<String>, List<SourceDiagnostic>>>>(result_str).also {
            assertNoLocalPins()
        }
    }

    fun compilePngRaw(
        fromPage: Int,
        toPage: Int,
        ppi: Float
    ): Warned<RResult<List<ByteArray>, List<SourceDiagnostic>>> {
        val result = ptr.map {
            owner.compile_png(
                it,
                fromPage,
                toPage,
                ppi
            )
        }.value!! // <Warned<Result<String, EcoVec<SourceDiagnostic>>>>
        val result_str = result.readString()
        owner.free_thick_byte_ptr(result)
        return json.decodeFromString<Warned<RResult<List<Base16ByteArray>, List<SourceDiagnostic>>>>(result_str).also {
            assertNoLocalPins()
        }.map { it.map { it.map { it.arr } } }
    }

    fun reset() {
        ptr.map { owner.reset_world(it) }
    }
}


/*
    Desired endpoints:

    evalDetached(what: String) -> TValue

    loadCentralPackage(what: PackageSpec) -> PackageResult<Bytes>

    compileRaw(
        ppi = 144
        format : PDF|SVG|PNG|HTML
    ) -> Warnings, (Errors|Bytes)

    queryRaw(
        selector: String,
        format : Yaml|Json|JsonPretty
    ) -> Warnings, (Errors|String)

 */