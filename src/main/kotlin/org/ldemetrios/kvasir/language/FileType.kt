package org.ldemetrios.kvasir.language

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon


class TypstFileType private constructor() : LanguageFileType(TypstLanguage.INSTANCE) {

    override fun getName(): String {
        return "Typst File"
    }

    override fun getDescription(): String {
        return "Typst language file"
    }

    override fun getDefaultExtension(): String {
        return "typ"
    }

    override fun getIcon(): Icon {
        return T_ICON
    }

    companion object {
//        val T_ICON = IconLoader.getIcon("/META-INF/pluginIcon.svg", TypstFileType::class.java)
        val T_ICON = IconLoader.getIcon("/icons/logo.svg", TypstFileType::class.java)

        val INSTANCE: TypstFileType = TypstFileType()
    }
}