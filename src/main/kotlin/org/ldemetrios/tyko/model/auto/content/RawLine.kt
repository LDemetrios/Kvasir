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
    "raw.line",
    ["raw.line", "content"],
    TRawLineImpl::class,
)
public interface TRawLine : TContent {
    public val number: TInt

    public val count: TInt

    public val text: TStr

    public val body: TContent

    override fun func(): TElement = TRawLine

    public companion object : TElementImpl("raw.line") {
        internal val numberType: InternalType = ConcreteType("int")

        internal val countType: InternalType = ConcreteType("int")

        internal val textType: InternalType = ConcreteType("str")

        internal val bodyType: InternalType = ConcreteType("content")
    }
}

internal data class TRawLineImpl(
    @TSerialName("number")
    override val number: TInt,
    @TSerialName("count")
    override val count: TInt,
    @TSerialName("text")
    override val text: TStr,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TRawLine {
    override fun format(): String = Representations.elementRepr("raw.line",ArgumentEntry(false, null,
            `number`),ArgumentEntry(false, null, `count`),ArgumentEntry(false, null,
            `text`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TRawLine(
    number: TInt,
    count: TInt,
    text: TStr,
    @TContentBody body: TContent,
    label: TLabel? = null,
): TRawLine = TRawLineImpl(`number`, `count`, `text`, `body`, `label`)
