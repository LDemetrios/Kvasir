package org.ldemetrios.kvasir.language

import com.intellij.lang.Language

class TypstMarkupLanguage : Language("Typst") {
    companion object {
        @JvmStatic
        val INSTANCE: TypstMarkupLanguage = TypstMarkupLanguage()
    }
}


class TypstCodeLanguage : Language("TypstCode") {
    companion object {
        @JvmStatic
        val INSTANCE: TypstCodeLanguage = TypstCodeLanguage()
    }
}

class TypstMathLanguage : Language("TypstMath") {
    companion object {
        @JvmStatic
        val INSTANCE: TypstMathLanguage = TypstMathLanguage()
    }
}