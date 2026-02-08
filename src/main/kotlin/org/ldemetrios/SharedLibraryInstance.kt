package org.ldemetrios

import com.dylibso.chicory.wasi.WasiOptions
import com.intellij.ide.plugins.DynamicPlugins
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages
import com.intellij.util.io.delete
import com.sun.jna.Platform
import org.ldemetrios.tyko.driver.chicory.ChicoryTypstCore
import org.ldemetrios.tyko.runtime.TypstRuntime
import org.ldemetrios.utilities.cast
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.locks.ReentrantLock
import kotlin.io.path.*
import kotlin.concurrent.withLock

private fun createRuntime(): TypstRuntime =
    TypstRuntime(
        ChicoryTypstCore(
            WasiOptions.builder()
                .inheritSystem()
                .withEnvironment("RUST_BACKTRACE", "full")
                .withDirectory("/", Paths.get("/"))
                .build()
        )
    )

private val compilerRuntime = createRuntime()

private val compilerRuntimeLock = ReentrantLock()

fun <T> withCompilerRuntime(block: TypstRuntime.() -> T): T = compilerRuntimeLock.withLock {
    compilerRuntime.block()
}

private val frontendRuntimePool = RuntimePool()

fun <T> withFrontendRuntime(block: TypstRuntime.() -> T): T =
    frontendRuntimePool.with(block)

private class RuntimePool {
    private val lock = ReentrantLock()
    private val pool = ArrayDeque<TypstRuntime>()
    private val log = Logger.getInstance("Kvasir.TypstRuntimePool")

    fun take(): TypstRuntime {
        lock.withLock {
            val existing = pool.removeFirstOrNull()
            if (existing != null) {
                return existing
            }
        }
        log.warn("Creating new TypstRuntime for frontend pool.")
        return createRuntime()
    }

    fun release(runtime: TypstRuntime) {
        lock.withLock {
            pool.addLast(runtime)
        }
    }

    fun <T> with(block: TypstRuntime.() -> T): T {
        val runtime = take()
        try {
            return runtime.block()
        } finally {
            release(runtime)
        }
    }
}
