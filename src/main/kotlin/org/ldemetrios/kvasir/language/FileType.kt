package org.ldemetrios.kvasir.language

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

val T_ICON = IconLoader.getIcon("/icons/logo.svg", TypstMarkupFileType::class.java)

sealed interface TypstFileTypeCommon

class TypstMarkupFileType private constructor() : LanguageFileType(TypstMarkupLanguage.INSTANCE),TypstFileTypeCommon {
    override fun getName(): String = "Typst"
    override fun getDescription(): String = "Typst markup"
    override fun getDefaultExtension(): String = "typ"
    override fun getIcon(): Icon = T_ICON

    @Suppress("CompanionObjectInExtension")
    companion object {
        val INSTANCE: TypstMarkupFileType = TypstMarkupFileType()
    }
}


class TypstCodeFileType private constructor() : LanguageFileType(TypstCodeLanguage.INSTANCE),TypstFileTypeCommon {
    override fun getName(): String = "Typst Code"
    override fun getDescription(): String = "Typst code"
    override fun getDefaultExtension(): String = "typc"
    override fun getIcon(): Icon = T_ICON

    @Suppress("CompanionObjectInExtension")
    companion object {
        val INSTANCE: TypstCodeFileType = TypstCodeFileType()
    }
}


class TypstMathFileType private constructor() : LanguageFileType(TypstMathLanguage.INSTANCE),TypstFileTypeCommon {
    override fun getName(): String = "Typst Math"
    override fun getDescription(): String = "Typst math"
    override fun getDefaultExtension(): String = "typm"
    override fun getIcon(): Icon = T_ICON

    @Suppress("CompanionObjectInExtension")
    companion object {
        val INSTANCE: TypstMathFileType = TypstMathFileType()
    }
}