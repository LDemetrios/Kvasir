--- Blocks ---

#{
  let parts = ("my fri", "end.")
  [Hello, ]
  for s in parts [#s]
}

// Evaluates to join of the content and strings.
#{
  [How]
  if true {
    " are"
  }
  [ ]
  if false [Nope]
  [you] + "?"
}

--- code-block-double-scope ---
// Double block creates a scope.
#{{
  import "module.typ": b
  test(b, 1)
}}

--- code-block-nested-scopes ---
// Multiple nested scopes.
#{
  let a = "a1"
  {
    let a = "a2"
    {
      test(a, "a2")
      let a = "a3"
      test(a, "a3")
    }
    test(a, "a2")
  }
  test(a, "a1")
}


--- code-block-multiple-expressions-without-semicolon ---
// Error: 13 expected semicolon or line break
// Error: 23 expected semicolon or line break
#{let x = -1 let y = 3 x + y}

--- code-block-incomplete-expressions ---
#{
  // Error: 7-10 expected pattern, found string
  for "v"

  // Error: 8 expected keyword `in`
  // Error: 22 expected block
  for v let z = 1 + 2

  z
}

--- content-block-in-markup-scope ---
// Content blocks also create a scope.
#[#let x = 1]

// Error: 2-3 unknown variable: x
#x

// Test function calls.

--- call-basic ---

// Omitted space.
#let f() = {}
#[#f()*Bold*]

// Call return value of function with body.
#let f(x, body) = (y) => [#x] + body + [#y]
#f(1)[2](3)

// Don't parse this as a function.
#test (it)

#let f(body) = body
#f[A]
#f()[A]
#f([A])

#let g(a, b) = a + b
#g[A][B]
#g([A], [B])
#g()[A][B]

#let h(x) = (y) => x + y

// Error: 2-6 expected function, found integer
#f(1)(2)

// Error: 2-6 expected function, found content
#f[1](2)


--- call-complex-callee-expression ---
// Callee expressions.
#{
  // Wrapped in parens.
  test((type)("hi"), str)

  // Call the return value of a function.
  let adder(dx) = x => x + dx
  test(adder(2)(5), 7)
}

--- call-args-bad-name-group ---
// Error: 7-10 expected identifier, found group
#func((x):1)

--- call-args-lone-underscore ---
// Test that lone underscore works.
#test((1, 2, 3).map(_ => {}).len(), 3)

--- call-args-spread-none ---
// None is spreadable.
#let f() = none
#f(..none)
#f(..if false {})
#f(..for x in () [])

--- call-args-unclosed ---
// Error: 7-8 unclosed delimiter
#{func(}

--- destructuring-group-1 ---
// This wasn't allowed.
#let ((x)) = 1
#test(x, 1)

--- destructuring-group-2 ---
// This also wasn't allowed.
#let ((a, b)) = (1, 2)
#test(a, 1)
#test(b, 2)

--- destructuring-dict-bad ---
// Error: 7-10 expected identifier, found group
// Error: 12-14 expected pattern, found integer
#let ((a): 10) = "world"

--- destructuring-non-atomic ---
// Ensure that we can't have non-atomic destructuring.
#let x = 1
#let c = [#() = ()]

--- destructuring-let-array ---
// Simple destructuring.
#let (a, b) = (1, 2)

--- destructuring-let-array-with-sink-at-end ---
// Destructuring with a sink.
#let (a, b, ..c) = (1, 2, 3, 4, 5, 6)

--- destructuring-assign ---
// Test destructuring assignments.

#((a,) = (1,))
#((_, a, b, _) = (1, 2, 3, 4))
#((a, b, ..c) = (1, 2, 3, 4, 5, 6))
#((a: a, b, x: c) = (a: 1, b: 2, x: 3))
#((a: a.at(0), b) = (a: 3, b: 4))
#((a.at(0), b) = (3, 4))
#((a, ..b) = (1, 2, 3, 4))
#((b, ..a.at(0)) = (1, 2, 3, 4))

--- issue-3275-normal-variable ---
// Normal variable.
#for x in (1, 2) {}
#for x in (a: 1, b: 2) {}
#for x in "foo" {}
#for x in bytes("😊") {}

