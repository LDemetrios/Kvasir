package org.ldemetrios.tyko


import org.ldemetrios.tyko.compiler.DetachedWorld
import org.ldemetrios.tyko.compiler.WorldBasedTypstCompiler
import org.ldemetrios.tyko.compiler.evalDetached
import org.ldemetrios.tyko.ffi.TypstSharedLibrary
import kotlin.io.path.Path

// They exist...

public const val SHARED_LIBRARY_PATH = "/home/ldemetrios/Workspace/TypstNKotlinInterop/libtypst_shared.so"

val sharedLib = TypstSharedLibrary.instance(Path(SHARED_LIBRARY_PATH))

fun main() {
    val world = WorldBasedTypstCompiler(sharedLib, DetachedWorld())
    val lim = 1
    for (i in 0 until lim) {
        world.evalDetached("\"1 + 2\"")
    }
}