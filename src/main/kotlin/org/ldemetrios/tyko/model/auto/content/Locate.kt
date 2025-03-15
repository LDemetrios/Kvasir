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
    "locate",
    ["locate", "content"],
    TLocateImpl::class,
)
public interface TLocate : TContent {
    public val func: TFunction

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("locate")

        internal val funcType: InternalType = ConcreteType("function")
    }
}

internal data class TLocateImpl(
    @TSerialName("func")
    override val func: TFunction,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TLocate {
    override fun format(): String = Representations.elementRepr("locate",ArgumentEntry(false, null,
            `func`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TLocate(func: TFunction, label: TLabel? = null): TLocate = TLocateImpl(`func`, `label`)
