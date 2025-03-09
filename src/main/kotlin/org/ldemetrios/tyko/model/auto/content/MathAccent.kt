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
    "math.accent",
    ["math.accent", "content"],
    TMathAccentImpl::class,
)
public interface TMathAccent : TContent {
    public val base: TContent

    public val accent: TContentOrStr

    public val sz: TAutoOrRelative?

    override fun func(): TElement = TMathAccent

    public companion object : TElementImpl("math.accent") {
        internal val baseType: InternalType = ConcreteType("content")

        internal val accentType: InternalType = UnionType(ConcreteType("content"), ConcreteType("str"))

        internal val szType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("relative"))
    }
}

internal data class TMathAccentImpl(
    @TSerialName("base")
    override val base: TContent,
    @TSerialName("accent")
    override val accent: TContentOrStr,
    @TSerialName("size")
    override val sz: TAutoOrRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathAccent {
    override fun format(): String = Representations.elementRepr("math.accent",ArgumentEntry(false,
            null, `base`),ArgumentEntry(false, null, `accent`),ArgumentEntry(false, "size",
            `sz`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathAccent(
    base: TContent,
    accent: TContentOrStr,
    sz: TAutoOrRelative? = null,
    label: TLabel? = null,
): TMathAccent = TMathAccentImpl(`base`, `accent`, `sz`, `label`)

@TSetRuleType(
    "math.accent",
    TSetMathAccentImpl::class,
)
public interface TSetMathAccent : TSetRule {
    override val elem: String
        get() = "math.accent"

    public val sz: TAutoOrRelative?

    override fun format(): String = Representations.setRepr("math.accent",ArgumentEntry(false, "size",
            `sz`),)
}

internal class TSetMathAccentImpl(
    @TSerialName("size")
    override val sz: TAutoOrRelative? = null,
) : TSetMathAccent

@TypstOverloads
public fun TSetMathAccent(sz: TAutoOrRelative? = null): TSetMathAccent = TSetMathAccentImpl(`sz`)
