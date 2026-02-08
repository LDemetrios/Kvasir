package org.ldemetrios.kvasir.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.childrenOfType
import com.intellij.psi.util.firstLeaf
import org.ldemetrios.kvasir.syntax.TypstElementType
import org.ldemetrios.kvasir.syntax.tokenType
import org.ldemetrios.tyko.compiler.SyntaxKind

sealed interface TypstPsiElement : NavigatablePsiElement {
    fun accept(visitor: TypstVisitor)

    val kind get() = (node.elementType as? TypstElementType)?.kind
}

sealed class ATypstPsiElement(node: ASTNode) : ASTWrapperPsiElement(node), TypstPsiElement {
    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is TypstVisitor) accept(visitor)
        else super.accept(visitor)
    }
}

val PsiElement.typ get() = this as? TypstPsiElement

sealed interface MarkupPart
sealed interface CodePart
sealed interface MathPart
sealed interface Ignored
sealed interface RawPart
sealed interface Keyword
sealed interface BinOp
sealed interface UnOp
sealed interface AssignOp
sealed interface Expr : CodePart, Param
sealed interface Literal : Expr
sealed interface KeywordLiteral : Keyword, Literal
sealed interface NumericLiteral : Literal
sealed interface Param
sealed interface ImportPart

/**
 * An invalid sequence of characters.
 */
class ErrorPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitError(this)
    }
}

/**
 * A shebang: `#! ...`
 *
 * Is always a Leaf.
 */
class ShebangPsiElement(node: ASTNode) : ATypstPsiElement(node), Ignored {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitShebang(this)
    }
}

/**
 * A line comment: `// ...`.
 *
 * Is always a Leaf.
 */
class LineCommentPsiElement(node: ASTNode) : ATypstPsiElement(node), Ignored {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLineComment(this)
    }
}

/**
 * A block comment: `/* ... */`.
 *
 * Is always a Leaf.
 */
class BlockCommentPsiElement(node: ASTNode) : ATypstPsiElement(node), Ignored {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitBlockComment(this)
    }
}

/**
 * The contents of a file or content block.
 *
 *  Consists of markup elements:
 *  ```
 *  Node Markup:
 *      Node Strong:
 *          Node Star: *
 *          Node Markup:
 *              Node Text: Hi
 *          Node Star: *
 *      Node Space:
 *      Node Text: there!
 *  ```
 */
class MarkupPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMarkup(this)
    }
}

/**
 * Plain text without markup.
 *
 * Always a leaf.
 */
class TextPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitText(this)
    }
}

/**
 * Whitespace in markup or math. Contains at most one newline in markup,
 * as more indicate a paragraph break.
 */
class SpacePsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart, MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSpace(this)
    }
}

/**
 * A forced line break: `\`.
 */
class LinebreakPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLinebreak(this)
    }
}

/**
 * A paragraph break, indicated by one or multiple blank lines.
 */
class ParbreakPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitParbreak(this)
    }
}

/**
 * An escape sequence: `\#`, `\u{1F5FA}`.
 */
class EscapePsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart, MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEscape(this)
    }
}

/**
 * A shorthand for a unicode codepoint. For example, `~` for non-breaking
 * space or `-?` for a soft hyphen.
 */
class ShorthandPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitShorthand(this)
    }
}

/**
 * A smart quote: `'` or `"`.
 */
class SmartQuotePsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSmartQuote(this)
    }
}

/**
 * Strong content: `*Strong*`.
 */
class StrongPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitStrong(this)
    }
}

/**
 * Emphasized content: `_Emphasized_`.
 */
class EmphPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEmph(this)
    }
}

/**
 * Raw text with optional syntax highlighting: `` `...` ``.
 */
class RawPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart, CodePart, PsiLanguageInjectionHost {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRaw(this)
    }

    override fun clone() = RawPsiElement(node.clone() as ASTNode)

    override fun isValidHost(): Boolean {
        return true
    }

    override fun updateText(text: String): PsiLanguageInjectionHost? {
//        println("updateText($text)")
        return this
    }

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost?> {
        return object : LiteralTextEscaper<RawPsiElement>(this) {
            override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean {
                outChars.append(rangeInsideHost.substring(originalElement.text))
                return true
            }

            override fun getOffsetInHost(offset: Int, rangeInsideHost: TextRange): Int {
                return offset + rangeInsideHost.startOffset
            }

            override fun isOneLine(): Boolean {
                return false
            }
        }
    }
}

/**
 * A language tag at the start of raw text: ``typ ``.
 */
class RawLangPsiElement(node: ASTNode) : ATypstPsiElement(node), RawPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRawLang(this)
    }
}

/**
 * A raw delimiter consisting of 1 or 3+ backticks: `` ` ``.
 */
class RawDelimPsiElement(node: ASTNode) : ATypstPsiElement(node), RawPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRawDelim(this)
    }
}

/**
 * A sequence of whitespace to ignore in a raw text: `    `.
 */
class RawTrimmedPsiElement(node: ASTNode) : ATypstPsiElement(node), RawPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRawTrimmed(this)
    }
}

/**
 * A hyperlink: `https://typst.org`.
 */
class LinkPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLink(this)
    }
}

/**
 * A label: `<intro>`.
 */
class LabelPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLabel(this)
    }
}

/**
 * A reference: `@target`, `@target[..]`.
 */
class RefPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRef(this)
    }
}

/**
 * Introduces a reference: `@target`.
 */
class RefMarkerPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRefMarker(this)
    }
}

/**
 * A section heading: `= Introduction`.
 */
class HeadingPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitHeading(this)
    }
}

/**
 * Introduces a section heading: `=`, `==`, ...
 */
class HeadingMarkerPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitHeadingMarker(this)
    }
}

/**
 * An item in a bullet list: `- ...`.
 */
class ListItemPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitListItem(this)
    }
}

/**
 * Introduces a list item: `-`.
 */
class ListMarkerPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitListMarker(this)
    }
}

/**
 * An item in an enumeration (numbered list): `+ ...` or `1. ...`.
 */
class EnumItemPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEnumItem(this)
    }
}

/**
 * Introduces an enumeration item: `+`, `1.`.
 */
class EnumMarkerPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEnumMarker(this)
    }
}

/**
 * An item in a term list: `/ Term: Details`.
 */
class TermItemPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitTermItem(this)
    }
}

/**
 * Introduces a term item: `/`.
 */
class TermMarkerPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitTermMarker(this)
    }
}

/**
 * A mathematical equation: `$x$`, `$ x^2 $`.
 */
class EquationPsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart, CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEquation(this)
    }
}

/**
 * The contents of a mathematical equation: `x^2 + 1`.
 */
class MathPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMath(this)
    }
}

/**
 * A lone text fragment in math: `x`, `25`, `3.1415`, `=`, `|`, `[`.
 */
class MathTextPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathText(this)
    }
}

/**
 * An identifier in math: `pi`.
 */
class MathIdentPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathIdent(this)
    }
}

/**
 * A shorthand for a unicode codepoint in math: `a <= b`.
 */
class MathShorthandPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathShorthand(this)
    }
}

/**
 * An alignment point in math: `&`.
 */
class MathAlignPointPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathAlignPoint(this)
    }
}

/**
 * Matched delimiters in math: `[x + y]`.
 */
class MathDelimitedPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathDelimited(this)
    }
}

/**
 * A base with optional attachments in math: `a_1^2`.
 *
 * Contains ^superscripts, _subscripts and primes:
 * `1''^2_3` ->
 * ```
 * Node MathAttach:
 *     Node MathText: 1
 *     Node MathPrimes:
 *         Node Prime: '
 *         Node Prime: '
 *     Node Hat: ^
 *     Node MathText: 2
 *     Node Underscore: _
 *     Node MathText: 3
 * ```
 */
class MathAttachPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathAttach(this)
    }
}

/**
 * Grouped primes in math: `a'''`.
 */
class MathPrimesPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathPrimes(this)
    }
}

/**
 * A fraction in math: `x/2`.
 */
class MathFracPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathFrac(this)
    }
}

/**
 * A root in math: `√x`, `∛x` or `∜x`.
 */
class MathRootPsiElement(node: ASTNode) : ATypstPsiElement(node), MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMathRoot(this)
    }
}

/**
 * A hash that switches into code mode: `#`.
 */
class HashPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitHash(this)
    }
}

/**
 * A left curly brace, starting a code block: `{`.
 */
class LeftBracePsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLeftBrace(this)
    }
}

/**
 * A right curly brace, terminating a code block: `}`.
 */
class RightBracePsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRightBrace(this)
    }
}

/**
 * A left square bracket, starting a content block: `[`.
 */
class LeftBracketPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLeftBracket(this)
    }
}

/**
 * A right square bracket, terminating a content block: `]`.
 */
class RightBracketPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRightBracket(this)
    }
}

/**
 * A left round parenthesis, starting a grouped expression, collection,
 * argument or parameter list: `(`.
 */
class LeftParenPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLeftParen(this)
    }
}

/**
 *  A right round parenthesis, terminating a grouped expression, collection,
 *  argument or parameter list: `)`.
 */
class RightParenPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRightParen(this)
    }
}

/**
 * A comma separator in a sequence: `,`.
 */
class CommaPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitComma(this)
    }
}

/**
 * A semicolon terminating an expression: `;`.
 */
class SemicolonPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSemicolon(this)
    }
}

/**
 * A colon between name/key and value in a dictionary, argument or
 * parameter list, or between the term and body of a term list term: `:`.
 */
class ColonPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitColon(this)
    }
}

/**
 * The strong text toggle, multiplication operator, and wildcard import symbol: `*`.
 */
class StarPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitStar(this)
    }
}

/**
 * Toggles emphasized text and indicates a subscript in math: `_`.
 */
class UnderscorePsiElement(node: ASTNode) : ATypstPsiElement(node), Param {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitUnderscore(this)
    }
}

/**
 * Starts and ends a mathematical equation: `$`.
 */
class DollarPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitDollar(this)
    }
}

/**
 * The unary plus and binary addition operator: `+`.
 */
class PlusPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp, UnOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitPlus(this)
    }
}

/**
 * The unary negation and binary subtraction operator: `-`.
 */
class MinusPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp, UnOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitMinus(this)
    }
}

/**
 * The division operator and fraction operator in math: `/`.
 */
class SlashPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSlash(this)
    }
}

/**
 * The superscript operator in math: `^`.
 */
class HatPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitHat(this)
    }
}

/**
 * The prime in math: `'`.
 */
class PrimePsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitPrime(this)
    }
}

/**
 * The field access and method call operator: `.`.
 */
class DotPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitDot(this)
    }
}

/**
 * The assignment operator: `=`.
 */
class EqPsiElement(node: ASTNode) : ATypstPsiElement(node), AssignOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEq(this)
    }
}

/**
 * The equality operator: `==`.
 */
class EqEqPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEqEq(this)
    }
}

/**
 * The inequality operator: `!=`.
 */
class ExclEqPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitExclEq(this)
    }
}

/**
 * The less-than operator: `<`.
 */
class LtPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLt(this)
    }
}

/**
 * The less-than or equal operator: `<=`.
 */
class LtEqPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLtEq(this)
    }
}

/**
 * The greater-than operator: `>`.
 */
class GtPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitGt(this)
    }
}

/**
 * The greater-than or equal operator: `>=`.
 */
class GtEqPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitGtEq(this)
    }
}

/**
 * The add-assign operator: `+=`.
 */
