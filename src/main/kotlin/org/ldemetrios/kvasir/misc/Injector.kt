package org.ldemetrios.kvasir.misc

import com.intellij.lang.Language
import com.intellij.lang.LanguageUtil
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.childrenOfType
import com.intellij.psi.util.endOffset
import com.intellij.psi.util.startOffset
import org.ldemetrios.kvasir.psi.RawDelimPsiElement
import org.ldemetrios.kvasir.psi.RawLangPsiElement
import org.ldemetrios.kvasir.psi.RawPsiElement


class RawLangInjector : MultiHostInjector {
    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        if (context !is RawPsiElement) return

        val injectedLanguage =
            findLanguageByTag(context.childrenOfType<RawLangPsiElement>().firstOrNull()?.text ?: return)
        if (injectedLanguage == null) return

        val start = context.childrenOfType<RawLangPsiElement>().firstOrNull()?.endOffset ?: return
        val end = context.lastChild.takeIf { it is RawDelimPsiElement }?.startOffset ?: return
        registrar.startInjecting(injectedLanguage);
        registrar.addPlace("", "", context, TextRange(start - context.startOffset, end - context.startOffset));
        registrar.doneInjecting();
    }

    override fun elementsToInjectIn(): MutableList<Class<out PsiElement>> {
        return mutableListOf(RawPsiElement::class.java)
    }
}

fun findLanguageByTag(tag: String): Language? {
    val lang = Language.findLanguageByID(tag)
    if (lang != null) {
        return lang
    }

    val fileType: FileType = FileTypeManager.getInstance().getFileTypeByExtension(tag)
    if (fileType is LanguageFileType) {
        return fileType.language
    }

    return LanguageUtil.getFileTypeLanguage(fileType)
}
