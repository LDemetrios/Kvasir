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
    "pagebreak",
    ["pagebreak", "content"],
    TPagebreakImpl::class,
)
public interface TPagebreak : TContent {
    public val weak: TBool?

    public val to: TNoneOrPagebreakTo?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("pagebreak")

        internal val weakType: InternalType = ConcreteType("bool")

        internal val toType: InternalType = UnionType(ConcreteType("none"),
                ConcreteType("pagebreak-to"))
    }
}

internal data class TPagebreakImpl(
    @TSerialName("weak")
    override val weak: TBool? = null,
    @TSerialName("to")
    override val to: TNoneOrPagebreakTo? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TPagebreak {
    override fun format(): String = Representations.elementRepr("pagebreak",ArgumentEntry(false,
            "weak", `weak`),ArgumentEntry(false, "to", `to`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TPagebreak(
    weak: TBool? = null,
    to: TNoneOrPagebreakTo? = null,
    label: TLabel? = null,
): TPagebreak = TPagebreakImpl(`weak`, `to`, `label`)

@TSetRuleType(
    "pagebreak",
    TSetPagebreakImpl::class,
)
public interface TSetPagebreak : TSetRule {
    override val elem: String
        get() = "pagebreak"

    public val weak: TBool?

    public val to: TNoneOrPagebreakTo?

    override fun format(): String = Representations.setRepr("pagebreak",ArgumentEntry(false, "weak",
            `weak`),ArgumentEntry(false, "to", `to`),)
}

internal class TSetPagebreakImpl(
    @TSerialName("weak")
    override val weak: TBool? = null,
    @TSerialName("to")
    override val to: TNoneOrPagebreakTo? = null,
) : TSetPagebreak

@TypstOverloads
public fun TSetPagebreak(weak: TBool? = null, to: TNoneOrPagebreakTo? = null): TSetPagebreak =
        TSetPagebreakImpl(`weak`, `to`)
