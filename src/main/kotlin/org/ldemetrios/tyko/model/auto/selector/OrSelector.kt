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
    "or-selector",
    ["or-selector", "selector"],
    TOrSelectorImpl::class,
)
public interface TOrSelector : TSelector {
    public val variants: TArray<TSelector>
}

internal data class TOrSelectorImpl(
    @TSerialName("variants")
    override val variants: TArray<TSelector>,
) : TOrSelector {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TOrSelector(@TVararg variants: TArray<TSelector>): TOrSelector =
        TOrSelectorImpl(`variants`)
