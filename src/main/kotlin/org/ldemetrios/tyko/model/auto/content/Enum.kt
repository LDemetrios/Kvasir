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
    "enum",
    ["enum", "content"],
    TEnumImpl::class,
)
public interface TEnum : TContent {
    public val children: TArray<TArrayOrEnumItem<TEnumItem>>

    public val tight: TBool?

    public val numbering: TFunctionOrStr?

    public val start: TInt?

    public val full: TBool?

    public val indent: TLength?

    public val bodyIndent: TLength?

    public val spacing: TAutoOrLength?

    public val numberAlign: TAlignment?

    public val reversed: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("enum")

        internal val childrenType: InternalType = ConcreteType("array",
                listOf(UnionType(ConcreteType("array", listOf(ConcreteType("enum.item"))),
                ConcreteType("enum.item"))))

        internal val tightType: InternalType = ConcreteType("bool")

        internal val numberingType: InternalType = UnionType(ConcreteType("function"),
                ConcreteType("str"))

        internal val startType: InternalType = ConcreteType("int")

        internal val fullType: InternalType = ConcreteType("bool")

        internal val indentType: InternalType = ConcreteType("length")

        internal val bodyIndentType: InternalType = ConcreteType("length")

        internal val spacingType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"))

        internal val numberAlignType: InternalType = ConcreteType("alignment")

        internal val reversedType: InternalType = ConcreteType("bool")
    }
}

internal data class TEnumImpl(
    @TSerialName("children")
    override val children: TArray<TArrayOrEnumItem<TEnumItem>>,
    @TSerialName("tight")
    override val tight: TBool? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrStr? = null,
    @TSerialName("start")
    override val start: TInt? = null,
    @TSerialName("full")
    override val full: TBool? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
    @TSerialName("body-indent")
    override val bodyIndent: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TAutoOrLength? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignment? = null,
    @TSerialName("reversed")
    override val reversed: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TEnum {
    override fun format(): String = Representations.elementRepr("enum",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "tight", `tight`),ArgumentEntry(false, "numbering",
            `numbering`),ArgumentEntry(false, "start", `start`),ArgumentEntry(false, "full",
            `full`),ArgumentEntry(false, "indent", `indent`),ArgumentEntry(false, "body-indent",
            `bodyIndent`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false, "number-align",
            `numberAlign`),ArgumentEntry(false, "reversed", `reversed`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TEnum(
    @TVararg children: TArray<TArrayOrEnumItem<TEnumItem>>,
    tight: TBool? = null,
    numbering: TFunctionOrStr? = null,
    start: TInt? = null,
    full: TBool? = null,
    indent: TLength? = null,
    bodyIndent: TLength? = null,
    spacing: TAutoOrLength? = null,
    numberAlign: TAlignment? = null,
    reversed: TBool? = null,
    label: TLabel? = null,
): TEnum = TEnumImpl(`children`, `tight`, `numbering`, `start`, `full`, `indent`, `bodyIndent`,
        `spacing`, `numberAlign`, `reversed`, `label`)

@TSetRuleType(
    "enum",
    TSetEnumImpl::class,
)
public interface TSetEnum : TSetRule {
    override val elem: String
        get() = "enum"

    public val tight: TBool?

    public val numbering: TFunctionOrStr?

    public val start: TInt?

    public val full: TBool?

    public val indent: TLength?

    public val bodyIndent: TLength?

    public val spacing: TAutoOrLength?

    public val numberAlign: TAlignment?

    public val reversed: TBool?

    override fun format(): String = Representations.setRepr("enum",ArgumentEntry(false, "tight",
            `tight`),ArgumentEntry(false, "numbering", `numbering`),ArgumentEntry(false, "start",
            `start`),ArgumentEntry(false, "full", `full`),ArgumentEntry(false, "indent",
            `indent`),ArgumentEntry(false, "body-indent", `bodyIndent`),ArgumentEntry(false, "spacing",
            `spacing`),ArgumentEntry(false, "number-align", `numberAlign`),ArgumentEntry(false,
            "reversed", `reversed`),)
}

internal class TSetEnumImpl(
    @TSerialName("tight")
    override val tight: TBool? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrStr? = null,
    @TSerialName("start")
    override val start: TInt? = null,
    @TSerialName("full")
    override val full: TBool? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
    @TSerialName("body-indent")
    override val bodyIndent: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TAutoOrLength? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignment? = null,
    @TSerialName("reversed")
    override val reversed: TBool? = null,
) : TSetEnum

@TypstOverloads
public fun TSetEnum(
    tight: TBool? = null,
    numbering: TFunctionOrStr? = null,
    start: TInt? = null,
    full: TBool? = null,
    indent: TLength? = null,
    bodyIndent: TLength? = null,
    spacing: TAutoOrLength? = null,
    numberAlign: TAlignment? = null,
    reversed: TBool? = null,
): TSetEnum = TSetEnumImpl(`tight`, `numbering`, `start`, `full`, `indent`, `bodyIndent`, `spacing`,
        `numberAlign`, `reversed`)
