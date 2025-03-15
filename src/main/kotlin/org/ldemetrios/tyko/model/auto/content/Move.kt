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
    "move",
    ["move", "content"],
    TMoveImpl::class,
)
public interface TMove : TContent {
    public val body: TContent

    public val dx: TRelative?

    public val dy: TRelative?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("move")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val dxType: InternalType = ConcreteType("relative")

        internal val dyType: InternalType = ConcreteType("relative")
    }
}

internal data class TMoveImpl(
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("dx")
    override val dx: TRelative? = null,
    @TSerialName("dy")
    override val dy: TRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TMove {
    override fun format(): String = Representations.elementRepr("move",ArgumentEntry(false, null,
            `body`),ArgumentEntry(false, "dx", `dx`),ArgumentEntry(false, "dy", `dy`),ArgumentEntry(false,
            "label", `label`),)
}

@TypstOverloads
public fun TMove(
    @TContentBody body: TContent,
    dx: TRelative? = null,
    dy: TRelative? = null,
    label: TLabel? = null,
): TMove = TMoveImpl(`body`, `dx`, `dy`, `label`)

@TSetRuleType(
    "move",
    TSetMoveImpl::class,
)
public interface TSetMove : TSetRule {
    override val elem: String
        get() = "move"

    public val dx: TRelative?

    public val dy: TRelative?

    override fun format(): String = Representations.setRepr("move",ArgumentEntry(false, "dx",
            `dx`),ArgumentEntry(false, "dy", `dy`),)
}

internal class TSetMoveImpl(
    @TSerialName("dx")
    override val dx: TRelative? = null,
    @TSerialName("dy")
    override val dy: TRelative? = null,
) : TSetMove

@TypstOverloads
public fun TSetMove(dx: TRelative? = null, dy: TRelative? = null): TSetMove = TSetMoveImpl(`dx`,
        `dy`)
