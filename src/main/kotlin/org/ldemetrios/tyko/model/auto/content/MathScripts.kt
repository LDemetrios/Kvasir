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
    "math.scripts",
    ["math.scripts", "content"],
    TMathScriptsImpl::class,
)
public interface TMathScripts : TContent {
    public val body: TContent

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.scripts")

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TMathScriptsImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathScripts {
    override fun format(): String = Representations.elementRepr("math.scripts",ArgumentEntry(false,
            null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathScripts(@TContentBody body: TContent, label: TLabel? = null): TMathScripts =
        TMathScriptsImpl(`body`, `label`)
