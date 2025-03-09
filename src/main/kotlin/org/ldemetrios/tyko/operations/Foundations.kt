package org.ldemetrios.tyko.operations

import org.ldemetrios.tyko.model.*
import org.ldemetrios.tyko.model.t

fun <A: TValue> TArguments<A>.at(i:Int) = positional[i]
fun <A: TValue> TArguments<A>.at(i: TInt) = positional[i.intValue.toInt()]
fun <A: TValue> TArguments<A>.at(i:String) = named[i]
fun <A: TValue> TArguments<A>.at(i: TStr) = named[i.strValue]

private val evalF = TNativeFunc("eval".t)

fun evalCode(source:TStr) = evalF(source)
fun evalMarkup(source:TStr) = evalF["mode" to "markup".t](source) as TContent
fun evalMath(source:TStr) = evalF["mode" to "math".t](source) as TContent

fun evalCode(source:String) = evalCode(source.t)
fun evalMarkup(source:String) = evalMarkup(source.t)
fun evalMath(source:String) = evalMath(source.t)


