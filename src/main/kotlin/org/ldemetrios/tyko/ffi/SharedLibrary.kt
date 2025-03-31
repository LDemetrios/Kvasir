package org.ldemetrios.tyko.ffi

import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

@Suppress("LocalVariableName", "FunctionName")
interface TypstSharedLibrary : Library {
    @TyKoFFIEntity
    fun free_library(ptr: Pointer)

    @TyKoFFIEntity
    fun free_world(ptr: Pointer)

    @TyKoFFIEntity
    fun free_str(ptr: Pointer)

    @TyKoFFIEntity
    fun free_thick_byte_ptr(ptr: ThickBytePtr)

    @TyKoFFIEntity
    fun set_freer(f: UnpinnerCallback): Int

    @TyKoFFIEntity
    fun new_world(
        library: Pointer/* *mut Library*/,
        main_callback: MainCallback,
        file_callback: FileCallback,
        now: JavaResult/*<Option<Now>>*/,
        auto_load_central: Int, // 1 -- true, 0 -- false
    ): JavaExceptPtrResult/*<JavaWorld>*/

    @TyKoFFIEntity
    fun create_stdlib(features: Int, inputs_thick: ThickBytePtr): Pointer/* *mut Library */

    @TyKoFFIEntity
    fun detached_eval(java_world: /* *mut JavaWorld */ Pointer, source_ptr: ThickBytePtr): JavaResult/*<Result<String, Vec<ExtendedSourceDiagnostic>>>*/

    @TyKoFFIEntity
    fun query(world_ptr: Pointer, selector_thick: ThickBytePtr, fmt_type: Int): JavaResult/*<Warned<Result<String, Vec<ExtendedSourceDiagnostic>>>>*/

    @TyKoFFIEntity
    fun compile_html(world_ptr: /* *mut JavaWorld */ Pointer): JavaResult // <Warned<Result<String, Vec<ExtendedSourceDiagnostic>>>>

    @TyKoFFIEntity
    fun compile_svg(world_ptr: /* *mut JavaWorld */ Pointer, from: Int, to: Int): JavaResult // <Warned<Result<List<String>, Vec<ExtendedSourceDiagnostic>>>>

    @TyKoFFIEntity
    fun compile_png(world_ptr: /* *mut JavaWorld */ Pointer, from: Int, to: Int, ppi: Float): JavaResult // <Warned<Result<List<Base16ByteArray>, Vec<ExtendedSourceDiagnostic>>>>

    @TyKoFFIEntity
    fun reset_world(pointer: Pointer)

    @TyKoFFIEntity
    fun format_source(content: ThickBytePtr, column: Int, tab_width: Int) : ThickBytePtr

    companion object {
//        @OptIn(TyKoFFIEntity::class)
//        val INSTANCE = loadLibrary<TypstSharedLibrary>(SHARED_LIBRARY_PATH).also {
//            val freer = UnpinnerCallback { unpin(it) }
//            freer.pinGlobally()
//            val set = it.set_freer(freer)
//            if (set != 1) {
//                throw IllegalStateException("Failed to set freer")
//            }
//        }

        private val CACHE = ConcurrentHashMap<Path, TypstSharedLibrary>()

        @OptIn(TyKoFFIEntity::class)
        fun instance(path: Path) = CACHE.getOrPut(path.toAbsolutePath()) {
            loadLibrary<TypstSharedLibrary>(path.toString()).also {
                val set = it.set_freer(FREER)
                if (set != 1) {
                    throw IllegalStateException("Failed to set freer")
                }
            }
        }

        fun anyInstance(): TypstSharedLibrary = CACHE.iterator().next().value
    }

    @TyKoFFIEntity
    fun parse_syntax(string: ThickBytePtr, mode: Int): CFlattenedSyntaxTree

    @TyKoFFIEntity
    fun release_flattened_tree(tree: CFlattenedSyntaxTree)

    fun evict_cache(max_age: Long)
}

@TyKoFFIEntity
val FREER = UnpinnerCallback { unpin(it) }

internal inline fun <reified T : Library> loadLibrary(path: String): T = Native.load(path, T::class.java)

@TyKoFFIEntity
fun interface MainCallback : Callback {
    fun callback(): JavaResult/*<ExtendedFileDescriptor>*/
}

@TyKoFFIEntity
fun interface FileCallback : Callback {
    fun callback(descr: ThickBytePtr): JavaResult/*<ExtendedFileResult<Base16ByteArray>>*/
}

@TyKoFFIEntity
fun interface UnpinnerCallback : Callback {
    fun callback(ticket: Long)
}