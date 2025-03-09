@file:Suppress("unused")

package org.ldemetrios.utilities.graphics

import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

class ContinuousMouseEvent(
    val `when`: Long,
    val modifiersEx: Int,
    val x: Double,
    val y: Double,
    val clickCount: Int,
    val parent: MouseEvent,
)

class ContinuousMouseWheelEvent(
    val `when`: Long,
    val modifiersEx: Int,
    val x: Double,
    val y: Double,
    val clickCount: Int,
    val preciseWheelRotation: Double,
    val parent: MouseWheelEvent,
)

