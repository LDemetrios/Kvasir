@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package org.ldemetrios.utilities.graphics

import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener
import javax.swing.JPanel
import kotlin.math.pow

enum class ZoomType {
    ZoomToPoint, ZoomCentered, NoZoom
}

enum class DragType {
    Drag, NoDrag
}

@Suppress("LeakingThis")
abstract class ContinuousCanvas(
    var centerX: Double,
    var centerY: Double,
    var scale: Double,
    var inverseY: Boolean,
    var autoRepaint: Boolean,
    var zoomType: ZoomType,
    var zoomFactor: Double,
    var dragType: DragType,
) : JPanel(), MouseListener, MouseMotionListener, MouseWheelListener {
    init {
        addMouseListener(this)
        addMouseWheelListener(this)
        addMouseMotionListener(this)
    }

    fun move(dx: Double, dy: Double) {
        centerX -= dx
        centerY -= dy
        if (autoRepaint) repaint()
    }

    fun zoom(dz: Double) {
        scale /= dz
        if (autoRepaint) repaint()
    }

    fun zoomTo(dz: Double, x: Double, y: Double) {
        if (inverseY) {
            // val newScale = scale * dz
            // val xInt = (x - centerX) * scale
            // val yInt = (y - centerY) * scale
            // val xIntNew = (x - newCenterX) * newScale
            // val yIntNew = (y - newCenterY) * newScale
            // xInt == xIntNew
            // (x - centerX) * scale = (x - newCenterX) * newScale = (x - newCenterX) * scale * dz
            // (x - centerX) = dz * x - newCenterX * dz
            centerX = x - (x - centerX) * dz
            centerY = y - (y - centerY) * dz
        } else {
//            val newScale = scale * dz
//            val xInt = (x - centerX) * scale
//            val yInt = (-y - centerY) * scale
//            val xIntNew = (x - newCenterX) * newScale
//            val yIntNew = (-y - newCenterY) * newScale
            centerX = x - (x - centerX) * dz
            // yInt == yIntNew
            // (-y - centerY) == (-y - newCenterY) * dz
            // (-y - centerY) / dz == -y - newCenterY
            // newCenterY == -y - (-y - centerY) / dz
            centerY = -y - (-y - centerY) * dz
        }
        zoom(dz)
    }

    fun zoomToPixel(dz: Double, x: Int, y: Int) {
        zoomTo(dz, unresolveX(x - width / 2), unresolveY(y - height / 2))
    }

    fun resolveX(x: Number) = ((x.toDouble() - centerX) * scale).toInt()
    fun resolveY(y: Number) = (((if (inverseY) -y.toDouble() else y.toDouble()) - centerY) * scale).toInt()

    fun resolveEdge(a: Number) = (a.toDouble() * scale).toInt()
    fun unresolveX(x: Int) = x / scale + centerX
    fun unresolveY(y: Int) = y / scale + centerY
    fun unresolveEdge(a: Int) = a / scale

    open fun setUp(g: Graphics) {
        g.translate(width / 2, height / 2)
    }

    open fun tearDown(g: Graphics) = Unit

    open fun paint(g: ContinuousGraphics) = Unit

    override fun paint(g: Graphics) {
        setUp(g)
        paint(ContinuousGraphics(g, centerX, centerY, scale, inverseY))
        tearDown(g)
    }

    private fun resolveMouseEvent(e: MouseEvent) = ContinuousMouseEvent(
        e.`when`,
        e.modifiersEx,
        unresolveX(e.x - width / 2),
        unresolveY(e.y - height / 2),
        e.clickCount,
        e,
    )

    private fun resolveMouseWheelEvent(e: MouseWheelEvent) = ContinuousMouseWheelEvent(
        e.`when`,
        e.modifiersEx,
        unresolveX(e.x - width / 2),
        unresolveY(e.y - height / 2),
        e.clickCount,
        e.preciseWheelRotation,
        e,
    )


    private inner class DiscreteMouseListener(val parent: ContinuousMouseListener) : MouseListener {
        override fun mouseClicked(e: MouseEvent) = parent.mouseClicked(resolveMouseEvent(e))
        override fun mousePressed(e: MouseEvent) = parent.mousePressed(resolveMouseEvent(e))
        override fun mouseReleased(e: MouseEvent) = parent.mouseReleased(resolveMouseEvent(e))
        override fun mouseEntered(e: MouseEvent) = parent.mouseEntered(resolveMouseEvent(e))
        override fun mouseExited(e: MouseEvent) = parent.mouseExited(resolveMouseEvent(e))
    }

    private inner class DiscreteMouseMotionListener(val parent: ContinuousMouseMotionListener) : MouseMotionListener {
        override fun mouseDragged(e: MouseEvent) = parent.mouseDragged(resolveMouseEvent(e))

        override fun mouseMoved(e: MouseEvent) = parent.mouseMoved(resolveMouseEvent(e))
    }

    private inner class DiscreteMouseWheelListener(val parent: ContinuousMouseWheelListener) : MouseWheelListener {
        override fun mouseWheelMoved(e: MouseWheelEvent) = parent.mouseWheelMoved(resolveMouseWheelEvent(e))
    }

    fun addMouseListener(listener: ContinuousMouseListener) {
        addMouseListener(DiscreteMouseListener(listener))
    }

    fun addMouseMotionListener(listener: ContinuousMouseMotionListener) {
        addMouseMotionListener(DiscreteMouseMotionListener(listener))
    }

    fun addMouseWheelListener(listener: ContinuousMouseWheelListener) {
        addMouseWheelListener(DiscreteMouseWheelListener(listener))
    }

    private var prevX = -1
    private var prevY = -1

    override fun mouseDragged(e: MouseEvent) {
        when (dragType) {
            DragType.Drag -> move(unresolveEdge(e.x - prevX), unresolveEdge(e.y - prevY))
            DragType.NoDrag -> Unit
        }
        prevX = e.x
        prevY = e.y
    }

    override fun mouseMoved(e: MouseEvent) = Unit

    override fun mouseClicked(e: MouseEvent) = Unit

    override fun mousePressed(e: MouseEvent) {
        prevX = e.x
        prevY = e.y
    }

    override fun mouseReleased(e: MouseEvent) {
        prevX = -1
        prevY = -1
    }

    override fun mouseEntered(e: MouseEvent) = Unit

    override fun mouseExited(e: MouseEvent) {
        prevX = -1
        prevY = -1
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        val dz = zoomFactor.pow(e.preciseWheelRotation)
        when (zoomType) {
            ZoomType.ZoomToPoint -> zoomToPixel(dz, e.x, e.y)
            ZoomType.ZoomCentered -> zoom(dz)
            ZoomType.NoZoom -> Unit
        }
    }
}
