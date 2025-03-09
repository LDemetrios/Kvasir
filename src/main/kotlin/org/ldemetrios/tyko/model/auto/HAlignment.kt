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

public interface THAlignment : TAlignment {
    override fun type(): TType = super<TAlignment>.type()

    public companion object {
        public val Left: THAlignment = THAlignmentImpl("left")

        public val Center: THAlignment = THAlignmentImpl("center")

        public val Right: THAlignment = THAlignmentImpl("right")

        public val Start: THAlignment = THAlignmentImpl("start")

        public val End: THAlignment = THAlignmentImpl("end")

        public fun of(str: TStr): THAlignment? = of(str.strValue)

        public fun of(str: String): THAlignment? = when(str) {
            "left" -> THAlignment.Left
            "center" -> THAlignment.Center
            "right" -> THAlignment.Right
            "start" -> THAlignment.Start
            "end" -> THAlignment.End
            else -> null
        }
    }
}

internal class THAlignmentImpl internal constructor(
    public val strValue: String,
) : THAlignment {
    override fun format(): String = strValue

    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}
