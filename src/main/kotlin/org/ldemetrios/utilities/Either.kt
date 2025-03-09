@file:Suppress("unused")

package org.ldemetrios.utilities

import org.ldemetrios.functional.identity

sealed interface Either<out L, out R> {
    fun isLeft(): Boolean
    fun isRight(): Boolean
    fun left(): L
    fun right(): R

    fun getLeftOrElse(default: () -> @UnsafeVariance L): L = when {
        isLeft() -> left()
        else -> default()
    }

    fun getRightOrElse(default: () -> @UnsafeVariance R): R = when {
        isRight() -> right()
        else -> default()
    }

    fun getLeftOrNull(): L? = when {
        isLeft() -> left()
        else -> null
    }

    fun getRightOrNull(): R? = when {
        isRight() -> right()
        else -> null
    }

    fun <NL, NR> map(leftTransform: (L) -> NL, rightTransform: (R) -> NR): Either<NL, NR> = when {
        isLeft() -> Left(leftTransform(left()))
        isRight() -> Right(rightTransform(right()))
        else -> throw IllegalStateException()
    }

    fun <NL> mapLeft(transform: (L) -> NL): Either<NL, R> = map(transform, identity())
    fun <NR> mapRight(transform: (R) -> NR): Either<L, NR> = map(identity(), transform)

    fun <T> flatMap(
        left: (L) -> Either<@UnsafeVariance L, T>,
        right: (R) -> Either<@UnsafeVariance L, T>
    ): Either<L, T> = when {
        isLeft() -> left(left()!!)
        isRight() -> right(right()!!)
        else -> throw IllegalStateException()
    }

    fun <T> flatMapLeft(
        left: (L) -> Either<@UnsafeVariance L, @UnsafeVariance R>,
    ): Either<L, R> = flatMap(left, identity())

    fun <T> flatMapRight(
        right: (R) -> Either<@UnsafeVariance L, @UnsafeVariance R>,
    ): Either<L, R> = flatMap(identity(), right)

    data class Left<L>(private val value: L) : Either<L, Nothing> {
        override fun isLeft(): Boolean = true
        override fun isRight(): Boolean = false
        override fun left(): L = value
        override fun right(): Nothing = throw IllegalStateException()
        override fun toString(): String = value.toString()
    }

    data class Right<R>(private val value: R) : Either<Nothing, R> {
        override fun isLeft(): Boolean = false
        override fun isRight(): Boolean = true
        override fun left(): Nothing = throw IllegalStateException()
        override fun right(): R = value
        override fun toString(): String = value.toString()
    }
}

fun <T, L : T, R : T> Either<L, R>.conjoin() = if (isLeft()) left() else right()

fun <L, R> Either<Either<L, R>, R>.flattenLeft(): Either<L, R> = when {
    isLeft() -> left()
    isRight() -> Either.Right(right())
    else -> throw IllegalStateException()
}

fun <L, R> Either<L, Either<L, R>>.flattenRight(): Either<L, R> = when {
    isLeft() -> Either.Left(left())
    isRight() -> right()
    else -> throw IllegalStateException()
}

fun <L, R> Either<Either<L, R>, Either<L, R>>.flatten(): Either<L, R> = when {
    isLeft() -> left()
    isRight() -> right()
    else -> throw IllegalStateException()
}

fun <L, R> Either<L, R>.swap(): Either<R, L> = when {
    isLeft() -> Either.Right(left())
    isRight() -> Either.Left(right())
    else -> throw IllegalStateException()
}
