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
    "label",
    ["label", "selector"],
    TLabelImpl::class,
)
public interface TLabel : TContentOrLabel, TLabelOrLocationOrStr,
        TFunctionOrLabelOrLocationOrSelector, TContentOrLabelOrNone,
        TFunctionOrLabelOrLocationOrSelectorOrStr, TSelector {
    public val name: TStr

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("label")

        internal val nameType: InternalType = ConcreteType("str")
    }
}

internal data class TLabelImpl(
    @TSerialName("name")
    override val name: TStr,
) : TLabel {
    override fun format(): String = Representations.structRepr("label",ArgumentEntry(false, null,
            `name`),)
}

@TypstOverloads
public fun TLabel(name: TStr): TLabel = TLabelImpl(`name`)
