@file:Suppress("unused")

package org.ldemetrios.utilities.graphics

import org.ldemetrios.utilities.recreate
import java.awt.Component
import java.awt.GridLayout
import java.awt.event.*
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.KeyStroke
import javax.swing.border.Border
import kotlin.system.exitProcess

interface Editor {
	fun component(): Component
	fun getContent(): String
	fun setContent(text: String)
}

const val CTRL = KeyEvent.CTRL_DOWN_MASK
const val SHIFT = KeyEvent.SHIFT_DOWN_MASK
const val ALT = KeyEvent.ALT_DOWN_MASK

class Window(val editor: Editor, border: Border) : JFrame("Untitled") {
	private var text = ""
	private var file: File? = null

	fun openFile(f: File) {
		if (text != editor.getContent()) {
			if (!askForSave()) return
		}
		file = f
		text = f.readText()
		editor.setContent(text)
		title = f.name
	}

	/**
	 * @return Successfully (User didn't press "Cancel")
	 */
	private fun askForSave(): Boolean {
		println("Ask for save")

		val ans = JOptionPane.showConfirmDialog(
			this,
			"Unsaved changes. Save?",
		)
		return when (ans) {
			JOptionPane.YES_OPTION    -> {
				if (file != null) save()
				else saveAs()
			}

			JOptionPane.NO_OPTION     -> true
			JOptionPane.CANCEL_OPTION -> false
			else                      -> throw IllegalStateException()
		}.also { println("askForSave returns $it") }
	}

	private fun save(): Boolean {
		println("Save")

		if (text == editor.getContent()) return true.also { println("save returns $it") }
		if (file == null) return saveAs().also { println("save returns $it") }
		else file!!.also {
			text = editor.getContent()
			it.recreate()
			it.writeText(text)
			return true.also { println("save returns $it") }
		}
	}

	private fun open() {
		println("Open")

		if (text == editor.getContent()) {
			if (!askForSave()) return
		}
		val chooser = JFileChooser(file?.parentFile ?: File("G:"))
		when (chooser.showOpenDialog(this)) {
			JFileChooser.CANCEL_OPTION  -> return

			JFileChooser.APPROVE_OPTION -> {
				val f = chooser.selectedFile
				openFile(f)
			}

			JFileChooser.ERROR_OPTION   -> {
				JOptionPane.showMessageDialog(this, "Error while selecting file")
			}
		}
	}

	private fun saveAs(): Boolean {
		println("Save as")
		if (text == editor.getContent()) {
			if (!askForSave()) return false.also { println("saveAs returns $it") }
		}
		val chooser = JFileChooser(file?.parentFile ?: File("G:"))
		return when (chooser.showSaveDialog(this)) {
			JFileChooser.CANCEL_OPTION  -> false

			JFileChooser.APPROVE_OPTION -> {
				file = chooser.selectedFile
				save()
			}

			JFileChooser.ERROR_OPTION   -> {
				JOptionPane.showMessageDialog(this, "Error while selecting file")
				false
			}

			else                        -> throw IllegalStateException()
		}.also { println("saveAs returns $it") }
	}

	private fun new() {
		if (text == editor.getContent()) {
			if (!askForSave()) return
		}
		file = null
		text = ""
		title = "Untitled"
	}

	init {
		layout = GridLayout(1, 1)
		val panel = JPanel(GridLayout(1, 1))
		panel.border = border
		panel.add(editor.component())
		add(panel)

		val menu = JMenu("File")
		menu.add(newItem("New file", KeyEvent.VK_N, CTRL) { new() })
		menu.add(newItem("Open...", KeyEvent.VK_O, CTRL) { open() })
		menu.add(newItem("Save", KeyEvent.VK_S, CTRL) { save() })
		menu.add(newItem("Save as...", KeyEvent.VK_S, CTRL + SHIFT) { saveAs() })

		val bar = JMenuBar()
		bar.add(menu)
		jMenuBar = bar

		addWindowListener(object : WindowListener {
			override fun windowOpened(e: WindowEvent?) = Unit
			override fun windowClosing(e: WindowEvent?) = close()
			override fun windowClosed(e: WindowEvent?) = close()
			override fun windowIconified(e: WindowEvent?) = Unit
			override fun windowDeiconified(e: WindowEvent?) = Unit
			override fun windowActivated(e: WindowEvent?) = Unit
			override fun windowDeactivated(e: WindowEvent?) = Unit
		})

		pack()
		setSize(800, 900)
		extendedState = extendedState or MAXIMIZED_BOTH
	}

	private fun newItem(text: String, key: Int, mask: Int, func: (ActionEvent) -> Unit): JMenuItem {
		val item = JMenuItem(text)
		item.accelerator = KeyStroke.getKeyStroke(key, mask)
		item.addActionListener(func)
		return item
	}

	private fun close() {
		if (text != editor.getContent()) {
			if (!askForSave()) return Unit.also { println("close returns") }
		}
		exitProcess(0)
	}
}