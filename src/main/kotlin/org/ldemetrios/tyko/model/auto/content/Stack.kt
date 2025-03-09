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
    "stack",
    ["stack", "content"],
    TStackImpl::class,
)
public interface TStack : TContent {
    public val children: TArray<TContentOrFractionOrRelative>

    public val dir: TDirection?

    public val spacing: TFractionOrNoneOrRelative?

    override fun func(): TElement = TStack

    public companion object : TElementImpl("stack") {
        internal val childrenType: InternalType = ConcreteType("array",
                listOf(UnionType(ConcreteType("content"), ConcreteType("fraction"),
                ConcreteType("relative"))))

        internal val dirType: InternalType = ConcreteType("direction")

        internal val spacingType: InternalType = UnionType(ConcreteType("fraction"),
                ConcreteType("none"), ConcreteType("relative"))
    }
}

internal data class TStackImpl(
    @TSerialName("children")
    override val children: TArray<TContentOrFractionOrRelative>,
    @TSerialName("dir")
    override val dir: TDirection? = null,
    @TSerialName("spacing")
    override val spacing: TFractionOrNoneOrRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TStack {
    override fun format(): String = Representations.elementRepr("stack",ArgumentEntry(true, null,
            `children`),ArgumentEntry(false, "dir", `dir`),ArgumentEntry(false, "spacing",
            `spacing`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TStack(
    @TVararg children: TArray<TContentOrFractionOrRelative>,
    dir: TDirection? = null,
    spacing: TFractionOrNoneOrRelative? = null,
    label: TLabel? = null,
): TStack = TStackImpl(`children`, `dir`, `spacing`, `label`)

@TSetRuleType(
    "stack",
    TSetStackImpl::class,
)
public interface TSetStack : TSetRule {
    override val elem: String
        get() = "stack"

    public val dir: TDirection?

    public val spacing: TFractionOrNoneOrRelative?

    override fun format(): String = Representations.setRepr("stack",ArgumentEntry(false, "dir",
            `dir`),ArgumentEntry(false, "spacing", `spacing`),)
}

internal class TSetStackImpl(
    @TSerialName("dir")
    override val dir: TDirection? = null,
    @TSerialName("spacing")
    override val spacing: TFractionOrNoneOrRelative? = null,
) : TSetStack

@TypstOverloads
public fun TSetStack(dir: TDirection? = null, spacing: TFractionOrNoneOrRelative? = null): TSetStack
        = TSetStackImpl(`dir`, `spacing`)
