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
    "emph",
    ["emph", "content"],
    TEmphImpl::class,
)
public interface TEmph : TContent {
    public val body: TContent

    override fun func(): TElement = TEmph

    public companion object : TElementImpl("emph") {
        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TEmphImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TEmph {
    override fun format(): String = Representations.elementRepr("emph",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TEmph(@TContentBody body: TContent, label: TLabel? = null): TEmph = TEmphImpl(`body`,
        `label`)
