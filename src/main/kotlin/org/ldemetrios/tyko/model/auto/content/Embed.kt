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
    "pdf.embed",
    ["pdf.embed", "content"],
    TEmbedImpl::class,
)
public interface TEmbed : TContent {
    public val path: TStr

    public val `data`: TBytes?

    public val relationship: TNoneOrStr?

    public val mimeType: TNoneOrStr?

    public val description: TNoneOrStr?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("pdf.embed")

        internal val pathType: InternalType = ConcreteType("str")

        internal val dataType: InternalType = ConcreteType("bytes")

        internal val relationshipType: InternalType = UnionType(ConcreteType("none"),
                ConcreteType("str"))

        internal val mimeTypeType: InternalType = UnionType(ConcreteType("none"), ConcreteType("str"))

        internal val descriptionType: InternalType = UnionType(ConcreteType("none"),
                ConcreteType("str"))
    }
}

internal data class TEmbedImpl(
    @TSerialName("path")
    override val path: TStr,
    @TSerialName("data")
    override val `data`: TBytes? = null,
    @TSerialName("relationship")
    override val relationship: TNoneOrStr? = null,
    @TSerialName("mime-type")
    override val mimeType: TNoneOrStr? = null,
    @TSerialName("description")
    override val description: TNoneOrStr? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TEmbed {
    override fun format(): String = Representations.elementRepr("pdf.embed",ArgumentEntry(false, null,
            `path`),ArgumentEntry(false, null, `data`),ArgumentEntry(false, "relationship",
            `relationship`),ArgumentEntry(false, "mime-type", `mimeType`),ArgumentEntry(false,
            "description", `description`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TEmbed(
    path: TStr,
    `data`: TBytes? = null,
    relationship: TNoneOrStr? = null,
    mimeType: TNoneOrStr? = null,
    description: TNoneOrStr? = null,
    label: TLabel? = null,
): TEmbed = TEmbedImpl(`path`, `data`, `relationship`, `mimeType`, `description`, `label`)

@TSetRuleType(
    "pdf.embed",
    TSetEmbedImpl::class,
)
public interface TSetEmbed : TSetRule {
    override val elem: String
        get() = "pdf.embed"

    public val relationship: TNoneOrStr?

    public val mimeType: TNoneOrStr?

    public val description: TNoneOrStr?

    override fun format(): String = Representations.setRepr("pdf.embed",ArgumentEntry(false,
            "relationship", `relationship`),ArgumentEntry(false, "mime-type",
            `mimeType`),ArgumentEntry(false, "description", `description`),)
}

internal class TSetEmbedImpl(
    @TSerialName("relationship")
    override val relationship: TNoneOrStr? = null,
    @TSerialName("mime-type")
    override val mimeType: TNoneOrStr? = null,
    @TSerialName("description")
    override val description: TNoneOrStr? = null,
) : TSetEmbed

@TypstOverloads
public fun TSetEmbed(
    relationship: TNoneOrStr? = null,
    mimeType: TNoneOrStr? = null,
    description: TNoneOrStr? = null,
): TSetEmbed = TSetEmbedImpl(`relationship`, `mimeType`, `description`)
