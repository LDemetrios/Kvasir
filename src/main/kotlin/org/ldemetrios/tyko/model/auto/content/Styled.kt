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
    "styled",
    ["styled", "content"],
    TStyledImpl::class,
)
public interface TStyled : TContent {
    public val styles: TArray<TStyle>

    public val child: TContent

    override fun func(): TElement = TStyled

    public companion object : TElementImpl("styled") {
        internal val stylesType: InternalType = ConcreteType("array", listOf(ConcreteType("style")))

        internal val childType: InternalType = ConcreteType("content")
    }
}

internal data class TStyledImpl(
    @TSerialName("styles")
    override val styles: TArray<TStyle>,
    @TSerialName("child")
    override val child: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TStyled {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TStyled(
    @TVararg styles: TArray<TStyle>,
    child: TContent,
    label: TLabel? = null,
): TStyled = TStyledImpl(`styles`, `child`, `label`)
