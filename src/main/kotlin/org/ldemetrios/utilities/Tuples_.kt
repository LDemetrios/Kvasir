  @file:Suppress("PackageDirectoryMismatch", "unused", "NAME_SHADOWING", "KotlinRedundantDiagnosticSuppress", "UNUSED_PARAMETER", "UNUSED_PARAMETER", "ObjectPropertyName", "NonAsciiCharacters", "RedundantLambdaArrow", "TrailingComma")  

 package org.ldemetrios.utilities

import java.io.Serializable

interface Tuple<out T> : Serializable {
      fun reify(): List<T>
      val size: Int get() = reify().size
      operator fun get(index: Int) = reify()[index]
      val head: T
      val tail: Tuple<T>
      fun <Next : @UnsafeVariance T> append(next: Next): Tuple<T>
      fun <NewFirst : @UnsafeVariance T> cons(new: NewFirst): Tuple<T>
 }

val None = Nullad<Nothing>()

fun <Fake> Nullad(list:List<Fake>) : Nullad<Fake> {
 require(list.isEmpty()) {"Nullad from non-empty list"}
 return None
 }

class Nullad<out Fake> : Tuple<Fake> {
      override fun reify() = listOf<Nothing>()
      override fun toString(): String = "()"
      
     override val head: Nothing get() = throw NoSuchElementException("head of empty tuple")
      override val tail: Tuple<Nothing> get() = throw NoSuchElementException("tail of empty tuple")
      override val size: Int get() = 0
      override fun <Next : @UnsafeVariance Fake> append(next: Next) = Monad(next)
      override fun <NewFirst : @UnsafeVariance Fake> cons(new: NewFirst) = Monad(new)
      override fun hashCode() = 1
      override fun equals(other: Any?) = other is Nullad<*>
 }

infix fun <Fake, F> Nullad<Fake>.and(next: F) = Monad(next)
 fun <Fake, F> cons(head: F, tail: Nullad<Fake>) = Monad(head)

fun <O> Monad(list:List<O>) = Monad(list.single())

data class Monad<out O>(val only: O) : Tuple<O> {
      override fun reify() = listOf(only)
      override fun toString(): String = "($only,)"
      
     override val head: O get() = only
      override val tail: Tuple<O> get() = None
      override val size: Int get() = 1
      val first get() = only
      override fun <Next : @UnsafeVariance O> append(next: Next) = Dyad(only, next)
      override fun <NewFirst : @UnsafeVariance O> cons(new: NewFirst) = Dyad(new, only)
 }

