package org.ldemetrios.tyko.ffi

import com.sun.jna.Memory
import com.sun.jna.Pointer
import java.io.Closeable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.getOrSet

// Handling Java memory handed to Native

private val registry: ConcurrentHashMap<Long, Any> = ConcurrentHashMap()

private val threadLocalRegistry: ThreadLocal<MutableSet<Long>> = ThreadLocal()

private val counter = AtomicLong(1)

@TyKoFFIEntity
fun <T : Any> T.alsoPin() = also(::pin)

@TyKoFFIEntity
fun <T : Any> T.pinGlobally(): Long {
    val c = counter.andIncrement
    if (c < 0) throw AssertionError("Ran out of tickets")
    registry.put(c, this)
    return c
}

@TyKoFFIEntity
fun pin(obj: Any): Long {
    val c = counter.andIncrement
    if (c < 0) throw AssertionError("Ran out of tickets")
    registry.put(c, obj)
    threadLocalRegistry.getOrSet { mutableSetOf<Long>() }.add(c)
    return c
}

@TyKoFFIEntity
fun unpin(ticket: Long): Any? {
    if (ticket == 0L) return null
    val was = registry.remove(ticket)
        ?: throw IllegalArgumentException("Ticket $ticket doesn't correspond to any pinned object")
    threadLocalRegistry.get()?.remove(ticket)
    return was
}

@TyKoFFIEntity
fun unpinAllLocal() {
    val local = threadLocalRegistry.get() ?: return
    local.forEach {
        val value = registry.remove(it)
        if (value is Memory) value.close()
    }
    local.clear()
}

@TyKoFFIEntity
fun assertNoLocalPins() {
    val local = threadLocalRegistry.get() ?: return
    if (local.isNotEmpty()) throw AssertionError("Local pins are not empty: $local (${local.map { registry.get(it) }})")
}

// Handling Native memory handed to Java

@TyKoFFIEntity
data class UniquePtr internal constructor(
    private val ptr: AtomicReference<Pointer?>,
    private val destructor: (Pointer) -> Unit
) : Closeable {
    private val name = ptr.get().toString()

    override fun close() {
        val was = ptr.getAndSet(null)
        if (was == null) throw IllegalStateException("Parallel or repeated closing $name")
        destructor(was)
    }

    fun tryClose() {
        val was = ptr.getAndSet(null) ?: return
        destructor(was)
    }

    fun release() = ptr.getAndSet(null)

    fun <T> map(func: (Pointer) -> T): T {
        return func(ptr.get() ?: throw IllegalStateException("Accessing disposed pointer"))
    }

    override fun toString(): String = if (ptr.get() == null) "$name (Released)" else name
}

@TyKoFFIEntity
fun UniquePtr(ptr: Pointer, destructor: (Pointer) -> Unit) = UniquePtr(AtomicReference(ptr), destructor)
