package org.ldemetrios.kvasir.preview.data

import com.intellij.codeInspection.InspectionsBundle
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import org.apache.commons.lang3.SystemProperties
import org.ldemetrios.kvasir.language.TypstCodeFileType
import org.ldemetrios.kvasir.language.TypstFileTypeCommon
import org.ldemetrios.kvasir.language.TypstMathFileType
import org.ldemetrios.kvasir.syntax.TypstCodeFile
import org.ldemetrios.kvasir.syntax.TypstMarkupFile
import org.ldemetrios.kvasir.syntax.TypstMathFile
import org.ldemetrios.tyko.compiler.Span
import org.ldemetrios.tyko.compiler.Tracepoint
import org.ldemetrios.tyko.mapByteIndicesToCharIndices
import java.io.File
import java.nio.file.Path


class CompilationErrorAnnotator : ExternalAnnotator<VirtualFile, List<CompiledDoc>>() {
    override fun collectInformation(file: PsiFile): VirtualFile? {
        if (file.fileType !is TypstFileTypeCommon) return null
        return file.virtualFile
    }

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): VirtualFile? =
        collectInformation(file)

    override fun doAnnotate(collectedInfo: VirtualFile?): List<CompiledDoc>? {
        collectedInfo ?: return null

        return getCompiled(collectedInfo).values.toList()
    }

    override fun apply(file: PsiFile, annotationResult: List<CompiledDoc>?, holder: AnnotationHolder) {
        val shift = when (file) {
            is TypstMarkupFile -> 0
            is TypstCodeFile -> 3
            is TypstMathFile -> 2
            else -> 0
        }
        if (annotationResult == null) return

        val range = file.textRange
        val from = range.startOffset
        val to = range.endOffset

        val byteIndices = annotationResult
            .flatMap { it.errors + it.warnings }
            .flatMap { it.trace.map { it.span } + it.span }
            .flatMap { listOf(it.startInd.toInt() - shift, it.endInd.toInt() - shift) }
            .toIntArray()

        byteIndices.sort()

        val charIndices = mapByteIndicesToCharIndices(file.text, byteIndices)

        val map = byteIndices.indices.associate { i -> byteIndices[i] to charIndices[i] }

        fun Long.resolve() = map[this.toInt()]!!.clip(from, to - 1)
        val filePath = ("/" + Path.of(file.project.basePath).relativize(file.virtualFile.toNioPath()).toString()).split("/")
        val errors = annotationResult.flatMap { it.errors }.sumOf { it.trace.size + 1 }

        fun annotate(error: Boolean, idx: Int, span: Span, message: String) {
            val thisFile = span.file?.pack == null && span.file?.path?.split(File.separator) == filePath
            val range = if (thisFile) {
                TextRange((span.startInd - shift).resolve(), (span.endInd - shift).resolve())
            } else {
                TextRange(0, 0)
            }
            holder.newAnnotation(
                if (error) error(idx) else warning(idx),
                span.file?.toString()?.let { "$it: " }.orEmpty() + message
            )
                .range(range)
                .highlightType(
                    when {
                        !thisFile -> ProblemHighlightType.INFORMATION
                        error -> ProblemHighlightType.GENERIC_ERROR
                        else -> ProblemHighlightType.WARNING
                    }
                )
                .create()
        }

        var idx = 401 + errors
        for (docData in annotationResult) {
            for (error in docData.errors) {
                annotate(true, idx, error.span, error.message)
                idx--
                for (tracepoint in error.trace) {
                    annotate(true, idx, tracepoint.span, tracepoint.v.message())
                    idx--
                }
            }
        }
        idx = 399

        for (docData in annotationResult) {
            for (error in docData.warnings) {
                annotate(false, idx, error.span, error.message)
                idx--
                for (tracepoint in error.trace) {
                    annotate(false, idx, tracepoint.span, tracepoint.v.message())
                    idx--
                }
            }
        }
    }
}


fun error(index: Int) = HighlightSeverity(
    "ERROR",
    index,
    InspectionsBundle.messagePointer("error.severity"),
    InspectionsBundle.messagePointer("error.severity.capitalized"),
    InspectionsBundle.messagePointer("error.severity.count.message")
);

fun warning(index: Int) = HighlightSeverity(
    "WARNING",
    index,
    InspectionsBundle.messagePointer("warning.severity"),
    InspectionsBundle.messagePointer("warning.severity.capitalized"),
    InspectionsBundle.messagePointer("warning.severity.count.message")
);


private fun Int.clip(start: Int, end: Int): Int = if (this < start) start else if (this > end) end else this.toInt()

private fun Tracepoint.message() = when (this) {
    is Tracepoint.Call -> "occurred in this call of function `${this.function}`"
    Tracepoint.Import -> "occured in this import"
    is Tracepoint.Show -> "occurred while applying show rule to this ${this.string}"
}
