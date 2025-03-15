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
    "scale",
    ["scale", "content"],
    TScaleImpl::class,
)
public interface TScale : TContent {
    public val factor: TAutoOrLengthOrRatio?

    public val body: TContent

    public val x: TAutoOrLengthOrRatio?

    public val y: TAutoOrLengthOrRatio?

    public val origin: TAlignment?

    public val reflow: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("scale")

        internal val factorType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"),
                ConcreteType("ratio"))

        internal val bodyType: InternalType = ConcreteType("content")

        internal val xType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"),
                ConcreteType("ratio"))

        internal val yType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"),
                ConcreteType("ratio"))

        internal val originType: InternalType = ConcreteType("alignment")

        internal val reflowType: InternalType = ConcreteType("bool")
    }
}

internal data class TScaleImpl(
    @TSerialName("factor")
    override val factor: TAutoOrLengthOrRatio? = null,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("x")
    override val x: TAutoOrLengthOrRatio? = null,
    @TSerialName("y")
    override val y: TAutoOrLengthOrRatio? = null,
    @TSerialName("origin")
    override val origin: TAlignment? = null,
    @TSerialName("reflow")
    override val reflow: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TScale {
    override fun format(): String = Representations.elementRepr("scale",ArgumentEntry(false, null,
            `factor`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "x",
            `x`),ArgumentEntry(false, "y", `y`),ArgumentEntry(false, "origin",
            `origin`),ArgumentEntry(false, "reflow", `reflow`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TScale(
    factor: TAutoOrLengthOrRatio? = null,
    @TContentBody body: TContent,
    x: TAutoOrLengthOrRatio? = null,
    y: TAutoOrLengthOrRatio? = null,
    origin: TAlignment? = null,
    reflow: TBool? = null,
    label: TLabel? = null,
): TScale = TScaleImpl(`factor`, `body`, `x`, `y`, `origin`, `reflow`, `label`)

@TSetRuleType(
    "scale",
    TSetScaleImpl::class,
)
public interface TSetScale : TSetRule {
    override val elem: String
        get() = "scale"

    public val factor: TAutoOrLengthOrRatio?

    public val x: TAutoOrLengthOrRatio?

    public val y: TAutoOrLengthOrRatio?

    public val origin: TAlignment?

    public val reflow: TBool?

    override fun format(): String = Representations.setRepr("scale",ArgumentEntry(false, null,
            `factor`),ArgumentEntry(false, "x", `x`),ArgumentEntry(false, "y", `y`),ArgumentEntry(false,
            "origin", `origin`),ArgumentEntry(false, "reflow", `reflow`),)
}

internal class TSetScaleImpl(
    @TSerialName("factor")
    override val factor: TAutoOrLengthOrRatio? = null,
    @TSerialName("x")
    override val x: TAutoOrLengthOrRatio? = null,
    @TSerialName("y")
    override val y: TAutoOrLengthOrRatio? = null,
    @TSerialName("origin")
    override val origin: TAlignment? = null,
    @TSerialName("reflow")
    override val reflow: TBool? = null,
) : TSetScale

@TypstOverloads
public fun TSetScale(
    factor: TAutoOrLengthOrRatio? = null,
    x: TAutoOrLengthOrRatio? = null,
    y: TAutoOrLengthOrRatio? = null,
    origin: TAlignment? = null,
    reflow: TBool? = null,
): TSetScale = TSetScaleImpl(`factor`, `x`, `y`, `origin`, `reflow`)
