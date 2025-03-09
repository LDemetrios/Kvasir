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
    "math.overline",
    ["math.overline", "content"],
    TMathOverlineImpl::class,
)
public interface TMathOverline : TContent {
    public val body: TContent

    override fun func(): TElement = TMathOverline

    public companion object : TElementImpl("math.overline") {
        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TMathOverlineImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathOverline {
    override fun format(): String = Representations.elementRepr("math.overline",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathOverline(@TContentBody body: TContent, label: TLabel? = null): TMathOverline =
        TMathOverlineImpl(`body`, `label`)
