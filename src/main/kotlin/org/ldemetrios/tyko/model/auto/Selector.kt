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

public interface TSelector : TFunctionOrLabelOrLocationOrSelector, TLocationOrSelectorOrStr,
        TNoneOrSelector, TFunctionOrLabelOrLocationOrSelectorOrStr, TValue {
    override fun type(): TType = TSelector

    public companion object : TTypeImpl("selector")
}
