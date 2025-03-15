@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.ByteArray
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
    "bytes",
    ["bytes"],
    TBytesImpl::class,
)
public interface TBytes : TValue, TBytesOrStr, TAutoOrBytesOrStr {
    public val bytesValue: ByteArray

    override fun type(): TType = Type

    override fun format(): String = Representations.reprOf(bytesValue)

    public companion object {
        public val Type: TType = TTypeImpl("bytes")
    }
}

internal data class TBytesImpl(
    override val bytesValue: ByteArray,
) : TBytes

public fun TBytes(`value`: ByteArray): TBytes = TBytesImpl(value)
