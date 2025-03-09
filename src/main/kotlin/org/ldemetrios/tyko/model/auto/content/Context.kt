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
    "context",
    ["context", "content"],
    TContextImpl::class,
)
public interface TContext : TContent {
    public val func: TFunction

    override fun func(): TElement = TContext

    public companion object : TElementImpl("context") {
        internal val funcType: InternalType = ConcreteType("function")
    }
}

internal data class TContextImpl(
    @TSerialName("func")
    override val func: TFunction,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TContext {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TContext(func: TFunction, label: TLabel? = null): TContext = TContextImpl(`func`,
        `label`)