infix fun <Z, A : Z, B : Z> Monad<A>.and(next: B) = Dyad(first, next)
 fun <Z, A : Z, B : Z> cons(head: A, tail: Monad<B>) = Dyad(head, tail.first)

 fun <Z, A : Z, B : Z> Dyad (list:List<Z>) : Dyad<Z, A, B> {
      require(list.size == 2) {"Dyad should be created from list of 2 elements"}
      return Dyad (list[0] as A, list[1] as B) 
 }

 data class Dyad<out Z, out A : Z, out B : Z>(val first : A,val second : B,) : Tuple<Z> {
     override fun reify() = listOf(first,second,)
     override fun toString(): String = "($first, $second)"

     override val head = first
     override val tail = Monad (second, )
     override val size = 2 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Triad (first, second, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Triad (first, first, second, )
}

infix fun <Z, A : Z, B : Z, C : Z> Dyad<Z, A, B>.and(next: C) = Triad (first, second, next)
fun <Z, A : Z, B : Z, C : Z> cons(head: A, tail: Dyad<Z, B, C>) = Triad (head, tail.first, tail.second)

 fun <Z, A : Z, B : Z, C : Z> Triad (list:List<Z>) : Triad<Z, A, B, C> {
      require(list.size == 3) {"Triad should be created from list of 3 elements"}
      return Triad (list[0] as A, list[1] as B, list[2] as C) 
 }

 data class Triad<out Z, out A : Z, out B : Z, out C : Z>(val first : A,val second : B,val third : C,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,)
     override fun toString(): String = "($first, $second, $third)"

     override val head = first
     override val tail = Dyad (second, third, )
     override val size = 3 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Tetrad (first, second, third, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Tetrad (first, first, second, third, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z> Triad<Z, A, B, C>.and(next: D) = Tetrad (first, second, third, next)
fun <Z, A : Z, B : Z, C : Z, D : Z> cons(head: A, tail: Triad<Z, B, C, D>) = Tetrad (head, tail.first, tail.second, tail.third)

 fun <Z, A : Z, B : Z, C : Z, D : Z> Tetrad (list:List<Z>) : Tetrad<Z, A, B, C, D> {
      require(list.size == 4) {"Tetrad should be created from list of 4 elements"}
      return Tetrad (list[0] as A, list[1] as B, list[2] as C, list[3] as D) 
 }

 data class Tetrad<out Z, out A : Z, out B : Z, out C : Z, out D : Z>(val first : A,val second : B,val third : C,val fourth : D,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,)
     override fun toString(): String = "($first, $second, $third, $fourth)"

     override val head = first
     override val tail = Triad (second, third, fourth, )
     override val size = 4 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Pentad (first, second, third, fourth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Pentad (first, first, second, third, fourth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z> Tetrad<Z, A, B, C, D>.and(next: E) = Pentad (first, second, third, fourth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z> cons(head: A, tail: Tetrad<Z, B, C, D, E>) = Pentad (head, tail.first, tail.second, tail.third, tail.fourth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z> Pentad (list:List<Z>) : Pentad<Z, A, B, C, D, E> {
      require(list.size == 5) {"Pentad should be created from list of 5 elements"}
      return Pentad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E) 
 }

 data class Pentad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"

     override val head = first
     override val tail = Tetrad (second, third, fourth, fifth, )
     override val size = 5 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Hexad (first, second, third, fourth, fifth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Hexad (first, first, second, third, fourth, fifth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z> Pentad<Z, A, B, C, D, E>.and(next: F) = Hexad (first, second, third, fourth, fifth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z> cons(head: A, tail: Pentad<Z, B, C, D, E, F>) = Hexad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z> Hexad (list:List<Z>) : Hexad<Z, A, B, C, D, E, F> {
      require(list.size == 6) {"Hexad should be created from list of 6 elements"}
      return Hexad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F) 
 }

 data class Hexad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"

     override val head = first
     override val tail = Pentad (second, third, fourth, fifth, sixth, )
     override val size = 6 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Heptad (first, second, third, fourth, fifth, sixth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Heptad (first, first, second, third, fourth, fifth, sixth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z> Hexad<Z, A, B, C, D, E, F>.and(next: G) = Heptad (first, second, third, fourth, fifth, sixth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z> cons(head: A, tail: Hexad<Z, B, C, D, E, F, G>) = Heptad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z> Heptad (list:List<Z>) : Heptad<Z, A, B, C, D, E, F, G> {
      require(list.size == 7) {"Heptad should be created from list of 7 elements"}
      return Heptad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G) 
 }

 data class Heptad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"

     override val head = first
     override val tail = Hexad (second, third, fourth, fifth, sixth, seventh, )
     override val size = 7 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Octad (first, second, third, fourth, fifth, sixth, seventh, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Octad (first, first, second, third, fourth, fifth, sixth, seventh, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z> Heptad<Z, A, B, C, D, E, F, G>.and(next: H) = Octad (first, second, third, fourth, fifth, sixth, seventh, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z> cons(head: A, tail: Heptad<Z, B, C, D, E, F, G, H>) = Octad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z> Octad (list:List<Z>) : Octad<Z, A, B, C, D, E, F, G, H> {
      require(list.size == 8) {"Octad should be created from list of 8 elements"}
      return Octad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H) 
 }

 data class Octad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"

     override val head = first
     override val tail = Heptad (second, third, fourth, fifth, sixth, seventh, eighth, )
     override val size = 8 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Ennead (first, second, third, fourth, fifth, sixth, seventh, eighth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Ennead (first, first, second, third, fourth, fifth, sixth, seventh, eighth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z> Octad<Z, A, B, C, D, E, F, G, H>.and(next: I) = Ennead (first, second, third, fourth, fifth, sixth, seventh, eighth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z> cons(head: A, tail: Octad<Z, B, C, D, E, F, G, H, I>) = Ennead (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z> Ennead (list:List<Z>) : Ennead<Z, A, B, C, D, E, F, G, H, I> {
      require(list.size == 9) {"Ennead should be created from list of 9 elements"}
      return Ennead (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I) 
 }

 data class Ennead<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"

     override val head = first
     override val tail = Octad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, )
     override val size = 9 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Decad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Decad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z> Ennead<Z, A, B, C, D, E, F, G, H, I>.and(next: J) = Decad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z> cons(head: A, tail: Ennead<Z, B, C, D, E, F, G, H, I, J>) = Decad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z> Decad (list:List<Z>) : Decad<Z, A, B, C, D, E, F, G, H, I, J> {
      require(list.size == 10) {"Decad should be created from list of 10 elements"}
      return Decad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J) 
 }

 data class Decad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth)"

     override val head = first
     override val tail = Ennead (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, )
     override val size = 10 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Undecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Undecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z> Decad<Z, A, B, C, D, E, F, G, H, I, J>.and(next: K) = Undecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z> cons(head: A, tail: Decad<Z, B, C, D, E, F, G, H, I, J, K>) = Undecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z> Undecad (list:List<Z>) : Undecad<Z, A, B, C, D, E, F, G, H, I, J, K> {
      require(list.size == 11) {"Undecad should be created from list of 11 elements"}
      return Undecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K) 
 }

 data class Undecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh)"

     override val head = first
     override val tail = Decad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, )
     override val size = 11 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Dodecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Dodecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z> Undecad<Z, A, B, C, D, E, F, G, H, I, J, K>.and(next: L) = Dodecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z> cons(head: A, tail: Undecad<Z, B, C, D, E, F, G, H, I, J, K, L>) = Dodecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z> Dodecad (list:List<Z>) : Dodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L> {
      require(list.size == 12) {"Dodecad should be created from list of 12 elements"}
      return Dodecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L) 
 }

 data class Dodecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth)"

     override val head = first
     override val tail = Undecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, )
     override val size = 12 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Tridecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Tridecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z> Dodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L>.and(next: M) = Tridecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z> cons(head: A, tail: Dodecad<Z, B, C, D, E, F, G, H, I, J, K, L, M>) = Tridecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z> Tridecad (list:List<Z>) : Tridecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M> {
      require(list.size == 13) {"Tridecad should be created from list of 13 elements"}
      return Tridecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M) 
 }

 data class Tridecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth)"

     override val head = first
     override val tail = Dodecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, )
     override val size = 13 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Quattuordecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Quattuordecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z> Tridecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M>.and(next: N) = Quattuordecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z> cons(head: A, tail: Tridecad<Z, B, C, D, E, F, G, H, I, J, K, L, M, N>) = Quattuordecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth, tail.thirteenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z> Quattuordecad (list:List<Z>) : Quattuordecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N> {
      require(list.size == 14) {"Quattuordecad should be created from list of 14 elements"}
      return Quattuordecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M, list[13] as N) 
 }

 data class Quattuordecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z, out N : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,val fourteenth : N,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,fourteenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth)"

     override val head = first
     override val tail = Tridecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, )
     override val size = 14 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Quindecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Quindecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z> Quattuordecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N>.and(next: O) = Quindecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z> cons(head: A, tail: Quattuordecad<Z, B, C, D, E, F, G, H, I, J, K, L, M, N, O>) = Quindecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth, tail.thirteenth, tail.fourteenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z> Quindecad (list:List<Z>) : Quindecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> {
      require(list.size == 15) {"Quindecad should be created from list of 15 elements"}
      return Quindecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M, list[13] as N, list[14] as O) 
 }

 data class Quindecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z, out N : Z, out O : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,val fourteenth : N,val fifteenth : O,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,fourteenth,fifteenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth)"

     override val head = first
     override val tail = Quattuordecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, )
     override val size = 15 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Hexdecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Hexdecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z> Quindecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>.and(next: P) = Hexdecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z> cons(head: A, tail: Quindecad<Z, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>) = Hexdecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth, tail.thirteenth, tail.fourteenth, tail.fifteenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z> Hexdecad (list:List<Z>) : Hexdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> {
      require(list.size == 16) {"Hexdecad should be created from list of 16 elements"}
      return Hexdecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M, list[13] as N, list[14] as O, list[15] as P) 
 }

 data class Hexdecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z, out N : Z, out O : Z, out P : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,val fourteenth : N,val fifteenth : O,val sixteenth : P,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,fourteenth,fifteenth,sixteenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth, $sixteenth)"

     override val head = first
     override val tail = Quindecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, )
     override val size = 16 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Heptadecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Heptadecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z> Hexdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>.and(next: Q) = Heptadecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z> cons(head: A, tail: Hexdecad<Z, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>) = Heptadecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth, tail.thirteenth, tail.fourteenth, tail.fifteenth, tail.sixteenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z> Heptadecad (list:List<Z>) : Heptadecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> {
      require(list.size == 17) {"Heptadecad should be created from list of 17 elements"}
      return Heptadecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M, list[13] as N, list[14] as O, list[15] as P, list[16] as Q) 
 }

 data class Heptadecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z, out N : Z, out O : Z, out P : Z, out Q : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,val fourteenth : N,val fifteenth : O,val sixteenth : P,val seventeenth : Q,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,fourteenth,fifteenth,sixteenth,seventeenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth, $sixteenth, $seventeenth)"

     override val head = first
     override val tail = Hexdecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, )
     override val size = 17 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Octodecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Octodecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z> Heptadecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>.and(next: R) = Octodecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z> cons(head: A, tail: Heptadecad<Z, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>) = Octodecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth, tail.thirteenth, tail.fourteenth, tail.fifteenth, tail.sixteenth, tail.seventeenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z> Octodecad (list:List<Z>) : Octodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> {
      require(list.size == 18) {"Octodecad should be created from list of 18 elements"}
      return Octodecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M, list[13] as N, list[14] as O, list[15] as P, list[16] as Q, list[17] as R) 
 }

 data class Octodecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z, out N : Z, out O : Z, out P : Z, out Q : Z, out R : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,val fourteenth : N,val fifteenth : O,val sixteenth : P,val seventeenth : Q,val eighteenth : R,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,fourteenth,fifteenth,sixteenth,seventeenth,eighteenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth, $sixteenth, $seventeenth, $eighteenth)"

     override val head = first
     override val tail = Heptadecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, )
     override val size = 18 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Novemdecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Novemdecad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z> Octodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>.and(next: S) = Novemdecad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z> cons(head: A, tail: Octodecad<Z, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>) = Novemdecad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth, tail.thirteenth, tail.fourteenth, tail.fifteenth, tail.sixteenth, tail.seventeenth, tail.eighteenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z> Novemdecad (list:List<Z>) : Novemdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> {
      require(list.size == 19) {"Novemdecad should be created from list of 19 elements"}
      return Novemdecad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M, list[13] as N, list[14] as O, list[15] as P, list[16] as Q, list[17] as R, list[18] as S) 
 }

 data class Novemdecad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z, out N : Z, out O : Z, out P : Z, out Q : Z, out R : Z, out S : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,val fourteenth : N,val fifteenth : O,val sixteenth : P,val seventeenth : Q,val eighteenth : R,val nineteenth : S,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,fourteenth,fifteenth,sixteenth,seventeenth,eighteenth,nineteenth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth, $sixteenth, $seventeenth, $eighteenth, $nineteenth)"

     override val head = first
     override val tail = Octodecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, )
     override val size = 19 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Icosad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, next)
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Icosad (first, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, )
}

