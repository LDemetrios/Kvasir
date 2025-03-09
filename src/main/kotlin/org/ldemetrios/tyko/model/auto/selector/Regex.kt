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
    "regex",
    ["regex", "selector"],
    TRegexImpl::class,
)
public interface TRegex : TSelector {
    public val regex: TStr?

    override fun type(): TType = TRegex

    public companion object : TTypeImpl("regex") {
        internal val regexType: InternalType = ConcreteType("str")
    }
}

internal data class TRegexImpl(
    @TSerialName("regex")
    override val regex: TStr? = null,
) : TRegex {
    override fun format(): String = Representations.structRepr("regex",ArgumentEntry(false, null,
            `regex`),)
}

@TypstOverloads
public fun TRegex(regex: TStr? = null): TRegex = TRegexImpl(`regex`)
