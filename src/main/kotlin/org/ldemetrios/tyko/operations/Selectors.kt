package org.ldemetrios.tyko.operations

import org.ldemetrios.tyko.model.*
import org.ldemetrios.tyko.model.TArray
import org.ldemetrios.tyko.model.t

fun TSelector.before(selector: TSelector, inclusive: Boolean) = TBeforeSelector(this, selector, inclusive.t)
fun TSelector.after(selector: TSelector, inclusive: Boolean) = TAfterSelector(this, selector, inclusive.t)

infix fun TSelector.before(selector: TSelector) = TBeforeSelector(this, selector)
infix fun TSelector.after(selector: TSelector) = TAfterSelector(this, selector)

fun TSelector.and(vararg others: TSelector) = TAndSelector(TArray(this, *others))
fun TSelector.or(vararg others: TSelector) = TOrSelector(TArray(this, *others))

