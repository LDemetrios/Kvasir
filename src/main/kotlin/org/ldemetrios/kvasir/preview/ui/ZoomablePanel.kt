package org.ldemetrios.kvasir.preview.ui

import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import org.ldemetrios.kvasir.settings.AppSettings
import java.awt.*
import java.awt.event.*
import java.io.Closeable
import javax.swing.JPanel
import kotlin.math.min
import kotlin.math.pow


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
    var barFore: Color? = Gray._195,
    var barBack: Color? = null,
    var barThickness: Int = /*UIUtil.getScrollBarWidth()*/ 12,
) : JPanel(), MouseWheelListener, MouseListener, MouseMotionListener, FocusListener, Closeable, KeyListener {
    var scale = 1.0
    var x = 0.0
    var y = 0.0

    fun reload() {
        if (inside.reload()) repaint()
    }

    val actualWidth get() = width - barThickness
    val actualHeight get() = height - barThickness

    val horFrameStart: Double get() = x / inside.width
    val horFrameEnd: Double get() = (x + actualWidth / scale) / inside.width
    val vertFrameStart: Double get() = y / inside.height
    val vertFrameEnd: Double get() = (y + actualHeight / scale) / inside.height

    val vertBarStart: Int get() = (vertFrameStart * height).toInt()
    val vertBarEnd: Int get() = (vertFrameEnd * height).toInt()
    val horBarLength: Int get() = (actualWidth - barThickness * 0.7).toInt()
    val horBarStart: Int get() = (horFrameStart * horBarLength).toInt()
    val horBarEnd: Int get() = (horFrameEnd * horBarLength).toInt()

    val needsVertBar get() = inside.height * scale > actualHeight
    val needsHorBar get() = inside.width * scale > actualWidth

    val xShiftCenter get() = if (actualWidth / scale > inside.width) (actualWidth - inside.width * scale) / 2 else 0.0
    val yShiftCenter get() = if (actualHeight / scale > inside.height) (actualHeight - inside.height * scale) / 2 else 0.0

    override fun paint(g: Graphics?) = g.isolating { g ->

        if (width < barThickness * 2 || height < barThickness * 1.3) {
            // TODO draw filler
            return@isolating
        }
        g.background = JBColor.GRAY
        g.clearRect(0, 0, width, height)
        try {
            if (needsVertBar) {
                // Draw vertical scrollbar
                barBack?.let {
                    g.color = it
                    g.fillRect(actualWidth, 0, barThickness, height)
                }
                barFore?.let {
                    g.color = it
                    g.fillRect(
                        actualWidth,
                        vertBarStart,
                        barThickness,
                        vertBarEnd - vertBarStart
                    )
                }
            }

            if (needsHorBar) {
                // Draw horizontal scrollbar

                barBack?.let {
                    g.color = it
                    g.fillRect(0, actualHeight, horBarLength, barThickness)
                }
                barFore?.let {
                    g.color = it

                    g.fillRect(
                        horBarStart,
                        actualHeight,
                        horBarEnd - horBarStart,
                        barThickness
                    )
                }
            }

            g.clipRect(0, 0, actualWidth, actualHeight)

            val transformWas = g.transform


            g.translate(xShiftCenter, yShiftCenter)
            g.translate(-x * scale, -y * scale)

            inside.render(
                this,
                g,
                Viewport(x, y, actualWidth / scale, actualHeight / scale),
                scale
            )

            g.transform = transformWas


        } catch (t: Throwable) {
            println("Rendering failed")
            t.printStackTrace(System.out)
            throw t
        }
    }

    override fun getPreferredSize(): Dimension = Dimension(
        (inside.width * scale + barThickness).toInt(),
        (inside.height * scale + barThickness).toInt()
    )

    init {
        addMouseListener(this)
        addMouseWheelListener(this)
        addMouseMotionListener(this)
    }

    override fun mouseWheelMoved(p0: MouseWheelEvent) {
        if (p0.isControlDown) {
            // x + p0.x * scale must remain the same
            // x' + p0.x / scale' = x + p0.x / scale
            // x'  = x + p0.x / scale - p0.x / scale'
            val scale1 = scale * 0.9.pow(p0.preciseWheelRotation)
            x += (p0.x - xShiftCenter) * (1 / scale - 1 / scale1)
            y += (p0.y - yShiftCenter) * (1 / scale - 1 / scale1)
            scale = scale1
        } else if (p0.isShiftDown) {
            x += p0.preciseWheelRotation * 20 / scale
        } else {
            y += p0.preciseWheelRotation * 20 / scale
        }

        clip()
    }

    /**
     * 0 --- no scrolling
     * 1 --- horizontal scrolling
     * 2 --- vertical scrolling
     */
    var barScrolling = 0

    var scrollingCoord = -1

    fun inHorizontal(p0: MouseEvent) = when {
        !needsHorBar -> -1
        p0.x < horBarLength && p0.y > actualHeight -> p0.x
        else -> -1
    }

    fun inVertical(p0: MouseEvent) = when {
        !needsVertBar -> -1
        p0.x > actualWidth -> p0.y
        else -> -1
    }

    override fun mousePressed(p0: MouseEvent) = mouseDragged(p0)

    override fun mouseClicked(p0: MouseEvent) {
        val inHorizontal = inHorizontal(p0)
        val inVertical = inVertical(p0)

        (inside as? TestPanel)?.click(
            x + (p0.x - xShiftCenter) / scale,
            y + (p0.y - yShiftCenter) / scale
        )

        if (inHorizontal == -1 && inVertical == -1) {
            return repaint()
        }

        when {
            inHorizontal > -1 -> {
                jumpX(p0)
            }

            inVertical > -1 -> {
                jumpY(p0)
            }
        }

        clip()
    }


    private fun jumpX(p0: MouseEvent) {
        if (scrollingCoord < horBarStart || scrollingCoord > horBarEnd) {
            // p0.x = (x / inside.width * horBarLength)
            barScrolling = 0
            x = (p0.x - (horBarEnd - horBarStart) / 2) * inside.width / horBarLength
        }
    }

    private fun jumpY(p0: MouseEvent) {
        if (scrollingCoord < vertBarStart || scrollingCoord > vertBarEnd) {
            barScrolling = 0
            y = (p0.y - (vertBarEnd - vertBarStart) / 2) * inside.height / height
        }
    }

    override fun mouseReleased(p0: MouseEvent?) {
        barScrolling = 0
    }

    override fun mouseDragged(p0: MouseEvent) {

        when (barScrolling) {
            0 -> when {
                inHorizontal(p0) > -1 -> {
                    barScrolling = 1
                    scrollingCoord = p0.x
                    jumpX(p0)
                }

                inVertical(p0) > -1 -> {
                    barScrolling = 2
                    scrollingCoord = p0.y
                    jumpY(p0)
                }
            }

            1 -> {
                x += (p0.x - scrollingCoord) * inside.width / horBarLength
                scrollingCoord = p0.x
            }

            2 -> {
                y += (p0.y - scrollingCoord) * inside.height / height
                scrollingCoord = p0.y
            }
        }

        clip()
    }

    override fun mouseMoved(p0: MouseEvent?) = Unit

    override fun keyTyped(p0: KeyEvent) {
        when (p0.keyCode) {
            KeyEvent.VK_PAGE_UP -> {
                y -= actualHeight / scale
            }

            KeyEvent.VK_PAGE_DOWN -> {
                y += actualHeight / scale
            }

            KeyEvent.VK_END -> {
                y = inside.height
            }

            KeyEvent.VK_HOME -> {
                y = .0
            }
        }
        clip()
    }

    override fun keyPressed(p0: KeyEvent) = Unit
    override fun keyReleased(p0: KeyEvent) = Unit

    override fun mouseEntered(p0: MouseEvent?) = Unit //  println("Entered")

    override fun mouseExited(p0: MouseEvent?) = Unit // println("Exited")

    override fun focusGained(p0: FocusEvent?) = Unit //  println("Gained")

    override fun focusLost(p0: FocusEvent?) = Unit // println("Lost")

    override fun close() {
        inside.close()
    }

    private fun clip() {
        if (!AppSettings.instance.state.unclippedScrolling) {
            val actualWidth = width - barThickness
            val actualHeight = height - barThickness

            x = if (actualWidth / scale > inside.width) .0
            else x.clip(0.0, inside.width - actualWidth / scale)

            y = if (actualHeight / scale > inside.height) .0
            else y.clip(0.0, inside.height - actualHeight / scale)
        }

        repaint()
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

fun Double.clip(min: Double, max: Double) = when {
    min >= max -> min(min, this)
    this <= min -> min
    this > max -> max
    else -> this
}