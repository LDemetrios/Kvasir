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
    "curve.close",
    ["curve.close", "content"],
    TCurveCloseImpl::class,
)
public interface TCurveClose : TContent {
    public val mode: TCurveCloseMode?

    override fun func(): TElement = TCurveClose

    public companion object : TElementImpl("curve.close") {
        internal val modeType: InternalType = ConcreteType("curve-close-mode")
    }
}

internal data class TCurveCloseImpl(
    @TSerialName("mode")
    override val mode: TCurveCloseMode? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCurveClose {
    override fun format(): String = Representations.elementRepr("curve.close",ArgumentEntry(false,
            "mode", `mode`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TCurveClose(mode: TCurveCloseMode? = null, label: TLabel? = null): TCurveClose =
        TCurveCloseImpl(`mode`, `label`)

@TSetRuleType(
    "curve.close",
    TSetCurveCloseImpl::class,
)
public interface TSetCurveClose : TSetRule {
    override val elem: String
        get() = "curve.close"

    public val mode: TCurveCloseMode?

    override fun format(): String = Representations.setRepr("curve.close",ArgumentEntry(false, "mode",
            `mode`),)
}

internal class TSetCurveCloseImpl(
    @TSerialName("mode")
    override val mode: TCurveCloseMode? = null,
) : TSetCurveClose

@TypstOverloads
public fun TSetCurveClose(mode: TCurveCloseMode? = null): TSetCurveClose =
        TSetCurveCloseImpl(`mode`)
