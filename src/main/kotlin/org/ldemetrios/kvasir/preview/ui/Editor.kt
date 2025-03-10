package org.ldemetrios.kvasir.preview.ui

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import org.ldemetrios.askForShutdown
import org.ldemetrios.sharedLib
import org.ldemetrios.kvasir.preview.data.KvasirVFSListener
import org.ldemetrios.kvasir.preview.data.ProjectCompilerService
import org.ldemetrios.tyko.compiler.SyntaxMode
import java.beans.PropertyChangeListener
import java.io.File
import java.nio.file.Path
import javax.swing.*

class TypstWithPreviewProvider : FileEditorProvider, DumbAware {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file.extension in listOf("typ", "typc", "typm")
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        if (sharedLib == null) {
            askForShutdown(project)
        }

        try {
            val base = TextEditorProvider.getInstance().createEditor(project, file) as TextEditor;
            val preview = TypstPreviewFileEditor(
                file, project, when (file.extension) {
                    "typ" -> SyntaxMode.Markup
                    "typc" -> SyntaxMode.Code
                    "typm" -> SyntaxMode.Math
                    else -> throw AssertionError()
                }
            )
            return TextEditorWithPreview(
                base, preview,
            ).also {
                it.component.addKeyListener(preview.COMPONENT)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }

    override fun getEditorTypeId(): String = "KVASIR_EDITOR_WITH_PREVIEW"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.HIDE_DEFAULT_EDITOR
}

class TypstPreviewFileEditor(private val f: VirtualFile, val project: Project, val mode: SyntaxMode) : FileEditor, DocumentListener {
    val RENDERER = SvgViewerPanel(listOf() /*For now*/)
    val COMPONENT = ZoomablePanel(RENDERER /*TestPanel(700, 1600)*/)

    init {
        VirtualFileManager.getInstance().addVirtualFileListener(KvasirVFSListener(mode));
        val doc = FileDocumentManager.getInstance().getDocument(f)
        if (doc != null) {
            doc.addDocumentListener(this)
            ProjectCompilerService.getInstance(project).registerDoc(
                File.separator + Path.of(project.basePath).relativize(file.toNioPath()).toString(),
                doc
            )
        }
        reload()
    }

    override fun dispose() {
        RENDERER.close()
        val doc = FileDocumentManager.getInstance().getDocument(f)
        if (doc != null) {
            ProjectCompilerService.getInstance(project).unregisterDoc(
                File.separator + Path.of(project.basePath).relativize(file.toNioPath()).toString()
            )
        }
    }

    private var bulk = false

    override fun bulkUpdateStarting(document: Document) {
        bulk = true
    }

    override fun bulkUpdateFinished(document: Document) {
        bulk = false
        reload()
    }

    override fun documentChanged(event: DocumentEvent) {
        if (!bulk) reload()
    }

    fun reload() {
        val service: ProjectCompilerService = ProjectCompilerService.getInstance(project)
        service.scheduleRecompile(f, this, mode)
    }

    val map = mutableMapOf<Key<*>, Any?>()
    override fun <T : Any?> getUserData(key: Key<T>): T? = map[key] as T?
    override fun <T : Any?> putUserData(key: Key<T>, value: T?) = map.set(key, value)
    override fun getComponent(): JComponent = COMPONENT
    override fun getPreferredFocusedComponent(): JComponent? = COMPONENT
    override fun getName(): String = "TypstPreviewFileEditor"
    override fun isModified(): Boolean = false
    override fun setState(state: FileEditorState) = Unit // println("Set State $state")
    override fun isValid(): Boolean = true
    override fun addPropertyChangeListener(listener: PropertyChangeListener) = Unit
    override fun removePropertyChangeListener(listener: PropertyChangeListener) = Unit
    override fun getFile(): VirtualFile = f
}

