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
    "regex-selector",
    ["regex-selector", "selector"],
    TRegexSelectorImpl::class,
)
public interface TRegexSelector : TSelector {
    public val regex: TRegex
}

internal data class TRegexSelectorImpl(
    @TSerialName("regex")
    override val regex: TRegex,
) : TRegexSelector {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TRegexSelector(regex: TRegex): TRegexSelector = TRegexSelectorImpl(`regex`)
