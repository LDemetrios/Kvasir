@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.String
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import org.ldemetrios.js.JSBoolean
import org.ldemetrios.js.JSNumber
import org.ldemetrios.js.JSObject
import org.ldemetrios.js.JSString
import org.ldemetrios.js.JSUndefined
import org.ldemetrios.utilities.cast
import org.ldemetrios.utilities.castUnchecked

@TInterfaceType(
    "relative-impl",
    ["relative-impl", "relative"],
    TRelativeImplImpl::class,
)
public interface TRelativeImpl : TRelative {
    public val rel: TRatio?

    public val abs: TLength?

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("relative-impl")

        internal val relType: InternalType = ConcreteType("ratio")

        internal val absType: InternalType = ConcreteType("length")
    }
}

internal data class TRelativeImplImpl(
    @TSerialName("rel")
    override val rel: TRatio? = null,
    @TSerialName("abs")
    override val abs: TLength? = null,
) : TRelativeImpl {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TRelativeImpl(rel: TRatio? = null, abs: TLength? = null): TRelativeImpl =
        TRelativeImplImpl(`rel`, `abs`)
