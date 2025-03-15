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
    "colbreak",
    ["colbreak", "content"],
    TColbreakImpl::class,
)
public interface TColbreak : TContent {
    public val weak: TBool?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("colbreak")

        internal val weakType: InternalType = ConcreteType("bool")
    }
}

internal data class TColbreakImpl(
    @TSerialName("weak")
    override val weak: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TColbreak {
    override fun format(): String = Representations.elementRepr("colbreak",ArgumentEntry(false,
            "weak", `weak`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TColbreak(weak: TBool? = null, label: TLabel? = null): TColbreak = TColbreakImpl(`weak`,
        `label`)

@TSetRuleType(
    "colbreak",
    TSetColbreakImpl::class,
)
public interface TSetColbreak : TSetRule {
    override val elem: String
        get() = "colbreak"

    public val weak: TBool?

    override fun format(): String = Representations.setRepr("colbreak",ArgumentEntry(false, "weak",
            `weak`),)
}

internal class TSetColbreakImpl(
    @TSerialName("weak")
    override val weak: TBool? = null,
) : TSetColbreak

@TypstOverloads
public fun TSetColbreak(weak: TBool? = null): TSetColbreak = TSetColbreakImpl(`weak`)
