package org.ldemetrios.kvasir.misc

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import org.ldemetrios.kvasir.language.TypstFileTypeCommon
import org.ldemetrios.kvasir.language.TypstMarkupFileType
import org.ldemetrios.kvasir.psi.modeAt
import org.ldemetrios.tyko.compiler.SyntaxMode

class Surrounder : TypedHandlerDelegate() {
    override fun beforeSelectionRemoved(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        if (file.fileType !is TypstFileTypeCommon) return Result.CONTINUE

        val document = editor.document
        val selectionModel = editor.selectionModel

        if (selectionModel.hasSelection()) {
            val selectedText = selectionModel.selectedText ?: return Result.CONTINUE
            val start = selectionModel.selectionStart
            val end = selectionModel.selectionEnd

            val replacement = when (c) {
                '_', '*', '$' -> "$c$selectedText$c"
                else -> return Result.CONTINUE
            }

            runWriteCommandAction(project) {
                document.replaceString(start, end, replacement)
            }

            editor.caretModel.moveToOffset(start + replacement.length)

            return Result.STOP
        } else {
            val offset = editor.caretModel.offset

            val replacement = when (c) {
                '(' -> "()"
                '[' -> "[]"
                '{' -> "{}"
                '<' -> if (file.modeAt(offset) == SyntaxMode.Markup) "<>" else return Result.CONTINUE
                ')', ']', '>', '}' -> if (file.charAt(offset) == c.code) "" else return Result.CONTINUE

                '_',
                '*',
                '$' -> when {
                    c.code == file.charAt(offset) -> ""
                    file.modeAt(offset) == SyntaxMode.Markup -> "$c$c"
                    else -> return Result.CONTINUE
                }

                else -> return Result.CONTINUE
            }

            runWriteCommandAction(project) {
                document.insertString(offset, replacement)
            }

            editor.caretModel.moveToOffset(offset + 1)

            return Result.STOP
        }

        return Result.CONTINUE
    }
}

fun PsiFile.charAt(offset: Int): Int =
    if (offset == this.textLength) -1 else fileDocument.getText(TextRange(offset, offset + 1))[0].code