@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
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
    "type",
    ["type"],
    TTypeImpl::class,
)
public interface TType : TValue {
    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("type")
    }
}

public open class TTypeImpl(
    public val name: String,
) : TType {
    override fun format(): String = Representations.reprOf(this)

    override fun equals(other: Any?): Boolean = this === other || other is TTypeImpl && this.name ==
            other.name

    override fun hashCode(): Int = name.hashCode()
}