--- issue-3275-placeholder ---
// Placeholder.
#for _ in (1, 2) {}
#for _ in (a: 1, b: 2) {}
#for _ in "foo" {}
#for _ in bytes("😊") {}

--- issue-3275-destructuring ---
// Destructuring.
#for (a,b,c) in (("a", 1, bytes(())), ("b", 2, bytes(""))) {}
#for (a, ..) in (("a", 1, bytes(())), ("b", 2, bytes(""))) {}
#for (k, v)  in (a: 1, b: 2, c: 3) {}
#for (.., v) in (a: 1, b: 2, c: 3) {}

--- issue-3275-loop-over-content ---
// Error: 11-17 cannot loop over content
#for x in [1, 2] {}

--- issue-3275-loop-over-arguments ---
// Error: 11-25 cannot loop over arguments
#for _ in arguments("a") {}

--- issue-3275-loop-over-integer ---
// Error: 16-21 cannot loop over integer
#for (x, y) in 12306 {}

--- issue-3275-destructuring-loop-over-content ---
// Error: 16-22 cannot loop over content
#for (x, y) in [1, 2] {}

--- issue-3275-destructuring-loop-over-string ---
// Error: 6-12 cannot destructure values of string
#for (x, y) in "foo" {}

--- issue-3275-destructuring-loop-over-string-array ---
// Error: 6-12 cannot destructure string
#for (x, y) in ("foo", "bar") {}

--- issue-3275-destructuring-loop-over-bytes ---
// Error: 6-12 cannot destructure values of bytes
#for (x, y) in bytes("😊") {}

--- issue-3275-destructuring-loop-over-int-array ---
// Error: 6-12 cannot destructure integer
#for (x, y) in (1, 2) {}

--- field-closure-invalid ---
// Closures cannot have fields.
#let f(x) = x
// Error: 4-11 cannot access fields on user-defined functions
#f.invalid

--- for-loop-basic ---

// Empty array.
#for x in () [Nope]

// Dictionary is traversed in insertion order.
// Should output `Name: Typst. Age: 2.`.
#for (k, v) in (Name: "Typst", Age: 2) [
  #k: #v.
]

// Block body.
// Should output `[1st, 2nd, 3rd, 4th]`.
#{
  "["
  for v in (1, 2, 3, 4) {
    if v > 1 [, ]
    [#v]
    if v == 1 [st]
    if v == 2 [nd]
    if v == 3 [rd]
    if v >= 4 [th]
   }
   "]"
}

// Content block body.
// Should output `2345`.
#for v in (1, 2, 3, 4, 5, 6, 7) [#if v >= 2 and v <= 5 { repr(v) }]

// Map captured arguments.
#let f1(..args) = args.pos().map(repr)
#let f2(..args) = args.named().pairs().map(p => repr(p.first()) + ": " + repr(p.last()))
#let f(..args) = (f1(..args) + f2(..args)).join(", ")
#f(1, a: 2)


--- for-loop-integrated ---
#let out = ()

// Values of array.
#for v in (1, 2, 3) {
  out += (v,)
}

// Indices and values of array.
#for (i, v) in ("1", "2", "3").enumerate() {
  test(repr(i + 1), v)
}

// Pairs of dictionary.
#for v in (a: 4, b: 5) {
  out += (v,)
}

// Keys and values of dictionary.
#for (k, v) in (a: 6, b: 7) {
  out += (k,)
  out += (v,)
}

#test(out, (1, 2, 3, ("a", 4), ("b", 5), "a", 6, "b", 7))

// Grapheme clusters of string.
#let first = true
#let joined = for c in "abc👩‍👩‍👦‍👦" {
  if not first { ", " }
  first = false
  c
}

#test(joined, "a, b, c, 👩‍👩‍👦‍👦")


--- for-loop-incomplete ---
// Error: 5 expected pattern
#for

// Error: 5 expected pattern
#for//

// Error: 6 expected pattern
#{for}

// Error: 7 expected keyword `in`
#for v

// Error: 10 expected expression
#for v in

// Error: 15 expected block
#for v in iter

// Error: 5 expected pattern
#for
v in iter {}

// Error: 7-10 expected pattern, found string
// Error: 16 expected block
A#for "v" thing

// Error: 6-9 expected pattern, found string
#for "v" in iter {}

// Error: 7 expected keyword `in`
#for a + b in iter {}

