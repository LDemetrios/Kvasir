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
    "cmyk",
    ["cmyk", "color"],
    TCmykImpl::class,
)
public interface TCmyk : TColor {
    public val cyan: TRatio

    public val magenta: TRatio

    public val yellow: TRatio

    public val key: TRatio

    override fun func(): TFunction = Func

    public companion object {
        public val Func: TNativeFunc = TNativeFunc("cmyk".t)

        internal val cyanType: InternalType = ConcreteType("ratio")

        internal val magentaType: InternalType = ConcreteType("ratio")

        internal val yellowType: InternalType = ConcreteType("ratio")

        internal val keyType: InternalType = ConcreteType("ratio")
    }
}

internal data class TCmykImpl(
    @TSerialName("cyan")
    override val cyan: TRatio,
    @TSerialName("magenta")
    override val magenta: TRatio,
    @TSerialName("yellow")
    override val yellow: TRatio,
    @TSerialName("key")
    override val key: TRatio,
) : TCmyk {
    override fun format(): String = Representations.structRepr("cmyk",ArgumentEntry(false, null,
            `cyan`),ArgumentEntry(false, null, `magenta`),ArgumentEntry(false, null,
            `yellow`),ArgumentEntry(false, null, `key`),)
}

@TypstOverloads
public fun TCmyk(
    cyan: TRatio,
    magenta: TRatio,
    yellow: TRatio,
    key: TRatio,
): TCmyk = TCmykImpl(`cyan`, `magenta`, `yellow`, `key`)
