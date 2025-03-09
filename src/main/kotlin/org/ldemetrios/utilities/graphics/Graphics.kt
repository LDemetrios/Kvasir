@file:Suppress("unused")

package org.ldemetrios.utilities.graphics

import java.awt.Component
import java.awt.Graphics
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTable
import kotlin.math.pow

fun drawBezier(g: Graphics, xs: DoubleArray, ys: DoubleArray, steps: Int) {
    require(xs.size == ys.size) { "xs and ys are supposed to be of equal length" }
    val n = xs.size - 1
    val cn = IntArray(n + 1)
    cn[0] = 1
    for (k in 1..n) {
        cn[k] = cn[k - 1] * (1 - k + n) / k
    }
    val step = 1.0 / steps
    val resXs = IntArray(steps + 1)
    val resYs = IntArray(steps + 1)
    for (i in 0..<steps) {
        resXs[i] = count(xs, n, cn, step * i).toInt()
        resYs[i] = count(ys, n, cn, step * i).toInt()
    }
    resXs[steps] = xs[n].toInt()
    resYs[steps] = ys[n].toInt()
    g.drawPolyline(resXs, resYs, steps + 1)
}

private fun count(coords: DoubleArray, n: Int, cn: IntArray, t: Double): Double {
    var sum = 0.0
    for (i in 0..n) {
        sum += cn[i] * t.pow(i.toDouble()) * (1 - t).pow((n - i).toDouble()) * coords[i]
    }
    return sum
}

fun createCanvasWindow(canvas: Component?, title: String?): JFrame {
    val frame = JFrame(title)
    frame.layout = GridLayout(1, 1)
    frame.add(canvas)
    frame.pack()
    return frame
}

fun createTableWindow(columns: Array<String?>?, data: Array<Array<Any?>?>?, title: String?): JFrame {
    val frame = JFrame(title)
    val table = JTable(data, columns)
    val scrollPane = JScrollPane(table)
    table.fillsViewportHeight = true
    frame.add(scrollPane)
    frame.pack()
    return frame
}