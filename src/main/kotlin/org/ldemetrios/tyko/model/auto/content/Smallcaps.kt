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
    "smallcaps",
    ["smallcaps", "content"],
    TSmallcapsImpl::class,
)
public interface TSmallcaps : TContent {
    public val body: TContent

    public val all: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("smallcaps")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val allType: InternalType = ConcreteType("bool")
    }
}

internal data class TSmallcapsImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("all")
    override val all: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TSmallcaps {
    override fun format(): String = Representations.elementRepr("smallcaps",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "all", `all`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TSmallcaps(
    @TContentBody body: TContent,
    all: TBool? = null,
    label: TLabel? = null,
): TSmallcaps = TSmallcapsImpl(`body`, `all`, `label`)

@TSetRuleType(
    "smallcaps",
    TSetSmallcapsImpl::class,
)
public interface TSetSmallcaps : TSetRule {
    override val elem: String
        get() = "smallcaps"

    public val all: TBool?

    override fun format(): String = Representations.setRepr("smallcaps",ArgumentEntry(false, "all",
            `all`),)
}

internal class TSetSmallcapsImpl(
    @TSerialName("all")
    override val all: TBool? = null,
) : TSetSmallcaps

@TypstOverloads
public fun TSetSmallcaps(all: TBool? = null): TSetSmallcaps = TSetSmallcapsImpl(`all`)
