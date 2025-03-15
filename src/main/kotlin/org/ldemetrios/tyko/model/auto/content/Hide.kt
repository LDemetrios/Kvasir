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
    "hide",
    ["hide", "content"],
    THideImpl::class,
)
public interface THide : TContent {
    public val body: TContent

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("hide")

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class THideImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : THide {
    override fun format(): String = Representations.elementRepr("hide",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun THide(@TContentBody body: TContent, label: TLabel? = null): THide = THideImpl(`body`,
        `label`)
