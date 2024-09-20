package org.ldemetrios.kvasir.preview

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.readText
import com.intellij.psi.PsiFile
import org.ldemetrios.kvasir.preview.watch.ErrorSeverity
import org.ldemetrios.kvasir.preview.watch.WatchServer
import org.ldemetrios.kvasir.syntax.TypstFile

data class Info(val path: String, val content: String)
data class EntryRetraced(val severity: ErrorSeverity, val file: String, val start: Int, val end: Int, val message:String)

class CompilationErrorAnnotator : ExternalAnnotator<VirtualFile, List<List<EntryRetraced>>>() {
    override fun collectInformation(file: PsiFile): VirtualFile? {
        if (file !is TypstFile) return null
        return file.virtualFile
    }

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): VirtualFile? =
        collectInformation(file)

    override fun doAnnotate(collectedInfo: VirtualFile?): List<List<EntryRetraced>>? {
        println("doAnnotate")
        if (collectedInfo == null) return null
        val lines = collectedInfo.readText().split(Regex("\r\n|[\r\n\u2028\u2029\u0085]" /*"(?<=(\r\n|[\r\n\u2028\u2029\u0085]))"*/))
        var lengths = lines.mapTo(mutableListOf(0)) { it.length }
        for (i in 1 until lengths.size) {
            lengths[i] += lengths[i - 1] + 1
        }
        val path = collectedInfo.path

        val retracedStacks = mutableListOf<List<EntryRetraced>>()

        for ((key, watch) in WatchServer.map) {
            val (file, root) = key
            // TODO keep annotations when problem is not in this file
            if (file != path) continue
            val trace = watch.deref().lastCompilationReport?.trace ?: continue
            for (stacktrace in trace) {
                val retraced = stacktrace.entries.map {
                    println("$file ~= $root ///// ${it.file}")
                    EntryRetraced(
                        it.severity,
                        it.file,
                        lengths[it.startLine] + it.startColumn,
                        lengths[it.endLine] + it.endColumn,
                        it.message
                    )
                }
                retracedStacks.add(retraced)
            }
        }
        return retracedStacks
    }

    override fun apply(file: PsiFile, annotationResult: List<List<EntryRetraced>>?, holder: AnnotationHolder) {
        if (annotationResult == null) return

        for (stack in annotationResult) {
            for (entry in stack) {
                holder.newAnnotation(HighlightSeverity.ERROR, entry.message)
                    .range(TextRange(entry.start, entry.end))
                    .create()
            }
        }
    }
}