infix fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z, T : Z> Novemdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>.and(next: T) = Icosad (first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, next)
fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z, T : Z> cons(head: A, tail: Novemdecad<Z, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>) = Icosad (head, tail.first, tail.second, tail.third, tail.fourth, tail.fifth, tail.sixth, tail.seventh, tail.eighth, tail.ninth, tail.tenth, tail.eleventh, tail.twelfth, tail.thirteenth, tail.fourteenth, tail.fifteenth, tail.sixteenth, tail.seventeenth, tail.eighteenth, tail.nineteenth)

 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z, T : Z> Icosad (list:List<Z>) : Icosad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> {
      require(list.size == 20) {"Icosad should be created from list of 20 elements"}
      return Icosad (list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F, list[6] as G, list[7] as H, list[8] as I, list[9] as J, list[10] as K, list[11] as L, list[12] as M, list[13] as N, list[14] as O, list[15] as P, list[16] as Q, list[17] as R, list[18] as S, list[19] as T) 
 }

 data class Icosad<out Z, out A : Z, out B : Z, out C : Z, out D : Z, out E : Z, out F : Z, out G : Z, out H : Z, out I : Z, out J : Z, out K : Z, out L : Z, out M : Z, out N : Z, out O : Z, out P : Z, out Q : Z, out R : Z, out S : Z, out T : Z>(val first : A,val second : B,val third : C,val fourth : D,val fifth : E,val sixth : F,val seventh : G,val eighth : H,val ninth : I,val tenth : J,val eleventh : K,val twelfth : L,val thirteenth : M,val fourteenth : N,val fifteenth : O,val sixteenth : P,val seventeenth : Q,val eighteenth : R,val nineteenth : S,val twentieth : T,) : Tuple<Z> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,twelfth,thirteenth,fourteenth,fifteenth,sixteenth,seventeenth,eighteenth,nineteenth,twentieth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth, $sixteenth, $seventeenth, $eighteenth, $nineteenth, $twentieth)"

     override val head = first
     override val tail = Novemdecad (second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, twentieth, )
     override val size = 20 
     override fun <Next : @UnsafeVariance Z> append(next: Next) = Myriad(listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, twentieth, next))
     override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Myriad(listOf(new, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, twentieth))
}

