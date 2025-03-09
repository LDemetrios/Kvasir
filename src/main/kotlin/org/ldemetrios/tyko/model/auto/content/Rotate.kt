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
    "rotate",
    ["rotate", "content"],
    TRotateImpl::class,
)
public interface TRotate : TContent {
    public val angle: TAngle?

    public val body: TContent

    public val origin: TAlignment?

    public val reflow: TBool?

    override fun func(): TElement = TRotate

    public companion object : TElementImpl("rotate") {
        internal val angleType: InternalType = ConcreteType("angle")

        internal val bodyType: InternalType = ConcreteType("content")

        internal val originType: InternalType = ConcreteType("alignment")

        internal val reflowType: InternalType = ConcreteType("bool")
    }
}

internal data class TRotateImpl(
    @TSerialName("angle")
    override val angle: TAngle? = null,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("origin")
    override val origin: TAlignment? = null,
    @TSerialName("reflow")
    override val reflow: TBool? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TRotate {
    override fun format(): String = Representations.elementRepr("rotate",ArgumentEntry(false, null,
            `angle`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "origin",
            `origin`),ArgumentEntry(false, "reflow", `reflow`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TRotate(
    angle: TAngle? = null,
    @TContentBody body: TContent,
    origin: TAlignment? = null,
    reflow: TBool? = null,
    label: TLabel? = null,
): TRotate = TRotateImpl(`angle`, `body`, `origin`, `reflow`, `label`)

@TSetRuleType(
    "rotate",
    TSetRotateImpl::class,
)
public interface TSetRotate : TSetRule {
    override val elem: String
        get() = "rotate"

    public val angle: TAngle?

    public val origin: TAlignment?

    public val reflow: TBool?

    override fun format(): String = Representations.setRepr("rotate",ArgumentEntry(false, null,
            `angle`),ArgumentEntry(false, "origin", `origin`),ArgumentEntry(false, "reflow", `reflow`),)
}

internal class TSetRotateImpl(
    @TSerialName("angle")
    override val angle: TAngle? = null,
    @TSerialName("origin")
    override val origin: TAlignment? = null,
    @TSerialName("reflow")
    override val reflow: TBool? = null,
) : TSetRotate

@TypstOverloads
public fun TSetRotate(
    angle: TAngle? = null,
    origin: TAlignment? = null,
    reflow: TBool? = null,
): TSetRotate = TSetRotateImpl(`angle`, `origin`, `reflow`)
