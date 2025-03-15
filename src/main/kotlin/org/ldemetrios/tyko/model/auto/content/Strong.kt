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
    "strong",
    ["strong", "content"],
    TStrongImpl::class,
)
public interface TStrong : TContent {
    public val body: TContent

    public val delta: TInt?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("strong")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val deltaType: InternalType = ConcreteType("int")
    }
}

internal data class TStrongImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("delta")
    override val delta: TInt? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TStrong {
    override fun format(): String = Representations.elementRepr("strong",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "delta", `delta`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TStrong(
    @TContentBody body: TContent,
    delta: TInt? = null,
    label: TLabel? = null,
): TStrong = TStrongImpl(`body`, `delta`, `label`)

@TSetRuleType(
    "strong",
    TSetStrongImpl::class,
)
public interface TSetStrong : TSetRule {
    override val elem: String
        get() = "strong"

    public val delta: TInt?

    override fun format(): String = Representations.setRepr("strong",ArgumentEntry(false, "delta",
            `delta`),)
}

internal class TSetStrongImpl(
    @TSerialName("delta")
    override val delta: TInt? = null,
) : TSetStrong

@TypstOverloads
public fun TSetStrong(delta: TInt? = null): TSetStrong = TSetStrongImpl(`delta`)
