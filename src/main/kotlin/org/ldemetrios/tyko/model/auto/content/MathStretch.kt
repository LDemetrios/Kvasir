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
    "math.stretch",
    ["math.stretch", "content"],
    TMathStretchImpl::class,
)
public interface TMathStretch : TContent {
    public val body: TContent

    public val sz: TAutoOrRelative?

    override fun func(): TElement = TMathStretch

    public companion object : TElementImpl("math.stretch") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val szType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("relative"))
    }
}

internal data class TMathStretchImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("size")
    override val sz: TAutoOrRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathStretch {
    override fun format(): String = Representations.elementRepr("math.stretch",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "size", `sz`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathStretch(
    @TContentBody body: TContent,
    sz: TAutoOrRelative? = null,
    label: TLabel? = null,
): TMathStretch = TMathStretchImpl(`body`, `sz`, `label`)

@TSetRuleType(
    "math.stretch",
    TSetMathStretchImpl::class,
)
public interface TSetMathStretch : TSetRule {
    override val elem: String
        get() = "math.stretch"

    public val sz: TAutoOrRelative?

    override fun format(): String = Representations.setRepr("math.stretch",ArgumentEntry(false,
            "size", `sz`),)
}

internal class TSetMathStretchImpl(
    @TSerialName("size")
    override val sz: TAutoOrRelative? = null,
) : TSetMathStretch

@TypstOverloads
public fun TSetMathStretch(sz: TAutoOrRelative? = null): TSetMathStretch = TSetMathStretchImpl(`sz`)
