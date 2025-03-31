package org.ldemetrios.kvasir.preview.ui

import com.github.weisj.jsvg.attributes.ViewBox
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil
import org.ldemetrios.kvasir.preview.data.MultipageSVGDocument
import org.ldemetrios.kvasir.preview.data.SVGPage
import java.awt.*
import java.awt.image.BufferedImage
import kotlin.math.*

val stroke = BasicStroke(
    1.toFloat(),  // Width of the stroke
    BasicStroke.CAP_SQUARE,  // Cap style
    BasicStroke.JOIN_MITER,  // Join style
    10.0f,  // Miter limit
    floatArrayOf(10.0f, 5.0f),  // Dash pattern
    0.0f // Phase
)


data class PageData(var image: BufferedImage, var resolution: Int)

//const val RESOLUTION_OVERHEAD_COEFF = 1.5
const val PAGE_GAP = 10 // Whatever that means...

fun Int.clip(lower: Int, upper: Int) = max(lower, min(this, upper))

val viewers = mutableSetOf<ZoomablePanel>()

class TestPanel(val w: Int, val h: Int) : PartiallyRenderableGraphics {
    override fun render(component: Component, g: Graphics2D, viewport: Viewport, scale: Double) {
        g.scale(scale, scale)
        g.clearRect(0, 0, w, h)

        g.stroke = stroke
        g.color = JBColor.BLACK
        g.drawRect(10, 10, w - 20, (h - 40) / 2)
        g.drawRect(10, 30 + (h - 40) / 2, w - 20, (h - 40) / 2)

        g.color = JBColor.BLUE
        g.drawRect(
            viewport.x.roundToInt() + 15,
            viewport.y.roundToInt() + 15,
            viewport.width.roundToInt() - 30,
            viewport.height.roundToInt() - 30
        )
        g.drawRect(x - 5, y - 5, 10, 10)
        g.scale(1/scale, 1/scale)
    }

    override val height: Double = h.toDouble()
    override val width: Double = w.toDouble()

    override fun close() = Unit
    var x = 0
    var y = 0

    fun click(x: Double, y: Double) {
        this.x = x.toInt()
        this.y = y.toInt()
    }
}

class SvgViewerPanel(document: List<String>) : PartiallyRenderableGraphics {
    @Volatile
    var buffer: MultipageSVGDocument = MultipageSVGDocument(document, 10.0)

    var first = true

    fun reload(document: List<String>) {
        buffer = MultipageSVGDocument(document, 10.0)
    }

    override fun reload(): Boolean {
        first = false
        return true
    }

    override fun render(component: Component, g: Graphics2D, viewport: Viewport, scale: Double) {
        fun updateVp() = buffer.stabilize(
            viewport.y,
            viewport.y + viewport.height,
        )

        updateVp()
        val context = g.color
        val br = (0.299 * context.red) + (0.587 * context.green) + (0.114 * context.blue)
        if (br < 127.5) g.color = JBColor.WHITE
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)

        if (first && buffer.size == 0) {
            reload()
            updateVp()
        } else {
            first = false
        }

        for (ind in 0 until buffer.size  /*buffer.lowerPage until buffer.upperPage*/) {
            val page = buffer.pages[ind]
            val x = ((width - page.width ) / 2)* scale
            val y = (((buffer.pageStarts.getOrNull(ind) ?: continue) ) + PAGE_GAP / 2)* scale
            val w = (page.width * scale)
            val h = (page.height * scale)

            val doc = buffer.pages[ind] // Parallel updates could cause this


            g.color = Color.WHITE
            g.fillRect((x ).toInt(), (y ).toInt(), (w ).toInt(), (h ).toInt())

//            g.isolating { g ->
////                g.stroke = stroke
//                g.color = JBColor.WHITE
//                g.fillRect(x.toInt(), y.toInt(), w.toInt(), h.toInt())
//            }

//            g.color = Color.WHITE
//            doc.render(component, g, ViewBox(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat()))

//            g.isolating {
//                g.color = JBColor.BLUE
//                g.stroke = stroke
//                g.drawRect(x.toInt(), y.toInt(), w.toInt(), h.toInt())
//            }

/*            val image = tryRendering(component, w, h, doc, scale)
            g.drawImage(image, (x ).toInt(), (y ).toInt(), null)*/

            val imageGr = g
            imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            imageGr.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
            @Suppress("UseJBColor") // White background of the page
            imageGr.color = Color.WHITE
//            imageGr.fillRect(x.toInt(), y.toInt(), w.toInt(), h.toInt())
            doc.render(component, imageGr, ViewBox((x ).toFloat(), (y).toFloat(), w.toFloat(), h.toFloat()))

//            imageGr.dispose()

//            g.isolating { g ->
//                g.stroke = stroke
//                g.color = JBColor.RED
//                g.drawRect(x.toInt() + 5, y.toInt() + 5, w.toInt() - 10, h.toInt() - 10)
//            }

        }
        g.color = context
    }

    private fun tryRendering(
        component: Component,
        w: Double,
        h: Double,
        doc: SVGPage,
        scale: Double
    ): BufferedImage {
        val width = w
        val height = h
        val image = UIUtil.createImage(component, width.toInt(), height.toInt(), BufferedImage.TYPE_INT_ARGB)
        repeat(1) {
            val imageGr = image.createGraphics()
            imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            imageGr.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
            @Suppress("UseJBColor") // White background of the page
            imageGr.color = Color.WHITE
            imageGr.fillRect(0.toInt(), 0.toInt(), w.toInt(), h.toInt())
            doc.render(component, imageGr, ViewBox(0f, 0f, width.toFloat(), height.toFloat()))
            imageGr.dispose()

//            val distinct = (0 until w.toInt() step ceil(scale).toInt()).asSequence()
//                .flatMap{ x->
//                    (0 until h.toInt() step ceil(scale).toInt()).asSequence().map{y -> image.getRGB((x).toInt(),  (y).toInt())}
//                }.toSet()
//            if (
//                distinct.size != 1 /*|| distinct.first() != 0*/
//            ) {
//                println("${distinct.size} different colors")
//                return image
//            }
//            else println("Ah shit, here we go again, " + Color(distinct.first()))
        }
        return image
    }

    override val height: Double get() = (buffer.height)
    override val width: Double get() = (buffer.width)
    override fun close() {
    }
}
