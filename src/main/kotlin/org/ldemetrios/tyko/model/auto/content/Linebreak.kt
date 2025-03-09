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
    "linebreak",
    ["linebreak", "content"],
    TLinebreakImpl::class,
)
public interface TLinebreak : TContent {
    public val justify: TBool?

    override fun func(): TElement = TLinebreak

    public companion object : TElementImpl("linebreak") {
        internal val justifyType: InternalType = ConcreteType("bool")
    }
}

internal data class TLinebreakImpl(
    @TSerialName("justify")
    override val justify: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TLinebreak {
    override fun format(): String = Representations.elementRepr("linebreak",ArgumentEntry(false,
            "justify", `justify`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TLinebreak(justify: TBool? = null, label: TLabel? = null): TLinebreak =
        TLinebreakImpl(`justify`, `label`)

@TSetRuleType(
    "linebreak",
    TSetLinebreakImpl::class,
)
public interface TSetLinebreak : TSetRule {
    override val elem: String
        get() = "linebreak"

    public val justify: TBool?

    override fun format(): String = Representations.setRepr("linebreak",ArgumentEntry(false,
            "justify", `justify`),)
}

internal class TSetLinebreakImpl(
    @TSerialName("justify")
    override val justify: TBool? = null,
) : TSetLinebreak

@TypstOverloads
public fun TSetLinebreak(justify: TBool? = null): TSetLinebreak = TSetLinebreakImpl(`justify`)
