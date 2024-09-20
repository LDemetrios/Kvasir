#set heading(numbering: "1.1")
#set text(lang: "ru")

// Emulating latex-like view

#set page(margin: 1in)
#set par(leading: 0.55em, justify: true)
#set text(font: "New Computer Modern")
#show raw: set text(font: "New Computer Modern Mono")
#show par: set block(spacing: 0.55em)
#show heading: set block(above: 1.4em, below: 1em)

= Матанализ от Виноградова

==

Пусть ${e_k}^oo_(k=1)$ --- ОС в $cal(H)$. Тогда следующие утверждения равносильны.

#let ek = ${e_k}$

+ #ek --- базис.

+ Для любых векторов $x, y in cal(H)$ выполнено обобщённое уравнение замкнутость

$ lr(angle.l x, y angle.r) = sum_(k=1)^oo c_k (x) overline(c_k (y)) lr(||e_k||)^2 $

#set enum(start:3)

+ #ek замкнута.

+ #ek полна.

+ Линейная оболочка системы #ek плотна в $cal(H)$

#set enum(start:1)

==

Пусть $k in [1 : n]$, $r in NN union {oo}$. Множество $M subset RR^n$ называется *гладким $k$-мерным многообразием класса  $C^((r))$* или *$r$-гладким $k$-мерным многообразием*, если для любого $x in M$ существует окрестность $V_x^M$ и регулярный класса $C^((r))$ гомоморфизм $phi : Pi_k -> V_x^M$, такой что $phi(OO_k) = x$.

= Большое задание от доктора Тренча

==

#align(center)[
  #set text(size:.9em)
  #show math.equation: math.display
  #set par(leading:2.3em)
  $f(t) = (sin t + 2 cos t) sum_(n=0)^oo u(t-2 n pi)$;
  $F(s) = (1+2s)/(s^2+1) sum_(n=0)^oo e^(-2 n pi s)$;
  $Y(s) = (1+2s)/((s^2+1)(s^2+2s+2)) sum_(n=0)^oo e^(-2 b pi s)$;
  $(1+2s) / ((s^2+1)(s^2+2s+2)) = (A s + B) / (s^2+1) + (C (s+1) + D) / ((s+1)^2 + 1)$
]

where

$ (A s + B) ((s+1)^2 + 1) + (C (s+1) + D) (s^2 +1) = 1+ 2s $

$
  2B+C+D &=& 1 & " " ("set" s = 0); \
  -A+B+2D &=& -1 & " " ("set" s = -1); \
  5A+5B+4C+2D &=& 3 & " " ("set" s = 1); \
  A + C &=& 0 & " " ("equate coefficients of " s^3)
$

Solving this system yields $A=0$, $B=1$, $C=0$, $D=-1$. Therefore,

$
  (1+2s) / (s^2+1)(s^2+2s+2) &= && 1 / (s^2 + 1) - 1 / ((s+1^2) + 1) \
  & <-> && (1-e^(-t)) sin t
$

Since $sin(t-2n pi) = sin t$,

$ e^(-2n pi s) (1 + 2s) / ((s^2 + 1)(s^2 + 2s + 2)) <-> u(t - 2n pi) (1 - e^(-(t-2n pi))) sin t $

so

$ y(t) = sin t sum_(n=0)^oo u(t-2 n pi) (1 - e^(-(t-2 n pi))) $

If $2 m pi <= t < 2 (m+1) pi$,

$
  y(t) = sin t sum_(n=0)^oo u(t-2 n pi) (1 - e^(-(t-2 n pi))) = (
    m + 1 - ((1-e^(2 (m+1) pi)) / (1 - e^(2pi))) e^(-t)
  ) sin t
$

= Маленькие задания от доктора Тренча

#show math.equation: math.display
==

$e^t integral_0^t e^(2 tau) sinh (t-tau) d tau = integral_0^t e^(3 tau) (e^(t-tau) sinh(t-tau)) d tau$; $e^(3t) <-> 1/(s-3)$ and $e^t sinh t <-> 1/((s-1)^2 - 1)$, so $H(s) = 1/((s-3)((s-1)^2-1)$.

==

$t^7 <-> 7!/s^8$ and $e^(-t) sin 2t <-> 2/((s+1)^2 + 4)$, so $H(s) = (2*7!)/(s^8 [(s+1)^2 + 4])$.

==

$e^t <-> 1/(s-1)$ and $sin a t <-> a/(s^2 + a^2)$, so $H(s) = a/((s-1)(s^2+a^2))$.
