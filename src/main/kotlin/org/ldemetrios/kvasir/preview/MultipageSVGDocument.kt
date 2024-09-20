package org.ldemetrios.kvasir.preview

import com.github.weisj.jsvg.SVGDocument
import com.github.weisj.jsvg.attributes.ViewBox
import com.github.weisj.jsvg.parser.SVGLoader
import org.ldemetrios.kvasir.util.CachedReference
import java.awt.Component
import java.awt.Graphics2D
import java.io.File

class SVGPage(val file: File, val loader: SVGLoader, createHard: Boolean = true) {
    fun harden() = content.harden()
    fun soften() = content.soften()

    fun render(component: Component, g: Graphics2D, viewBox: ViewBox) {
        content.deref().render(component, g, viewBox)
    }

    val content = CachedReference({ loader.load(file.toURI().toURL())!! }, createHard)

    val vb = content.deref().viewBox().run { width to height }

    val width get() = vb.first.toDouble()
    val height get() = vb.second.toDouble()
}

class MultipageSVGDocument(val folder: File, val pageGap: Double) {
    val loader = SVGLoader()

    val pages = folder.listFiles()!!.sortedBy { it.name }.map { SVGPage(it, loader) }
    val size = pages.size

    val width = pages.maxOfOrNull { it.width } ?: 0.0
    val heights = pages.run { DoubleArray(size) { this[it].height } }
    val height = heights.sum() + pageGap * (size - 1)

    val pageStarts = run {
        val result = DoubleArray(pages.size)
        var y = 0.0
        for (i in pages.indices) {
            result[i] = y
            y += pages[i].height + pageGap
        }
        result
    }

    var lowerPage = -1
    var upperPage = -1

    fun stabilize(lower: Double, upper: Double) {
        val l = pageStarts.binarySearch(lower).let {
            when {
                it > 0 -> it
                (pageStarts.getOrElse(-it) { .0 }) - lower > PAGE_GAP -> -it - 2
                else -> -it - 1
            }
        }
        val u = pageStarts.binarySearch(upper).let {
            when {
                it >= 0 -> it
                else -> -it - 1
            }
        }

        stabilize(
            l.clip(0, pages.size - 1),
            u.clip(0, pages.size)
        )
    }

    private fun stabilize(newLowerPage: Int, newUpperPage:Int) {
        // Harden and soften whatever needed first

        if (lowerPage != -1) {
            if (newLowerPage >= upperPage) {
                for (i in newLowerPage until newUpperPage) {
                    pages[i].harden()
                }
                for (i in lowerPage until upperPage) {
                    pages[i].soften()
                }
            } else {
                if (newLowerPage < lowerPage) {
                    for (i in newLowerPage until lowerPage) {
                        pages[i].harden()
                    }
                } else {
                    for (i in lowerPage until newLowerPage) {
                        pages[i].soften()
                    }
                }
                if (newUpperPage > upperPage) {
                    for (i in upperPage until newUpperPage) {
                        pages[i].harden()
                    }
                } else {
                    for (i in newUpperPage until upperPage) {
                        pages[i].soften()
                    }
                }
            }
        }

        lowerPage = newLowerPage
        upperPage = newUpperPage
    }
}