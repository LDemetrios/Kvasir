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
    "super",
    ["super", "content"],
    TSuperImpl::class,
)
public interface TSuper : TContent {
    public val body: TContent

    public val typographic: TBool?

    public val baseline: TLength?

    public val sz: TLength?

    override fun func(): TElement = TSuper

    public companion object : TElementImpl("super") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val typographicType: InternalType = ConcreteType("bool")

        internal val baselineType: InternalType = ConcreteType("length")

        internal val szType: InternalType = ConcreteType("length")
    }
}

internal data class TSuperImpl(
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
) : TSuper {
    override fun format(): String = Representations.elementRepr("super",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "typographic", `typographic`),ArgumentEntry(false, "baseline",
            `baseline`),ArgumentEntry(false, "size", `sz`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TSuper(
    @TContentBody body: TContent,
    typographic: TBool? = null,
    baseline: TLength? = null,
    sz: TLength? = null,
    label: TLabel? = null,
): TSuper = TSuperImpl(`body`, `typographic`, `baseline`, `sz`, `label`)

@TSetRuleType(
    "super",
    TSetSuperImpl::class,
)
public interface TSetSuper : TSetRule {
    override val elem: String
        get() = "super"

    public val typographic: TBool?

    public val baseline: TLength?

    public val sz: TLength?

    override fun format(): String = Representations.setRepr("super",ArgumentEntry(false,
            "typographic", `typographic`),ArgumentEntry(false, "baseline",
            `baseline`),ArgumentEntry(false, "size", `sz`),)
}

internal class TSetSuperImpl(
    @TSerialName("typographic")
    override val typographic: TBool? = null,
    @TSerialName("baseline")
    override val baseline: TLength? = null,
    @TSerialName("size")
    override val sz: TLength? = null,
) : TSetSuper

@TypstOverloads
public fun TSetSuper(
    typographic: TBool? = null,
    baseline: TLength? = null,
    sz: TLength? = null,
): TSetSuper = TSetSuperImpl(`typographic`, `baseline`, `sz`)
