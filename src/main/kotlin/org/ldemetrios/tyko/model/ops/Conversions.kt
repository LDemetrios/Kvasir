package org.ldemetrios.tyko.model

import org.ldemetrios.tyko.model.*
import java.awt.Color

val Float.t get() = TFloat(this.toDouble())
val Double.t get() = TFloat(this)
val Byte.t get() = TInt(this.toLong())
val Short.t get() = TInt(this.toLong())
val Int.t get() = TInt(this.toLong())
val Long.t get() = TInt(this)
val String.t get() = TStr(this)
val Boolean.t get() = TBool(this)
val ByteArray.t get() = TBytes(this)

val TFloat.pt get() = TLength(this, 0f.t)
val TFloat.em get() = TLength(0f.t, this)
val TInt.pt get() = TLength(intValue.toDouble().t, 0f.t)
val TInt.em get() = TLength(0f.t, intValue.toDouble().t)

val TFloat.fr get() = TFraction(this)
val TInt.fr get() = TFraction(intValue.toDouble().t)

val TFloat.pc get() = TRatio((floatValue / 100).t)
val TInt.pc get() = TRatio((intValue / 100.0).t)

val TFloat.deg get() = TAngle(this)
val TInt.deg get() = TAngle(intValue.toDouble().t)
val TFloat.rad get() = TAngle((this.floatValue * (180 / Math.PI)).t)
val TInt.rad get() = TAngle(((this.intValue * (180 / Math.PI))).t)

fun <E : TValue> TArray(vararg elements: E) = TArray(elements.toList())
fun <E : TValue> TDictionary(vararg pairs: Pair<String, E>) = TDictionary(pairs.toMap())
fun TSequence(vararg elements: TContent) = TSequence(TArray(elements.toList()))
fun TSequence(builder: SequenceBuilder.() -> Unit) = SequenceBuilder().apply(builder).create()

// Temporary, later replace with code generation
val Byte.pt get() = this.t.pt
val Byte.em get() = this.t.em
val Byte.fr get() = this.t.fr
val Byte.pc get() = this.t.pc
val Byte.deg get() = this.t.deg
val Byte.rad get() = this.t.rad

val Short.pt get() = this.t.pt
val Short.em get() = this.t.em
val Short.fr get() = this.t.fr
val Short.pc get() = this.t.pc
val Short.deg get() = this.t.deg
val Short.rad get() = this.t.rad

val Int.pt get() = this.t.pt
val Int.em get() = this.t.em
val Int.fr get() = this.t.fr
val Int.pc get() = this.t.pc
val Int.deg get() = this.t.deg
val Int.rad get() = this.t.rad

val Long.pt get() = this.t.pt
val Long.em get() = this.t.em
val Long.fr get() = this.t.fr
val Long.pc get() = this.t.pc
val Long.deg get() = this.t.deg
val Long.rad get() = this.t.rad

val Float.pt get() = this.t.pt
val Float.em get() = this.t.em
val Float.fr get() = this.t.fr
val Float.pc get() = this.t.pc
val Float.deg get() = this.t.deg
val Float.rad get() = this.t.rad

val Double.pt get() = this.t.pt
val Double.em get() = this.t.em
val Double.fr get() = this.t.fr
val Double.pc get() = this.t.pc
val Double.deg get() = this.t.deg
val Double.rad get() = this.t.rad

val Color.t get() = TRgb("#%02x%02x%02x%02x".format(red, green, blue, alpha).t)

val String.text get() = TText(text = this.t)
val TStr.text get() = TText(text = this)

val TBool.value get() = this.boolValue
val TInt.value get() = intValue
val TFloat.value get() = floatValue
val TStr.value get() = strValue
val <T : TValue> TArray<T>.value get() = arrayValue
val <T : TValue> TDictionary<T>.value get() = dictionaryValue
val TBytes.value get() = this.bytesValue

operator fun THAlignment.plus(other: TVAlignment): TAlignment = TAlignmentImpl(this, other)
operator fun TVAlignment.plus(other: THAlignment): TAlignment = TAlignmentImpl(other, this)
operator fun TLength.plus(other: TLength): TLength = TLength(
    listOfNotNull(this.pt, other.pt).sumOf { it.floatValue }.t,
    listOfNotNull(this.em, other.em).sumOf { it.floatValue }.t,
)

operator fun TLength.plus(other: TRatio): TRelative = TRelativeImpl(other, this)
operator fun TRatio.plus(other: TLength): TRelative = TRelativeImpl(this, other)
