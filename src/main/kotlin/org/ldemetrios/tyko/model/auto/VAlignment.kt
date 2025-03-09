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

public interface TVAlignment : TAlignment {
    override fun type(): TType = super<TAlignment>.type()

    public companion object {
        public val Top: TVAlignment = TVAlignmentImpl("top")

        public val Horizon: TVAlignment = TVAlignmentImpl("horizon")

        public val Bottom: TVAlignment = TVAlignmentImpl("bottom")

        public fun of(str: TStr): TVAlignment? = of(str.strValue)

        public fun of(str: String): TVAlignment? = when(str) {
            "top" -> TVAlignment.Top
            "horizon" -> TVAlignment.Horizon
            "bottom" -> TVAlignment.Bottom
            else -> null
        }
    }
}

internal class TVAlignmentImpl internal constructor(
    public val strValue: String,
) : TVAlignment {
    override fun format(): String = strValue

    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}
