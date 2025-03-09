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
    "html.elem",
    ["html.elem", "content"],
    TElemImpl::class,
)
public interface TElem : TContent {
    public val tag: TStr

    public val body: TContentOrNone?

    public val attrs: TDictionary<TValue>?

    override fun func(): TElement = TElem

    public companion object : TElementImpl("html.elem") {
        internal val tagType: InternalType = ConcreteType("str")

        internal val bodyType: InternalType = UnionType(ConcreteType("content"), ConcreteType("none"))

        internal val attrsType: InternalType = ConcreteType("dictionary", listOf(AnyType))
    }
}

internal data class TElemImpl(
    @TSerialName("tag")
    override val tag: TStr,
    @TSerialName("body")
    override val body: TContentOrNone? = null,
    @TSerialName("attrs")
    override val attrs: TDictionary<TValue>? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TElem {
    override fun format(): String = Representations.elementRepr("html.elem",ArgumentEntry(false, null,
            `tag`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "attrs",
            `attrs`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TElem(
    tag: TStr,
    body: TContentOrNone? = null,
    attrs: TDictionary<TValue>? = null,
    label: TLabel? = null,
): TElem = TElemImpl(`tag`, `body`, `attrs`, `label`)

@TSetRuleType(
    "html.elem",
    TSetElemImpl::class,
)
public interface TSetElem : TSetRule {
    override val elem: String
        get() = "html.elem"

    public val attrs: TDictionary<TValue>?

    override fun format(): String = Representations.setRepr("html.elem",ArgumentEntry(false, "attrs",
            `attrs`),)
}

internal class TSetElemImpl(
    @TSerialName("attrs")
    override val attrs: TDictionary<TValue>? = null,
) : TSetElem

@TypstOverloads
public fun TSetElem(attrs: TDictionary<TValue>? = null): TSetElem = TSetElemImpl(`attrs`)
