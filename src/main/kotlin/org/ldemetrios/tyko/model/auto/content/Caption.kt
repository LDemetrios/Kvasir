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
    "figure.caption",
    ["figure.caption", "content"],
    TCaptionImpl::class,
)
public interface TCaption : TContent {
    public val body: TContent

    public val position: TAlignment?

    public val separator: TAutoOrContent?

    override fun func(): TElement = TCaption

    public companion object : TElementImpl("figure.caption") {
        internal val bodyType: InternalType = ConcreteType("content")

        internal val positionType: InternalType = ConcreteType("alignment")

        internal val separatorType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("content"))
    }
}

internal data class TCaptionImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("position")
    override val position: TAlignment? = null,
    @TSerialName("separator")
    override val separator: TAutoOrContent? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TCaption {
    override fun format(): String = Representations.elementRepr("figure.caption",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "position", `position`),ArgumentEntry(false, "separator",
            `separator`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TCaption(
    @TContentBody body: TContent,
    position: TAlignment? = null,
    separator: TAutoOrContent? = null,
    label: TLabel? = null,
): TCaption = TCaptionImpl(`body`, `position`, `separator`, `label`)

@TSetRuleType(
    "figure.caption",
    TSetCaptionImpl::class,
)
public interface TSetCaption : TSetRule {
    override val elem: String
        get() = "figure.caption"

    public val position: TAlignment?

    public val separator: TAutoOrContent?

    override fun format(): String = Representations.setRepr("figure.caption",ArgumentEntry(false,
            "position", `position`),ArgumentEntry(false, "separator", `separator`),)
}

internal class TSetCaptionImpl(
    @TSerialName("position")
    override val position: TAlignment? = null,
    @TSerialName("separator")
    override val separator: TAutoOrContent? = null,
) : TSetCaption

@TypstOverloads
public fun TSetCaption(position: TAlignment? = null, separator: TAutoOrContent? = null): TSetCaption
        = TSetCaptionImpl(`position`, `separator`)
