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
    "document",
    ["document", "content"],
    TDocumentImpl::class,
)
public interface TDocument : TContent {
    public val title: TContentOrNone?

    public val author: TArrayOrStr<TStr>?

    public val keywords: TArrayOrStr<TStr>?

    public val date: TAutoOrDatetimeOrNone?

    override fun func(): TElement = TDocument

    public companion object : TElementImpl("document") {
        internal val titleType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val authorType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("str"))), ConcreteType("str"))

        internal val keywordsType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("str"))), ConcreteType("str"))

        internal val dateType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("datetime"),
                ConcreteType("none"))
    }
}

internal data class TDocumentImpl(
    @TSerialName("title")
    override val title: TContentOrNone? = null,
    @TSerialName("author")
    override val author: TArrayOrStr<TStr>? = null,
    @TSerialName("keywords")
    override val keywords: TArrayOrStr<TStr>? = null,
    @TSerialName("date")
    override val date: TAutoOrDatetimeOrNone? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TDocument {
    override fun format(): String = Representations.elementRepr("document",ArgumentEntry(false,
            "title", `title`),ArgumentEntry(false, "author", `author`),ArgumentEntry(false, "keywords",
            `keywords`),ArgumentEntry(false, "date", `date`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TDocument(
    title: TContentOrNone? = null,
    author: TArrayOrStr<TStr>? = null,
    keywords: TArrayOrStr<TStr>? = null,
    date: TAutoOrDatetimeOrNone? = null,
    label: TLabel? = null,
): TDocument = TDocumentImpl(`title`, `author`, `keywords`, `date`, `label`)

@TSetRuleType(
    "document",
    TSetDocumentImpl::class,
)
public interface TSetDocument : TSetRule {
    override val elem: String
        get() = "document"

    public val title: TContentOrNone?

    public val author: TArrayOrStr<TStr>?

    public val keywords: TArrayOrStr<TStr>?

    public val date: TAutoOrDatetimeOrNone?

    override fun format(): String = Representations.setRepr("document",ArgumentEntry(false, "title",
            `title`),ArgumentEntry(false, "author", `author`),ArgumentEntry(false, "keywords",
            `keywords`),ArgumentEntry(false, "date", `date`),)
}

internal class TSetDocumentImpl(
    @TSerialName("title")
    override val title: TContentOrNone? = null,
    @TSerialName("author")
    override val author: TArrayOrStr<TStr>? = null,
    @TSerialName("keywords")
    override val keywords: TArrayOrStr<TStr>? = null,
    @TSerialName("date")
    override val date: TAutoOrDatetimeOrNone? = null,
) : TSetDocument

@TypstOverloads
public fun TSetDocument(
    title: TContentOrNone? = null,
    author: TArrayOrStr<TStr>? = null,
    keywords: TArrayOrStr<TStr>? = null,
    date: TAutoOrDatetimeOrNone? = null,
): TSetDocument = TSetDocumentImpl(`title`, `author`, `keywords`, `date`)
