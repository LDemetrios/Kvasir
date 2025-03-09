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
    "and-selector",
    ["and-selector", "selector"],
    TAndSelectorImpl::class,
)
public interface TAndSelector : TSelector {
    public val variants: TArray<TSelector>
}

internal data class TAndSelectorImpl(
    @TSerialName("variants")
    override val variants: TArray<TSelector>,
) : TAndSelector {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TAndSelector(@TVararg variants: TArray<TSelector>): TAndSelector =
        TAndSelectorImpl(`variants`)
