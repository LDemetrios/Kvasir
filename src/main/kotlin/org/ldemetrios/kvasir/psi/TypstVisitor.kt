package org.ldemetrios.kvasir.psi

import com.intellij.psi.PsiElementVisitor

abstract class TypstVisitor : PsiElementVisitor() {
    open fun visitTypstElement(element: TypstPsiElement) {
        element.acceptChildren(this)
    }

    /** An invalid sequence of characters. */
    open fun visitError(element: ErrorPsiElement) = visitTypstElement(element)

    /** A shebang: `#! ...` */
    open fun visitShebang(element: ShebangPsiElement) = visitTypstElement(element)

    /** A line comment: `// ...`. */
    open fun visitLineComment(element: LineCommentPsiElement) = visitTypstElement(element)

    /** A block comment: `/* ... */`. */
    open fun visitBlockComment(element: BlockCommentPsiElement) = visitTypstElement(element)

    /** The contents of a file or content block. */
    open fun visitMarkup(element: MarkupPsiElement) = visitTypstElement(element)

    /** Plain text without markup. */
    open fun visitText(element: TextPsiElement) = visitTypstElement(element)

    /**
     * Whitespace. Contains at most one newline in markup, as more indicate a
     * paragraph break.
     */
    open fun visitSpace(element: SpacePsiElement) = visitTypstElement(element)

    /** A forced line break: `\`. */
    open fun visitLinebreak(element: LinebreakPsiElement) = visitTypstElement(element)

    /** A paragraph break, indicated by one or multiple blank lines. */
    open fun visitParbreak(element: ParbreakPsiElement) = visitTypstElement(element)

    /** An escape sequence: `\#`, `\u{1F5FA}`. */
    open fun visitEscape(element: EscapePsiElement) = visitTypstElement(element)

    /**
     * A shorthand for a unicode codepoint. For example, `~` for non-breaking
     * space or `-?` for a soft hyphen.
     */
    open fun visitShorthand(element: ShorthandPsiElement) = visitTypstElement(element)

    /** A smart quote: `'` or `"`. */
    open fun visitSmartQuote(element: SmartQuotePsiElement) = visitTypstElement(element)

    /** Strong content: `*Strong*`. */
    open fun visitStrong(element: StrongPsiElement) = visitTypstElement(element)

    /** Emphasized content: `_Emphasized_`. */
    open fun visitEmph(element: EmphPsiElement) = visitTypstElement(element)

    /** Raw text with optional syntax highlighting: `` `...` ``. */
    open fun visitRaw(element: RawPsiElement) = visitTypstElement(element)

    /** A language tag at the start of raw text: ``typ ``. */
    open fun visitRawLang(element: RawLangPsiElement) = visitTypstElement(element)

    /** A raw delimiter consisting of 1 or 3+ backticks: `` ` ``. */
    open fun visitRawDelim(element: RawDelimPsiElement) = visitTypstElement(element)

    /** A sequence of whitespace to ignore in a raw text: `    `. */
    open fun visitRawTrimmed(element: RawTrimmedPsiElement) = visitTypstElement(element)

    /** A hyperlink: `https://typst.org`. */
    open fun visitLink(element: LinkPsiElement) = visitTypstElement(element)

    /** A label: `<intro>`. */
    open fun visitLabel(element: LabelPsiElement) = visitTypstElement(element)

    /** A reference: `@target`, `@target[..]`. */
    open fun visitRef(element: RefPsiElement) = visitTypstElement(element)

    /** Introduces a reference: `@target`. */
    open fun visitRefMarker(element: RefMarkerPsiElement) = visitTypstElement(element)

    /** A section heading: `= Introduction`. */
    open fun visitHeading(element: HeadingPsiElement) = visitTypstElement(element)

    /** Introduces a section heading: `=`, `==`, ... */
    open fun visitHeadingMarker(element: HeadingMarkerPsiElement) = visitTypstElement(element)

