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
    "par",
    ["par", "content"],
    TParImpl::class,
)
public interface TPar : TContent {
    public val body: TContent

    public val leading: TLength?

    public val spacing: TLength?

    public val justify: TBool?

    public val linebreaks: TAutoOrParLinebreaks?

    public val firstLineIndent: TDictionaryOrLength<TBoolOrLength>?

    public val hangingIndent: TLength?

    override fun func(): TElement = TPar

    public companion object : TElementImpl("par") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val leadingType: InternalType = ConcreteType("length")

        internal val spacingType: InternalType = ConcreteType("length")

        internal val justifyType: InternalType = ConcreteType("bool")

        internal val linebreaksType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("par-linebreaks"))

        internal val firstLineIndentType: InternalType = UnionType(ConcreteType("dictionary",
                listOf(UnionType(ConcreteType("bool"), ConcreteType("length")))), ConcreteType("length"))

        internal val hangingIndentType: InternalType = ConcreteType("length")
    }
}

internal data class TParImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("leading")
    override val leading: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TLength? = null,
    @TSerialName("justify")
    override val justify: TBool? = null,
    @TSerialName("linebreaks")
    override val linebreaks: TAutoOrParLinebreaks? = null,
    @TSerialName("first-line-indent")
    override val firstLineIndent: TDictionaryOrLength<TBoolOrLength>? = null,
    @TSerialName("hanging-indent")
    override val hangingIndent: TLength? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TPar {
    override fun format(): String = Representations.elementRepr("par",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "leading", `leading`),ArgumentEntry(false, "spacing",
            `spacing`),ArgumentEntry(false, "justify", `justify`),ArgumentEntry(false, "linebreaks",
            `linebreaks`),ArgumentEntry(false, "first-line-indent",
            `firstLineIndent`),ArgumentEntry(false, "hanging-indent",
            `hangingIndent`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TPar(
    @TContentBody body: TContent,
    leading: TLength? = null,
    spacing: TLength? = null,
    justify: TBool? = null,
    linebreaks: TAutoOrParLinebreaks? = null,
    firstLineIndent: TDictionaryOrLength<TBoolOrLength>? = null,
    hangingIndent: TLength? = null,
    label: TLabel? = null,
): TPar = TParImpl(`body`, `leading`, `spacing`, `justify`, `linebreaks`, `firstLineIndent`,
        `hangingIndent`, `label`)

@TSetRuleType(
    "par",
    TSetParImpl::class,
)
public interface TSetPar : TSetRule {
    override val elem: String
        get() = "par"

    public val leading: TLength?

    public val spacing: TLength?

    public val justify: TBool?

    public val linebreaks: TAutoOrParLinebreaks?

    public val firstLineIndent: TDictionaryOrLength<TBoolOrLength>?

    public val hangingIndent: TLength?

    override fun format(): String = Representations.setRepr("par",ArgumentEntry(false, "leading",
            `leading`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false, "justify",
            `justify`),ArgumentEntry(false, "linebreaks", `linebreaks`),ArgumentEntry(false,
            "first-line-indent", `firstLineIndent`),ArgumentEntry(false, "hanging-indent",
            `hangingIndent`),)
}

internal class TSetParImpl(
    @TSerialName("leading")
    override val leading: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TLength? = null,
    @TSerialName("justify")
    override val justify: TBool? = null,
    @TSerialName("linebreaks")
    override val linebreaks: TAutoOrParLinebreaks? = null,
    @TSerialName("first-line-indent")
    override val firstLineIndent: TDictionaryOrLength<TBoolOrLength>? = null,
    @TSerialName("hanging-indent")
    override val hangingIndent: TLength? = null,
) : TSetPar

@TypstOverloads
public fun TSetPar(
    leading: TLength? = null,
    spacing: TLength? = null,
    justify: TBool? = null,
    linebreaks: TAutoOrParLinebreaks? = null,
    firstLineIndent: TDictionaryOrLength<TBoolOrLength>? = null,
    hangingIndent: TLength? = null,
): TSetPar = TSetParImpl(`leading`, `spacing`, `justify`, `linebreaks`, `firstLineIndent`,
        `hangingIndent`)
