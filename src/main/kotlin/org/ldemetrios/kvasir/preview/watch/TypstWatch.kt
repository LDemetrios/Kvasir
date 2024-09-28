package org.ldemetrios.kvasir.preview.watch

import com.jetbrains.rd.util.concurrentMapOf
//import org.apache.pdfbox.pdmodel.PDDocument
import org.ldemetrios.kvasir.preview.viewers
import org.ldemetrios.kvasir.util.Rc
import java.awt.Component
import java.io.Closeable
import java.io.File
import java.io.IOException
import kotlin.io.path.createTempDirectory


object WatchServer : Runnable {
    val map = concurrentMapOf<Pair<String, String>, Rc<FileWatch>>()
    var runningThread: Thread? = null

    fun register(input: String, root: String): Rc<FileWatch> {
        if (runningThread == null) {
            runningThread = Thread(this).also {
                it.isDaemon = true
                it.start()
            }
        }
        val key = input to root

        return map.computeIfAbsent(key) {
//            println("WatchServer: STARTING watch ${File(input).relativeTo(File(root))}, with root $root")
            val watch = FileWatch(File(input), File(root))
            Rc(watch) {
//                println("WatchServer: DESTROYING watch ${File(input).relativeTo(File(root))}, with root $root")
                map.remove(key)
            }
        }
    }

    override fun run() {
        while (true) { // TODO probably need non-blocking handling or something
            for (watch in map.values) { // Hopefully there is not much of them
                try {
                    watch.deref().update()
                } catch (e: RuntimeException) {
                    // Ignore
                    e.printStackTrace()
                }
            }
            Thread.yield() // Which probably does nothing, but for now will do.
        }
    }
}