    /** An item in a bullet list: `- ...`. */
    open fun visitListItem(element: ListItemPsiElement) = visitTypstElement(element)

    /** Introduces a list item: `-`. */
    open fun visitListMarker(element: ListMarkerPsiElement) = visitTypstElement(element)

    /** An item in an enumeration (numbered list): `+ ...` or `1. ...`. */
    open fun visitEnumItem(element: EnumItemPsiElement) = visitTypstElement(element)

    /** Introduces an enumeration item: `+`, `1.`. */
    open fun visitEnumMarker(element: EnumMarkerPsiElement) = visitTypstElement(element)

    /** An item in a term list: `/ Term: Details`. */
    open fun visitTermItem(element: TermItemPsiElement) = visitTypstElement(element)

    /** Introduces a term item: `/`. */
    open fun visitTermMarker(element: TermMarkerPsiElement) = visitTypstElement(element)

    /** A mathematical equation: `$x$`, `$ x^2 $`. */
    open fun visitEquation(element: EquationPsiElement) = visitTypstElement(element)

    /** The contents of a mathematical equation: `x^2 + 1`. */
    open fun visitMath(element: MathPsiElement) = visitTypstElement(element)

    /** A lone text fragment in math: `x`, `25`, `3.1415`, `=`, `|`, `[`. */
    open fun visitMathText(element: MathTextPsiElement) = visitTypstElement(element)

    /** An identifier in math: `pi`. */
    open fun visitMathIdent(element: MathIdentPsiElement) = visitTypstElement(element)

    /** A shorthand for a unicode codepoint in math: `a <= b`. */
    open fun visitMathShorthand(element: MathShorthandPsiElement) = visitTypstElement(element)

    /** An alignment point in math: `&`. */
    open fun visitMathAlignPoint(element: MathAlignPointPsiElement) = visitTypstElement(element)

    /** Matched delimiters in math: `[x + y]`. */
    open fun visitMathDelimited(element: MathDelimitedPsiElement) = visitTypstElement(element)

    /** A base with optional attachments in math: `a_1^2`. */
    open fun visitMathAttach(element: MathAttachPsiElement) = visitTypstElement(element)

    /** Grouped primes in math: `a'''`. */
    open fun visitMathPrimes(element: MathPrimesPsiElement) = visitTypstElement(element)

    /** A fraction in math: `x/2`. */
    open fun visitMathFrac(element: MathFracPsiElement) = visitTypstElement(element)

    /** A root in math: `√x`, `∛x` or `∜x`. */
    open fun visitMathRoot(element: MathRootPsiElement) = visitTypstElement(element)

    /** A hash that switches into code mode: `#`. */
    open fun visitHash(element: HashPsiElement) = visitTypstElement(element)

    /** A left curly brace, starting a code block: `{`. */
    open fun visitLeftBrace(element: LeftBracePsiElement) = visitTypstElement(element)

    /** A right curly brace, terminating a code block: `}`. */
    open fun visitRightBrace(element: RightBracePsiElement) = visitTypstElement(element)

    /** A left square bracket, starting a content block: `[`. */
    open fun visitLeftBracket(element: LeftBracketPsiElement) = visitTypstElement(element)

    /** A right square bracket, terminating a content block: `]`. */
    open fun visitRightBracket(element: RightBracketPsiElement) = visitTypstElement(element)

    /**
     * A left round parenthesis, starting a grouped expression, collection,
     * argument or parameter list: `(`.
     */
    open fun visitLeftParen(element: LeftParenPsiElement) = visitTypstElement(element)

    /**
     *  A right round parenthesis, terminating a grouped expression, collection,
     *  argument or parameter list: `)`.
     */
    open fun visitRightParen(element: RightParenPsiElement) = visitTypstElement(element)

    /** A comma separator in a sequence: `,`. */
    open fun visitComma(element: CommaPsiElement) = visitTypstElement(element)

    /** A semicolon terminating an expression: `;`. */
    open fun visitSemicolon(element: SemicolonPsiElement) = visitTypstElement(element)

