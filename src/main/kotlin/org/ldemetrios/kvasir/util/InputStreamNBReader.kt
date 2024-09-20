package org.ldemetrios.kvasir.util

import java.io.IOException
import java.io.InputStream


class InputStreamNBReader(val stream: InputStream) {
    private val cache = StringBuilder()
    private var wasCR = false

    private inline fun process(c: Char, callback: (String) -> Unit) {
        if (wasCR) {
            callback(cache.toString())
            cache.clear()
            when (c) {
                '\n' -> Unit //Just do nothing
                '\r' -> Unit // Same
                in "\u2028\u2029\u0085" -> {
                    wasCR = false
                    callback("")
                }
                else -> cache.append(c)
            }
        } else {
            when (c) {
                '\r' -> Unit // Wait for the next
                in "\n\u2028\u2029\u0085" -> {
                    callback(cache.toString())
                    cache.clear()
                }
                else -> cache.append(c)
            }
        }
        wasCR = c == '\r'
    }

    fun readAvailable(callback: (String) -> Unit): Boolean {
        var anyChanges = false
        try {
            while (stream.available() > 0) {
                anyChanges = true
                val list = String(stream.readNBytes(stream.available()))
                for (c in list) {
                    process(c, callback)
                }
            }
        } catch (e: IOException) {
        }
        return anyChanges
    }

    fun flush(): String {
        val s = cache.toString()
        cache.clear()
        return s
    }

}