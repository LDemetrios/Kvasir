@file:Suppress("unused")

package org.ldemetrios.utilities.graphics


import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

class DragListener(val parent: (Int, Int, Int) -> Unit) : MouseMotionListener, MouseListener {
	private var prevX = -1
	private var prevY = -1
	override fun mouseDragged(e: MouseEvent) {
		parent(e.x - prevX, e.y - prevY, e.modifiersEx)
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
}