    /**
     * A colon between name/key and value in a dictionary, argument or
     * parameter list, or between the term and body of a term list term: `:`.
     */
    open fun visitColon(element: ColonPsiElement) = visitTypstElement(element)

    /** The strong text toggle, multiplication operator, and wildcard import symbol: `*`. */
    open fun visitStar(element: StarPsiElement) = visitTypstElement(element)

    /** Toggles emphasized text and indicates a subscript in math: `_`. */
    open fun visitUnderscore(element: UnderscorePsiElement) = visitTypstElement(element)

    /** Starts and ends a mathematical equation: `$`. */
    open fun visitDollar(element: DollarPsiElement) = visitTypstElement(element)

    /** The unary plus and binary addition operator: `+`. */
    open fun visitPlus(element: PlusPsiElement) = visitTypstElement(element)

    /** The unary negation and binary subtraction operator: `-`. */
    open fun visitMinus(element: MinusPsiElement) = visitTypstElement(element)

    /** The division operator and fraction operator in math: `/`. */
    open fun visitSlash(element: SlashPsiElement) = visitTypstElement(element)

    /** The superscript operator in math: `^`. */
    open fun visitHat(element: HatPsiElement) = visitTypstElement(element)

    /** The prime in math: `'`. */
    open fun visitPrime(element: PrimePsiElement) = visitTypstElement(element)

    /** The field access and method call operator: `.`. */
    open fun visitDot(element: DotPsiElement) = visitTypstElement(element)

    /** The assignment operator: `=`. */
    open fun visitEq(element: EqPsiElement) = visitTypstElement(element)

    /** The equality operator: `==`. */
    open fun visitEqEq(element: EqEqPsiElement) = visitTypstElement(element)

    /** The inequality operator: `!=`. */
    open fun visitExclEq(element: ExclEqPsiElement) = visitTypstElement(element)

    /** The less-than operator: `<`. */
    open fun visitLt(element: LtPsiElement) = visitTypstElement(element)

    /** The less-than or equal operator: `<=`. */
    open fun visitLtEq(element: LtEqPsiElement) = visitTypstElement(element)

    /** The greater-than operator: `>`. */
    open fun visitGt(element: GtPsiElement) = visitTypstElement(element)

    /** The greater-than or equal operator: `>=`. */
    open fun visitGtEq(element: GtEqPsiElement) = visitTypstElement(element)

    /** The add-assign operator: `+=`. */
    open fun visitPlusEq(element: PlusEqPsiElement) = visitTypstElement(element)

    /** The subtract-assign operator: `-=`. */
    open fun visitHyphEq(element: HyphEqPsiElement) = visitTypstElement(element)

    /** The multiply-assign operator: `*=`. */
    open fun visitStarEq(element: StarEqPsiElement) = visitTypstElement(element)

    /** The divide-assign operator: `/=`. */
    open fun visitSlashEq(element: SlashEqPsiElement) = visitTypstElement(element)

    /** Indicates a spread or sink: `..`. */
    open fun visitDots(element: DotsPsiElement) = visitTypstElement(element)

    /** An arrow between a closure's parameters and body: `=>`. */
    open fun visitArrow(element: ArrowPsiElement) = visitTypstElement(element)

    /** A root: `√`, `∛` or `∜`. */
    open fun visitRoot(element: RootPsiElement) = visitTypstElement(element)

    /** The `not` operator. */
    open fun visitNot(element: NotPsiElement) = visitTypstElement(element)

    /** The `and` operator. */
    open fun visitAnd(element: AndPsiElement) = visitTypstElement(element)

    /** The `or` operator. */
    open fun visitOr(element: OrPsiElement) = visitTypstElement(element)

    /** The `none` literal. */
    open fun visitNone(element: NonePsiElement) = visitTypstElement(element)

    /** The `auto` literal. */
    open fun visitAuto(element: AutoPsiElement) = visitTypstElement(element)

