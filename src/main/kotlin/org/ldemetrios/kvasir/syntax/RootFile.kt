package org.ldemetrios.kvasir.syntax

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import com.intellij.openapi.fileTypes.FileType;
import org.ldemetrios.kvasir.language.TypstFileType
import org.ldemetrios.kvasir.language.TypstLanguage


class TypstFile( viewProvider: FileViewProvider) :
    PsiFileBase(viewProvider, TypstLanguage.INSTANCE) {
    override fun getFileType(): FileType {
        return TypstFileType.INSTANCE
    }

    override fun toString(): String {
        return "Typst File"
    }
}
