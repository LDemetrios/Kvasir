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
    "show-rule",
    ["show-rule"],
    TShowRuleImpl::class,
)
public interface TShowRule : TStyle {
    public val selector: TNoneOrSelector

    public val transform: TArrayOrContentOrFunction<TStyle>

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("show-rule")

        internal val selectorType: InternalType = UnionType(ConcreteType("none"),
                ConcreteType("selector"))

        internal val transformType: InternalType = UnionType(ConcreteType("array",
                listOf(ConcreteType("style"))), ConcreteType("content"), ConcreteType("function"))
    }
}

internal data class TShowRuleImpl(
    @TSerialName("selector")
    override val selector: TNoneOrSelector,
    @TSerialName("transform")
    override val transform: TArrayOrContentOrFunction<TStyle>,
) : TShowRule {
    override fun format(): String = Representations.reprOf(this)
}

@TypstOverloads
public fun TShowRule(selector: TNoneOrSelector, transform: TArrayOrContentOrFunction<TStyle>):
        TShowRule = TShowRuleImpl(`selector`, `transform`)