    /** The `let` keyword. */
    open fun visitLet(element: LetPsiElement) = visitTypstElement(element)

    /** The `set` keyword. */
    open fun visitSet(element: SetPsiElement) = visitTypstElement(element)

    /** The `show` keyword. */
    open fun visitShow(element: ShowPsiElement) = visitTypstElement(element)

    /** The `context` keyword. */
    open fun visitContext(element: ContextPsiElement) = visitTypstElement(element)

    /** The `if` keyword. */
    open fun visitIf(element: IfPsiElement) = visitTypstElement(element)

    /** The `else` keyword. */
    open fun visitElse(element: ElsePsiElement) = visitTypstElement(element)

    /** The `for` keyword. */
    open fun visitFor(element: ForPsiElement) = visitTypstElement(element)

    /** The `in` keyword. */
    open fun visitIn(element: InPsiElement) = visitTypstElement(element)

    /** The `while` keyword. */
    open fun visitWhile(element: WhilePsiElement) = visitTypstElement(element)

    /** The `break` keyword. */
    open fun visitBreak(element: BreakPsiElement) = visitTypstElement(element)

    /** The `continue` keyword. */
    open fun visitContinue(element: ContinuePsiElement) = visitTypstElement(element)

    /** The `return` keyword. */
    open fun visitReturn(element: ReturnPsiElement) = visitTypstElement(element)

    /** The `import` keyword. */
    open fun visitImport(element: ImportPsiElement) = visitTypstElement(element)

    /** The `include` keyword. */
    open fun visitInclude(element: IncludePsiElement) = visitTypstElement(element)

    /** The `as` keyword. */
    open fun visitAs(element: AsPsiElement) = visitTypstElement(element)

    /** The contents of a code block. */
    open fun visitCode(element: CodePsiElement) = visitTypstElement(element)

    /** A boolean: `true`, `false`. */
    open fun visitBool(element: BoolPsiElement) = visitTypstElement(element)

    /** An integer: `120`. */
    open fun visitInt(element: IntPsiElement) = visitTypstElement(element)

    /** A floating-point number: `1.2`, `10e-4`. */
    open fun visitFloat(element: FloatPsiElement) = visitTypstElement(element)

    /** A numeric value with a unit: `12pt`, `3cm`, `2em`, `90deg`, `50%`. */
    open fun visitNumeric(element: NumericPsiElement) = visitTypstElement(element)

    /** A quoted string: `"..."`. */
    open fun visitStr(element: StrPsiElement) = visitTypstElement(element)

    /** A code block: `{ let x = 1; x + 2 }`. */
    open fun visitCodeBlock(element: CodeBlockPsiElement) = visitTypstElement(element)

    /** A content block: `[*Hi* there!]`. */
    open fun visitContentBlock(element: ContentBlockPsiElement) = visitTypstElement(element)

    /** A grouped expression: `(1 + 2)`. */
    open fun visitParenthesized(element: ParenthesizedPsiElement) = visitTypstElement(element)

    /** An array: `(1, "hi", 12cm)`. */
    open fun visitArray(element: ArrayPsiElement) = visitTypstElement(element)

    /** A dictionary: `(thickness: 3pt, dash: "solid")`. */
    open fun visitDict(element: DictPsiElement) = visitTypstElement(element)

    /** A named pair: `thickness: 3pt`. */
    open fun visitNamed(element: NamedPairPsiElement) = visitTypstElement(element)

    /** A keyed pair: `"spacy key": true`. */
    open fun visitKeyed(element: KeyedPairPsiElement) = visitTypstElement(element)

    /** A unary operation: `-x`. */
    open fun visitUnary(element: UnaryPsiElement) = visitTypstElement(element)

    /** A binary operation: `a + b`. */
    open fun visitBinary(element: BinaryPsiElement) = visitTypstElement(element)

    /** A field access: `properties.age`. */
    open fun visitFieldAccess(element: FieldAccessPsiElement) = visitTypstElement(element)

