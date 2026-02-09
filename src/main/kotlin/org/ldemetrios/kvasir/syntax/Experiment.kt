package org.ldemetrios.kvasir.syntax

import org.ldemetrios.frontendPool
import org.ldemetrios.tyko.compiler.SyntaxMark
import org.ldemetrios.tyko.compiler.SyntaxMode

fun main() {

    val source = """
            #!/usr/bin/shebang
            
            // Comment
            #let x = /* Another comment */ 1
            
            This is regular
            text
            
            with \ linebreaks and
            
            parbreaks
            
            \\ \u{2399}
            $\$$
            
            "smart" 'quotes'
            
            This *is* _strong_ *em _pha_ sized*
            ```kt
                fun main() {
                    println("Hello")
                }
                ```
            https://typst.org <label> @ref @ref[smth _lol_]
            
            = Headings
            == Different headings
            - Items
              - In 
              - Lists
                  - And
                - some
                - strange
              + Stuff
            1. Going on
            
            / Term: this is
            $ E q u a "tion" $ $ a n d$
            $ pi <= & [x + y] a_(1+2)^3_4^5 a''' x/2 (x+2)/2 √x ∛(x+y) #[lol] $
            $ lr(|x|) |x|$
             $1''^2_3$
            
            #{
                let x = (y, z) => y + z * y - -y + 1 not in 2
                1; x
            }(1, 2)
            
            #f.field.method(1, 2).multiple()[][]
            
            #(x: 3, y: 5)
            
            #context text.fill
            
            #set text(fill: red)
            #show regex("."): it => it
            #show: it => it
            #show element.with(some:thing): box
            #for x in range(0, 5) [
                lol
            ]
            
            #if true { x } else if (2 + 3) {
                break
                continue
                return (1, "hi", 12cm)
            } else [*Hi* there!]
            
            #(("spacy key": true) + 3)
            #import 
            
            #f(12pt, y, ..sink)
            
            #((x, y, _, ..sink) => z)
            
            #let (x, y) = (1, 2)
            
            #let f(x) = x
            #while x { y }
            
            #import "utils.typ": a, b, c.d.e, f as g
            
            #include "chapter1.typ"
            
            #show heading: set text(size: 12pt)
            #set text(fill: red) if 1 + 2 == 3
        """.trimIndent()
    val syntax = frontendPool.with { parseSyntax(source, SyntaxMode.Markup).marks }
    var depth = 0
    for (i in syntax.indices) {
        val from = syntax[i].index
        val to = if (i + 1 < syntax.size) syntax[i + 1].index else source.length
        val text = source.substring(from, to).replace("\n", "\\n")
        when (val mark = syntax[i].mark) {
            is SyntaxMark.Error -> {
                println("    ".repeat(depth) + "Error ${mark.message} $from..$to: $text")
                depth++
            }

            SyntaxMark.NodeEnd -> {
                depth--
            }

            is SyntaxMark.NodeStart -> {
                println("    ".repeat(depth) + "Node ${mark.kind}: $text")
                depth++
            }
        }
    }
}