data class Myriad<out Z>(val elements: List<Z>) : Tuple<Z> {
      override fun reify() = elements
      override fun toString(): String = elements.joinToString(", ", "(", ")")
 
      override val head = elements[0]
      override val tail = tupleOf(elements.drop(1))
      override val size = elements.size
      override fun <Next : @UnsafeVariance Z> append(next: Next) = Myriad(elements + next)
      override fun <NewFirst : @UnsafeVariance Z> cons(new: NewFirst) = Myriad(listOf(new) + elements)
 }

fun <Z> tupleOf(list: List<Z>) = if (list.size > 20) Myriad(list) else when(list.size) {
      0 -> None
       1 -> Monad ( list[0],  )
       2 -> Dyad ( list[0], list[1],  )
       3 -> Triad ( list[0], list[1], list[2],  )
       4 -> Tetrad ( list[0], list[1], list[2], list[3],  )
       5 -> Pentad ( list[0], list[1], list[2], list[3], list[4],  )
       6 -> Hexad ( list[0], list[1], list[2], list[3], list[4], list[5],  )
       7 -> Heptad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6],  )
       8 -> Octad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7],  )
       9 -> Ennead ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8],  )
       10 -> Decad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9],  )
       11 -> Undecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10],  )
       12 -> Dodecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11],  )
       13 -> Tridecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12],  )
       14 -> Quattuordecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13],  )
       15 -> Quindecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14],  )
       16 -> Hexdecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14], list[15],  )
       17 -> Heptadecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14], list[15], list[16],  )
       18 -> Octodecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14], list[15], list[16], list[17],  )
       19 -> Novemdecad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14], list[15], list[16], list[17], list[18],  )
       20 -> Icosad ( list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14], list[15], list[16], list[17], list[18], list[19],  )
       else -> throw AssertionError()
 }

