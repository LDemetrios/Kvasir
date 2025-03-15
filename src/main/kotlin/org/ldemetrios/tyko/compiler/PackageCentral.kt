//package org.ldemetrios.tyko.compiler
//
//import org.apache.commons.compress.archivers.tar.TarArchiveEntry
//import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
//import org.ldemetrios.js.JSStuff
//import java.io.ByteArrayInputStream
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.io.FileInputStream
//import java.io.IOException
//import java.lang.ref.SoftReference
//import java.net.URI
//import java.net.URL
//import java.util.concurrent.ConcurrentHashMap
//import java.util.zip.GZIPInputStream
//
//TODO Probably get rid off this.
//
//data class PackageContent(val content: Map<String, ByteArray?>)
//
//private val CACHE = ConcurrentHashMap<PackageSpec, PackageContent>()
//
//fun packageFromCentral(file: FileDescriptor): RResult<ByteArray, FileError> {
//    val pack = file.pack!!
//    val path = file.path.removePrefix("/")
//    return try {
//        val content = CACHE.computeIfAbsent(pack) { _ ->
//            loadArchive(pack).let(::PackageContent)
//        }
//        if (path !in content.content) {
//            RResult.Err(FileError.NotFound(file.path))
//        } else {
//            content.content[path]?.let { RResult.Ok(it) } ?: RResult.Err(FileError.IsDirectory)
//        }
//    } catch (e: IOException) {
//        RResult.Err(FileError.Package(PackageError.NetworkFailed(null)))
//    }
//}
//
//private fun loadArchive(spec: PackageSpec): Map<String, ByteArray?> {
//    val addr = "https://packages.typst.org/${spec.namespace}/${spec.name}-${spec.version}.tar.gz"
//    val bytes = URI(addr).toURL().readBytes()
//    return extractTarGz(bytes)
//}
//
//private fun extractTarGz(tarGzFile: ByteArray): MutableMap<String, ByteArray?> {
//    return GZIPInputStream(ByteArrayInputStream(tarGzFile)).use { gis ->
//        TarArchiveInputStream(gis).use { tarInput ->
//            var entry: TarArchiveEntry?
//            val result = mutableMapOf<String, ByteArray?>()
//            while (tarInput.nextEntry.also { entry = it } != null) {
//                if (entry!!.isDirectory) {
//                    result[entry!!.name] = null
//                } else {
//                    ByteArrayOutputStream().use { fos ->
//                        tarInput.copyTo(fos)
//                        result[entry!!.name] = fos.toByteArray()
//                    }
//                }
//            }
//            result
//        }
//    }
//}
