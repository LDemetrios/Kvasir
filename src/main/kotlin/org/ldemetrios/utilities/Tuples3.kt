//@file:Suppress("unused")
//
//package org.ldemetrios.utilities
//
//infix fun <T, F, S> Couple<F, S>.and(t: T): Triple<F, S, T> = Triple(first, second, t)
//infix fun <F, S, T, U> Triple<F, S, T>.and(u: U): Quadruple<F, S, T, U> = Quadruple(first, second, third, u)
//infix fun <F, S, T, U, Q> Quadruple<F, S, T, U>.and(q: Q): Quintuple<F, S, T, U, Q> = Quintuple(first, second, third, fourth, q)
//
//fun <T, F, S> tee(    f: (T) -> F,    s: (T) -> S): (T) -> Couple<F, S> = { t: T -> (f(t) to s(t)) }
//fun <A, F, S, T> tee(f: (A) -> F, s: (A) -> S, t: (A) -> T): (A) -> Triple<F, S, T> = { a: A -> f(a) to s(a) and t(a) }
//fun <A, F, S, T, U> tee(f: (A) -> F, s: (A) -> S, t: (A) -> T, u: (A) -> U): (A) -> Quadruple<F, S, T, U> = { a: A -> f(a) to s(a) and t(a) and u(a) }
//fun <A, F, S, T, U, Q> tee(f: (A) -> F, s: (A) -> S, t: (A) -> T, u: (A) -> U, q: (A) -> Q): (A) -> Quintuple<F, S, T, U, Q> = { a: A -> f(a) to s(a) and t(a) and u(a) and q(a) }
//
//fun <F, S> lift(f: (F) -> F, s: (S) -> S): (Couple<F, S>) -> Couple<F, S> {
//    return { p: Couple<F, S> -> (f(p.first) to s(p.second)) }
//}
//
//fun <F, S> lift(f: (F, F) -> F, s: (S, S) -> S): (Couple<F, S>, Couple<F, S>) -> Couple<F, S> {
//    return { p1: Couple<F, S>, p2: Couple<F, S> -> f(p1.first, p2.first) to s(p1.second, p2.second) }
//}
//
//fun <F, S, T> lift(f: (F) -> F, s: (S) -> S, t: (T) -> T): (Triple<F, S, T>) -> Triple<F, S, T> {
//    return { p: Triple<F, S, T> -> f(p.first) to s(p.second) and t(p.third) }
//}
//
//fun <F, S, T> lift(f: (F, F) -> F, s: (S, S) -> S, t: (T, T) -> T): (Triple<F, S, T>, Triple<F, S, T>) -> Triple<F, S, T> {
//    return { p1: Triple<F, S, T>, p2: Triple<F, S, T> -> f(p1.first, p2.first) to s(p1.second, p2.second) and t(p1.third, p2.third) }
//}
//
//fun <F, S, T, U> lift(f: (F) -> F, s: (S) -> S, t: (T) -> T, u: (U) -> U): (Quadruple<F, S, T, U>) -> Quadruple<F, S, T, U> {
//    return { p: Quadruple<F, S, T, U> -> f(p.first) to s(p.second) and t(p.third) and u(p.fourth) }
//}
//
//fun <F, S, T, U> lift(f: (F, F) -> F, s: (S, S) -> S, t: (T, T) -> T, u: (U, U) -> U): (Quadruple<F, S, T, U>, Quadruple<F, S, T, U>) -> Quadruple<F, S, T, U> {
//    return { p1: Quadruple<F, S, T, U>, p2: Quadruple<F, S, T, U> -> f(p1.first, p2.first) to s(p1.second, p2.second) and t(p1.third, p2.third) and u(p1.fourth, p2.fourth) }
//}
//
//
//fun <F, S, T, U, Q> lift(f: (F) -> F, s: (S) -> S, t: (T) -> T, u: (U) -> U, q: (Q) -> Q): (Quintuple<F, S, T, U, Q>) -> Quintuple<F, S, T, U, Q> {
//    return { p: Quintuple<F, S, T, U, Q> -> f(p.first) to s(p.second) and t(p.third) and u(p.fourth) and q(p.fifth) }
//}
//
//fun <F, S, T, U, Q> lift(f: (F, F) -> F, s: (S, S) -> S, t: (T, T) -> T, u: (U, U) -> U, q: (Q, Q) -> Q):
//            (Quintuple<F, S, T, U, Q>, Quintuple<F, S, T, U, Q>) -> Quintuple<F, S, T, U, Q> {
//    return { p1: Quintuple<F, S, T, U, Q>, p2: Quintuple<F, S, T, U, Q> ->
//        f(p1.first, p2.first) to s(p1.second, p2.second) and t(p1.third, p2.third) and u(p1.fourth, p2.fourth) and q(p1.fifth, p2.fifth)
//    }
//}
//
//data class Quadruple<out A, out B, out C, out D>(
//    val first: A,
//    val second: B,
//    val third: C,
//    val fourth: D,
//) {
//    override fun toString(): String = "($first, $second, $third, $fourth)"
//
//    fun toList() = listOf(first, second, third, fourth)
//}
//
//data class Quintuple<out A, out B, out C, out D, out E>(
//    val first: A,
//    val second: B,
//    val third: C,
//    val fourth: D,
//    val fifth: E,
//) {
//    override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
//
//    fun toList() = listOf(first, second, third, fourth, fifth)
//}
//
