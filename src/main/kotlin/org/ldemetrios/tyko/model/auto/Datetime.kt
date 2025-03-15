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
    "datetime",
    ["datetime"],
    TDatetimeImpl::class,
)
public interface TDatetime : TAutoOrDatetimeOrNone, TValue {
    public val year: TInt?

    public val month: TInt?

    public val day: TInt?

    public val hour: TInt?

    public val minute: TInt?

    public val second: TInt?

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("datetime")

        internal val yearType: InternalType = ConcreteType("int")

        internal val monthType: InternalType = ConcreteType("int")

        internal val dayType: InternalType = ConcreteType("int")

        internal val hourType: InternalType = ConcreteType("int")

        internal val minuteType: InternalType = ConcreteType("int")

        internal val secondType: InternalType = ConcreteType("int")
    }
}

internal data class TDatetimeImpl(
    @TSerialName("year")
    override val year: TInt? = null,
    @TSerialName("month")
    override val month: TInt? = null,
    @TSerialName("day")
    override val day: TInt? = null,
    @TSerialName("hour")
    override val hour: TInt? = null,
    @TSerialName("minute")
    override val minute: TInt? = null,
    @TSerialName("second")
    override val second: TInt? = null,
) : TDatetime {
    override fun format(): String = Representations.structRepr("datetime",ArgumentEntry(false, "year",
            `year`),ArgumentEntry(false, "month", `month`),ArgumentEntry(false, "day",
            `day`),ArgumentEntry(false, "hour", `hour`),ArgumentEntry(false, "minute",
            `minute`),ArgumentEntry(false, "second", `second`),)
}

@TypstOverloads
public fun TDatetime(
    year: TInt? = null,
    month: TInt? = null,
    day: TInt? = null,
    hour: TInt? = null,
    minute: TInt? = null,
    second: TInt? = null,
): TDatetime = TDatetimeImpl(`year`, `month`, `day`, `hour`, `minute`, `second`)
