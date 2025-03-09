package org.ldemetrios.tyko.compiler

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Warned<T>(val output: T, val warnings: List<SourceDiagnostic>)

fun <T, R> Warned<T>.map(f: (T) -> R): Warned<R> = Warned(f(output), warnings)

@Serializable
data class SourceDiagnostic(
    val severity: Severity,
    val span: Span,
    val message: String,
    val trace: List<Spanned<Tracepoint>>,
    val hints: List<String>,
)

@Serializable
data class Span(
    val native: ULong,
    val file: FileDescriptor?,
    @SerialName("start_ind") val startInd: Long,
    @SerialName("end_ind") val endInd: Long,
    @SerialName("start_line") val startLine: Long,
    @SerialName("start_col") val startCol: Long,
    @SerialName("end_line") val endLine: Long,
    @SerialName("end_col") val endCol: Long,
)

@Serializable
data class FileDescriptor(val pack: PackageSpec?, val path: String) {
    override fun toString(): String = if (pack == null) path else "$pack$path"
}

@Serializable
data class PackageSpec(val namespace: String, val name: String, val version: PackageVersion) {
    override fun toString(): String =  "@$namespace/$name:$version"
}

@Serializable
data class PackageVersion(val major: UInt, val minor: UInt, val patch: UInt) {
    override fun toString(): String = "$major.$minor.$patch"
}

@Serializable
sealed class FileError {
    @Serializable
    @SerialName("NotFound")
    data class NotFound(val path: String) : FileError()

    @Serializable
    @SerialName("AccessDenied")
    data object AccessDenied : FileError()

    @Serializable
    @SerialName("IsDirectory")
    data object IsDirectory : FileError()

    @Serializable
    @SerialName("NotSource")
    data object NotSource : FileError()

    @Serializable
    @SerialName("InvalidUtf8")
    data object InvalidUtf8 : FileError()

    @Serializable
    @SerialName("Package")
    data class Package(val error: PackageError) : FileError()

    @Serializable
    @SerialName("Other")
    data class Other(val message: String?) : FileError()
}

@Serializable
sealed class PackageError {
    @Serializable
    @SerialName("NotFound")
    data class NotFound(@SerialName("package") val spec: PackageSpec) : PackageError()

    @Serializable
    @SerialName("VersionNotFound")
    data class VersionNotFound(@SerialName("package") val spec: PackageSpec, val version: PackageVersion) :
        PackageError()

    @Serializable
    @SerialName("NetworkFailed")
    data class NetworkFailed(val message: EcoString?) : PackageError()

    @Serializable
    @SerialName("MalformedArchive")
    data class MalformedArchive(val message: EcoString?) : PackageError()

    @Serializable
    @SerialName("Other")
    data class Other(val message: EcoString?) : PackageError()
}

@Serializable
enum class Severity { Error, Warning }

@Serializable
data class Spanned<T>(
    val v: T,
    val span: Span,
)

@Serializable
sealed class Tracepoint {
    @Serializable
    @SerialName("Call")
    data class Call(val function: EcoString?) : Tracepoint()

    @Serializable
    @SerialName("Show")
    data class Show(val string: EcoString?) : Tracepoint()

    @Serializable
    @SerialName("Import")
    data object Import : Tracepoint()
}

val Tracepoint.name
    get() = when (this) {
        is Tracepoint.Call -> function
        is Tracepoint.Show -> string
        Tracepoint.Import -> null
    }

//fun span(world: World, native: Long, file: FileDescriptor?, content: String?, start: Long, end: Long): Span {
//    val text = file?.let { world.file(it).unwrap().decodeToString() } ?: content!!
//
//    var line = 1
//    var column = 1
//    var currentIndex = 0
//    var startLine: Int = -1
//    var startCol: Int = -1
//    var endLine: Int = -1
//    var endCol: Int = -1
//    while (currentIndex < text.length) {
//        if (currentIndex == start.toInt()) {
//            startLine = line
//            startCol = column
//        }
//        if (currentIndex == end.toInt()) {
//            endLine = line
//            endCol = column
//            break
//        }
//
//        when {
//            text.startsWith("\r\n", currentIndex) -> {
//                line++
//                column = 1
//                currentIndex += 2
//            }
//
//            text[currentIndex] == '\n' || text[currentIndex] == '\r' -> {
//                line++
//                column = 1
//                currentIndex++
//            }
//
//            else -> {
//                column++
//                currentIndex++
//            }
//        }
//    }
//
//    return Span(
//        native, file, start, end, startLine, startCol, endLine, endCol,
//    )
//}
