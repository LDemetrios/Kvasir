package org.ldemetrios.kvasir.util

import com.intellij.reference.SoftReference
import java.io.Closeable
import java.util.concurrent.atomic.AtomicInteger


class HardenableReference<T : Any> private constructor(var value: T?, var ref: SoftReference<T>) {
    constructor(value: T) : this(value, SoftReference(value))

    companion object {
        fun <T : Any> of(ref: SoftReference<T>) = HardenableReference(null, ref)
        fun <T : Any> empty() = HardenableReference(null, SoftReference<T>(null))
    }

    fun harden(): Boolean {
        val v = ref.get() ?: return false
        value = v
        return true
    }

    fun soften() {
        value = null
    }

    fun deref(): T? = ref.get()

    fun set(value: T) {
        this.value = null
        ref = SoftReference(value)
    }

    fun set(value: SoftReference<T>) {
        this.value = null
        ref = value
    }

    fun setHard(value: T) {
        this.value = value
        ref = SoftReference(value)
    }
}

class CachedReference<T : Any>(val supplier: () -> T, createHard: Boolean = false) {
    private val ref = if (createHard) HardenableReference.of(SoftReference(supplier())) else HardenableReference.empty()

    fun harden() : T {
        if (ref.harden()) return ref.deref()!!
        ref.setHard(supplier())
        return ref.deref()!!
    }

    fun soften() {
        ref.soften()
    }

    fun deref(): T = /*ref.deref() ?:*/ supplier()
}

class Rc<T : Closeable>(val value: T, val notify: T.() -> Unit = {}) : AutoCloseable by value {
    val count = AtomicInteger(1)

    fun clone(): Rc<T> {
        count.incrementAndGet()
        return this
    }

    override fun close() {
        if (count.decrementAndGet() == 0) {
            value.close()
            value.notify()
        }
    }

    fun deref(): T = value
}
