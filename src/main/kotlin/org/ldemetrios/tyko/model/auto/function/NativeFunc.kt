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
    "native-func",
    ["native-func", "function"],
    TNativeFuncImpl::class,
)
public interface TNativeFunc : TFunction {
    public val name: TStr

    override fun type(): TType = TNativeFunc

    public companion object : TTypeImpl("native-func") {
        internal val nameType: InternalType = ConcreteType("str")
    }
}

internal data class TNativeFuncImpl(
    @TSerialName("name")
    override val name: TStr,
) : TNativeFunc {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TNativeFunc(name: TStr): TNativeFunc = TNativeFuncImpl(`name`)
