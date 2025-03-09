package org.ldemetrios.tyko.operations

import org.ldemetrios.tyko.model.*
import org.ldemetrios.tyko.model.TArray
import org.ldemetrios.tyko.model.manual.TDelayedExecution
import org.ldemetrios.tyko.model.TDictionary
import org.ldemetrios.tyko.model.repr

fun TFunction.with(arguments: TArguments<*>) = TWith(this, arguments)

fun TFunction.with(vararg positionals: TValue) = with(TArguments(TArray(*positionals), TDictionary<TValue>()))
fun TFunction.with(vararg named: Pair<String, TValue>) = with(TArguments(TArray(), TDictionary(*named)))

operator fun TFunction.get(vararg named: Pair<String, TValue>) = with(*named)

operator fun TFunction.invoke(vararg args: TValue): TValue {
    return TDelayedExecution("(${this.repr()})(${args.joinToString { it.repr() }})")
}
