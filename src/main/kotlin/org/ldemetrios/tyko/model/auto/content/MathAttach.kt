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
    "math.attach",
    ["math.attach", "content"],
    TMathAttachImpl::class,
)
public interface TMathAttach : TContent {
    public val base: TContent

    public val t: TContentOrNone?

    public val b: TContentOrNone?

    public val tl: TContentOrNone?

    public val bl: TContentOrNone?

    public val tr: TContentOrNone?

    public val br: TContentOrNone?

    override fun func(): TElement = TMathAttach

    public companion object : TElementImpl("math.attach") {
        internal val baseType: InternalType = ConcreteType("content")

        internal val tType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val bType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val tlType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val blType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val trType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val brType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))
    }
}

internal data class TMathAttachImpl(
    @TSerialName("base")
    override val base: TContent,
    @TSerialName("t")
    override val t: TContentOrNone? = null,
    @TSerialName("b")
    override val b: TContentOrNone? = null,
    @TSerialName("tl")
    override val tl: TContentOrNone? = null,
    @TSerialName("bl")
    override val bl: TContentOrNone? = null,
    @TSerialName("tr")
    override val tr: TContentOrNone? = null,
    @TSerialName("br")
    override val br: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathAttach {
    override fun format(): String = Representations.elementRepr("math.attach",ArgumentEntry(false,
            null, `base`),ArgumentEntry(false, "t", `t`),ArgumentEntry(false, "b",
            `b`),ArgumentEntry(false, "tl", `tl`),ArgumentEntry(false, "bl", `bl`),ArgumentEntry(false,
            "tr", `tr`),ArgumentEntry(false, "br", `br`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathAttach(
    base: TContent,
    t: TContentOrNone? = null,
    b: TContentOrNone? = null,
    tl: TContentOrNone? = null,
    bl: TContentOrNone? = null,
    tr: TContentOrNone? = null,
    br: TContentOrNone? = null,
    label: TLabel? = null,
): TMathAttach = TMathAttachImpl(`base`, `t`, `b`, `tl`, `bl`, `tr`, `br`, `label`)

@TSetRuleType(
    "math.attach",
    TSetMathAttachImpl::class,
)
public interface TSetMathAttach : TSetRule {
    override val elem: String
        get() = "math.attach"

    public val t: TContentOrNone?

    public val b: TContentOrNone?

    public val tl: TContentOrNone?

    public val bl: TContentOrNone?

    public val tr: TContentOrNone?

    public val br: TContentOrNone?

    override fun format(): String = Representations.setRepr("math.attach",ArgumentEntry(false, "t",
            `t`),ArgumentEntry(false, "b", `b`),ArgumentEntry(false, "tl", `tl`),ArgumentEntry(false,
            "bl", `bl`),ArgumentEntry(false, "tr", `tr`),ArgumentEntry(false, "br", `br`),)
}

internal class TSetMathAttachImpl(
    @TSerialName("t")
    override val t: TContentOrNone? = null,
    @TSerialName("b")
    override val b: TContentOrNone? = null,
    @TSerialName("tl")
    override val tl: TContentOrNone? = null,
    @TSerialName("bl")
    override val bl: TContentOrNone? = null,
    @TSerialName("tr")
    override val tr: TContentOrNone? = null,
    @TSerialName("br")
    override val br: TContentOrNone? = null,
) : TSetMathAttach

@TypstOverloads
public fun TSetMathAttach(
    t: TContentOrNone? = null,
    b: TContentOrNone? = null,
    tl: TContentOrNone? = null,
    bl: TContentOrNone? = null,
    tr: TContentOrNone? = null,
    br: TContentOrNone? = null,
): TSetMathAttach = TSetMathAttachImpl(`t`, `b`, `tl`, `bl`, `tr`, `br`)
