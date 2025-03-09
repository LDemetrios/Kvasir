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
    "ref",
    ["ref", "content"],
    TRefImpl::class,
)
public interface TRef : TContent {
    public val target: TLabel

    public val supplement: TAutoOrContentOrFunctionOrNone?

    public val form: TRefForm?

    override fun func(): TElement = TRef

    public companion object : TElementImpl("ref") {
        internal val targetType: InternalType = ConcreteType("label")

        internal val supplementType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("content"), ConcreteType("function"), ConcreteType("none"))

        internal val formType: InternalType = ConcreteType("ref-form")
    }
}

internal data class TRefImpl(
    @TSerialName("target")
    override val target: TLabel,
    @TSerialName("supplement")
    override val supplement: TAutoOrContentOrFunctionOrNone? = null,
    @TSerialName("form")
    override val form: TRefForm? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TRef {
    override fun format(): String = Representations.elementRepr("ref",ArgumentEntry(false, null,
            `target`),ArgumentEntry(false, "supplement", `supplement`),ArgumentEntry(false, "form",
            `form`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TRef(
    target: TLabel,
    supplement: TAutoOrContentOrFunctionOrNone? = null,
    form: TRefForm? = null,
    label: TLabel? = null,
): TRef = TRefImpl(`target`, `supplement`, `form`, `label`)

@TSetRuleType(
    "ref",
    TSetRefImpl::class,
)
public interface TSetRef : TSetRule {
    override val elem: String
        get() = "ref"

    public val supplement: TAutoOrContentOrFunctionOrNone?

    public val form: TRefForm?

    override fun format(): String = Representations.setRepr("ref",ArgumentEntry(false, "supplement",
            `supplement`),ArgumentEntry(false, "form", `form`),)
}

internal class TSetRefImpl(
    @TSerialName("supplement")
    override val supplement: TAutoOrContentOrFunctionOrNone? = null,
    @TSerialName("form")
    override val form: TRefForm? = null,
) : TSetRef

@TypstOverloads
public fun TSetRef(supplement: TAutoOrContentOrFunctionOrNone? = null, form: TRefForm? = null):
        TSetRef = TSetRefImpl(`supplement`, `form`)
