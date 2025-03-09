package org.ldemetrios.tyko.model

@TSyntheticType(["location"])
public interface TLocation : TLocationSuper, TDictionary<TIntOrLength> {
    override val dictionaryValue: Map<String, TIntOrLength>
        get() = mapOf(
            "page" to page,
            "x" to x,
            "y" to y,
        )
}

internal data class TLocationImpl(
    override val page: TInt,
    override val x: TLength,
    override val y: TLength
) : TLocation

fun TLocation(page: TInt, x: TLength, y: TLength): TLocation = TLocationImpl(page, x, y)

@TSyntheticType(["point"])
public interface TPoint : TPointSuper, TDictionary<TLength> {
    override val dictionaryValue: Map<String, TLength>
        get() = mapOf(
            "x" to x,
            "y" to y,
        )
}

internal data class TPointImpl(
    override val x: TLength,
    override val y: TLength,
) : TPoint

fun TPoint(x: TLength, y: TLength): TPoint = TPointImpl(x, y)

@TSyntheticType(["sides", "margin"])
public interface TSides<S : TValue> : TSidesSuper<S>, TMargin<S>, TDictionary<S> {
    override val dictionaryValue: Map<String, S>
        get() = mapOf(
            "top" to top,
            "right" to right,
            "bottom" to bottom,
            "left" to left,
        ).filterValues { it != null }.mapValues { it.value!! }
    override val inside: S? get() = null
    override val outside: S? get() = null
}

internal data class TSidesImpl<S : TValue>(
    override val top: S?,
    override val right: S?,
    override val bottom: S?,
    override val left: S?,
) : TSides<S>

fun <S : TValue> TSides(top: S?, right: S?, bottom: S?, left: S?): TSides<S> = TSidesImpl(top, right, bottom, left)

@TSyntheticType(["margin"])
public interface TMargin<M : TValue> : TMarginSuper<M>, TDictionary<M> {
    override val dictionaryValue: Map<String, M>
        get() = mapOf(
            "top" to top,
            "right" to right,
            "bottom" to bottom,
            "left" to left,
            "inside" to inside,
            "outside" to outside,
        ).filterValues { it != null }.mapValues { it.value!! }
}

internal data class TMarginImpl<M : TValue>(
    override val top: M?,
    override val right: M?,
    override val bottom: M?,
    override val inside: M?,
    override val left: M?,
    override val outside: M?,
) : TMargin<M>

fun <M : TValue> TMargin(
    top: M?, right: M?, bottom: M?, inside: M?, left: M?, outside: M?
): TMargin<M> = TMarginImpl(top, right, bottom, inside, left, outside)

interface TEmptyDictionary : TSides<TDynamic>, TMargin<TDynamic> {
    override val top: TDynamic? get() = null
    override val right: TDynamic? get() = null
    override val bottom: TDynamic? get() = null
    override val left: TDynamic? get() = null
    override val inside: TDynamic? get() = null
    override val outside: TDynamic? get() = null

    override val dictionaryValue: Map<String, TDynamic> get() = mapOf()
}

data object TEmptyDictionaryImpl : TEmptyDictionary

fun <V : TValue> TDictionary(value: Map<String, V>): TDictionary<V> {
    return dictionarySpecial(value) ?: TDictionaryImpl(value) as TDictionary<V>
}

private fun <V : TValue> dictionarySpecial(value: Map<String, V>): TDictionary<V>? {
    @Suppress("UNCHECKED_CAST")
    if (value.isEmpty()) return TEmptyDictionaryImpl as TDictionary<V>
    if (value.size > 6) return null

    val keys = value.keys
    return if (keys ==  LOCATION_KEYS) {
        TLocation(
            value["page"] as? TInt ?: return null,
            value["x"] as? TLength ?: return null,
            value["y"] as? TLength ?: return null
        ) as TDictionary<V>
    } else if (keys == POINT_KEYS) {
        TPoint(
            value["x"] as? TLength ?: return null,
            value["y"] as? TLength ?: return null
        ) as TDictionary<V>
    } else if (keys.all{it in SIDES_KEYS}) {
        TSides(
            value["top"] as? V?,
            value["right"] as? V?,
            value["bottom"] as? V?,
            value["left"] as? V?,
        )
    } else if (keys.all{it in MARGIN_KEYS}) {
        TMargin(
            value["top"] as? V,
            value["right"] as? V,
            value["bottom"] as? V,
            value["inside"] as? V,
            value["left"] as? V,
            value["outside"] as? V,
        )
    } else null
}

private val LOCATION_KEYS = setOf("page", "x", "y")
private val POINT_KEYS = setOf("x", "y")
private val MARGIN_KEYS = setOf("top", "right", "bottom", "left", "inside", "outside")
private val SIDES_KEYS = setOf("top", "right", "bottom", "left")
