package org.ldemetrios.tyko.operations

import org.ldemetrios.tyko.model.*

private val loremF = TNativeFunc("lorem".t)

fun lorem(x:Int) = lorem(x.t)
fun lorem(x: TInt) = loremF(x) as TStr