--- get-rule-basic ---
// Test basic get rule.
#context test(text.lang, "en")
#set text(lang: "de")
#context test(text.lang, "de")
#text(lang: "es", context test(text.lang, "es"))

--- get-rule-in-function ---
// Test whether context is retained in nested function.
#let translate(..args) = args.named().at(text.lang)
#set text(lang: "de")
#context test(translate(de: "Inhalt", en: "Contents"), "Inhalt")

--- get-rule-in-array-callback ---
// Test whether context is retained in built-in callback.
#set text(lang: "de")
#context test(
  ("en", "de", "fr").sorted(key: v => v != text.lang),
  ("de", "en", "fr"),
)

--- get-rule-folding ---
// Test folding.
#set rect(stroke: red)
#context {
  test(type(rect.stroke), stroke)
  test(rect.stroke.paint, red)
}
#[
  #set rect(stroke: 4pt)
  #context test(rect.stroke, 4pt + red)
]
#context test(rect.stroke, stroke(red))

// Test if-else expressions.

--- if-markup ---
// Test condition evaluation.
#if 1 < 2 [
  One.
]

#if true == false [
  {Bad}, but we {dont-care}!
]

--- if-condition-complex ---
// Braced condition.
#if {true} [
  One.
]

// Content block in condition.
#if [] != none [
  Two.
]

// Multi-line condition with parens.
#if (
  1 + 1
    == 1
) [
  Nope.
] else {
  "Three."
}

// Multiline.
#if false [
  Bad.
] else {
  let point = "."
  "Four" + point
}

// Content block can be argument or body depending on whitespace.
#if content == type[b] [Fi] else [Nope]
#if content == type [Nope] else [ve.]

#let i = 3
#if i < 2 [
  Five.
] else if i < 4 [
  Six.
] else [
  Seven.
]


--- if-else-if-else ---
// Test else if.

#if n == 1 { "st" } else if n == 2 { "nd" } else if n == 3 { "rd" } else { "th" }


--- if-condition-string-invalid ---
// Condition must be boolean.
// If it isn't, neither branch is evaluated.
// Error: 5-14 expected boolean, found string
#if "a" + "b" { nope } else { nope }


--- if-incomplete ---
// Error: 4 expected expression
#if

// Error: 5 expected expression
#{if}

// Error: 6 expected block
#if x

// Error: 2-6 unexpected keyword `else`
#else {}

// Should output `x`.
// Error: 4 expected expression
#if
x {}

// Should output `something`.
// Error: 6 expected block
#if x something

// Should output `A thing.`
// Error: 19 expected block
A#if false {} else thing

#if a []else [b]
#if a [] else [b]
#if a {} else [b]

// Test function and module imports.

--- import-basic ---
// Test basic syntax and semantics.

// Test that this will be overwritten.
#let value = [foo]

// Import multiple things.
#import "module.typ": fn, value
#fn[Like and Subscribe!]
#value

// Should output `bye`.
// Stop at semicolon.
#import "module.typ": a, c;bye

--- import-item-markup ---
// An item import.
#import "module.typ": item
#test(item(1, 2), 3)

--- import-item-in-code ---
// Code mode
#{
  import "module.typ": b
  test(b, 1)
}

--- import-wildcard-in-markup ---
// A wildcard import.
#import "module.typ": *

// It exists now!
#test(d, 3)

--- import-item-renamed ---
// A renamed item import.
#import "module.typ": item as something
#test(something(1, 2), 3)

--- import-items-renamed-mixed ---
// Mixing renamed and not renamed items.
#import "module.typ": fn, b as val, item as other
#test(val, 1)
#test(other(1, 2), 3)


--- import-nested-item ---
// Nested item imports.
#import "modules/chap1.typ" as orig-chap1
#import "modules/chap2.typ" as orig-chap2
#import "module.typ": chap2, chap2.name, chap2.chap1, chap2.chap1.name as othername


--- include-file ---
#set page(width: 200pt)

= Document

// Include a file
#include "modules/chap1.typ"

// Expression as a file name.
#let chap2 = include "modu" + "les/chap" + "2.typ"

-- _Intermission_ --
#chap2


--- let-basic ---
// Automatically initialized with none.
#let x
#test(x, none)

// Manually initialized with one.
#let z = 1
#test(z, 1)

