package org.ldemetrios

import com.dylibso.chicory.wasi.WasiOptions
import com.dylibso.chicory.wasm.Parser
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import org.ldemetrios.tyko.driver.chicory.ChicoryTypstCore
import org.ldemetrios.tyko.runtime.TypstRuntime
import java.nio.file.Paths
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

val options = WasiOptions.builder()
    .inheritSystem()
    .withEnvironment("RUST_BACKTRACE", "full")
    .withDirectory("/", Paths.get("/"))
    .build()


private val parserModule = Parser.parse(
    RuntimePool::class.java.classLoader.getResourceAsStream("typst-syntax-only.wasm")!!
)

private val formatterModule = Parser.parse(
    RuntimePool::class.java.classLoader.getResourceAsStream("typst-formatter-only.wasm")!!
)

val parserRuntimePool = RuntimePool {
    TypstRuntime(ChicoryTypstCore(parserModule, options))
}

val formatterRuntimePool = RuntimePool {
    TypstRuntime(ChicoryTypstCore(formatterModule, options))
}

class RuntimePool(val creator: () -> TypstRuntime) {
    private val lock = ReentrantLock()
    private val pool = ArrayDeque<TypstRuntime>()
    private val log = Logger.getInstance("Kvasir.TypstRuntimePool")
    private var creationScheduled = false

    fun take(): TypstRuntime {
        lock.withLock {
            val existing = pool.removeFirstOrNull()
            if (existing != null) {
                return existing
            }
        }
        log.warn("Creating new TypstRuntime for frontend pool.")
        return creator()
    }

    fun takeOrSchedule(): TypstRuntime? {
        lock.withLock {
            val existing = pool.removeFirstOrNull()
            if (existing != null) {
                return existing
            }
            if (!creationScheduled) {
                creationScheduled = true
                scheduleCreation()
            }
        }
        return null
    }

    private fun scheduleCreation() {
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                lock.withLock {
                    if (pool.isNotEmpty()) {
                        return@executeOnPooledThread
                    }
                }
                val runtime = creator()
                lock.withLock {
                    pool.addLast(runtime)
                }
            } catch (e: Throwable) {
                log.warn("Failed to create TypstRuntime for frontend pool.", e)
            } finally {
                lock.withLock {
                    creationScheduled = false
                }
            }
        }
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
