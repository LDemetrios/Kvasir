@file:Suppress("unused")

package org.ldemetrios.utilities.graphics

import java.awt.Component
import java.awt.Container
import java.awt.GridLayout
import java.awt.event.*
import java.util.*
import javax.swing.JFrame
import javax.swing.WindowConstants

fun canvasFrame(canvas: Component, title: String): JFrame {
    val frame = JFrame(title)
    frame.layout = GridLayout(1, 1)
    frame.add(canvas)
    return frame
}

fun JFrame.showFrame() = also { isVisible = true }
fun JFrame.hideFrame() = also { isVisible = false }
fun JFrame.setDisposeOnClose() = also { defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE }
fun JFrame.setExitOnClose() = also { defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE }
fun JFrame.maximize() = also { extendedState = extendedState or JFrame.MAXIMIZED_BOTH }
fun JFrame.unmaximize() = also { extendedState = extendedState and JFrame.MAXIMIZED_BOTH.inv() }

fun Component.addSomewhatCommonListener(listener: EventListener) {
    if (listener is ComponentListener) addComponentListener(listener)
    if (listener is ContainerListener && this is Container) addContainerListener(listener)
    if (listener is FocusListener) addFocusListener(listener)
    if (listener is HierarchyBoundsListener) addHierarchyBoundsListener(listener)
    if (listener is HierarchyListener) addHierarchyListener(listener)
    if (listener is InputMethodListener) addInputMethodListener(listener)
    if (listener is KeyListener) addKeyListener(listener)
    if (listener is MouseListener) addMouseListener(listener)
    if (listener is MouseMotionListener) addMouseMotionListener(listener)
    if (listener is MouseWheelListener) addMouseWheelListener(listener)
}