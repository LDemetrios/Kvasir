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
    "closure",
    ["closure", "function"],
    TClosureImpl::class,
)
public interface TClosure : TFunction {
    public val node: TStr

    public val defaults: TArray<TValue>?

    public val captured: TDictionary<TValue>?

    public val numPosParams: TInt?

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("closure")

        internal val nodeType: InternalType = ConcreteType("str")

        internal val defaultsType: InternalType = ConcreteType("array", listOf(AnyType))

        internal val capturedType: InternalType = ConcreteType("dictionary", listOf(AnyType))

        internal val numPosParamsType: InternalType = ConcreteType("int")
    }
}

internal data class TClosureImpl(
    @TSerialName("node")
    override val node: TStr,
    @TSerialName("defaults")
    override val defaults: TArray<TValue>? = null,
    @TSerialName("captured")
    override val captured: TDictionary<TValue>? = null,
    @TSerialName("num-pos-params")
    override val numPosParams: TInt? = null,
) : TClosure {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TClosure(
    node: TStr,
    defaults: TArray<TValue>? = null,
    captured: TDictionary<TValue>? = null,
    numPosParams: TInt? = null,
): TClosure = TClosureImpl(`node`, `defaults`, `captured`, `numPosParams`)
