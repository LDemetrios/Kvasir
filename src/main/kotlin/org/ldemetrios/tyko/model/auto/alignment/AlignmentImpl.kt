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
    "alignment-impl",
    ["alignment-impl", "alignment"],
    TAlignmentImplImpl::class,
)
public interface TAlignmentImpl : TAlignment {
    public val horizontal: THAlignment?

    public val vertical: TVAlignment?

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("alignment-impl")

        internal val horizontalType: InternalType = ConcreteType("h-alignment")

        internal val verticalType: InternalType = ConcreteType("v-alignment")
    }
}

internal data class TAlignmentImplImpl(
    @TSerialName("horizontal")
    override val horizontal: THAlignment? = null,
    @TSerialName("vertical")
    override val vertical: TVAlignment? = null,
) : TAlignmentImpl {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TAlignmentImpl(horizontal: THAlignment? = null, vertical: TVAlignment? = null):
        TAlignmentImpl = TAlignmentImplImpl(`horizontal`, `vertical`)
