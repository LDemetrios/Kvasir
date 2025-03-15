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

@TInterfaceType(
    "content",
    ["content"],
    TContent::class,
)
public abstract interface TContent : TValue, TAutoOrContentOrNone,
        TArrayOrContentOrFunction<TDynamic>, TContentOrNone, TAutoOrContentOrFunctionOrNone,
        TAutoOrContent, TContentOrLabel, TContentOrLabelOrNone, TArrayOrContent<TDynamic>,
        TContentOrStr, TContentOrFractionOrRelative {
    public val label: TLabel?

    public fun func(): TElement

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("content")
    }
}
