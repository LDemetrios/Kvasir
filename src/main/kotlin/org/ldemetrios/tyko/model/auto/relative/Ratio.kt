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
    "ratio",
    ["ratio", "relative"],
    TRatioImpl::class,
)
public interface TRatio : TAutoOrLengthOrRatio, TIntOrRatio, TFloatOrRatio, TColorOrRatio, TRelative
        {
    public val `value`: TFloat

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("ratio")

        internal val valueType: InternalType = ConcreteType("float")
    }
}

internal data class TRatioImpl(
    @TSerialName("value")
    override val `value`: TFloat,
) : TRatio {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TRatio(`value`: TFloat): TRatio = TRatioImpl(`value`)
