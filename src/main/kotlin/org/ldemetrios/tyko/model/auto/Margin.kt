@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.reflect.KType
import kotlin.reflect.typeOf
import org.ldemetrios.js.JSBoolean
import org.ldemetrios.js.JSNumber
import org.ldemetrios.js.JSObject
import org.ldemetrios.js.JSString
import org.ldemetrios.js.JSUndefined
import org.ldemetrios.utilities.cast
import org.ldemetrios.utilities.castUnchecked

public interface TMarginSuper<out M : TValue> : TAutoOrMarginOrRelative<TDynamic> {
    public val top: M?

    public val right: M?

    public val bottom: M?

    public val left: M?

    public val inside: M?

    public val outside: M?
}
