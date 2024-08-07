package org.ldemetrios.kvasir.syntax

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import org.ldemetrios.kvasir.language.TypstLanguage


class TypstTokenType(debugName: @NonNls String) : IElementType(debugName, TypstLanguage.INSTANCE) {
    override fun toString(): String {
        return "TypstTokenType." + super.toString()
    }
}