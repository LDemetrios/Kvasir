package org.ldemetrios.utilities.graphics


interface ContinuousMouseMotionListener {
    fun mouseDragged(e: ContinuousMouseEvent)
    fun mouseMoved(e: ContinuousMouseEvent)
}

interface ContinuousMouseListener {
    fun mouseClicked(e: ContinuousMouseEvent)
    fun mousePressed(e: ContinuousMouseEvent)
    fun mouseReleased(e: ContinuousMouseEvent)
    fun mouseEntered(e: ContinuousMouseEvent)
    fun mouseExited(e: ContinuousMouseEvent)
}

interface ContinuousMouseWheelListener {
    fun mouseWheelMoved(e: ContinuousMouseWheelEvent)
}
