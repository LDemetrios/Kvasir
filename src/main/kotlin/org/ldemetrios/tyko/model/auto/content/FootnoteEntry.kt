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
    "footnote.entry",
    ["footnote.entry", "content"],
    TFootnoteEntryImpl::class,
)
public interface TFootnoteEntry : TContent {
    public val note: TContent

    public val separator: TContent?

    public val clearance: TLength?

    public val gap: TLength?

    public val indent: TLength?

    override fun func(): TElement = TFootnoteEntry

    public companion object : TElementImpl("footnote.entry") {
        internal val noteType: InternalType = ConcreteType("content")

        internal val separatorType: InternalType = ConcreteType("content")

        internal val clearanceType: InternalType = ConcreteType("length")

        internal val gapType: InternalType = ConcreteType("length")

        internal val indentType: InternalType = ConcreteType("length")
    }
}

internal data class TFootnoteEntryImpl(
    @TSerialName("note")
    override val note: TContent,
    @TSerialName("separator")
    override val separator: TContent? = null,
    @TSerialName("clearance")
    override val clearance: TLength? = null,
    @TSerialName("gap")
    override val gap: TLength? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
    @TSerialName("label")
    override val label: TLabel? = null,
) : TFootnoteEntry {
    override fun format(): String = Representations.elementRepr("footnote.entry",ArgumentEntry(false,
            null, `note`),ArgumentEntry(false, "separator", `separator`),ArgumentEntry(false, "clearance",
            `clearance`),ArgumentEntry(false, "gap", `gap`),ArgumentEntry(false, "indent",
            `indent`),ArgumentEntry(false, "label", `label`),)
}

@TypstOverloads
public fun TFootnoteEntry(
    note: TContent,
    separator: TContent? = null,
    clearance: TLength? = null,
    gap: TLength? = null,
    indent: TLength? = null,
    label: TLabel? = null,
): TFootnoteEntry = TFootnoteEntryImpl(`note`, `separator`, `clearance`, `gap`, `indent`, `label`)

@TSetRuleType(
    "footnote.entry",
    TSetFootnoteEntryImpl::class,
)
public interface TSetFootnoteEntry : TSetRule {
    override val elem: String
        get() = "footnote.entry"

    public val separator: TContent?

    public val clearance: TLength?

    public val gap: TLength?

    public val indent: TLength?

    override fun format(): String = Representations.setRepr("footnote.entry",ArgumentEntry(false,
            "separator", `separator`),ArgumentEntry(false, "clearance", `clearance`),ArgumentEntry(false,
            "gap", `gap`),ArgumentEntry(false, "indent", `indent`),)
}

internal class TSetFootnoteEntryImpl(
    @TSerialName("separator")
    override val separator: TContent? = null,
    @TSerialName("clearance")
    override val clearance: TLength? = null,
    @TSerialName("gap")
    override val gap: TLength? = null,
    @TSerialName("indent")
    override val indent: TLength? = null,
) : TSetFootnoteEntry

@TypstOverloads
public fun TSetFootnoteEntry(
    separator: TContent? = null,
    clearance: TLength? = null,
    gap: TLength? = null,
    indent: TLength? = null,
): TSetFootnoteEntry = TSetFootnoteEntryImpl(`separator`, `clearance`, `gap`, `indent`)