class PlusEqPsiElement(node: ASTNode) : ATypstPsiElement(node), AssignOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitPlusEq(this)
    }
}

/**
 * The subtract-assign operator: `-=`.
 */
class HyphEqPsiElement(node: ASTNode) : ATypstPsiElement(node), AssignOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitHyphEq(this)
    }
}

/**
 * The multiply-assign operator: `*=`.
 */
class StarEqPsiElement(node: ASTNode) : ATypstPsiElement(node), AssignOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitStarEq(this)
    }
}

/**
 * The divide-assign operator: `/=`.
 */
class SlashEqPsiElement(node: ASTNode) : ATypstPsiElement(node), AssignOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSlashEq(this)
    }
}

/**
 * Indicates a spread or sink: `..`.
 */
class DotsPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitDots(this)
    }
}

/**
 * An arrow between a closure's parameters and body: `=>`.
 */
class ArrowPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitArrow(this)
    }
}

/**
 * A root: `√`, `∛` or `∜`.
 */
class RootPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRoot(this)
    }
}

/**
 * The `not` operator.
 */
class NotPsiElement(node: ASTNode) : ATypstPsiElement(node), UnOp, Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitNot(this)
    }
}

/**
 * The `and` operator.
 */
class AndPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp, Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitAnd(this)
    }
}

/**
 * The `or` operator.
 */
class OrPsiElement(node: ASTNode) : ATypstPsiElement(node), BinOp, Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitOr(this)
    }
}

/**
 * The `none` literal.
 */
class NonePsiElement(node: ASTNode) : ATypstPsiElement(node), KeywordLiteral {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitNone(this)
    }
}

/**
 * The `auto` literal.
 */
class AutoPsiElement(node: ASTNode) : ATypstPsiElement(node), KeywordLiteral {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitAuto(this)
    }
}

/**
 * The `let` keyword.
 */
class LetPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLet(this)
    }
}

/**
 * The `set` keyword.
 */
class SetPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSet(this)
    }
}

/**
 * The `show` keyword.
 */
class ShowPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitShow(this)
    }
}

/**
 * The `context` keyword.
 */
class ContextPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitContext(this)
    }
}

/**
 * The `if` keyword.
 */
class IfPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitIf(this)
    }
}

/**
 * The `else` keyword.
 */
class ElsePsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitElse(this)
    }
}

/**
 * The `for` keyword.
 */
class ForPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitFor(this)
    }
}

/**
 * The `in` keyword.
 */
class InPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword, BinOp {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitIn(this)
    }
}

/**
 * The `while` keyword.
 */
class WhilePsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitWhile(this)
    }
}

/**
 * The `break` keyword.
 */
class BreakPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitBreak(this)
    }
}

/**
 * The `continue` keyword.
 */
class ContinuePsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitContinue(this)
    }
}

/**
 * The `return` keyword.
 */
class ReturnPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitReturn(this)
    }
}

/**
 * The `import` keyword.
 */
class ImportPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitImport(this)
    }
}

/**
 * The `include` keyword.
 */
class IncludePsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitInclude(this)
    }
}

/**
 * The `as` keyword.
 */
class AsPsiElement(node: ASTNode) : ATypstPsiElement(node), Keyword {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitAs(this)
    }
}

/**
 * The contents of a code block.
 */
class CodePsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitCode(this)
    }
}

/**
 * A boolean: `true`, `false`.
 */
class BoolPsiElement(node: ASTNode) : ATypstPsiElement(node), KeywordLiteral {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitBool(this)
    }
}

/**
 * An integer: `120`.
 */
class IntPsiElement(node: ASTNode) : ATypstPsiElement(node), NumericLiteral {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitInt(this)
    }
}

/**
 * A floating-point number: `1.2`, `10e-4`.
 */
class FloatPsiElement(node: ASTNode) : ATypstPsiElement(node), NumericLiteral {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitFloat(this)
    }
}

