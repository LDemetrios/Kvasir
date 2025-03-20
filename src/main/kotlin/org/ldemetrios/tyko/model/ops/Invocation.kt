package org.ldemetrios.tyko.model

fun TFunction.with(arguments: TArguments<*>) = TWith(this, arguments)

fun TFunction.with(vararg positionals: TValue) = with(TArguments(TArray(*positionals), TDictionary<TValue>()))
fun TFunction.with(vararg named: Pair<String, TValue>) = with(TArguments(TArray(), TDictionary(*named)))

operator fun TFunction.get(vararg named: Pair<String, TValue>) = with(*named)

operator fun TFunction.invoke(vararg args: TValue): TDynamic {
    return TDelayedExecution("(${this.repr()})(${args.joinToString { it.repr() }})")
}
