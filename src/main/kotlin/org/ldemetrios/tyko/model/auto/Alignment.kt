@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.reflect.KType
import kotlin.reflect.typeOf
import org.ldemetrios.js.JSBoolean
import org.ldemetrios.js.JSNumber
import org.ldemetrios.js.JSObject
import org.ldemetrios.js.JSString
import org.ldemetrios.js.JSUndefined
import org.ldemetrios.utilities.cast
import org.ldemetrios.utilities.castUnchecked

public interface TAlignment : TAlignmentOrAutoOrNone, TAlignmentOrAuto,
        TAlignmentOrArrayOrAutoOrFunction<TDynamic>, TValue {
    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("alignment")
    }
}
