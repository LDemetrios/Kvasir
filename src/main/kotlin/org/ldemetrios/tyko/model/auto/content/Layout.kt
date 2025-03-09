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
    "layout",
    ["layout", "content"],
    TLayoutImpl::class,
)
public interface TLayout : TContent {
    public val func: TFunction

    override fun func(): TElement = TLayout

    public companion object : TElementImpl("layout") {
        internal val funcType: InternalType = ConcreteType("function")
    }
}

internal data class TLayoutImpl(
    @TSerialName("func")
    override val func: TFunction,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TLayout {
    override fun format(): String = Representations.elementRepr("layout",ArgumentEntry(false, null,
            `func`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TLayout(func: TFunction, label: TLabel? = null): TLayout = TLayoutImpl(`func`, `label`)
