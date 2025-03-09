package org.ldemetrios.tyko.compiler

import org.ldemetrios.tyko.compiler.RResult
import org.ldemetrios.tyko.ffi.unpinAllLocal
import org.ldemetrios.tyko.model.*
import java.io.Closeable

interface TypstCompiler : Closeable {
    fun evalDetachedRaw(source: String): RResult<String, List<SourceDiagnostic>>
    fun queryRaw(selector: String, format: SerialFormat): Warned<RResult<String, List<SourceDiagnostic>>>
    fun compileHtmlRaw(): Warned<RResult<String, List<SourceDiagnostic>>>
    fun compileSvgRaw(fromPage: Int, toPage: Int): Warned<RResult<List<String>, List<SourceDiagnostic>>>
    fun compilePngRaw(fromPage: Int, toPage: Int, ppi: Float): Warned<RResult<List<ByteArray>, List<SourceDiagnostic>>>
    fun reset()
}

enum class SerialFormat {
    PrettyJSON, JSON, YAML;
}

fun TypstCompiler.evalDetached(source: String): TValue {
    val result = evalDetachedRaw(source)
    return when (result) {
        is RResult.Ok -> deserialize(result.value)!!
        is RResult.Err -> {
            throw TypstCompilerException(result.error)
        }
    }
}

fun TypstCompiler.query(selector: TSelector): TArray<TValue> {
    val result = queryRaw(selector.repr(), SerialFormat.JSON).output
    return when (result) {
        is RResult.Ok -> deserialize(result.value) as? TArray<TValue> ?: throw AssertionError("queryRaw should return TArray")
        is RResult.Err -> {
            throw TypstCompilerException(result.error)
        }
    }
}

fun TypstCompiler.compileHtml(): String {
    val result = compileHtmlRaw().output
    return when (result) {
        is RResult.Ok -> result.value
        is RResult.Err -> {
            throw TypstCompilerException(result.error)
        }
    }
}

fun TypstCompiler.compileSvg(fromPage: Int = 0, toPage: Int = Int.MAX_VALUE): List<String> {
    val result = compileSvgRaw(fromPage, toPage).output
    return when (result) {
        is RResult.Ok -> result.value
        is RResult.Err -> {
            throw TypstCompilerException(result.error)
        }
    }
}

fun TypstCompiler.compilePng(fromPage: Int = 0, toPage: Int = Int.MAX_VALUE, ppi: Float = 144.0f): List<ByteArray> {
    val result = compilePngRaw(fromPage, toPage, ppi).output
    return when (result) {
        is RResult.Ok -> result.value
        is RResult.Err -> {
            throw TypstCompilerException(result.error)
        }
    }
}