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
    "terms",
    ["terms", "content"],
    TTermsImpl::class,
)
public interface TTerms : TContent {
    public val children: TArray<TArrayOrContent<TValue>>

    public val tight: TBool?

    public val separator: TContent?

    public val indent: TLength?

    public val hangingIndent: TLength?

    public val spacing: TAutoOrLength?

    override fun func(): TElement = TTerms

    public companion object : TElementImpl("terms") {
        internal val childrenType: InternalType = ConcreteType("array",
                listOf(UnionType(ConcreteType("array", listOf(AnyType)), ConcreteType("content"))))

        internal val tightType: InternalType = ConcreteType("bool")

        internal val separatorType: InternalType = ConcreteType("content")

        internal val indentType: InternalType = ConcreteType("length")

        internal val hangingIndentType: InternalType = ConcreteType("length")

        internal val spacingType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"))
    }
}

internal data class TTermsImpl(
    @TSerialName("children")
    override val children: TArray<TArrayOrContent<TValue>>,
    @TSerialName("tight")
    override val tight: TBool? = null,
    @TSerialName("separator")
    override val separator: TContent? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
    @TSerialName("hanging-indent")
    override val hangingIndent: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TAutoOrLength? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TTerms {
    override fun format(): String = Representations.elementRepr("terms",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "tight", `tight`),ArgumentEntry(false, "separator",
            `separator`),ArgumentEntry(false, "indent", `indent`),ArgumentEntry(false, "hanging-indent",
            `hangingIndent`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TTerms(
    @TVararg children: TArray<TArrayOrContent<TValue>>,
    tight: TBool? = null,
    separator: TContent? = null,
    indent: TLength? = null,
    hangingIndent: TLength? = null,
    spacing: TAutoOrLength? = null,
    label: TLabel? = null,
): TTerms = TTermsImpl(`children`, `tight`, `separator`, `indent`, `hangingIndent`, `spacing`,
        `label`)

@TSetRuleType(
    "terms",
    TSetTermsImpl::class,
)
public interface TSetTerms : TSetRule {
    override val elem: String
        get() = "terms"

    public val tight: TBool?

    public val separator: TContent?

    public val indent: TLength?

    public val hangingIndent: TLength?

    public val spacing: TAutoOrLength?

    override fun format(): String = Representations.setRepr("terms",ArgumentEntry(false, "tight",
            `tight`),ArgumentEntry(false, "separator", `separator`),ArgumentEntry(false, "indent",
            `indent`),ArgumentEntry(false, "hanging-indent", `hangingIndent`),ArgumentEntry(false,
            "spacing", `spacing`),)
}

internal class TSetTermsImpl(
    @TSerialName("tight")
    override val tight: TBool? = null,
    @TSerialName("separator")
    override val separator: TContent? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
    @TSerialName("hanging-indent")
    override val hangingIndent: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TAutoOrLength? = null,
) : TSetTerms

@TypstOverloads
public fun TSetTerms(
    tight: TBool? = null,
    separator: TContent? = null,
    indent: TLength? = null,
    hangingIndent: TLength? = null,
    spacing: TAutoOrLength? = null,
): TSetTerms = TSetTermsImpl(`tight`, `separator`, `indent`, `hangingIndent`, `spacing`)
