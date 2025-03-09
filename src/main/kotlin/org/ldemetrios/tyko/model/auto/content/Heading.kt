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
    "heading",
    ["heading", "content"],
    THeadingImpl::class,
)
public interface THeading : TContent {
    public val body: TContent

    public val level: TAutoOrInt?

    public val depth: TInt?

    public val offset: TInt?

    public val numbering: TFunctionOrNoneOrStr?

    public val supplement: TAutoOrContentOrFunctionOrNone?

    public val outlined: TBool?

    public val bookmarked: TAutoOrBool?

    public val hangingIndent: TAutoOrLength?

    override fun func(): TElement = THeading

    public companion object : TElementImpl("heading") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val levelType: InternalType = UnionType(ConcreteType("auto"), ConcreteType("int"))

        internal val depthType: InternalType = ConcreteType("int")

        internal val offsetType: InternalType = ConcreteType("int")

        internal val numberingType: InternalType = UnionType(ConcreteType("function"),
                ConcreteType("none"), ConcreteType("str"))

        internal val supplementType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("content"), ConcreteType("function"), ConcreteType("none"))

        internal val outlinedType: InternalType = ConcreteType("bool")

        internal val bookmarkedType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("bool"))

        internal val hangingIndentType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("length"))
    }
}

internal data class THeadingImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("level")
    override val level: TAutoOrInt? = null,
    @TSerialName("depth")
    override val depth: TInt? = null,
    @TSerialName("offset")
    override val offset: TInt? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("supplement")
    override val supplement: TAutoOrContentOrFunctionOrNone? = null,
    @TSerialName("outlined")
    override val outlined: TBool? = null,
    @TSerialName("bookmarked")
    override val bookmarked: TAutoOrBool? = null,
    @TSerialName("hanging-indent")
    override val hangingIndent: TAutoOrLength? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : THeading {
    override fun format(): String = Representations.elementRepr("heading",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "level", `level`),ArgumentEntry(false, "depth",
            `depth`),ArgumentEntry(false, "offset", `offset`),ArgumentEntry(false, "numbering",
            `numbering`),ArgumentEntry(false, "supplement", `supplement`),ArgumentEntry(false, "outlined",
            `outlined`),ArgumentEntry(false, "bookmarked", `bookmarked`),ArgumentEntry(false,
            "hanging-indent", `hangingIndent`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun THeading(
    @TContentBody body: TContent,
    level: TAutoOrInt? = null,
    depth: TInt? = null,
    offset: TInt? = null,
    numbering: TFunctionOrNoneOrStr? = null,
    supplement: TAutoOrContentOrFunctionOrNone? = null,
    outlined: TBool? = null,
    bookmarked: TAutoOrBool? = null,
    hangingIndent: TAutoOrLength? = null,
    label: TLabel? = null,
): THeading = THeadingImpl(`body`, `level`, `depth`, `offset`, `numbering`, `supplement`,
        `outlined`, `bookmarked`, `hangingIndent`, `label`)

@TSetRuleType(
    "heading",
    TSetHeadingImpl::class,
)
public interface TSetHeading : TSetRule {
    override val elem: String
        get() = "heading"

    public val level: TAutoOrInt?

    public val depth: TInt?

    public val offset: TInt?

    public val numbering: TFunctionOrNoneOrStr?

    public val supplement: TAutoOrContentOrFunctionOrNone?

    public val outlined: TBool?

    public val bookmarked: TAutoOrBool?

    public val hangingIndent: TAutoOrLength?

    override fun format(): String = Representations.setRepr("heading",ArgumentEntry(false, "level",
            `level`),ArgumentEntry(false, "depth", `depth`),ArgumentEntry(false, "offset",
            `offset`),ArgumentEntry(false, "numbering", `numbering`),ArgumentEntry(false, "supplement",
            `supplement`),ArgumentEntry(false, "outlined", `outlined`),ArgumentEntry(false, "bookmarked",
            `bookmarked`),ArgumentEntry(false, "hanging-indent", `hangingIndent`),)
}

internal class TSetHeadingImpl(
    @TSerialName("level")
    override val level: TAutoOrInt? = null,
    @TSerialName("depth")
    override val depth: TInt? = null,
    @TSerialName("offset")
    override val offset: TInt? = null,
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("supplement")
    override val supplement: TAutoOrContentOrFunctionOrNone? = null,
    @TSerialName("outlined")
    override val outlined: TBool? = null,
    @TSerialName("bookmarked")
    override val bookmarked: TAutoOrBool? = null,
    @TSerialName("hanging-indent")
    override val hangingIndent: TAutoOrLength? = null,
) : TSetHeading

@TypstOverloads
public fun TSetHeading(
    level: TAutoOrInt? = null,
    depth: TInt? = null,
    offset: TInt? = null,
    numbering: TFunctionOrNoneOrStr? = null,
    supplement: TAutoOrContentOrFunctionOrNone? = null,
    outlined: TBool? = null,
    bookmarked: TAutoOrBool? = null,
    hangingIndent: TAutoOrLength? = null,
): TSetHeading = TSetHeadingImpl(`level`, `depth`, `offset`, `numbering`, `supplement`, `outlined`,
        `bookmarked`, `hangingIndent`)
