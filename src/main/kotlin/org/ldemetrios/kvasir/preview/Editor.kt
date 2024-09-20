package org.ldemetrios.kvasir.preview

import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.ui.JBColor
import org.ldemetrios.kvasir.preview.watch.WatchServer
import java.beans.PropertyChangeListener
import javax.swing.*

class TestProvider : FileEditorProvider, DumbAware {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file.extension == "typ"
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        try {
            val ed = TextEditorProvider.getInstance().createEditor(project, file);
            return TextEditorWithPreview(ed as TextEditor, TestFileEditor(file, project))
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }

    override fun getEditorTypeId(): String = "KVASIR_TEST_EDITOR_BLAH_BLAH"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
}



class TestFileEditor(val f: VirtualFile, val project: Project) : FileEditor {
    val watch = WatchServer.register(f.path, project.basePath!!)
    val RENDERER = SvgViewerPanel(watch)
    val COMPONENT = ZoomablePanel(RENDERER, JBColor.WHITE, JBColor.BLACK, 10)

    init {
        watch.deref().onSuccessDo {
            RENDERER.reload()
            COMPONENT.repaint()
        }
    }

    init {
        viewers.add(COMPONENT)
    }

    init {
        PsiManager.getInstance(project).findFile(f)?.viewProvider?.document?.addDocumentListener(DocReloader)
            ?: println("AAAH NULL")
    }

    val map = mutableMapOf<Key<*>, Any?>()
    override fun <T : Any?> getUserData(key: Key<T>): T? {
        println("Get $key")
        return map.get(key) as T?
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        println("Put $key = $value")
        map[key] = value
    }

    override fun dispose() {
        RENDERER.close()
        viewers.remove(COMPONENT)
    }

    override fun getComponent(): JComponent {
        return COMPONENT
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return COMPONENT
    }

    override fun getName(): String {
        return "Test Fucking Editor"
    }

    override fun setState(state: FileEditorState) {
        println("Set State $state")
    }

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        println("Add listener $listener")
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        println("Remove listener $listener")
    }

    override fun getFile(): VirtualFile {
        return f
    }
}


