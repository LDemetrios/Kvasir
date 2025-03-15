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
    "repeat",
    ["repeat", "content"],
    TRepeatImpl::class,
)
public interface TRepeat : TContent {
    public val body: TContent

    public val gap: TLength?

    public val justify: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("repeat")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val gapType: InternalType = ConcreteType("length")

        internal val justifyType: InternalType = ConcreteType("bool")
    }
}

internal data class TRepeatImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("gap")
    override val gap: TLength? = null,
    @TSerialName("justify")
    override val justify: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TRepeat {
    override fun format(): String = Representations.elementRepr("repeat",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "gap", `gap`),ArgumentEntry(false, "justify",
            `justify`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TRepeat(
    @TContentBody body: TContent,
    gap: TLength? = null,
    justify: TBool? = null,
    label: TLabel? = null,
): TRepeat = TRepeatImpl(`body`, `gap`, `justify`, `label`)

@TSetRuleType(
    "repeat",
    TSetRepeatImpl::class,
)
public interface TSetRepeat : TSetRule {
    override val elem: String
        get() = "repeat"

    public val gap: TLength?

    public val justify: TBool?

    override fun format(): String = Representations.setRepr("repeat",ArgumentEntry(false, "gap",
            `gap`),ArgumentEntry(false, "justify", `justify`),)
}

internal class TSetRepeatImpl(
    @TSerialName("gap")
    override val gap: TLength? = null,
    @TSerialName("justify")
    override val justify: TBool? = null,
) : TSetRepeat

@TypstOverloads
public fun TSetRepeat(gap: TLength? = null, justify: TBool? = null): TSetRepeat =
        TSetRepeatImpl(`gap`, `justify`)
