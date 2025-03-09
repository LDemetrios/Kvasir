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
    "pad",
    ["pad", "content"],
    TPadImpl::class,
)
public interface TPad : TContent {
    public val body: TContent

    public val left: TRelative?

    public val top: TRelative?

    public val right: TRelative?

    public val bottom: TRelative?

    public val x: TRelative?

    public val y: TRelative?

    public val rest: TRelative?

    override fun func(): TElement = TPad

    public companion object : TElementImpl("pad") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val leftType: InternalType = ConcreteType("relative")

        internal val topType: InternalType = ConcreteType("relative")

        internal val rightType: InternalType = ConcreteType("relative")

        internal val bottomType: InternalType = ConcreteType("relative")

        internal val xType: InternalType = ConcreteType("relative")

        internal val yType: InternalType = ConcreteType("relative")

        internal val restType: InternalType = ConcreteType("relative")
    }
}

internal data class TPadImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("left")
    override val left: TRelative? = null,
    @TSerialName("top")
    override val top: TRelative? = null,
    @TSerialName("right")
    override val right: TRelative? = null,
    @TSerialName("bottom")
    override val bottom: TRelative? = null,
    @TSerialName("x")
    override val x: TRelative? = null,
    @TSerialName("y")
    override val y: TRelative? = null,
    @TSerialName("rest")
    override val rest: TRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TPad {
    override fun format(): String = Representations.elementRepr("pad",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "left", `left`),ArgumentEntry(false, "top",
            `top`),ArgumentEntry(false, "right", `right`),ArgumentEntry(false, "bottom",
            `bottom`),ArgumentEntry(false, "x", `x`),ArgumentEntry(false, "y", `y`),ArgumentEntry(false,
            "rest", `rest`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TPad(
    @TContentBody body: TContent,
    left: TRelative? = null,
    top: TRelative? = null,
    right: TRelative? = null,
    bottom: TRelative? = null,
    x: TRelative? = null,
    y: TRelative? = null,
    rest: TRelative? = null,
    label: TLabel? = null,
): TPad = TPadImpl(`body`, `left`, `top`, `right`, `bottom`, `x`, `y`, `rest`, `label`)

@TSetRuleType(
    "pad",
    TSetPadImpl::class,
)
public interface TSetPad : TSetRule {
    override val elem: String
        get() = "pad"

    public val left: TRelative?

    public val top: TRelative?

    public val right: TRelative?

    public val bottom: TRelative?

    public val x: TRelative?

    public val y: TRelative?

    public val rest: TRelative?

    override fun format(): String = Representations.setRepr("pad",ArgumentEntry(false, "left",
            `left`),ArgumentEntry(false, "top", `top`),ArgumentEntry(false, "right",
            `right`),ArgumentEntry(false, "bottom", `bottom`),ArgumentEntry(false, "x",
            `x`),ArgumentEntry(false, "y", `y`),ArgumentEntry(false, "rest", `rest`),)
}

internal class TSetPadImpl(
    @TSerialName("left")
    override val left: TRelative? = null,
    @TSerialName("top")
    override val top: TRelative? = null,
    @TSerialName("right")
    override val right: TRelative? = null,
    @TSerialName("bottom")
    override val bottom: TRelative? = null,
    @TSerialName("x")
    override val x: TRelative? = null,
    @TSerialName("y")
    override val y: TRelative? = null,
    @TSerialName("rest")
    override val rest: TRelative? = null,
) : TSetPad

@TypstOverloads
public fun TSetPad(
    left: TRelative? = null,
    top: TRelative? = null,
    right: TRelative? = null,
    bottom: TRelative? = null,
    x: TRelative? = null,
    y: TRelative? = null,
    rest: TRelative? = null,
): TSetPad = TSetPadImpl(`left`, `top`, `right`, `bottom`, `x`, `y`, `rest`)
