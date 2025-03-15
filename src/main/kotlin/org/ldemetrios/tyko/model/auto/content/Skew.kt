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
    "skew",
    ["skew", "content"],
    TSkewImpl::class,
)
public interface TSkew : TContent {
    public val body: TContent

    public val ax: TAngle?

    public val ay: TAngle?

    public val origin: TAlignment?

    public val reflow: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("skew")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val axType: InternalType = ConcreteType("angle")

        internal val ayType: InternalType = ConcreteType("angle")

        internal val originType: InternalType = ConcreteType("alignment")

        internal val reflowType: InternalType = ConcreteType("bool")
    }
}

internal data class TSkewImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("ax")
    override val ax: TAngle? = null,
    @TSerialName("ay")
    override val ay: TAngle? = null,
    @TSerialName("origin")
    override val origin: TAlignment? = null,
    @TSerialName("reflow")
    override val reflow: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TSkew {
    override fun format(): String = Representations.elementRepr("skew",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "ax", `ax`),ArgumentEntry(false, "ay", `ay`),ArgumentEntry(false,
            "origin", `origin`),ArgumentEntry(false, "reflow", `reflow`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TSkew(
    @TContentBody body: TContent,
    ax: TAngle? = null,
    ay: TAngle? = null,
    origin: TAlignment? = null,
    reflow: TBool? = null,
    label: TLabel? = null,
): TSkew = TSkewImpl(`body`, `ax`, `ay`, `origin`, `reflow`, `label`)

@TSetRuleType(
    "skew",
    TSetSkewImpl::class,
)
public interface TSetSkew : TSetRule {
    override val elem: String
        get() = "skew"

    public val ax: TAngle?

    public val ay: TAngle?

    public val origin: TAlignment?

    public val reflow: TBool?

    override fun format(): String = Representations.setRepr("skew",ArgumentEntry(false, "ax",
            `ax`),ArgumentEntry(false, "ay", `ay`),ArgumentEntry(false, "origin",
            `origin`),ArgumentEntry(false, "reflow", `reflow`),)
}

internal class TSetSkewImpl(
    @TSerialName("ax")
    override val ax: TAngle? = null,
    @TSerialName("ay")
    override val ay: TAngle? = null,
    @TSerialName("origin")
    override val origin: TAlignment? = null,
    @TSerialName("reflow")
    override val reflow: TBool? = null,
) : TSetSkew

@TypstOverloads
public fun TSetSkew(
    ax: TAngle? = null,
    ay: TAngle? = null,
    origin: TAlignment? = null,
    reflow: TBool? = null,
): TSetSkew = TSetSkewImpl(`ax`, `ay`, `origin`, `reflow`)
