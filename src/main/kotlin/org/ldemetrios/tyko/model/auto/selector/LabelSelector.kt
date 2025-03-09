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
    "label-selector",
    ["label-selector", "selector"],
    TLabelSelectorImpl::class,
)
public interface TLabelSelector : TSelector {
    public val label: TLabel
}

internal data class TLabelSelectorImpl(
    @TSerialName("label")
    override val label: TLabel,
) : TLabelSelector {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TLabelSelector(label: TLabel): TLabelSelector = TLabelSelectorImpl(`label`)
