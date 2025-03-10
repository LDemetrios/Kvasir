#set text(fill: sys.inputs.kvasir-preview-foreground)
#set page(fill: sys.inputs.kvasir-preview-background)

#set heading(numbering: "1.1")
#set text(lang: "ru", size: 1.2em)

#show math.equation.where(block: false): box

= Mathanalysis by Vinogradov

==

Let ${e_k}^oo_(k=1)$ --- be an OS in $cal(H)$. Then the following statements are _equivalent_.

#let ek = ${e_k}$

+ #ek is a basis.

+

$ forall x, y in cal(H) quad lr(angle.l x, y angle.r) = sum_(k=1)^oo c_k (x) overline(c_k (y)) lr(||e_k||)^2 $

#set enum(start: 3)

+ #ek is closed.

+ #ek is complete.

+ The linear span of the system #ek is dense in $cal(H)$.

#set enum(start: 1)

==

Let $k in [1 : n]$, $r in NN union {oo}$. A set $M subset RR^n$ is called a *smooth $k$-dimensional manifold of class $C^((r))$* or an *$r$-smooth $k$-dimensional manifold* if for all $x in M$ there exists a neighborhood $V_x^M$ and a regular homomorphism $phi : Pi_k -> V_x^M$ of class $C^((r))$, such that $phi(OO_k) = x$.



/*
= Total Store Order

#import "@preview/fletcher:0.5.5" as fletcher: diagram as dia, node, edge

#let diagram(..args) = align(center)[
    \
    #context dia(
        edge-stroke: 1pt + text.fill,
        node-corner-radius: 5pt,
        edge-corner-radius: 8pt,
        mark-scale: 80%,
        node-fill: eastern.darken(50%),
        label-wrapper: edge => box(
            [#edge.label],
            inset: .2em,
            radius: .2em,
            fill: page.fill,
        ),
        ..args,
    )
    \
]

#diagram(
    node((0, 0), [#h(2em) CPU 1 #h(2em)], name: <cpu1>),
    node((.8, 0), [#h(2em) CPU 2 #h(2em)], name: <cpu2>),
    node((0.15, 1), [buffer], name: <buf1>),
    node((0.65, 1), [buffer], name: <buf2>),
    node((0.4, 2), [#h(6em) Memory #h(6em)], name: <mem>),

    edge(<cpu1>, "dd", "<{-", shift: -.5, label-side: center, [read]),
    edge(<cpu2>, "dd", "<{-", shift: 0.2, label-side: center, [read]),

    edge(<buf1>, "u", "<{-", label-side: center, [write]),
    edge(<buf2>, "u", "<{-", label-side: center, [write]),
    edge(<buf1>, "d", "-}>", label-side: center),
    edge(<buf2>, "d", "-}>", label-side: right, [write back]),
)

*/