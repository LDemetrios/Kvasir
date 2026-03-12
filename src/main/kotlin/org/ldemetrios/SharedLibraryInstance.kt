package org.ldemetrios

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.jetbrains.rd.util.addUnique
import com.jetbrains.rd.util.first
import org.ldemetrios.tyko.driver.chicory_based.ChicoryTypstCore
import org.ldemetrios.tyko.runtime.TypstRuntime
import java.util.IdentityHashMap
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

//val options = WasiOptions.builder()
//    .inheritSystem()
//    .withEnvironment("RUST_BACKTRACE", "full")
//    .withDirectory("/", Paths.get("/"))
//    .build()

val frontendPool = Pool {
    TypstRuntime { (ChicoryTypstCore()) }
}

class Pool<T : Any>(val creator: () -> T) {
    private val lock = ReentrantLock()
    private val perThread = ThreadLocal.withInitial<IdentityHashMap<T, Unit>> { IdentityHashMap() }
    private val pool = ArrayDeque<T>()

    private val log = Logger.getInstance("Kvasir.TypstRuntimePool")

    fun <R> withResource(canBeSame: Boolean, allowsReentrance: Boolean, block: T.() -> R): R {
        val pt = perThread.get()
        if (canBeSame && pt.isNotEmpty()) {
            val resource = pt.first().key
            if (!allowsReentrance) pt.remove(resource)
            try {
                return resource.block()
            } finally {
                if (!allowsReentrance) pt.put(resource, Unit)
            }
        } else {
            val resource = takeFromPoolOrNull()
            if (resource == null) {
                val another = warninglyCreate()
                if (allowsReentrance) pt.put(another, Unit)
                try {
                    return another.block()
                } finally {
                    if (allowsReentrance) pt.remove(another)
                    returnToPool(another)
                }
            } else {
                if (allowsReentrance) pt.put(resource, Unit)
                try {
                    return resource.block()
                } finally {
                    if (allowsReentrance) pt.remove(resource)
                    returnToPool(resource)
                }
            }
        }
    }

    private fun takeFromPoolOrNull() = lock.withLock { pool.removeFirstOrNull() }
    private fun returnToPool(it: T) = lock.withLock { pool.addLast(it) }

    private fun warninglyCreate(): T {
        log.warn("Creating new TypstRuntime for frontend pool.")
        return creator()
    }
}