// Syntax sugar for function definitions.
#let fill = conifer
#let f(body) = rect(width: 2cm, fill: fill, inset: 5pt, body)
#f[Hi!]

--- let-termination ---
// Termination.

// Terminated by line break.
#let v1 = 1
One

// Terminated by semicolon.
#let v2 = 2; Two

// Terminated by semicolon and line break.
#let v3 = 3;
Three

#test(v1, 1)
#test(v2, 2)
#test(v3, 3)

--- let-valid-idents ---
// Test what constitutes a valid Typst identifier.
#let name = 1
#test(name, 1)
#let name_ = 1
#test(name_, 1)
#let name-2 = 1
#test(name-2, 1)
#let name_2 = 1
#test(name_2, 1)
#let __name = 1
#test(__name, 1)
#let ůñıćóðė = 1
#test(ůñıćóðė, 1)


--- let-ident-parenthesized ---
// Test parenthesised assignments.
#let (a) = (1, 2)

--- let-incomplete ---
// Error: 5 expected pattern
#let

// Error: 6 expected pattern
#{let}

// Error: 6-9 expected pattern, found string
#let "v"

// Error: 7 expected semicolon or line break
#let v 1

// Error: 9 expected expression
#let v =

// Error: 6-9 expected pattern, found string
#let "v" = 1

// Terminated because expression ends.
// Error: 12 expected semicolon or line break
#let v4 = 4 Four

// Terminated by semicolon even though we are in a paren group.
// Error: 18 expected expression
// Error: 11-12 unclosed delimiter
#let v5 = (1, 2 + ; Five

// Error: 9-13 expected pattern, found boolean
#let (..true) = false


--- let-function-incomplete ---
// Error: 13 expected equals sign
#let func(x)

// Error: 15 expected expression
#let func(x) =


--- let-with-no-init-group ---
// This was unintentionally allowed ...
// Error: 9 expected equals sign
#let (a)

--- let-with-no-init-destructuring ---
// ... where this wasn't.
// Error: 12 expected equals sign
#let (a, b)

// Test binary expressions.

--- ops-add-content ---
// Test adding content.
#([*Hello* ] + [world!])

--- ops-unary-basic ---
// Test math operators.

// Test plus and minus.
#for v in (1, 3.14, 12pt, 45deg, 90%, 13% + 10pt, 6.3fr) {
  // Test plus.
  test(+v, v)

  // Test minus.
  test(-v, -1 * v)
  test(--v, v)

  // Test combination.
  test(-++ --v, -v)
}

#test(-(4 + 2), 6-12)

// Addition.
#test(2 + 4, 6)
#test("a" + "b", "ab")
#test("a" + if false { "b" }, "a")
#test("a" + if true { "b" }, "ab")
#test(13 * "a" + "bbbbbb", "aaaaaaaaaaaaabbbbbb")
#test((1, 2) + (3, 4), (1, 2, 3, 4))
#test((a: 1) + (b: 2, c: 3), (a: 1, b: 2, c: 3))

--- ops-add-too-large ---
// Error: 3-26 value is too large
#(9223372036854775807 + 1)

--- ops-binary-basic ---
// Subtraction.
#test(1-4, 3*-1)
#test(4cm - 2cm, 2cm)
#test(1e+2-1e-2, 99.99)

// Multiplication.
#test(2 * 4, 8)

// Division.
#test(12pt/.4, 30pt)
#test(7 / 2, 3.5)

// Combination.
#test(3-4 * 5 < -10, true)
#test({ let x; x = 1 + 4*5 >= 21 and { x = "a"; x + "b" == "ab" }; x }, true)

// With block.
#test(if true {
  1
} + 2, 3)

// Mathematical identities.
#let nums = (
  1, 3.14,
  12pt, 3em, 12pt + 3em,
  45deg,
  90%,
  13% + 10pt, 5% + 1em + 3pt,
  2.3fr,
)

#for v in nums {
  // Test plus and minus.
  test(v + v - v, v)
  test(v - v - v, -v)

  // Test plus/minus and multiplication.
  test(v - v, 0 * v)
  test(v + v, 2 * v)

  // Integer addition does not give a float.
  if type(v) != int {
    test(v + v, 2.0 * v)
  }

  if type(v) != relative and ("pt" not in repr(v) or "em" not in repr(v)) {
    test(v / v, 1.0)
  }
}

