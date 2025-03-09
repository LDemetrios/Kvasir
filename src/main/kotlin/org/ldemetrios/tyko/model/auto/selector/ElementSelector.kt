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
    "element-selector",
    ["element-selector", "selector"],
    TElementSelectorImpl::class,
)
public interface TElementSelector : TSelector {
    public val element: TStr

    public val `where`: TDictionary<TValue>?
}

internal data class TElementSelectorImpl(
    @TSerialName("element")
    override val element: TStr,
    @TSerialName("where")
    override val `where`: TDictionary<TValue>? = null,
) : TElementSelector {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TElementSelector(element: TStr, `where`: TDictionary<TValue>? = null): TElementSelector =
        TElementSelectorImpl(`element`, `where`)
