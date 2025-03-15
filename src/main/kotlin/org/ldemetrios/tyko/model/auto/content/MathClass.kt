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
    "math.class",
    ["math.class", "content"],
    TMathClassImpl::class,
)
public interface TMathClass : TContent {
    public val `class`: TMathClassClass

    public val body: TContent

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("math.class")

        internal val classType: InternalType = ConcreteType("math.class-class")

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TMathClassImpl(
    @TSerialName("class")
    override val `class`: TMathClassClass,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMathClass {
    override fun format(): String = Representations.elementRepr("math.class",ArgumentEntry(false,
            null, `class`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TMathClass(
    `class`: TMathClassClass,
    @TContentBody body: TContent,
    label: TLabel? = null,
): TMathClass = TMathClassImpl(`class`, `body`, `label`)
