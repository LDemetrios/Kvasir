package org.ldemetrios.kvasir.syntax

import com.intellij.lexer.FlexAdapter


class TypstLexerAdapter internal constructor(internal val underlying: TypstLexer = TypstLexer(null)) : FlexAdapter(underlying) {

}