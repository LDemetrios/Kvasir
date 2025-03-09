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
    "bibliography",
    ["bibliography", "content"],
    TBibliographyImpl::class,
)
public interface TBibliography : TContent {
    public val path: TArrayOrStr<TStr>

    public val title: TAutoOrContentOrNone?

    public val full: TBool?

    public val style: TBibliographyStyle?

    override fun func(): TElement = TBibliography

    public companion object : TElementImpl("bibliography") {
        internal val pathType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("str"))), ConcreteType("str"))

        internal val titleType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("content"),
                ConcreteType("none"))

        internal val fullType: InternalType = ConcreteType("bool")

        internal val styleType: InternalType = ConcreteType("bibliography-style")
    }
}

internal data class TBibliographyImpl(
    @TSerialName("path")
    override val path: TArrayOrStr<TStr>,
    @TSerialName("title")
    override val title: TAutoOrContentOrNone? = null,
    @TSerialName("full")
    override val full: TBool? = null,
    @TSerialName("style")
    override val style: TBibliographyStyle? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TBibliography {
    override fun format(): String = Representations.elementRepr("bibliography",ArgumentEntry(false,
            null, `path`),ArgumentEntry(false, "title", `title`),ArgumentEntry(false, "full",
            `full`),ArgumentEntry(false, "style", `style`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TBibliography(
    path: TArrayOrStr<TStr>,
    title: TAutoOrContentOrNone? = null,
    full: TBool? = null,
    style: TBibliographyStyle? = null,
    label: TLabel? = null,
): TBibliography = TBibliographyImpl(`path`, `title`, `full`, `style`, `label`)

@TSetRuleType(
    "bibliography",
    TSetBibliographyImpl::class,
)
public interface TSetBibliography : TSetRule {
    override val elem: String
        get() = "bibliography"

    public val title: TAutoOrContentOrNone?

    public val full: TBool?

    public val style: TBibliographyStyle?

    override fun format(): String = Representations.setRepr("bibliography",ArgumentEntry(false,
            "title", `title`),ArgumentEntry(false, "full", `full`),ArgumentEntry(false, "style",
            `style`),)
}

internal class TSetBibliographyImpl(
    @TSerialName("title")
    override val title: TAutoOrContentOrNone? = null,
    @TSerialName("full")
    override val full: TBool? = null,
    @TSerialName("style")
    override val style: TBibliographyStyle? = null,
) : TSetBibliography

@TypstOverloads
public fun TSetBibliography(
    title: TAutoOrContentOrNone? = null,
    full: TBool? = null,
    style: TBibliographyStyle? = null,
): TSetBibliography = TSetBibliographyImpl(`title`, `full`, `style`)
