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
    "raw",
    ["raw", "content"],
    TRawImpl::class,
)
public interface TRaw : TContent {
    public val text: TStr

    public val block: TBool?

    public val lang: TNoneOrStr?

    public val align: TAlignment?

    public val syntaxes: TArrayOrStr<TStr>?

    public val theme: TAutoOrNoneOrStr?

    public val tabSize: TInt?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("raw")

        internal val textType: InternalType = ConcreteType("str")

        internal val blockType: InternalType = ConcreteType("bool")

        internal val langType: InternalType = UnionType(ConcreteType("none"), ConcreteType("str"))

        internal val alignType: InternalType = ConcreteType("alignment")

        internal val syntaxesType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("str"))), ConcreteType("str"))

        internal val themeType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("none"),
                ConcreteType("str"))

        internal val tabSizeType: InternalType = ConcreteType("int")
    }
}

internal data class TRawImpl(
    @TSerialName("text")
    override val text: TStr,
    @TSerialName("block")
    override val block: TBool? = null,
    @TSerialName("lang")
    override val lang: TNoneOrStr? = null,
    @TSerialName("align")
    override val align: TAlignment? = null,
    @TSerialName("syntaxes")
    override val syntaxes: TArrayOrStr<TStr>? = null,
    @TSerialName("theme")
    override val theme: TAutoOrNoneOrStr? = null,
    @TSerialName("tab-size")
    override val tabSize: TInt? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TRaw {
    override fun format(): String = Representations.elementRepr("raw",ArgumentEntry(false, null,
            `text`),ArgumentEntry(false, "block", `block`),ArgumentEntry(false, "lang",
            `lang`),ArgumentEntry(false, "align", `align`),ArgumentEntry(false, "syntaxes",
            `syntaxes`),ArgumentEntry(false, "theme", `theme`),ArgumentEntry(false, "tab-size",
            `tabSize`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TRaw(
    text: TStr,
    block: TBool? = null,
    lang: TNoneOrStr? = null,
    align: TAlignment? = null,
    syntaxes: TArrayOrStr<TStr>? = null,
    theme: TAutoOrNoneOrStr? = null,
    tabSize: TInt? = null,
    label: TLabel? = null,
): TRaw = TRawImpl(`text`, `block`, `lang`, `align`, `syntaxes`, `theme`, `tabSize`, `label`)

@TSetRuleType(
    "raw",
    TSetRawImpl::class,
)
public interface TSetRaw : TSetRule {
    override val elem: String
        get() = "raw"

    public val block: TBool?

    public val lang: TNoneOrStr?

    public val align: TAlignment?

    public val syntaxes: TArrayOrStr<TStr>?

    public val theme: TAutoOrNoneOrStr?

    public val tabSize: TInt?

    override fun format(): String = Representations.setRepr("raw",ArgumentEntry(false, "block",
            `block`),ArgumentEntry(false, "lang", `lang`),ArgumentEntry(false, "align",
            `align`),ArgumentEntry(false, "syntaxes", `syntaxes`),ArgumentEntry(false, "theme",
            `theme`),ArgumentEntry(false, "tab-size", `tabSize`),)
}

internal class TSetRawImpl(
    @TSerialName("block")
    override val block: TBool? = null,
    @TSerialName("lang")
    override val lang: TNoneOrStr? = null,
    @TSerialName("align")
    override val align: TAlignment? = null,
    @TSerialName("syntaxes")
    override val syntaxes: TArrayOrStr<TStr>? = null,
    @TSerialName("theme")
    override val theme: TAutoOrNoneOrStr? = null,
    @TSerialName("tab-size")
    override val tabSize: TInt? = null,
) : TSetRaw

@TypstOverloads
public fun TSetRaw(
    block: TBool? = null,
    lang: TNoneOrStr? = null,
    align: TAlignment? = null,
    syntaxes: TArrayOrStr<TStr>? = null,
    theme: TAutoOrNoneOrStr? = null,
    tabSize: TInt? = null,
): TSetRaw = TSetRawImpl(`block`, `lang`, `align`, `syntaxes`, `theme`, `tabSize`)
