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
    "after-selector",
    ["after-selector", "selector"],
    TAfterSelectorImpl::class,
)
public interface TAfterSelector : TSelector {
    public val selector: TSelector

    public val start: TSelector

    public val inclusive: TBool?
}

internal data class TAfterSelectorImpl(
    @TSerialName("selector")
    override val selector: TSelector,
    @TSerialName("start")
    override val start: TSelector,
    @TSerialName("inclusive")
    override val inclusive: TBool? = null,
) : TAfterSelector {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TAfterSelector(
    selector: TSelector,
    start: TSelector,
    inclusive: TBool? = null,
): TAfterSelector = TAfterSelectorImpl(`selector`, `start`, `inclusive`)