infix fun <T, N : T> Tuple<T>.and(next: N) = this.append(next)
 fun <T, N : T> cons(head: N, tail: Tuple<T>) = tail.cons(head)

fun <From, A> tee (first : (From) -> A) : (From) -> Monad<A> = {Monad (first (it)) }
 fun <A> lift (firstFunc : (A) -> A) : (Monad<A>) -> Monad<A> = {
      Monad (firstFunc(it.first))
 }

fun <From, Z, A : Z, B : Z> tee (first : (From) -> A, second : (From) -> B) : (From) -> Dyad<Z, A, B> = {Dyad (first (it), second (it)) }
fun <From, Z, A : Z, B : Z, C : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C) : (From) -> Triad<Z, A, B, C> = {Triad (first (it), second (it), third (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D) : (From) -> Tetrad<Z, A, B, C, D> = {Tetrad (first (it), second (it), third (it), fourth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E) : (From) -> Pentad<Z, A, B, C, D, E> = {Pentad (first (it), second (it), third (it), fourth (it), fifth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F) : (From) -> Hexad<Z, A, B, C, D, E, F> = {Hexad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G) : (From) -> Heptad<Z, A, B, C, D, E, F, G> = {Heptad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H) : (From) -> Octad<Z, A, B, C, D, E, F, G, H> = {Octad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I) : (From) -> Ennead<Z, A, B, C, D, E, F, G, H, I> = {Ennead (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J) : (From) -> Decad<Z, A, B, C, D, E, F, G, H, I, J> = {Decad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K) : (From) -> Undecad<Z, A, B, C, D, E, F, G, H, I, J, K> = {Undecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L) : (From) -> Dodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L> = {Dodecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M) : (From) -> Tridecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M> = {Tridecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M, fourteenth : (From) -> N) : (From) -> Quattuordecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N> = {Quattuordecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it), fourteenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M, fourteenth : (From) -> N, fifteenth : (From) -> O) : (From) -> Quindecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = {Quindecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it), fourteenth (it), fifteenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M, fourteenth : (From) -> N, fifteenth : (From) -> O, sixteenth : (From) -> P) : (From) -> Hexdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = {Hexdecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it), fourteenth (it), fifteenth (it), sixteenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M, fourteenth : (From) -> N, fifteenth : (From) -> O, sixteenth : (From) -> P, seventeenth : (From) -> Q) : (From) -> Heptadecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = {Heptadecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it), fourteenth (it), fifteenth (it), sixteenth (it), seventeenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M, fourteenth : (From) -> N, fifteenth : (From) -> O, sixteenth : (From) -> P, seventeenth : (From) -> Q, eighteenth : (From) -> R) : (From) -> Octodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = {Octodecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it), fourteenth (it), fifteenth (it), sixteenth (it), seventeenth (it), eighteenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M, fourteenth : (From) -> N, fifteenth : (From) -> O, sixteenth : (From) -> P, seventeenth : (From) -> Q, eighteenth : (From) -> R, nineteenth : (From) -> S) : (From) -> Novemdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = {Novemdecad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it), fourteenth (it), fifteenth (it), sixteenth (it), seventeenth (it), eighteenth (it), nineteenth (it)) }
fun <From, Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z, T : Z> tee (first : (From) -> A, second : (From) -> B, third : (From) -> C, fourth : (From) -> D, fifth : (From) -> E, sixth : (From) -> F, seventh : (From) -> G, eighth : (From) -> H, ninth : (From) -> I, tenth : (From) -> J, eleventh : (From) -> K, twelfth : (From) -> L, thirteenth : (From) -> M, fourteenth : (From) -> N, fifteenth : (From) -> O, sixteenth : (From) -> P, seventeenth : (From) -> Q, eighteenth : (From) -> R, nineteenth : (From) -> S, twentieth : (From) -> T) : (From) -> Icosad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = {Icosad (first (it), second (it), third (it), fourth (it), fifth (it), sixth (it), seventh (it), eighth (it), ninth (it), tenth (it), eleventh (it), twelfth (it), thirteenth (it), fourteenth (it), fifteenth (it), sixteenth (it), seventeenth (it), eighteenth (it), nineteenth (it), twentieth (it)) }
 

