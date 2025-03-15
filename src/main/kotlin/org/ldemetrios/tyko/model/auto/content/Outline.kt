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
    "outline",
    ["outline", "content"],
    TOutlineImpl::class,
)
public interface TOutline : TContent {
    public val title: TAutoOrContentOrNone?

    public val target: TFunctionOrLabelOrLocationOrSelector?

    public val depth: TIntOrNone?

    public val indent: TAutoOrBoolOrFunctionOrNoneOrRelative?

    public val fill: TContentOrNone?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("outline")

        internal val titleType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("content"),
                ConcreteType("none"))

        internal val targetType: InternalType = UnionType(ConcreteType("function"),
                ConcreteType("label"), ConcreteType("location"), ConcreteType("selector"))

        internal val depthType: InternalType = UnionType(ConcreteType("int"), ConcreteType("none"))

        internal val indentType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("bool"),
                ConcreteType("function"), ConcreteType("none"), ConcreteType("relative"))

        internal val fillType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))
    }
}

internal data class TOutlineImpl(
    @TSerialName("title")
    override val title: TAutoOrContentOrNone? = null,
    @TSerialName("target")
    override val target: TFunctionOrLabelOrLocationOrSelector? = null,
    @TSerialName("depth")
    override val depth: TIntOrNone? = null,
    @TSerialName("indent")
    override val indent: TAutoOrBoolOrFunctionOrNoneOrRelative? = null,
    @TSerialName("fill")
    override val fill: TContentOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TOutline {
    override fun format(): String = Representations.elementRepr("outline",ArgumentEntry(false,
            "title", `title`),ArgumentEntry(false, "target", `target`),ArgumentEntry(false, "depth",
            `depth`),ArgumentEntry(false, "indent", `indent`),ArgumentEntry(false, "fill",
            `fill`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TOutline(
    title: TAutoOrContentOrNone? = null,
    target: TFunctionOrLabelOrLocationOrSelector? = null,
    depth: TIntOrNone? = null,
    indent: TAutoOrBoolOrFunctionOrNoneOrRelative? = null,
    fill: TContentOrNone? = null,
    label: TLabel? = null,
): TOutline = TOutlineImpl(`title`, `target`, `depth`, `indent`, `fill`, `label`)

@TSetRuleType(
    "outline",
    TSetOutlineImpl::class,
)
public interface TSetOutline : TSetRule {
    override val elem: String
        get() = "outline"

    public val title: TAutoOrContentOrNone?

    public val target: TFunctionOrLabelOrLocationOrSelector?

    public val depth: TIntOrNone?

    public val indent: TAutoOrBoolOrFunctionOrNoneOrRelative?

    public val fill: TContentOrNone?

    override fun format(): String = Representations.setRepr("outline",ArgumentEntry(false, "title",
            `title`),ArgumentEntry(false, "target", `target`),ArgumentEntry(false, "depth",
            `depth`),ArgumentEntry(false, "indent", `indent`),ArgumentEntry(false, "fill", `fill`),)
}

internal class TSetOutlineImpl(
    @TSerialName("title")
    override val title: TAutoOrContentOrNone? = null,
    @TSerialName("target")
    override val target: TFunctionOrLabelOrLocationOrSelector? = null,
    @TSerialName("depth")
    override val depth: TIntOrNone? = null,
    @TSerialName("indent")
    override val indent: TAutoOrBoolOrFunctionOrNoneOrRelative? = null,
    @TSerialName("fill")
    override val fill: TContentOrNone? = null,
) : TSetOutline

@TypstOverloads
public fun TSetOutline(
    title: TAutoOrContentOrNone? = null,
    target: TFunctionOrLabelOrLocationOrSelector? = null,
    depth: TIntOrNone? = null,
    indent: TAutoOrBoolOrFunctionOrNoneOrRelative? = null,
    fill: TContentOrNone? = null,
): TSetOutline = TSetOutlineImpl(`title`, `target`, `depth`, `indent`, `fill`)
