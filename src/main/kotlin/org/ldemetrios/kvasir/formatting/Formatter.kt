package org.ldemetrios.kvasir.formatting

import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.psi.PsiFile
import org.ldemetrios.sharedLib
import org.ldemetrios.kvasir.language.TypstCodeFileType
import org.ldemetrios.kvasir.language.TypstMarkupFileType
import org.ldemetrios.kvasir.language.TypstMathFileType
import org.ldemetrios.kvasir.settings.AppSettings
import org.ldemetrios.kvasir.util.*
import org.ldemetrios.tyko.compiler.format
import java.util.*

abstract class TypstFormatterCommon : AsyncDocumentFormattingService() {
    override fun createFormattingTask(formattingRequest: AsyncFormattingRequest): FormattingTask {
        val text = formattingRequest.documentText
        return object : FormattingTask {
            override fun run() {
                val state = AppSettings.instance.state
                val converted = this@TypstFormatterCommon.format(text, state.textWidth, state.tabSize)
                formattingRequest.onTextReady(converted)
            }

            override fun cancel(): Boolean {
                return true
            }

            override fun isRunUnderProgress(): Boolean {
                return true
            }
        }
    }

    abstract fun format(text: String, textWidth: Int, tabSize: Int): String

    override fun getFeatures(): Set<FormattingService.Feature> = FEATURES

    override fun getNotificationGroupId(): String = "Typst"

    override fun getName(): String = "Typstyle"

}

private val FEATURES: MutableSet<FormattingService.Feature> =
    EnumSet.noneOf(FormattingService.Feature::class.java)

class TypstMarkupFormatter : TypstFormatterCommon() {
    override fun format(text: String, textWidth: Int, tabSize: Int) = sharedLib?.format(text, textWidth, tabSize) ?: text
    override fun canFormat(file: PsiFile): Boolean = file.fileType is TypstMarkupFileType
}

abstract class SurroundingFormatter(val prefix: String, val suffix: String) : TypstFormatterCommon() {
    override fun format(text: String, textWidth: Int, tabSize: Int): String {
        val prepared = "#{\n$text\n}"
        val result = sharedLib?.format(prepared, textWidth + tabSize, tabSize)?.trim() ?: return text
        if (result.lines().size == 1) {
            return result.removePrefix(prefix).removeSuffix(suffix).trim() + "\n"
        } else {
            val tab = " ".repeat(tabSize)
            return result
                .removePrefix(prefix).removeSuffix(suffix)
                .lines()
                .dropWhile { it.isBlank() }
                .dropLastWhile { it.isBlank() }
                .map { it.removePrefix(tab) }
                .joinToString("") { it + "\n" }
        }
    }
}

class TypstCodeFormatter : SurroundingFormatter("#{", "}") {
    override fun canFormat(file: PsiFile): Boolean = file.fileType is TypstCodeFileType
}

class TypstMathFormatter : SurroundingFormatter("$", "$") {
    override fun canFormat(file: PsiFile): Boolean = file.fileType is TypstMathFileType
}