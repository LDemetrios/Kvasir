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
    "version",
    ["version"],
    TVersionImpl::class,
)
public interface TVersion : TValue {
    public val `value`: TArray<TInt>

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("version")

        internal val valueType: InternalType = ConcreteType("array", listOf(ConcreteType("int")))
    }
}

internal data class TVersionImpl(
    @TSerialName("value")
    override val `value`: TArray<TInt>,
) : TVersion {
    override fun format(): String = Representations.structRepr("version",ArgumentEntry(false, null,
            `value`),)
}

@TypstOverloads
public fun TVersion(`value`: TArray<TInt>): TVersion = TVersionImpl(`value`)
