package org.ldemetrios.kvasir.syntax

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType
import org.ldemetrios.withFrontendRuntime
import org.ldemetrios.tyko.compiler.*
import org.ldemetrios.utilities.cast


class TypstLexer(val mode: SyntaxMode) : LexerBase() {
    private var tokenType: IElementType? = null
    private var text: CharSequence? = null

    private var tokenStart = 0
    private var tokenEnd = 0

    private var bufferStart = 0
    private var bufferEnd = 0
    private var myState = 0

    private var stack = mutableListOf<SyntaxKind>()
    private lateinit var marks: List<IndexedMark>
    private var curMark = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        text = buffer
        tokenEnd = startOffset
        tokenStart = tokenEnd
        bufferStart = startOffset
        bufferEnd = endOffset
        tokenType = null
        stack = mutableListOf()
        val text = buffer.subSequence(startOffset, endOffset).toString()
        marks = withFrontendRuntime { parseSyntax(text, mode).marks }
        curMark = 0
        myState = if (initialState == 0) 0 else throw UnsupportedOperationException("Couldn't restart")
    }

    override fun getState(): Int {
        locateToken()
        return if (tokenStart == bufferEnd) 0 else 1
    }

    override fun getTokenType(): IElementType? {
        locateToken()
        return tokenType
    }

    override fun getTokenStart(): Int {
        locateToken()
        return tokenStart
    }

    override fun getTokenEnd(): Int {
        locateToken()
        return tokenEnd
    }

    override fun advance() {
        locateToken()
        tokenType = null
    }

    override fun getBufferSequence(): CharSequence {
        return text!!
    }

    override fun getBufferEnd(): Int {
        return bufferEnd
    }

    private fun locateToken() {
        if (tokenType != null) return

        while (curMark < marks.size && marks[curMark].mark is SyntaxMark.NodeEnd) {
            curMark++
            stack.removeLast()
        }
        while (curMark < marks.size && marks[curMark].mark is SyntaxMark.NodeStart) {
            stack.add(marks[curMark].mark.cast<SyntaxMark.NodeStart>().kind)
            curMark++
        }

        if (curMark == marks.size) {
            tokenStart = bufferEnd
            tokenEnd = bufferEnd
            tokenType = null
            return
        }

        if (marks[curMark].mark is SyntaxMark.Error) {
            tokenStart = marks[curMark].index + bufferStart
            tokenEnd = marks[curMark + 1].index + bufferStart
            // take end
            curMark += 2
            tokenType = SyntaxKind.Error.tokenType
        } else {
            tokenStart = marks[curMark - 1].index + bufferStart
            tokenEnd = marks[curMark].index + bufferStart
            tokenType = (marks[curMark - 1].mark as SyntaxMark.NodeStart).kind.tokenType
            curMark++
            if (reinterpretSpace()) tokenType = TYPST_WHITESPACE
            stack.removeLast()
        }
    }


    private fun reinterpretSpace(): Boolean {
        var idx = stack.lastIndex
        if (idx == -1 || stack[idx] != SyntaxKind.Space) return false
        while (idx > 0) {
            when (val cur = stack[idx]) {
                SyntaxKind.Space -> Unit
                SyntaxKind.Error,
                SyntaxKind.Shebang,
                SyntaxKind.LineComment,
                SyntaxKind.BlockComment,
                SyntaxKind.Text,
                SyntaxKind.Linebreak,
                SyntaxKind.Parbreak,
                SyntaxKind.Escape,
                SyntaxKind.Shorthand,

                SyntaxKind.Raw,
                SyntaxKind.RawLang,
                SyntaxKind.RawDelim,
                SyntaxKind.RawTrimmed,
                SyntaxKind.Link,
                SyntaxKind.Label,
                SyntaxKind.RefMarker,
                SyntaxKind.SmartQuote,
                SyntaxKind.HeadingMarker,
                SyntaxKind.ListMarker,
                SyntaxKind.Bang,
                SyntaxKind.EnumMarker,
                SyntaxKind.TermMarker,
                SyntaxKind.End -> {
                    AssertionError("Unreachable $cur").printStackTrace()
                    return false
                }

                SyntaxKind.Markup -> return false   // Text space
                SyntaxKind.Strong -> return false
                SyntaxKind.Emph -> return false
                SyntaxKind.Ref -> return false
                SyntaxKind.Heading -> return false
                SyntaxKind.ListItem -> return false
                SyntaxKind.EnumItem -> return false
                SyntaxKind.TermItem -> return false
                SyntaxKind.Equation -> return false
                SyntaxKind.Math -> return false
                SyntaxKind.MathText -> return false
                SyntaxKind.MathIdent -> return false
                SyntaxKind.MathShorthand -> return false
                SyntaxKind.MathAlignPoint -> return false
                SyntaxKind.MathDelimited -> return false
                SyntaxKind.MathAttach -> return false
                SyntaxKind.MathPrimes -> return false
                SyntaxKind.MathFrac -> return false
                SyntaxKind.MathRoot -> return false

                SyntaxKind.Hash,
                SyntaxKind.LeftBrace,
                SyntaxKind.RightBrace,
                SyntaxKind.LeftBracket,
                SyntaxKind.RightBracket,
                SyntaxKind.LeftParen,
                SyntaxKind.RightParen,
                SyntaxKind.Comma,
                SyntaxKind.Semicolon,
                SyntaxKind.Colon,
                SyntaxKind.Star,
                SyntaxKind.Underscore,
                SyntaxKind.Dollar,
                SyntaxKind.Plus,
                SyntaxKind.Minus,
                SyntaxKind.Slash,
                SyntaxKind.Hat,
                SyntaxKind.Dot,
                SyntaxKind.Eq,
                SyntaxKind.EqEq,
                SyntaxKind.ExclEq,
                SyntaxKind.Lt,
                SyntaxKind.LtEq,
                SyntaxKind.Gt,
                SyntaxKind.GtEq,
                SyntaxKind.PlusEq,
                SyntaxKind.HyphEq,
                SyntaxKind.StarEq,
                SyntaxKind.SlashEq,
                SyntaxKind.Dots,
                SyntaxKind.Arrow,
                SyntaxKind.Root,
                SyntaxKind.Not,
                SyntaxKind.And,
                SyntaxKind.Or,
                SyntaxKind.None,
                SyntaxKind.Auto,
                SyntaxKind.Let,
                SyntaxKind.Set,
                SyntaxKind.Show,
                SyntaxKind.Context,
                SyntaxKind.If,
                SyntaxKind.Else,
                SyntaxKind.For,
                SyntaxKind.In,
                SyntaxKind.While,
                SyntaxKind.Break,
                SyntaxKind.Continue,
                SyntaxKind.Return,
                SyntaxKind.Import,
                SyntaxKind.Include,
                SyntaxKind.As,
                SyntaxKind.Ident,
                SyntaxKind.Bool,
                SyntaxKind.Int,
                SyntaxKind.Float,
                SyntaxKind.Numeric,
                SyntaxKind.Str -> {
                    AssertionError("Unreachable $cur").printStackTrace()
                    return false
                }

                SyntaxKind.ContentBlock -> return false // Content space

                SyntaxKind.Args -> Unit
                SyntaxKind.FuncCall -> Unit
                SyntaxKind.FieldAccess -> Unit

                SyntaxKind.Code,
                SyntaxKind.CodeBlock,
                SyntaxKind.Parenthesized,
                SyntaxKind.Array,
                SyntaxKind.Dict,
                SyntaxKind.Named,
                SyntaxKind.Keyed,
                SyntaxKind.Unary,
                SyntaxKind.Binary,
                SyntaxKind.FieldAccess,
                SyntaxKind.Args,
                SyntaxKind.Spread,
                SyntaxKind.Closure,
                SyntaxKind.Params,
                SyntaxKind.LetBinding,
                SyntaxKind.SetRule,
                SyntaxKind.ShowRule,
                SyntaxKind.Contextual,
                SyntaxKind.Conditional,
                SyntaxKind.WhileLoop,
                SyntaxKind.ForLoop,
                SyntaxKind.ModuleImport,
                SyntaxKind.ImportItems,
                SyntaxKind.ImportItemPath,
                SyntaxKind.RenamedImportItem,
                SyntaxKind.ModuleInclude,
                SyntaxKind.LoopBreak,
                SyntaxKind.LoopContinue,
                SyntaxKind.FuncReturn,
                SyntaxKind.Destructuring,
                SyntaxKind.DestructAssignment -> {
                    return true
                }
            }
            idx--
        }
        return false
    }

    override fun toString(): String {
        return "Typst Lexer"
    }
}
