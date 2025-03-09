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
    "metadata",
    ["metadata", "content"],
    TMetadataImpl::class,
)
public interface TMetadata<out D : TValue> : TContent {
    public val `value`: D

    override fun func(): TElement = TMetadata

    public companion object : TElementImpl("metadata") {
        internal fun valueType(D: InternalType): InternalType = D
    }
}

internal data class TMetadataImpl<out D : TValue>(
    @TSerialName("value")
    override val `value`: D,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMetadata<D> {
    override fun format(): String = Representations.elementRepr("metadata",ArgumentEntry(false, null,
            `value`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun <D : TValue> TMetadata(`value`: D, label: TLabel? = null): TMetadata<D> =
        TMetadataImpl(`value`, `label`)
