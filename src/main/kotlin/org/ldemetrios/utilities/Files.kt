@file:Suppress("PackageDirectoryMismatch", "unused")
@file:JvmName("Files")

package org.ldemetrios.utilities

import java.io.*

fun File.recreate() {
    if (exists()) delete()
    forcefullyCreate()
}

fun File.forcefullyCreate() {
    if (exists()) throw IOException("File already exists")

    try {
        createNewFile()
    } catch (e: IOException) {
        if (e.message != "The system cannot find the path specified") throw e
        val lastSeparator = path.indexOfLast { it == '\\' || it == '/' }
        val dirs = path.substring(0, lastSeparator)

        val successfully = File(dirs).mkdirs()
        if (!successfully) throw IOException("Can't create the path specified")

        try {
            createNewFile()
        } catch (e: IOException) {
            if (e.message != "The system cannot find the path specified") throw e
            throw IOException("Can't create the path specified")
        }
    }
}