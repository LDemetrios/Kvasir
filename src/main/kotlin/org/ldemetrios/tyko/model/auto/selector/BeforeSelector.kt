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
    "before-selector",
    ["before-selector", "selector"],
    TBeforeSelectorImpl::class,
)
public interface TBeforeSelector : TSelector {
    public val selector: TSelector

    public val end: TSelector

    public val inclusive: TBool?
}

internal data class TBeforeSelectorImpl(
    @TSerialName("selector")
    override val selector: TSelector,
    @TSerialName("end")
    override val end: TSelector,
    @TSerialName("inclusive")
    override val inclusive: TBool? = null,
) : TBeforeSelector {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TBeforeSelector(
    selector: TSelector,
    end: TSelector,
    inclusive: TBool? = null,
): TBeforeSelector = TBeforeSelectorImpl(`selector`, `end`, `inclusive`)