// Make sure length, ratio and relative length
// - can all be added to / subtracted from each other,
// - multiplied with integers and floats,
// - divided by integers and floats.
#let dims = (10pt, 1em, 10pt + 1em, 30%, 50% + 3cm, 40% + 2em + 1cm)
#for a in dims {
  for b in dims {
    test(type(a + b), type(a - b))
  }

  for b in (7, 3.14) {
    test(type(a * b), type(a))
    test(type(b * a), type(a))
    test(type(a / b), type(a))
  }
}

// Test division of different numeric types with zero components.
#for a in (0pt, 0em, 0%) {
  for b in (10pt, 10em, 10%) {
    test((2 * b) / b, 2)
    test((a + b * 2) / b, 2)
    test(b / (b * 2 + a), 0.5)
  }
}


--- ops-precedence-basic ---
// Multiplication binds stronger than addition.
#test(1+2*-3, -5)

// Subtraction binds stronger than comparison.
#test(3 == 5 - 2, true)

// Boolean operations bind stronger than '=='.
#test("a" == "a" and 2 < 3, true)
#test(not "b" == "b", false)

--- ops-precedence-boolean-ops ---
// Assignment binds stronger than boolean operations.
// Error: 2:3-2:8 cannot mutate a temporary value
#let x = false
#(not x = "a")

--- ops-precedence-unary ---
// Precedence doesn't matter for chained unary operators.
// Error: 3-12 cannot apply '-' to boolean
#(-not true)

--- ops-precedence-not-in ---
// Not in handles precedence.
#test(-1 not in (1, 2, 3), true)

--- ops-precedence-parentheses ---
// Parentheses override precedence.
#test((1), 1)
#test((1+2)*-3, -9)

// Error: 8-9 unclosed delimiter
#test({(1 + 1}, 2)

--- ops-associativity-left ---
// Math operators are left-associative.
#test(10 / 2 / 2 == (10 / 2) / 2, true)
#test(10 / 2 / 2 == 10 / (2 / 2), false)
#test(1 / 2 * 3, 1.5)

--- ops-associativity-right ---
// Assignment is right-associative.
#{
  let x = 1
  let y = 2
  x = y = "ok"
  test(x, none)
  test(y, "ok")
}

--- ops-assign-unknown-var-lhs ---
#{
  // Error: 3-6 unknown variable: a-1
  // Hint: 3-6 if you meant to use subtraction, try adding spaces around the minus sign
  a-1 = 2
}

--- param-underscore-missing-argument ---
// Error: 17-20 missing argument: pattern parameter
#let f(a: 10) = a() + 1
#f(a: _ => 5)

--- params-sink-named ---
// ... but this was.
#let f(..x) = {}
#f(arg: 1)

--- params-sink-unnamed ---
// unnamed spread
#let f(.., a) = a
#test(f(1, 2, 3), 3)

// This wasn't allowed before the bug fix ...
#let f(..) = 2
#test(f(arg: 1), 2)

--- params-sink-bool-invalid ---
// Error: 10-14 expected pattern, found boolean
#let f(..true) = none

--- params-sink-multiple-invalid ---
// Error: 13-16 only one argument sink is allowed
#let f(..a, ..b) = none


--- issue-1029-parameter-destructuring ---
// Test that underscore works in parameter patterns.
#test((1, 2, 3).zip((1, 2, 3)).map(((_, x)) => x), (1, 2, 3))

--- issue-1351-parameter-dictionary ---
// Error: 17-22 expected pattern, found string
#let foo((test: "bar")) = {}


--- while-loop-basic ---
// Should output `2 4 6 8 10`.
#let i = 0
#while i < 10 [
  #(i += 2)
  #i
]

// Should output `Hi`.
#let iter = true
#while iter {
  iter = false
  "Hi."
}

#while false {
  dont-care
}

--- while-loop-expr ---
// Value of while loops.

#test(while false {}, none)

#let i = 0
#test(type(while i < 1 [#(i += 1)]), content)

--- while-loop-condition-content-invalid ---
// Condition must be boolean.
// Error: 8-14 expected boolean, found content
#while [nope] [nope]


--- while-loop-incomplete ---
// Error: 7 expected expression
#while

// Error: 8 expected expression
#{while}