    /** An invocation of a open function or method: `f(x, y)`. */
    open fun visitFuncCall(element: FuncCallPsiElement) = visitTypstElement(element)

    /** A open function call's argument list: `(12pt, y)`. */
    open fun visitArgs(element: ArgsPsiElement) = visitTypstElement(element)

    /** Spread arguments or an argument sink: `..x`. */
    open fun visitSpread(element: SpreadPsiElement) = visitTypstElement(element)

    /** A closure: `(x, y) => z`. */
    open fun visitClosure(element: ClosurePsiElement) = visitTypstElement(element)

    /** A closure's parameters: `(x, y)`. */
    open fun visitParams(element: ParamsPsiElement) = visitTypstElement(element)

    /** A let binding: `let x = 1`. */
    open fun visitLetBinding(element: LetBindingPsiElement) = visitTypstElement(element)

    /** A set rule: `set text(...)`. */
    open fun visitSetRule(element: SetRulePsiElement) = visitTypstElement(element)

    /** A show rule: `show heading: it => emph(it.body)`. */
    open fun visitShowRule(element: ShowRulePsiElement) = visitTypstElement(element)

    /** A contextual expression: `context text.lang`. */
    open fun visitContextual(element: ContextualPsiElement) = visitTypstElement(element)

    /** An if-else conditional: `if x { y } else { z }`. */
    open fun visitConditional(element: ConditionalPsiElement) = visitTypstElement(element)

    /** A while loop: `while x { y }`. */
    open fun visitWhileLoop(element: WhileLoopPsiElement) = visitTypstElement(element)

    /** A for loop: `for x in y { z }`. */
    open fun visitForLoop(element: ForLoopPsiElement) = visitTypstElement(element)

    /** A module import: `import "utils.typ": a, b, c`. */
    open fun visitModuleImport(element: ModuleImportPsiElement) = visitTypstElement(element)

    /** Items to import from a module: `a, b, c`. */
    open fun visitImportItems(element: ImportItemsPsiElement) = visitTypstElement(element)

    /** A path to an imported name from a submodule: `a.b.c`. */
    open fun visitImportItemPath(element: ImportItemPathPsiElement) = visitTypstElement(element)

    /** A renamed import item: `a as d`. */
    open fun visitRenamedImportItem(element: RenamedImportItemPsiElement) = visitTypstElement(element)

    /** A module include: `include "chapter1.typ"`. */
    open fun visitModuleInclude(element: ModuleIncludePsiElement) = visitTypstElement(element)

    /** A break from a loop: `break`. */
    open fun visitLoopBreak(element: LoopBreakPsiElement) = visitTypstElement(element)

    /** A continue in a loop: `continue`. */
    open fun visitLoopContinue(element: LoopContinuePsiElement) = visitTypstElement(element)

    /** A return from a open function: `return`, `return x + 1`. */
    open fun visitFuncReturn(element: FuncReturnPsiElement) = visitTypstElement(element)

    /** A destructuring pattern: `(x, _, ..y)`. */
    open fun visitDestructuring(element: DestructuringPsiElement) = visitTypstElement(element)

    /** A destructuring assignment expression: `(x, y) = (1, 2)`. */
    open fun visitDestructAssignment(element: DestructAssignmentPsiElement) = visitTypstElement(element)

    /** An embedded code expression: `#f(1)` */
    open fun visitEmbeddedCode(element: EmbeddedCodePsiElement) = visitTypstElement(element)

    /** A text in raw. */
    open fun visitRawText(element: RawTextPsiElement) = visitTypstElement(element)

    /** A declaration of an identifier: `it` in `let it = 1` */
    open fun visitIdentDecl(element: IdentDeclPsiElement) = visitTypstElement(element)

    /** A reference to an identifier: `x` in `x + 1` */
    open fun visitIdentRef(element: IdentRefPsiElement) = visitTypstElement(element)
}