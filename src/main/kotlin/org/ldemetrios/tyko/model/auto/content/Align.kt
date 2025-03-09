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
    "align",
    ["align", "content"],
    TAlignImpl::class,
)
public interface TAlign : TContent {
    public val alignment: TAlignment?

    public val body: TContent

    override fun func(): TElement = TAlign

    public companion object : TElementImpl("align") {
        internal val alignmentType: InternalType = ConcreteType("alignment")

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TAlignImpl(
    @TSerialName("alignment")
    override val alignment: TAlignment? = null,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TAlign {
    override fun format(): String = Representations.elementRepr("align",ArgumentEntry(false, null,
            `alignment`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TAlign(
    alignment: TAlignment? = null,
    @TContentBody body: TContent,
    label: TLabel? = null,
): TAlign = TAlignImpl(`alignment`, `body`, `label`)

@TSetRuleType(
    "align",
    TSetAlignImpl::class,
)
public interface TSetAlign : TSetRule {
    override val elem: String
        get() = "align"

    public val alignment: TAlignment?

    override fun format(): String = Representations.setRepr("align",ArgumentEntry(false, null,
            `alignment`),)
}

internal class TSetAlignImpl(
    @TSerialName("alignment")
    override val alignment: TAlignment? = null,
) : TSetAlign

@TypstOverloads
public fun TSetAlign(alignment: TAlignment? = null): TSetAlign = TSetAlignImpl(`alignment`)