/**
 * A numeric value with a unit: `12pt`, `3cm`, `2em`, `90deg`, `50%`.
 */
class NumericPsiElement(node: ASTNode) : ATypstPsiElement(node), NumericLiteral {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitNumeric(this)
    }
}

/**
 * A quoted string: `"..."`.
 */
class StrPsiElement(node: ASTNode) : ATypstPsiElement(node), Literal, MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitStr(this)
    }
}

/**
 * A code block: `{ let x = 1; x + 2 }`.
 */
class CodeBlockPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitCodeBlock(this)
    }
}

/**
 * A content block: `[*Hi* there!]`.
 */
class ContentBlockPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitContentBlock(this)
    }
}

/**
 * A grouped expression: `(1 + 2)`.
 */
class ParenthesizedPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitParenthesized(this)
    }
}

/**
 * An array: `(1, "hi", 12cm)`.
 */
class ArrayPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitArray(this)
    }
}

/**
 * A dictionary: `(thickness: 3pt, dash: "solid")`.
 */
class DictPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitDict(this)
    }
}

/**
 * A named pair: `thickness: 3pt`.
 */
class NamedPairPsiElement(node: ASTNode) : ATypstPsiElement(node), Param {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitNamed(this)
    }
}

/**
 * A keyed pair: `"spacy key": true`.
 */
class KeyedPairPsiElement(node: ASTNode) : ATypstPsiElement(node), Param {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitKeyed(this)
    }
}

/**
 * A unary operation: `-x`.
 */
class UnaryPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitUnary(this)
    }
}

/**
 * A binary operation: `a + b`.
 */
class BinaryPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitBinary(this)
    }
}

/**
 * A field access: `properties.age`.
 */
class FieldAccessPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitFieldAccess(this)
    }
}

/**
 * An invocation of a function or method: `f(x, y)`.
 */
class FuncCallPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitFuncCall(this)
    }
}

/**
 * A function call's argument list: `(12pt, y)`.
 */
class ArgsPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitArgs(this)
    }
}

/**
 * Spread arguments or an argument sink: `..x`.
 */
class SpreadPsiElement(node: ASTNode) : ATypstPsiElement(node), Param {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSpread(this)
    }
}

/**
 * A closure: `(x, y) => z`.
 */
class ClosurePsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitClosure(this)
    }
}

/**
 * A closure's parameters: `(x, y)`.
 */
class ParamsPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitParams(this)
    }
}

/**
 * A let binding: `let x = 1`.
 */
class LetBindingPsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLetBinding(this)
    }
}

/**
 * A set rule: `set text(...)`.
 */
class SetRulePsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitSetRule(this)
    }
}

/**
 * A show rule: `show heading: it => emph(it.body)`.
 */
class ShowRulePsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitShowRule(this)
    }
}

/**
 * A contextual expression: `context text.lang`.
 */
class ContextualPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitContextual(this)
    }
}

/**
 * An if-else conditional: `if x { y } else { z }`.
 */
class ConditionalPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitConditional(this)
    }
}

/**
 * A while loop: `while x { y }`.
 */
class WhileLoopPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitWhileLoop(this)
    }
}

/**
 * A for loop: `for x in y { z }`.
 */
class ForLoopPsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitForLoop(this)
    }
}

/**
 * A module import: `import "utils.typ": a, b, c`.
 */
class ModuleImportPsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitModuleImport(this)
    }
}

/**
 * Items to import from a module: `a, b, c`.
 */
class ImportItemsPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitImportItems(this)
    }
}

/**
 * A path to an imported name from a submodule: `a.b.c`.
 */
class ImportItemPathPsiElement(node: ASTNode) : ATypstPsiElement(node), ImportPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitImportItemPath(this)
    }
}

/**
 * A renamed import item: `a as d`.
 */
class RenamedImportItemPsiElement(node: ASTNode) : ATypstPsiElement(node), ImportPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRenamedImportItem(this)
    }
}

