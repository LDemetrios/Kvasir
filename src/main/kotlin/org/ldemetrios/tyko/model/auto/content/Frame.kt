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
    "html.frame",
    ["html.frame", "content"],
    TFrameImpl::class,
)
public interface TFrame : TContent {
    public val body: TContent

    override fun func(): TElement = TFrame

    public companion object : TElementImpl("html.frame") {
        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TFrameImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TFrame {
    override fun format(): String = Representations.elementRepr("html.frame",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TFrame(@TContentBody body: TContent, label: TLabel? = null): TFrame = TFrameImpl(`body`,
        `label`)
