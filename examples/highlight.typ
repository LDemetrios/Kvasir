This *is* just _formatting_. It's *<emst>_composable_</emst>*.

= Headings, possibly with *formatting*

/ Term: this is.
/ Formatted _as well_: it might be.

(Unfortunately, color settings page doesn't support custom effects mixing)

There are <labels> and @references. Shorthands are highlighted --- like this.

- List
    - markers,
  - as well as
    + enum
  + markers,
    + are highlighted
      + based on their level

#let f = 1
#[The color of the hashes depends on the context]; and so is the color of the semicolons.

// These are comments.
/*
    These are as well.
*/
#(x: (x: (x: (x: (x: (x: (x: (x: (x: (x: (x: (x) => y) => y) => y) => y) => y) => y) => y) => y) => y) => y))

#"string with \n escapes", links: https://typst.app/

#show raw: box.with(inset: 1em, stroke: (left: black))

````md
A bit more ~~formatting~~
```kt
fun main() {
    println("Hello")
    val x = 1 + 2
}
```
````

#pagebreak()

Rainbowifying can be disabled in plugin's settings.

#(x: (x: (x: (x: (x: (x: (x: (x: (x: (x: (x: (x) => y) => y) => y) => y) => y) => y) => y) => y) => y) => y) => y)

Кириллица тоже прекрасно работает!
#let и-юникод-идентификаторы = [seem to work as well]

#for i in range(5) {
    for x in range(i) {
        for y in range(x) {
            [1]
        }
    }
}

#set heading(numbering:"1.1")
= ... <references>
