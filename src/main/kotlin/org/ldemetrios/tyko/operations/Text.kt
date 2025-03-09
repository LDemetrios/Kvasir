package org.ldemetrios.tyko.operations

import org.ldemetrios.tyko.model.TInt
import org.ldemetrios.tyko.model.TNativeFunc
import org.ldemetrios.tyko.model.TStr
import org.ldemetrios.tyko.model.t

private val loremF = TNativeFunc("lorem".t)

fun lorem(x:Int) = lorem(x.t)
fun lorem(x: TInt) = loremF(x) as TStr