// Error: 9 expected block
#while x

// Error: 7 expected expression
#while
x {}

// Error: 9 expected block
#while x something

--- plain math ---

$ x &= p \ dot(x) &= v \ dot.double(x) &= a \ dot.triple(x) &= j \ dot.quad(x) &= s $

#{
    $ x &= p \ dot(x) &= v \ dot.double(x) &= a \ dot.triple(x) &= j \ dot.quad(x) &= s $
}

#{

}

// Test interactions with styling and normal layout.
// Hint: They are bad ...

--- math-nested-normal-layout ---
// Test images and font fallback.
#let monkey = move(dy: 0.2em, image("/assets/images/monkey.svg", height: 1em))
$ sum_(i=#emoji.apple)^#emoji.apple.red i + monkey/2 $

--- math-table ---
// Test tables.
$ x := #table(columns: 2)[x][y]/mat(1, 2, 3)
     = #table[A][B][C] $

--- math-equation-auto-wrapping ---
// Test non-equation math directly in content.
#math.attach($a$, t: [b])

--- math-font-switch ---
// Test font switch.
// Warning: 29-40 unknown font family: noto sans
#let here = text.with(font: "Noto Sans")
$#here[f] := #here[Hi there]$.

--- math-box-without-baseline ---
// Test boxes without a baseline act as if the baseline is at the base
#{
  box(stroke: 0.2pt, $a #box(stroke: 0.2pt, $a$)$)
  h(12pt)
  box(stroke: 0.2pt, $a #box(stroke: 0.2pt, $g$)$)
  h(12pt)
  box(stroke: 0.2pt, $g #box(stroke: 0.2pt, $g$)$)
}

--- math-box-with-baseline ---
// Test boxes with a baseline are respected
#box(stroke: 0.2pt, $a #box(baseline:0.5em, stroke: 0.2pt, $a$)$)

--- math-at-par-start ---
// Test that equation at start of paragraph works fine.
$x$ is a variable.

--- math-at-par-end ---
// Test that equation at end of paragraph works fine.
One number is $1$

--- math-at-line-start ---
// Test math at the natural end of a line.
#h(60pt) Number $1$ exists.

--- math-at-line-end ---
// Test math at the natural end of a line.
#h(50pt) Number $1$ exists.

--- math-consecutive ---
// Test immediately consecutive equations.
$x$$y$

--- issue-2821-missing-fields ---
// Issue #2821: Setting a figure's supplement to none removes the field
#show figure.caption: it => {
  assert(it.has("supplement"))
  assert(it.supplement == none)
}
#figure([], caption: [], supplement: none)

--- math-symbol-show-rule ---
// Test using rules for symbols
#show sym.tack: it => $#h(1em) it #h(1em)$
$ a tack b $

--- issue-math-realize-show ---
// Test that content in math can be realized without breaking
// nested equations.
#let my = $pi$
#let f1 = box(baseline: 10pt, [f])
#let f2 = context f1
#show math.vec: [nope]

$ pi a $
$ my a $
$ 1 + sqrt(x/2) + sqrt(#hide($x/2$)) $
$ a x #link("url", $+ b$) $
$ f f1 f2 $
$ vec(1,2) * 2 $

--- issue-math-realize-hide ---
$ x^2 #hide[$(>= phi.alt) union y^2 0$] z^2 $
Hello #hide[there $x$]
and #hide[$ f(x) := x^2 $]

--- issue-math-realize-scripting ---
// Test equations can embed equation pieces built by functions
#let foo(v1, v2) = {
  // Return an equation piece that would've been rendered in
  // inline style if the piece is not embedded
  $v1 v2^2$
}
#let bar(v1, v2) = {
  // Return an equation piece that would've been rendered in
  // block style if the piece is not embedded
  $ v1 v2^2 $
}
#let baz(..sink) = {
  // Return an equation piece built by joining arrays
  sink.pos().map(x => $hat(#x)$).join(sym.and)
}

Inline $2 foo(alpha, (M+foo(a, b)))$.

Inline $2 bar(alpha, (M+foo(a, b)))$.

Inline $2 baz(x,y,baz(u, v))$.

$ 2 foo(alpha, (M+foo(a, b))) $
$ 2 bar(alpha, (M+foo(a, b))) $
$ 2 baz(x,y,baz(u, v)) $
