package org.ldemetrios.tyko.ffi

import com.sun.jna.Memory
import com.sun.jna.Pointer
import com.sun.jna.Structure
import com.sun.jna.Structure.ByValue
import org.ldemetrios.tyko.compiler.IndexedMark
import org.ldemetrios.tyko.compiler.SyntaxKind
import org.ldemetrios.tyko.compiler.SyntaxMark
import org.ldemetrios.tyko.mapByteIndicesToCharIndices
import java.io.Closeable
import java.util.Vector

@Structure.FieldOrder("ptr", "len", "cap")
@TyKoFFIEntity
open class CVec : Structure(), ByValue, Closeable {
    @JvmField
    var ptr: Pointer? = null

    @JvmField
    var len: Long = 0

    @JvmField
    var cap: Long = 0

    override fun close() {
        (ptr as? Memory)?.close()
    }
}

@Structure.FieldOrder("marks", "errors", "errors_starts")
@TyKoFFIEntity
class CFlattenedSyntaxTree() : Structure(), ByValue {
    @JvmField
    var marks: CVec? = null

    @JvmField
    var errors: CVec? = null

    @JvmField
    var errors_starts: CVec? = null
}

@TyKoFFIEntity
fun CFlattenedSyntaxTree.intoKotlin(source: String): List<IndexedMark> {
    this.errors!!.read()
    this.errors_starts!!.read()
    this.marks!!.read()
    val errorSize = this.errors!!.len.toInt()
    val errors = ByteArray(errorSize).also { this.errors!!.ptr!!.read(0, it, 0, errorSize) }
    val errorStartsSize = this.errors_starts!!.len.toInt()
    val errorStarts = IntArray(errorStartsSize).also { this.errors_starts!!.ptr!!.read(0, it, 0, errorStartsSize) }
    val errorStrings = errorStarts.indices.map {
        val from = errorStarts[it]
        val to = if (it + 1 < errorStarts.size) errorStarts[it + 1] else errors.size
        String(errors, from, to - from)
    }
    val marksSize = this.marks!!.len.toInt()
    val marks = LongArray(marksSize).also { this.marks!!.ptr!!.read(0, it, 0, marksSize) }
    val byteIndexedMarks = marks.map {
        val markEncoded = (it shr 32).toInt()
        val idx = it.toInt()
        val mark = when {
            markEncoded < 134 -> SyntaxMark.NodeStart(SyntaxKind.entries[markEncoded])
            markEncoded == 134 -> SyntaxMark.NodeEnd
            else -> SyntaxMark.Error(errorStrings.getOrNull(markEncoded - 135) ?: "AAAA Invalid")
        }
        IndexedMark(mark, idx)
    }
    val byteIndices = IntArray(byteIndexedMarks.size) { byteIndexedMarks[it].index }
    val charIndices = mapByteIndicesToCharIndices(source, byteIndices)
    return List(byteIndexedMarks.size) { byteIndexedMarks[it].copy(index = charIndices[it]) }
}
