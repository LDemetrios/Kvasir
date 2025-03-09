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
    "module",
    ["module"],
    TModuleImpl::class,
)
public interface TModule : TValue {
    public val name: TStr

    override fun type(): TType = TModule

    public companion object : TTypeImpl("module") {
        internal val nameType: InternalType = ConcreteType("str")
    }
}

internal data class TModuleImpl(
    @TSerialName("name")
    override val name: TStr,
) : TModule {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TModule(name: TStr): TModule = TModuleImpl(`name`)
