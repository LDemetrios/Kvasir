@file:Suppress("unused")

package org.ldemetrios.utilities.graphics

import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener

class CumulativeWheelListener(
	val parent: (Double) -> Unit,
	val shiftScale: Double = .0,
	val ctrlScale: Double = .0,
	val altScale: Double = .0,
) : MouseWheelListener {
	private var accum = .0
	override fun mouseWheelMoved(e: MouseWheelEvent) {
		accum += e.getRotation(shiftScale, ctrlScale, altScale)
		parent(accum)
	}
}

private fun MouseWheelEvent.getRotation(
	shiftScale: Double,
	ctrlScale: Double,
	altScale: Double,
): Double {
	var appendix = preciseWheelRotation
	if (isShiftDown) appendix *= shiftScale
	if (isControlDown) appendix *= ctrlScale
	if (isAltDown) appendix *= altScale
	return appendix
}

class NonCumulativeWheelListener(
	val parent: (Double) -> Unit,
	val shiftScale: Double = .0,
	val ctrlScale: Double = .0,
	val altScale: Double = .0,
) : MouseWheelListener {
	override fun mouseWheelMoved(e: MouseWheelEvent) {
		parent(e.getRotation(shiftScale, ctrlScale, altScale))
	}
}