/**
 * A module include: `include "chapter1.typ"`.
 */
class ModuleIncludePsiElement(node: ASTNode) : ATypstPsiElement(node), Expr {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitModuleInclude(this)
    }
}

/**
 * A break from a loop: `break`.
 */
class LoopBreakPsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLoopBreak(this)
    }
}

/**
 * A continue in a loop: `continue`.
 */
class LoopContinuePsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitLoopContinue(this)
    }
}

/**
 * A return from a function: `return`, `return x + 1`.
 */
class FuncReturnPsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitFuncReturn(this)
    }
}

/**
 * A destructuring pattern: `(x, _, ..y)`.
 */
class DestructuringPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitDestructuring(this)
    }
}

/**
 * A destructuring assignment expression: `(x, y) = (1, 2)`.
 */
class DestructAssignmentPsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitDestructAssignment(this)
    }
}

// Non original syntax nodes

/**
 * An in-code whitespace
 */
class WhitespacePsiElement(node: ASTNode) : ATypstPsiElement(node), CodePart {
    override fun accept(visitor: TypstVisitor) {
        // TODO Sure?
    }
}

/**
 * An embedded code expression: `#f(1)`
 */
class EmbeddedCodePsiElement(node: ASTNode) : ATypstPsiElement(node), MarkupPart, MathPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitEmbeddedCode(this)
    }
}

/**
 * A text in raw.
 */
class RawTextPsiElement(node: ASTNode) : ATypstPsiElement(node), RawPart {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitRawText(this)
    }
}

/**
 * A declaration of an identifier: `it` in `let it = 1`
 */
class IdentDeclPsiElement(node: ASTNode) : ATypstPsiElement(node)/*, PsiNamedElement*/ {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitIdentDecl(this)
    }
//
//    override fun getName(): String {
//        return this.text.trim()
//    }
//
//    override fun setName(name: String): PsiElement {
//        val name = getName()
//        val child = this.firstLeaf().nextSiblingOf(inclusive = true) { it.text == name }!!
//        child.replace(LeafPsiElement(SyntaxKind.Ident.tokenType, name))
//        return this
//    }
}


/**
 * A reference to an identifier: `x` in `x + 1`
 */
class IdentRefPsiElement(node: ASTNode) : ATypstPsiElement(node) {
    override fun accept(visitor: TypstVisitor) {
        visitor.visitIdentRef(this)
    }

//    private inner class Reference : PsiReferenceBase<IdentRefPsiElement>(this) {
//        override fun resolve(): PsiElement? {
//            return null
//        }
//
//        override fun getVariants(): Array<Any> {
//            return arrayOf()
//        }
//    }

}


