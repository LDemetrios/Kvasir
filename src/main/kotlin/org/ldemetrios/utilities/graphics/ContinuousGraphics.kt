@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package org.ldemetrios.utilities.graphics

import java.awt.*
import java.text.AttributedCharacterIterator


data class ContinuousRectangle(val x: Double, val y: Double, val width: Double, val height: Double)

class ContinuousGraphics(
    var parent: Graphics,
    var centerX: Double,
    var centerY: Double,
    var scale: Double = 1.0,
    var inverseY: Boolean = true
) {
    fun resolveX(x: Number) = ((x.toDouble() + translationY - centerX) * scale).toInt()
    fun resolveY(y: Number) =
        (((if (inverseY) -y.toDouble() else y.toDouble()) + translationY - centerY) * scale).toInt()

    fun resolveEdge(a: Number) = (a.toDouble() * scale).toInt()
    fun unresolveX(x: Int) = x / scale + centerX
    fun unresolveY(y: Int) = y / scale - translationY + centerY
    fun unresolveEdge(a: Int) = a / scale
    fun resolveRectangle(rect: ContinuousRectangle) = Rectangle(
        resolveX(rect.x),
        resolveY(rect.y),
        resolveEdge(rect.width),
        resolveEdge(rect.height)
    )

    fun unresolveRectangle(rect: Rectangle) = ContinuousRectangle(
        unresolveX(rect.x),
        unresolveY(rect.y),
        unresolveEdge(rect.width),
        unresolveEdge(rect.height)
    )

    private var translationX: Double = .0
    private var translationY: Double = .0

    fun translate(dx: Double, dy: Double) {
        translationX += dx * scale
        translationY += dy * scale
    }

    var color: Color by parent::color

    fun setPaDoubleMode() = parent.setPaintMode()

    fun setXORMode(p0: Color?) = parent.setXORMode(p0)

    var font: Font by parent::font

    fun getFontMetrics(p0: Font?): FontMetrics = parent.getFontMetrics(p0)

    val clipBounds: ContinuousRectangle get() = unresolveRectangle(parent.clipBounds)

    fun clipRect(x: Double, y: Double, width: Double, height: Double) =
        parent.clipRect(resolveX(x), resolveY(y), resolveEdge(width), resolveEdge(height))

    fun setClip(x: Double, y: Double, width: Double, height: Double) =
        parent.setClip(resolveX(x), resolveY(y), resolveEdge(width), resolveEdge(height))

//     fun setClip(p0: Shape?) {
//        TODO("Not yet implemented")
//    }
//
//     fun getClip(): Shape {
//        TODO("Not yet implemented")
//    }

    fun copyArea(x: Double, y: Double, width: Double, height: Double, dx: Double, dy: Double) = parent.copyArea(
        resolveX(x),
        resolveY(y),
        resolveEdge(width),
        resolveEdge(height),
        resolveEdge(dx),
        resolveEdge(dy)
    )

    fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double) =
        parent.drawLine(resolveX(x1), resolveY(y1), resolveX(x2), resolveY(y2))

    fun drawRect(x: Double, y: Double, width: Double, height: Double) =
        parent.drawRect(resolveX(x), resolveY(y), resolveEdge(width), resolveEdge(height))

    fun fillRect(x: Double, y: Double, width: Double, height: Double) =
        parent.fillRect(resolveX(x), resolveY(y), resolveEdge(width), resolveEdge(height))


    fun clearRect(x: Double, y: Double, width: Double, height: Double) =
        parent.clearRect(resolveX(x), resolveY(y), resolveEdge(width), resolveEdge(height))

    fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double) =
        parent.drawRoundRect(
            resolveX(x),
            resolveY(y),
            resolveEdge(width),
            resolveEdge(height),
            resolveEdge(arcWidth),
            resolveEdge(arcHeight)
        )

    fun fillRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double) =
        parent.fillRoundRect(
            resolveX(x),
            resolveY(y),
            resolveEdge(width),
            resolveEdge(height),
            resolveEdge(arcWidth),
            resolveEdge(arcHeight)
        )

    fun drawOval(x: Double, y: Double, width: Double, height: Double) =
        parent.drawOval(resolveX(x), resolveY(y), resolveEdge(width), resolveEdge(height))


    fun fillOval(x: Double, y: Double, width: Double, height: Double) =
        parent.fillOval(resolveX(x), resolveY(y), resolveEdge(width), resolveEdge(height))

    fun drawArc(x: Double, y: Double, width: Double, height: Double, startAngle: Double, arcAngle: Double) =
        parent.drawArc(
            resolveX(x),
            resolveY(y),
            resolveEdge(width),
            resolveEdge(height),
            startAngle.toInt(),
            arcAngle.toInt(),
        )

    fun fillArc(x: Double, y: Double, width: Double, height: Double, startAngle: Double, arcAngle: Double) =
        parent.fillArc(
            resolveX(x),
            resolveY(y),
            resolveEdge(width),
            resolveEdge(height),
            startAngle.toInt(),
            arcAngle.toInt(),
        )

    fun drawPolyline(xs: Iterable<Double>, ys: Iterable<Double>) = parent.drawPolyline(
        xs.map { resolveX(it) }.toIntArray(),
        ys.map { resolveY(it) }.toIntArray(),
        minOf(xs.count(), ys.count())
    )


    fun drawPolygon(xs: Iterable<Double>, ys: Iterable<Double>) = parent.drawPolygon(
        xs.map { resolveX(it) }.toIntArray(),
        ys.map { resolveY(it) }.toIntArray(),
        minOf(xs.count(), ys.count())
    )

    fun fillPolygon(xs: Iterable<Double>, ys: Iterable<Double>) = parent.fillPolygon(
        xs.map { resolveX(it) }.toIntArray(),
        ys.map { resolveY(it) }.toIntArray(),
        minOf(xs.count(), ys.count())
    )

    fun drawString(str: String, x: Double, y: Double) = parent.drawString(str, resolveX(x), resolveY(y))

    fun drawString(iterator: AttributedCharacterIterator?, x: Double, y: Double) =
        parent.drawString(iterator, resolveX(x), resolveY(y))

    fun dispose() = parent.dispose()
}

