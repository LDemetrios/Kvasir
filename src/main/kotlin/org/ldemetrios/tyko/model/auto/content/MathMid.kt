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
    "math.mid",
    ["math.mid", "content"],
    TMathMidImpl::class,
)
public interface TMathMid : TContent {
    public val body: TContent

    override fun func(): TElement = TMathMid

    public companion object : TElementImpl("math.mid") {
        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TMathMidImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathMid {
    override fun format(): String = Representations.elementRepr("math.mid",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathMid(@TContentBody body: TContent, label: TLabel? = null): TMathMid =
        TMathMidImpl(`body`, `label`)
