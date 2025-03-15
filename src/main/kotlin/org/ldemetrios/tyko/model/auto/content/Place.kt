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
    "place",
    ["place", "content"],
    TPlaceImpl::class,
)
public interface TPlace : TContent {
    public val alignment: TAlignmentOrAuto?

    public val body: TContent

    public val scope: TFigureScope?

    public val float: TBool?

    public val clearance: TLength?

    public val dx: TRelative?

    public val dy: TRelative?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("place")

        internal val alignmentType: InternalType = UnionType(ConcreteType("alignment"),
                ConcreteType("auto"))

        internal val bodyType: InternalType = ConcreteType("content")

        internal val scopeType: InternalType = ConcreteType("figure-scope")

        internal val floatType: InternalType = ConcreteType("bool")

        internal val clearanceType: InternalType = ConcreteType("length")

        internal val dxType: InternalType = ConcreteType("relative")

        internal val dyType: InternalType = ConcreteType("relative")
    }
}

internal data class TPlaceImpl(
    @TSerialName("alignment")
    override val alignment: TAlignmentOrAuto? = null,
    @TSerialName("body")
    override val body: TContent,
    @TSerialName("scope")
    override val scope: TFigureScope? = null,
    @TSerialName("float")
    override val float: TBool? = null,
    @TSerialName("clearance")
    override val clearance: TLength? = null,
    @TSerialName("dx")
    override val dx: TRelative? = null,
    @TSerialName("dy")
    override val dy: TRelative? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TPlace {
    override fun format(): String = Representations.elementRepr("place",ArgumentEntry(false, null,
            `alignment`),ArgumentEntry(false, null, `body`),ArgumentEntry(false, "scope",
            `scope`),ArgumentEntry(false, "float", `float`),ArgumentEntry(false, "clearance",
            `clearance`),ArgumentEntry(false, "dx", `dx`),ArgumentEntry(false, "dy",
            `dy`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TPlace(
    alignment: TAlignmentOrAuto? = null,
    @TContentBody body: TContent,
    scope: TFigureScope? = null,
    float: TBool? = null,
    clearance: TLength? = null,
    dx: TRelative? = null,
    dy: TRelative? = null,
    label: TLabel? = null,
): TPlace = TPlaceImpl(`alignment`, `body`, `scope`, `float`, `clearance`, `dx`, `dy`, `label`)

@TSetRuleType(
    "place",
    TSetPlaceImpl::class,
)
public interface TSetPlace : TSetRule {
    override val elem: String
        get() = "place"

    public val alignment: TAlignmentOrAuto?

    public val scope: TFigureScope?

    public val float: TBool?

    public val clearance: TLength?

    public val dx: TRelative?

    public val dy: TRelative?

    override fun format(): String = Representations.setRepr("place",ArgumentEntry(false, null,
            `alignment`),ArgumentEntry(false, "scope", `scope`),ArgumentEntry(false, "float",
            `float`),ArgumentEntry(false, "clearance", `clearance`),ArgumentEntry(false, "dx",
            `dx`),ArgumentEntry(false, "dy", `dy`),)
}

internal class TSetPlaceImpl(
    @TSerialName("alignment")
    override val alignment: TAlignmentOrAuto? = null,
    @TSerialName("scope")
    override val scope: TFigureScope? = null,
    @TSerialName("float")
    override val float: TBool? = null,
    @TSerialName("clearance")
    override val clearance: TLength? = null,
    @TSerialName("dx")
    override val dx: TRelative? = null,
    @TSerialName("dy")
    override val dy: TRelative? = null,
) : TSetPlace

@TypstOverloads
public fun TSetPlace(
    alignment: TAlignmentOrAuto? = null,
    scope: TFigureScope? = null,
    float: TBool? = null,
    clearance: TLength? = null,
    dx: TRelative? = null,
    dy: TRelative? = null,
): TSetPlace = TSetPlaceImpl(`alignment`, `scope`, `float`, `clearance`, `dx`, `dy`)
