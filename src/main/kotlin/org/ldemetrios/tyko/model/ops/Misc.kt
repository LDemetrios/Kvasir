package org.ldemetrios.tyko.model


val TRelative.abs: TLength?
    get() = when (this) {
        is TRatio -> 0.pt
        is TLength -> this
        is TRelativeImpl -> this.abs
        else -> throw AssertionError()
    }

val TRelative.rel: TRatio?
    get() = when (this) {
        is TRatio -> this
        is TLength -> 0.pc
        is TRelativeImpl -> this.rel
        else -> throw AssertionError()
    }

val TAlignment.horizontal: THAlignment?
    get() = when (this) {
        is THAlignment -> this
        is TVAlignment -> null
        is TAlignmentImpl -> this.horizontal
        else -> throw AssertionError()
    }

val TAlignment.vertical: TVAlignment?
    get() = when (this) {
        is THAlignment -> null
        is TVAlignment -> this
        is TAlignmentImpl -> this.vertical
        else -> throw AssertionError()
    }
