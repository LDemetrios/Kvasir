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
    "style-deprecated",
    ["style-deprecated", "content"],
    TStyleDeprecatedImpl::class,
)
public interface TStyleDeprecated : TContent {
    public val func: TFunction

    override fun func(): TElement = TStyleDeprecated

    public companion object : TElementImpl("style-deprecated") {
        internal val funcType: InternalType = ConcreteType("function")
    }
}

internal data class TStyleDeprecatedImpl(
    @TSerialName("func")
    override val func: TFunction,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TStyleDeprecated {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TStyleDeprecated(func: TFunction, label: TLabel? = null): TStyleDeprecated =
        TStyleDeprecatedImpl(`func`, `label`)