fun <Z, A : Z, B : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B) : (Dyad<Z, A, B>) -> Dyad<Z, A, B> = {
      Dyad (firstFunc(it.first), secondFunc(it.second))
}
 fun <Z, A : Z, B : Z, C : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C) : (Triad<Z, A, B, C>) -> Triad<Z, A, B, C> = {
      Triad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D) : (Tetrad<Z, A, B, C, D>) -> Tetrad<Z, A, B, C, D> = {
      Tetrad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E) : (Pentad<Z, A, B, C, D, E>) -> Pentad<Z, A, B, C, D, E> = {
      Pentad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F) : (Hexad<Z, A, B, C, D, E, F>) -> Hexad<Z, A, B, C, D, E, F> = {
      Hexad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G) : (Heptad<Z, A, B, C, D, E, F, G>) -> Heptad<Z, A, B, C, D, E, F, G> = {
      Heptad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H) : (Octad<Z, A, B, C, D, E, F, G, H>) -> Octad<Z, A, B, C, D, E, F, G, H> = {
      Octad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I) : (Ennead<Z, A, B, C, D, E, F, G, H, I>) -> Ennead<Z, A, B, C, D, E, F, G, H, I> = {
      Ennead (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J) : (Decad<Z, A, B, C, D, E, F, G, H, I, J>) -> Decad<Z, A, B, C, D, E, F, G, H, I, J> = {
      Decad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K) : (Undecad<Z, A, B, C, D, E, F, G, H, I, J, K>) -> Undecad<Z, A, B, C, D, E, F, G, H, I, J, K> = {
      Undecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L) : (Dodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L>) -> Dodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L> = {
      Dodecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M) : (Tridecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M>) -> Tridecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M> = {
      Tridecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M, fourteenthFunc : (N) -> N) : (Quattuordecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N>) -> Quattuordecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N> = {
      Quattuordecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth), fourteenthFunc(it.fourteenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M, fourteenthFunc : (N) -> N, fifteenthFunc : (O) -> O) : (Quindecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>) -> Quindecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = {
      Quindecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth), fourteenthFunc(it.fourteenth), fifteenthFunc(it.fifteenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M, fourteenthFunc : (N) -> N, fifteenthFunc : (O) -> O, sixteenthFunc : (P) -> P) : (Hexdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>) -> Hexdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = {
      Hexdecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth), fourteenthFunc(it.fourteenth), fifteenthFunc(it.fifteenth), sixteenthFunc(it.sixteenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M, fourteenthFunc : (N) -> N, fifteenthFunc : (O) -> O, sixteenthFunc : (P) -> P, seventeenthFunc : (Q) -> Q) : (Heptadecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>) -> Heptadecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = {
      Heptadecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth), fourteenthFunc(it.fourteenth), fifteenthFunc(it.fifteenth), sixteenthFunc(it.sixteenth), seventeenthFunc(it.seventeenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M, fourteenthFunc : (N) -> N, fifteenthFunc : (O) -> O, sixteenthFunc : (P) -> P, seventeenthFunc : (Q) -> Q, eighteenthFunc : (R) -> R) : (Octodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>) -> Octodecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = {
      Octodecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth), fourteenthFunc(it.fourteenth), fifteenthFunc(it.fifteenth), sixteenthFunc(it.sixteenth), seventeenthFunc(it.seventeenth), eighteenthFunc(it.eighteenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M, fourteenthFunc : (N) -> N, fifteenthFunc : (O) -> O, sixteenthFunc : (P) -> P, seventeenthFunc : (Q) -> Q, eighteenthFunc : (R) -> R, nineteenthFunc : (S) -> S) : (Novemdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>) -> Novemdecad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = {
      Novemdecad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth), fourteenthFunc(it.fourteenth), fifteenthFunc(it.fifteenth), sixteenthFunc(it.sixteenth), seventeenthFunc(it.seventeenth), eighteenthFunc(it.eighteenth), nineteenthFunc(it.nineteenth))
}
 fun <Z, A : Z, B : Z, C : Z, D : Z, E : Z, F : Z, G : Z, H : Z, I : Z, J : Z, K : Z, L : Z, M : Z, N : Z, O : Z, P : Z, Q : Z, R : Z, S : Z, T : Z> lift (firstFunc : (A) -> A, secondFunc : (B) -> B, thirdFunc : (C) -> C, fourthFunc : (D) -> D, fifthFunc : (E) -> E, sixthFunc : (F) -> F, seventhFunc : (G) -> G, eighthFunc : (H) -> H, ninthFunc : (I) -> I, tenthFunc : (J) -> J, eleventhFunc : (K) -> K, twelfthFunc : (L) -> L, thirteenthFunc : (M) -> M, fourteenthFunc : (N) -> N, fifteenthFunc : (O) -> O, sixteenthFunc : (P) -> P, seventeenthFunc : (Q) -> Q, eighteenthFunc : (R) -> R, nineteenthFunc : (S) -> S, twentiethFunc : (T) -> T) : (Icosad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>) -> Icosad<Z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = {
      Icosad (firstFunc(it.first), secondFunc(it.second), thirdFunc(it.third), fourthFunc(it.fourth), fifthFunc(it.fifth), sixthFunc(it.sixth), seventhFunc(it.seventh), eighthFunc(it.eighth), ninthFunc(it.ninth), tenthFunc(it.tenth), eleventhFunc(it.eleventh), twelfthFunc(it.twelfth), thirteenthFunc(it.thirteenth), fourteenthFunc(it.fourteenth), fifteenthFunc(it.fifteenth), sixteenthFunc(it.sixteenth), seventeenthFunc(it.seventeenth), eighteenthFunc(it.eighteenth), nineteenthFunc(it.nineteenth), twentiethFunc(it.twentieth))
}
   
