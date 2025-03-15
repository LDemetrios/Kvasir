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
    "link",
    ["link", "content"],
    TLinkImpl::class,
)
public interface TLink : TContent {
    public val dest: TLabelOrLocationOrStr

    public val body: TContent

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("link")

        internal val destType: InternalType = UnionType(ConcreteType("label"), ConcreteType("location"),
                ConcreteType("str"))

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TLinkImpl(
    @TSerialName("dest")
    override val dest: TLabelOrLocationOrStr,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TLink {
    override fun format(): String = Representations.elementRepr("link",ArgumentEntry(false, null,
            `dest`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TLink(
    dest: TLabelOrLocationOrStr,
    @TContentBody body: TContent,
    label: TLabel? = null,
): TLink = TLinkImpl(`dest`, `body`, `label`)