val constructorsMap: Map<SyntaxKind, (ASTNode) -> PsiElement> = mapOf(
    SyntaxKind.Error to ::ErrorPsiElement,
    SyntaxKind.Shebang to ::ShebangPsiElement,
    SyntaxKind.LineComment to ::LineCommentPsiElement,
    SyntaxKind.BlockComment to ::BlockCommentPsiElement,
    SyntaxKind.Markup to ::MarkupPsiElement,
    SyntaxKind.Text to ::TextPsiElement,
    SyntaxKind.Space to ::SpacePsiElement,
    SyntaxKind.Linebreak to ::LinebreakPsiElement,
    SyntaxKind.Parbreak to ::ParbreakPsiElement,
    SyntaxKind.Escape to ::EscapePsiElement,
    SyntaxKind.Shorthand to ::ShorthandPsiElement,
    SyntaxKind.SmartQuote to ::SmartQuotePsiElement,
    SyntaxKind.Strong to ::StrongPsiElement,
    SyntaxKind.Emph to ::EmphPsiElement,
    SyntaxKind.Raw to ::RawPsiElement,
    SyntaxKind.RawLang to ::RawLangPsiElement,
    SyntaxKind.RawDelim to ::RawDelimPsiElement,
    SyntaxKind.RawTrimmed to ::RawTrimmedPsiElement,
    SyntaxKind.Link to ::LinkPsiElement,
    SyntaxKind.Label to ::LabelPsiElement,
    SyntaxKind.Ref to ::RefPsiElement,
    SyntaxKind.RefMarker to ::RefMarkerPsiElement,
    SyntaxKind.Heading to ::HeadingPsiElement,
    SyntaxKind.HeadingMarker to ::HeadingMarkerPsiElement,
    SyntaxKind.ListItem to ::ListItemPsiElement,
    SyntaxKind.ListMarker to ::ListMarkerPsiElement,
    SyntaxKind.EnumItem to ::EnumItemPsiElement,
    SyntaxKind.EnumMarker to ::EnumMarkerPsiElement,
    SyntaxKind.TermItem to ::TermItemPsiElement,
    SyntaxKind.TermMarker to ::TermMarkerPsiElement,
    SyntaxKind.Equation to ::EquationPsiElement,
    SyntaxKind.Math to ::MathPsiElement,
    SyntaxKind.MathText to ::MathTextPsiElement,
    SyntaxKind.MathIdent to ::MathIdentPsiElement,
    SyntaxKind.MathShorthand to ::MathShorthandPsiElement,
    SyntaxKind.MathAlignPoint to ::MathAlignPointPsiElement,
    SyntaxKind.MathDelimited to ::MathDelimitedPsiElement,
    SyntaxKind.MathAttach to ::MathAttachPsiElement,
    SyntaxKind.MathPrimes to ::MathPrimesPsiElement,
    SyntaxKind.MathFrac to ::MathFracPsiElement,
    SyntaxKind.MathRoot to ::MathRootPsiElement,
    SyntaxKind.Hash to ::HashPsiElement,
    SyntaxKind.LeftBrace to ::LeftBracePsiElement,
    SyntaxKind.RightBrace to ::RightBracePsiElement,
    SyntaxKind.LeftBracket to ::LeftBracketPsiElement,
    SyntaxKind.RightBracket to ::RightBracketPsiElement,
    SyntaxKind.LeftParen to ::LeftParenPsiElement,
    SyntaxKind.RightParen to ::RightParenPsiElement,
    SyntaxKind.Comma to ::CommaPsiElement,
    SyntaxKind.Semicolon to ::SemicolonPsiElement,
    SyntaxKind.Colon to ::ColonPsiElement,
    SyntaxKind.Star to ::StarPsiElement,
    SyntaxKind.Underscore to ::UnderscorePsiElement,
    SyntaxKind.Dollar to ::DollarPsiElement,
    SyntaxKind.Plus to ::PlusPsiElement,
    SyntaxKind.Minus to ::MinusPsiElement,
    SyntaxKind.Slash to ::SlashPsiElement,
    SyntaxKind.Hat to ::HatPsiElement,
    SyntaxKind.Dot to ::DotPsiElement,
    SyntaxKind.Eq to ::EqPsiElement,
    SyntaxKind.EqEq to ::EqEqPsiElement,
    SyntaxKind.ExclEq to ::ExclEqPsiElement,
    SyntaxKind.Lt to ::LtPsiElement,
    SyntaxKind.LtEq to ::LtEqPsiElement,
    SyntaxKind.Gt to ::GtPsiElement,
    SyntaxKind.GtEq to ::GtEqPsiElement,
    SyntaxKind.PlusEq to ::PlusEqPsiElement,
    SyntaxKind.HyphEq to ::HyphEqPsiElement,
    SyntaxKind.StarEq to ::StarEqPsiElement,
    SyntaxKind.SlashEq to ::SlashEqPsiElement,
    SyntaxKind.Dots to ::DotsPsiElement,
    SyntaxKind.Arrow to ::ArrowPsiElement,
    SyntaxKind.Root to ::RootPsiElement,
    SyntaxKind.Not to ::NotPsiElement,
    SyntaxKind.And to ::AndPsiElement,
    SyntaxKind.Or to ::OrPsiElement,
    SyntaxKind.None to ::NonePsiElement,
    SyntaxKind.Auto to ::AutoPsiElement,
    SyntaxKind.Let to ::LetPsiElement,
    SyntaxKind.Set to ::SetPsiElement,
    SyntaxKind.Show to ::ShowPsiElement,
    SyntaxKind.Context to ::ContextPsiElement,
    SyntaxKind.If to ::IfPsiElement,
    SyntaxKind.Else to ::ElsePsiElement,
    SyntaxKind.For to ::ForPsiElement,
    SyntaxKind.In to ::InPsiElement,
    SyntaxKind.While to ::WhilePsiElement,
    SyntaxKind.Break to ::BreakPsiElement,
    SyntaxKind.Continue to ::ContinuePsiElement,
    SyntaxKind.Return to ::ReturnPsiElement,
    SyntaxKind.Import to ::ImportPsiElement,
    SyntaxKind.Include to ::IncludePsiElement,
    SyntaxKind.As to ::AsPsiElement,
    SyntaxKind.Code to ::CodePsiElement,
    SyntaxKind.Bool to ::BoolPsiElement,
    SyntaxKind.Int to ::IntPsiElement,
    SyntaxKind.Float to ::FloatPsiElement,
    SyntaxKind.Numeric to ::NumericPsiElement,
    SyntaxKind.Str to ::StrPsiElement,
    SyntaxKind.CodeBlock to ::CodeBlockPsiElement,
    SyntaxKind.ContentBlock to ::ContentBlockPsiElement,
    SyntaxKind.Parenthesized to ::ParenthesizedPsiElement,
    SyntaxKind.Array to ::ArrayPsiElement,
    SyntaxKind.Dict to ::DictPsiElement,
    SyntaxKind.Named to ::NamedPairPsiElement,
    SyntaxKind.Keyed to ::KeyedPairPsiElement,
    SyntaxKind.Unary to ::UnaryPsiElement,
    SyntaxKind.Binary to ::BinaryPsiElement,
    SyntaxKind.FieldAccess to ::FieldAccessPsiElement,
    SyntaxKind.FuncCall to ::FuncCallPsiElement,
    SyntaxKind.Args to ::ArgsPsiElement,
    SyntaxKind.Spread to ::SpreadPsiElement,
    SyntaxKind.Closure to ::ClosurePsiElement,
    SyntaxKind.Params to ::ParamsPsiElement,
    SyntaxKind.LetBinding to ::LetBindingPsiElement,
    SyntaxKind.SetRule to ::SetRulePsiElement,
    SyntaxKind.ShowRule to ::ShowRulePsiElement,
    SyntaxKind.Contextual to ::ContextualPsiElement,
    SyntaxKind.Conditional to ::ConditionalPsiElement,
    SyntaxKind.WhileLoop to ::WhileLoopPsiElement,
    SyntaxKind.ForLoop to ::ForLoopPsiElement,
    SyntaxKind.ModuleImport to ::ModuleImportPsiElement,
    SyntaxKind.ImportItems to ::ImportItemsPsiElement,
    SyntaxKind.ImportItemPath to ::ImportItemPathPsiElement,
    SyntaxKind.RenamedImportItem to ::RenamedImportItemPsiElement,
    SyntaxKind.ModuleInclude to ::ModuleIncludePsiElement,
    SyntaxKind.LoopBreak to ::LoopBreakPsiElement,
    SyntaxKind.LoopContinue to ::LoopContinuePsiElement,
    SyntaxKind.FuncReturn to ::FuncReturnPsiElement,
    SyntaxKind.Destructuring to ::DestructuringPsiElement,
    SyntaxKind.DestructAssignment to ::DestructAssignmentPsiElement,
)


