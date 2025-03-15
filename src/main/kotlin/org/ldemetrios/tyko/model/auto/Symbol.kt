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
    "symbol",
    ["symbol"],
    TSymbolImpl::class,
)
public interface TSymbol : TArrayOrNoneOrStrOrSymbol<TDynamic>, TNoneOrStrOrSymbol, TValue {
    public val variants: TArray<TArrayOrStr<TStr>>

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("symbol")

        internal val variantsType: InternalType = ConcreteType("array",
                listOf(UnionType(ConcreteType("array", listOf(ConcreteType("str"))), ConcreteType("str"))))
    }
}

internal data class TSymbolImpl(
    @TSerialName("variants")
    override val variants: TArray<TArrayOrStr<TStr>>,
) : TSymbol {
    override fun format(): String = Representations.structRepr("symbol",ArgumentEntry(true, null,
            `variants`),)
}

@TypstOverloads
public fun TSymbol(@TVararg variants: TArray<TArrayOrStr<TStr>>): TSymbol = TSymbolImpl(`variants`)
