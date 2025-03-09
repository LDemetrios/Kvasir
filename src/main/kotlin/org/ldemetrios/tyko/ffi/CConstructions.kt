package org.ldemetrios.tyko.ffi

import com.sun.jna.Memory
import com.sun.jna.Pointer
import com.sun.jna.Structure
import com.sun.jna.Structure.ByValue
import java.io.Closeable

@Structure.FieldOrder("ticket", "value")
@TyKoFFIEntity
class JavaResult : Structure(), ByValue, Closeable {
    @JvmField
    var ticket: Long = 0

    @JvmField
    var value: ThickBytePtr? = null

    companion object {
        fun fromString(str: String): JavaResult {
            val value = ThickBytePtr.fromString(str)
            val ticket = pin(value)
            return JavaResult().apply { this.ticket = ticket; this.value = value; write() }
        }
    }

    override fun close() {
        value?.close()
    }
}

@Structure.FieldOrder("ptr", "len")
@TyKoFFIEntity
class ThickBytePtr : Structure(), ByValue, Closeable {
    @JvmField
    var ptr: Pointer? = null

    @JvmField
    var len: Int = 0

    companion object {
        fun fromString(str: String): ThickBytePtr {
            val bytes = str.toByteArray(Charsets.UTF_8)
            val memory = if(bytes.isNotEmpty()) Memory(bytes.size.toLong()) else null
            memory?.write(0, bytes, 0, bytes.size)
            return ThickBytePtr().apply { len = bytes.size; ptr = memory ; write() }
        }
    }

    fun readString(): String {
        return ByteArray(len).also { ptr!!.read(0, it, 0, len) }.decodeToString()
    }

    override fun close() {
        (ptr as? Memory)?.close()
    }
}

@Structure.FieldOrder("comment", "ptr")
@TyKoFFIEntity
class JavaExceptPtrResult : Structure(), ByValue {
    @JvmField
    var comment: ThickBytePtr? = null

    @JvmField
    var ptr: Pointer? = null
}



