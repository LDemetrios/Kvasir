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
    "list",
    ["list", "content"],
    TListImpl::class,
)
public interface TList : TContent {
    public val children: TArray<TListItem>

    public val tight: TBool?

    public val marker: TArrayOrContentOrFunction<TContent>?

    public val indent: TLength?

    public val bodyIndent: TLength?

    public val spacing: TAutoOrLength?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("list")

        internal val childrenType: InternalType = ConcreteType("array",
                listOf(ConcreteType("list.item")))

        internal val tightType: InternalType = ConcreteType("bool")

        internal val markerType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("content"))), ConcreteType("content"), ConcreteType("function"))

        internal val indentType: InternalType = ConcreteType("length")

        internal val bodyIndentType: InternalType = ConcreteType("length")

        internal val spacingType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("length"))
    }
}

internal data class TListImpl(
    @TSerialName("children")
    override val children: TArray<TListItem>,
    @TSerialName("tight")
    override val tight: TBool? = null,
    @TSerialName("marker")
    override val marker: TArrayOrContentOrFunction<TContent>? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
    @TSerialName("body-indent")
    override val bodyIndent: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TAutoOrLength? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TList {
    override fun format(): String = Representations.elementRepr("list",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "tight", `tight`),ArgumentEntry(false, "marker",
            `marker`),ArgumentEntry(false, "indent", `indent`),ArgumentEntry(false, "body-indent",
            `bodyIndent`),ArgumentEntry(false, "spacing", `spacing`),ArgumentEntry(false, "label",
            `label`),)
}

@TypstOverloads
public fun TList(
    @TVararg children: TArray<TListItem>,
    tight: TBool? = null,
    marker: TArrayOrContentOrFunction<TContent>? = null,
    indent: TLength? = null,
    bodyIndent: TLength? = null,
    spacing: TAutoOrLength? = null,
    label: TLabel? = null,
): TList = TListImpl(`children`, `tight`, `marker`, `indent`, `bodyIndent`, `spacing`, `label`)

@TSetRuleType(
    "list",
    TSetListImpl::class,
)
public interface TSetList : TSetRule {
    override val elem: String
        get() = "list"

    public val tight: TBool?

    public val marker: TArrayOrContentOrFunction<TContent>?

    public val indent: TLength?

    public val bodyIndent: TLength?

    public val spacing: TAutoOrLength?

    override fun format(): String = Representations.setRepr("list",ArgumentEntry(false, "tight",
            `tight`),ArgumentEntry(false, "marker", `marker`),ArgumentEntry(false, "indent",
            `indent`),ArgumentEntry(false, "body-indent", `bodyIndent`),ArgumentEntry(false, "spacing",
            `spacing`),)
}

internal class TSetListImpl(
    @TSerialName("tight")
    override val tight: TBool? = null,
    @TSerialName("marker")
    override val marker: TArrayOrContentOrFunction<TContent>? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
    @TSerialName("body-indent")
    override val bodyIndent: TLength? = null,
    @TSerialName("spacing")
    override val spacing: TAutoOrLength? = null,
) : TSetList

@TypstOverloads
public fun TSetList(
    tight: TBool? = null,
    marker: TArrayOrContentOrFunction<TContent>? = null,
    indent: TLength? = null,
    bodyIndent: TLength? = null,
    spacing: TAutoOrLength? = null,
): TSetList = TSetListImpl(`tight`, `marker`, `indent`, `bodyIndent`, `spacing`)
