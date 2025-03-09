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

public interface TSidesSuper<out S : TValue> :
        TArrayOrFractionOrFunctionOrRelativeOrSides<TDynamic, TDynamic>,
        TAutoOrRelativeOrSides<TDynamic>, TRelativeOrSides<TDynamic>,
        TArrayOrFunctionOrRelativeOrSides<TDynamic, TDynamic> {
    public val top: S?

    public val right: S?

    public val bottom: S?

    public val left: S?
}
