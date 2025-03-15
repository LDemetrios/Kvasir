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
    "duration",
    ["duration"],
    TDurationImpl::class,
)
public interface TDuration : TValue {
    public val seconds: TInt?

    public val minutes: TInt?

    public val hours: TInt?

    public val days: TInt?

    public val weeks: TInt?

    override fun type(): TType = Type

    public companion object {
        public val Type: TType = TTypeImpl("duration")

        internal val secondsType: InternalType = ConcreteType("int")

        internal val minutesType: InternalType = ConcreteType("int")

        internal val hoursType: InternalType = ConcreteType("int")

        internal val daysType: InternalType = ConcreteType("int")

        internal val weeksType: InternalType = ConcreteType("int")
    }
}

internal data class TDurationImpl(
    @TSerialName("seconds")
    override val seconds: TInt? = null,
    @TSerialName("minutes")
    override val minutes: TInt? = null,
    @TSerialName("hours")
    override val hours: TInt? = null,
    @TSerialName("days")
    override val days: TInt? = null,
    @TSerialName("weeks")
    override val weeks: TInt? = null,
) : TDuration {
    override fun format(): String = Representations.structRepr("duration",ArgumentEntry(false,
            "seconds", `seconds`),ArgumentEntry(false, "minutes", `minutes`),ArgumentEntry(false, "hours",
            `hours`),ArgumentEntry(false, "days", `days`),ArgumentEntry(false, "weeks", `weeks`),)
}

@TypstOverloads
public fun TDuration(
    seconds: TInt? = null,
    minutes: TInt? = null,
    hours: TInt? = null,
    days: TInt? = null,
    weeks: TInt? = null,
): TDuration = TDurationImpl(`seconds`, `minutes`, `hours`, `days`, `weeks`)
