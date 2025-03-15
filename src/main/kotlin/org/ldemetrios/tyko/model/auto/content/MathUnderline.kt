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
    "math.underline",
    ["math.underline", "content"],
    TMathUnderlineImpl::class,
)
public interface TMathUnderline : TContent {
    public val body: TContent

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.underline")

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TMathUnderlineImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathUnderline {
    override fun format(): String = Representations.elementRepr("math.underline",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathUnderline(@TContentBody body: TContent, label: TLabel? = null): TMathUnderline =
        TMathUnderlineImpl(`body`, `label`)
