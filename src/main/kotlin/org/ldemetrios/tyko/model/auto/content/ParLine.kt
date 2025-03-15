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
    "par.line",
    ["par.line", "content"],
    TParLineImpl::class,
)
public interface TParLine : TContent {
    public val numbering: TFunctionOrNoneOrStr?

    public val numberAlign: TAlignmentOrAuto?

    public val numberMargin: TAlignment?

    public val numberClearance: TAutoOrLength?

    public val numberingScope: TParLineNumberingScope?

    override fun func(): TElement = Elem

    public companion object {
        public val Elem: TElement = TElementImpl("par.line")

        internal val numberingType: InternalType = UnionType(ConcreteType("function"),
                ConcreteType("none"), ConcreteType("str"))

        internal val numberAlignType: InternalType = UnionType(ConcreteType("alignment"),
                ConcreteType("auto"))

        internal val numberMarginType: InternalType = ConcreteType("alignment")

        internal val numberClearanceType: InternalType = UnionType(ConcreteType("auto"),
                ConcreteType("length"))

        internal val numberingScopeType: InternalType = ConcreteType("par.line-numbering-scope")
    }
}

internal data class TParLineImpl(
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignmentOrAuto? = null,
    @TSerialName("number-margin")
    override val numberMargin: TAlignment? = null,
    @TSerialName("number-clearance")
    override val numberClearance: TAutoOrLength? = null,
    @TSerialName("numbering-scope")
    override val numberingScope: TParLineNumberingScope? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TParLine {
    override fun format(): String = Representations.elementRepr("par.line",ArgumentEntry(false,
            "numbering", `numbering`),ArgumentEntry(false, "number-align",
            `numberAlign`),ArgumentEntry(false, "number-margin", `numberMargin`),ArgumentEntry(false,
            "number-clearance", `numberClearance`),ArgumentEntry(false, "numbering-scope",
            `numberingScope`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TParLine(
    numbering: TFunctionOrNoneOrStr? = null,
    numberAlign: TAlignmentOrAuto? = null,
    numberMargin: TAlignment? = null,
    numberClearance: TAutoOrLength? = null,
    numberingScope: TParLineNumberingScope? = null,
    label: TLabel? = null,
): TParLine = TParLineImpl(`numbering`, `numberAlign`, `numberMargin`, `numberClearance`,
        `numberingScope`, `label`)

@TSetRuleType(
    "par.line",
    TSetParLineImpl::class,
)
public interface TSetParLine : TSetRule {
    override val elem: String
        get() = "par.line"

    public val numbering: TFunctionOrNoneOrStr?

    public val numberAlign: TAlignmentOrAuto?

    public val numberMargin: TAlignment?

    public val numberClearance: TAutoOrLength?

    public val numberingScope: TParLineNumberingScope?

    override fun format(): String = Representations.setRepr("par.line",ArgumentEntry(false,
            "numbering", `numbering`),ArgumentEntry(false, "number-align",
            `numberAlign`),ArgumentEntry(false, "number-margin", `numberMargin`),ArgumentEntry(false,
            "number-clearance", `numberClearance`),ArgumentEntry(false, "numbering-scope",
            `numberingScope`),)
}

internal class TSetParLineImpl(
    @TSerialName("numbering")
    override val numbering: TFunctionOrNoneOrStr? = null,
    @TSerialName("number-align")
    override val numberAlign: TAlignmentOrAuto? = null,
    @TSerialName("number-margin")
    override val numberMargin: TAlignment? = null,
    @TSerialName("number-clearance")
    override val numberClearance: TAutoOrLength? = null,
    @TSerialName("numbering-scope")
    override val numberingScope: TParLineNumberingScope? = null,
) : TSetParLine

@TypstOverloads
public fun TSetParLine(
    numbering: TFunctionOrNoneOrStr? = null,
    numberAlign: TAlignmentOrAuto? = null,
    numberMargin: TAlignment? = null,
    numberClearance: TAutoOrLength? = null,
    numberingScope: TParLineNumberingScope? = null,
): TSetParLine = TSetParLineImpl(`numbering`, `numberAlign`, `numberMargin`, `numberClearance`,
        `numberingScope`)
