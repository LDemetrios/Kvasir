package org.ldemetrios.kvasir.preview

import java.awt.*
import java.awt.event.*
import java.io.Closeable
import javax.swing.JPanel
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

data class Viewport(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double
) {
    val aspectRatio get() = width / height
    val top get() = y
    val bottom get() = y + height
    val left get() = x
    val right get() = x + width

    override fun toString(): String = "Viewport [$top .. $bottom] x [$left .. $right]"
}


interface PartiallyRenderableGraphics : Closeable {
    fun render(
        component: Component,
        g: Graphics2D,
        viewport: Viewport,
        scale: Double
    )

    fun reload(): Boolean = false

    val height: Double
    val width: Double
}

class ZoomablePanel(
    val inside: PartiallyRenderableGraphics,
    var barFore: Color?,
    var barBack: Color?,
    var barThickness: Int,
) : JPanel(), MouseWheelListener, MouseListener, FocusListener, Closeable {
    var scale = 1.0
    var x = 0.0
    var y = 0.0

    fun reload() {
        if (inside.reload()) repaint()
    }

    override fun paint(g: Graphics?) = g.isolating { g ->
        if (width < barThickness * 2 || height < barThickness * 1.3) {
            // TODO draw filler
            return@isolating
        }

        g as Graphics2D

        println("ERASE")
//        g.clearRect(0, 0, width, height)
        try {
            val transformWas = g.transform

            val actualWidth = width - barThickness
            val actualHeight = height - barThickness

            g.scale(scale, scale)
            if (actualWidth / scale > inside.width) {
                g.translate(((actualWidth / scale - inside.width) / 2).roundToInt(), 0)
            }
            if (actualHeight / scale > inside.height) {
                g.translate(0, ((actualHeight / scale - inside.height) / 2).roundToInt())
            }
            g.translate(-x, -y)

            inside.render(
                this,
                g,
                Viewport(x, y, actualWidth / scale, actualHeight / scale),
                1.0
            )

            g.transform = transformWas

            if (inside.height * scale > actualHeight) {
                // Draw vertical scrollbar
                barBack?.let {
                    g.color = it
                    g.fillRect(actualWidth, 0, barThickness, height)
                }
                barFore?.let {
                    val frameStart = y / inside.height
                    val frameEnd = (y + actualHeight / scale) / inside.height
                    g.color = it
                    g.fillRect(
                        actualWidth,
                        (frameStart * height).toInt(),
                        barThickness,
                        (frameEnd * height).toInt() - (frameStart * height).toInt()
                    )
                }
            }

            if (inside.width * scale > actualWidth) {
                // Draw horizontal scrollbar
                val barLength = (actualWidth - barThickness * 0.7).toInt()
                barBack?.let {
                    g.color = it
                    g.fillRect(0, actualHeight, barLength, barThickness)
                }
                barFore?.let {
                    val frameStart = x / inside.width
                    val frameEnd = (x + actualWidth / scale) / inside.width
                    g.color = it
                    g.fillRect(
                        (frameStart * barLength).toInt(),
                        actualHeight,
                        (frameEnd * barLength).toInt() - (frameStart * barLength).toInt(),
                        barThickness
                    )
                }
            }
        } catch (t: Throwable) {
            println("Rendering failed")
            t.printStackTrace(System.out)
            throw t
        }
        println("Rendering succeeded")
    }

    override fun getPreferredSize(): Dimension = Dimension(
        (inside.width * scale + barThickness).toInt(),
        (inside.height * scale + barThickness).toInt()
    )

    init {
        addMouseListener(this)
        addMouseWheelListener(this)
        addFocusListener(this)
    }

    override fun mouseWheelMoved(p0: MouseWheelEvent) {
        println(
            (if (p0.isControlDown) "Ctrl + " else "") +
                    (if (p0.isShiftDown) "Shift + " else "") +
                    "Wheel ${p0.preciseWheelRotation}"
        )
        if (p0.isControlDown) {
            // x + p0.x * scale must remain the same
            // x' + p0.x / scale' = x + p0.x / scale
            // x'  = x + p0.x / scale - p0.x / scale'
            val scale1 = scale * 0.9.pow(p0.preciseWheelRotation)
            x += p0.x * (1 / scale - 1 / scale1)
            y += p0.y * (1 / scale - 1 / scale1)
            scale = scale1
        } else if (p0.isShiftDown) {
            x += p0.preciseWheelRotation * 20 / scale
        } else {
            y += p0.preciseWheelRotation * 20 / scale
        }

        val actualWidth = width - barThickness
        val actualHeight = height - barThickness

        x = if (actualWidth / scale > inside.width) .0
        else x.clip(0.0, inside.width - actualWidth / scale)

        y = if (actualHeight / scale > inside.height) .0
        else y.clip(0.0, inside.height - actualHeight / scale)

        repaint()
    }

    override fun mouseClicked(p0: MouseEvent) {
//        println("Click $x $y ${p0.x} ${p0.y}")
//        (inside as? TestPanel)?.click(
//            x + p0.x                    / scale,
//            y + p0.y                    / scale
//        )
//        repaint()
    }

    override fun mousePressed(p0: MouseEvent) {
        println("Pressed")
    }

    override fun mouseReleased(p0: MouseEvent?) {
        println("Released")
    }

    override fun mouseEntered(p0: MouseEvent?) {
        println("Entered")
    }

    override fun mouseExited(p0: MouseEvent?) {
        println("Exited")
    }

    override fun focusGained(p0: FocusEvent?) {
        println("Gained")
    }

    override fun focusLost(p0: FocusEvent?) {
        println("Lost")
    }

    override fun close() {
        inside.close()
    }
}

fun Graphics?.isolating(func: (Graphics2D) -> Unit) {
    val g = this as Graphics2D
    val transform = g.transform
    val color = g.color
    val background = g.background
    func(g)
    g.transform = transform
    g.color = color
    g.background = background
}

fun Double.clip(min: Double, max: Double) = if (min >= max) {
    min(min, this)
} else when {
    this <= min -> min
    this > max -> max
    else -> this
}