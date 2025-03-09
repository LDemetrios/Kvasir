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
    "sub",
    ["sub", "content"],
    TSubImpl::class,
)
public interface TSub : TContent {
    public val body: TContent

    public val typographic: TBool?

    public val baseline: TLength?

    public val sz: TLength?

    override fun func(): TElement = TSub

    public companion object : TElementImpl("sub") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val typographicType: InternalType = ConcreteType("bool")

        internal val baselineType: InternalType = ConcreteType("length")

        internal val szType: InternalType = ConcreteType("length")
    }
}

internal data class TSubImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("typographic")
    override val typographic: TBool? = null,
    @TSerialName("baseline")
    override val baseline: TLength? = null,
    @TSerialName("size")
    override val sz: TLength? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TSub {
    override fun format(): String = Representations.elementRepr("sub",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "typographic", `typographic`),ArgumentEntry(false, "baseline",
            `baseline`),ArgumentEntry(false, "size", `sz`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TSub(
    @TContentBody body: TContent,
    typographic: TBool? = null,
    baseline: TLength? = null,
    sz: TLength? = null,
    label: TLabel? = null,
): TSub = TSubImpl(`body`, `typographic`, `baseline`, `sz`, `label`)

@TSetRuleType(
    "sub",
    TSetSubImpl::class,
)
public interface TSetSub : TSetRule {
    override val elem: String
        get() = "sub"

    public val typographic: TBool?

    public val baseline: TLength?

    public val sz: TLength?

    override fun format(): String = Representations.setRepr("sub",ArgumentEntry(false, "typographic",
            `typographic`),ArgumentEntry(false, "baseline", `baseline`),ArgumentEntry(false, "size",
            `sz`),)
}

internal class TSetSubImpl(
    @TSerialName("typographic")
    override val typographic: TBool? = null,
    @TSerialName("baseline")
    override val baseline: TLength? = null,
    @TSerialName("size")
    override val sz: TLength? = null,
) : TSetSub

@TypstOverloads
public fun TSetSub(
    typographic: TBool? = null,
    baseline: TLength? = null,
    sz: TLength? = null,
): TSetSub = TSetSubImpl(`typographic`, `baseline`, `sz`)
