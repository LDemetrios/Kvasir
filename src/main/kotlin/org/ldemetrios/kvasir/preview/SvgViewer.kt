package org.ldemetrios.kvasir.preview

import com.github.weisj.jsvg.attributes.ViewBox
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.ui.JBColor
import org.ldemetrios.kvasir.preview.watch.FileWatch
import org.ldemetrios.kvasir.util.Rc
import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.*
import kotlin.math.*

val stroke =  BasicStroke(
    1.toFloat(),  // Width of the stroke
    BasicStroke.CAP_SQUARE,  // Cap style
    BasicStroke.JOIN_MITER,  // Join style
    10.0f,  // Miter limit
    floatArrayOf(10.0f, 5.0f),  // Dash pattern
    0.0f // Phase
);


data class PageData(var image: BufferedImage, var resolution: Int)

//const val RESOLUTION_OVERHEAD_COEFF = 1.5
const val PAGE_GAP = 10 // Whatever that means...

fun Int.clip(lower: Int, upper: Int) = max(lower, min(this, upper))

val viewers = mutableSetOf<ZoomablePanel>()

object DocReloader : DocumentListener {
    var bulk = false

    override fun bulkUpdateStarting(document: Document) {
        println("Start bulk")
        bulk = true
    }

    override fun bulkUpdateFinished(document: Document) {
        println("End bulk")
        bulk = false
        reload()
    }

    override fun documentChanged(event: DocumentEvent) {
        println("Document changed")
        if (!bulk) reload()
    }

    fun reload() {
        if (viewers.isNotEmpty()) {
            try {
                FileDocumentManager.getInstance().saveAllDocuments()
            } catch (_: IllegalStateException) {
            }
        }
    }
}

class TestPanel(val w: Int, val h: Int) : PartiallyRenderableGraphics {
    override fun render(component: Component, g: Graphics2D, viewport: Viewport, scale: Double) {
        g.clearRect(0, 0, w, h)

        g.stroke = stroke
        g.color = JBColor.BLACK
        g.drawRect(10, 10, w - 20, (h - 40) / 2)
        g.drawRect(10, 30 + (h - 40) / 2, w - 20, (h - 40) / 2)

        g.color = JBColor.BLUE
        g.drawRect(
            viewport.x.roundToInt() + 10,
            viewport.y.roundToInt() + 10,
            viewport.width.roundToInt() - 20,
            viewport.height.roundToInt() - 20
        )
        g.drawRect(x - 5, y - 5, 10, 10)
    }

    override val height: Double = h.toDouble()
    override val width: Double = w.toDouble()

    override fun close() = Unit
    var x = 0;
    var y = 0;

    fun click(x: Double, y: Double) {
        this.x = x.toInt()
        this.y = y.toInt()
    }
}

class SvgViewerPanel(val watch: Rc<FileWatch>) : PartiallyRenderableGraphics {
    @Volatile
    var buffer : MultipageSVGDocument = MultipageSVGDocument(watch.deref().svgFolder, 10.0)

    var first = true

    override fun reload(): Boolean {
        buffer = MultipageSVGDocument(watch.deref().svgFolder, 10.0)
        first = false
        return true
    }

    override fun render(component: Component, g: Graphics2D, viewport: Viewport, scale: Double) {
        fun updateVp() = buffer.stabilize(
            viewport.y,
            (viewport.y + viewport.height),
        )

        updateVp()
        val context = g.color
        val br = (0.299 * context.red) + (0.587 * context.green) + (0.114 * context.blue);
        if (br < 127.5) g.color = JBColor.WHITE
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        if (first && buffer.size == 0) {
            reload()
            updateVp()
        } else {
            first = false
        }

        for (ind in /*0 until buffer.list.size*/ buffer.lowerPage until buffer.upperPage) {
            val page = buffer.pages[ind]
            val x = ((width - page.width * scale) / 2)
            val y = ((buffer.pageStarts.getOrNull(ind) ?: continue) * scale) + PAGE_GAP / 2
            val w = (page.width * scale)
            val h = (page.height * scale)

            val doc = buffer.pages[ind] // Parallel updates could cause this

            g.fillRect(x.toInt(), y.toInt(), w.toInt(), h.toInt())
            println("RENDERING PAGE $ind")
            g.stroke = stroke
            g.color = Color.blue
            g.drawRect(x.toInt(), y.toInt(), w.toInt(), h.toInt())

            doc.render(component, g, ViewBox(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat()))
            println("RENDERED PAGE $ind")
            if (ind == buffer.size - 1) {
                println("${y + h} == ${buffer.height * scale}")
            }
        }
        g.color = context
    }

    override val height: Double get() = (buffer.height)
    override val width: Double get() = (buffer.width)
    override fun close() {
    }
}

class Wrapper(val panel: JPanel) : JPanel(BorderLayout()) {
    init {
        add(panel)
    }
}
