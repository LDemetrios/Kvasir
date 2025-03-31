package org.ldemetrios.tyko.model

import org.ldemetrios.tyko.compiler.DetachedWorld
import org.ldemetrios.tyko.compiler.TypstCompilerException
import org.ldemetrios.tyko.compiler.WorldBasedTypstCompiler
import org.ldemetrios.tyko.compiler.evalDetached
import org.ldemetrios.tyko.ffi.TypstSharedLibrary
import org.ldemetrios.tyko.model.value

typealias CommonInterfaceName = TValue

fun TValue.repr() = format()

data class ArgumentEntry(var variadic: Boolean, var name: String?, var value: TValue?) {
    fun repr(): String? = when {
        value == null -> null
        variadic -> (".." + value!!.repr())
        name != null -> (name + ": " + value!!.repr())
        else -> (value!!.repr())
    }

    val first get() = variadic
    val second get() = name
    val third get() = value
}

object Representations {
    fun reprOf(value: TNone.Companion) = "none"
    fun reprOf(value: TAuto.Companion) = "auto"

    fun reprOf(value: TPlugin): String = TODO()

    fun reprOf(value: TAlignmentImpl): String = sumOfNotNull(value.vertical?.repr(), value.horizontal?.repr())

    fun <E : CommonInterfaceName> reprOf(value: List<E>): String = when (value.size) {
        0 -> "()"
        1 -> "(" + value[0].repr() + ",)"
        else -> "(" + value.joinToString(", ") { it.repr() } + ")"
    }

    fun reprOf(value: TStr): String = reprOf(value.strValue)


    fun <V : CommonInterfaceName> reprOf(value: Map<String, V>): String = when (value.size) {
        0 -> "(:)"
        else -> "(" + value.entries.joinToString(", ") { reprOf(it.key) + " : " + it.value.repr() } + ")"
    }

    fun <V : CommonInterfaceName> reprOf(value: TMargin<V>): String =
        reprOf(value.dictionaryValue.filter { it.value != TNone })

    fun <V : CommonInterfaceName> reprOf(value: TSides<V>): String =
        reprOf(value.dictionaryValue.filter { it.value != TNone })

    fun reprOf(value: String): String = "\"" +
            value.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\"", "\\\"") + "\""

    fun reprOf(value: Boolean): String = value.toString()
    fun reprOf(value: Long): String = value.toString()
    fun reprOf(value: Double): String = value.toString()


    fun <A : CommonInterfaceName> reprOf(value: TArguments<A>): String = "((..args) => args)(" +
            (value.named.dictionaryValue.map { it.key + " : " + it.value.repr() } +
                    value.positional.arrayValue.map { it.repr() }).joinToString(", ") + ")"

    fun reprOf(value: TSpace): String = "[ ]"
    fun reprOf(value: TElement): String = when (val it = value.name.strValue) {
        "context" -> "((context none).func())"
        else -> it
    }

    fun reprOf(value: TSequence): String {
        val styleStart = value.children.indexOfFirst { it is TStyle && it !is TDynamic }
        if (styleStart == -1) return elementRepr(
            "[a\\ ].func()",
            ArgumentEntry(false, null, value.children),
            ArgumentEntry(false, "label", value.label)
        ) else {
            val before = value.children.subList(0, styleStart)
            val styles =
                value.children.subList(styleStart, value.children.size).takeWhile { it is TStyle && it !is TDynamic }
            val after = value.children.subList(styleStart + styles.size, value.children.size)
            return elementRepr(
                "[a\\ ].func()",
                ArgumentEntry(
                    false,
                    null,
                    TArray(before + TStyled(TArray(styles as List<TStyle>), TSequence(TArray(after))))
                ),
                ArgumentEntry(false, "label", value.label)
            )
        }
    }

    fun structRepr(
        name: String,
        vararg elements: ArgumentEntry, // vararg, name (null if positional), value
    ): String {
        when (name) {
            "none", "auto" -> return name

            "math.mat" -> {
                elements.find { it.name == "augment" }?.let { argument ->
                    val asDict = argument.value as? TDictionary<*> ?: return@let
                    val map = asDict.dictionaryValue.toMutableMap()
                    if (map["stroke"] is TAuto) map.remove("stroke")
                    argument.value = TDictionary(map)
                }
            }
        }

        return name +
                "(" +
                elements.mapNotNull { it.repr() }.joinToString(", ") +
                ")"
    }

    private fun sumOfNotNull(vararg values: String?) = run {
        val list = listOfNotNull(*values)
        val filtered = list.filter { !it.matches(Regex("0\\.0(%|fr|em|pt|deg|rad)")) }
        if (filtered.isNotEmpty()) filtered.joinToString(" + ")
        else list.minBy { it.length }
    }


    fun reprOf(value: TAngle): String = "${value.deg.value}deg"
    fun reprOf(value: TFraction): String = "${value.value.value}fr"
    fun reprOf(value: TRatio): String = "${value.value.value * 100}%"
    fun reprOf(value: TLength): String =
        sumOfNotNull(value.em?.let { "${it.repr()}em" }, value.pt?.let { "${it.repr()}pt" })

    fun reprOf(value: TRelative): String =
        sumOfNotNull(
            value.abs?.em?.let { "${it.repr()}em" },
            value.abs?.pt?.let { "${it.repr()}pt" },
            value.rel?.repr()
        )

    fun reprOf(value: TCounter): String =
        "counter(${value.value.repr()})"

    fun reprOf(value: TRegexSelector): String = "selector(${value.regex.repr()})"

    fun reprOf(value: TLabelSelector): String = "selector(${value.label.repr()})"

    fun reprOf(value: TElementSelector): String = when (val where = value.where) {
        null -> value.element.value
        else -> value.element.value +
                ".where(" +
                where.dictionaryValue.map { it.key + " : " + it.value.repr() }.joinToString(", ") +
                ")"
    }

    fun reprOf(value: TAfterSelector): String {
        return "selector(" + value.selector.repr() +
                ").after(" +
                value.start.repr() +
                (value.inclusive?.let { ", inclusive: ${it.repr()}" } ?: "") +
                ")"
    }

    fun reprOf(value: TBeforeSelector): String {
        return "selector(" + value.selector.repr() +
                ").before(" +
                value.end.repr() +
                (value.inclusive?.let { ", inclusive: ${it.repr()}" } ?: "") +
                ")"
    }

    fun reprOf(value: TAndSelector): String = when (value.variants.size) {
        0 -> throw IllegalArgumentException()
        1 -> value.variants[0].repr()
        else -> "selector(" + value.variants[0].repr() + ").and(" +
                value.variants.drop(1).joinToString(", ") { it.repr() } + ")"
    }

    fun reprOf(value: TOrSelector): String = when (value.variants.size) {
        0 -> throw IllegalArgumentException()
        1 -> value.variants[0].repr()
        else -> "selector(" + value.variants[0].repr() + ").or(" +
                value.variants.drop(1).joinToString(", ") { it.repr() } + ")"
    }

    fun reprOf(value: TTypeImpl): String = when (val it = value.name) {
        "none", "auto" -> "type($it)"
        else -> it
    }

    fun reprOf(value: TModule): String = value.name.value
    fun reprOf(value: TDirection): String = value.value.value

    fun reprOf(value: TMathRoot): String = if (value.index == null) {
        structRepr("math.sqrt", ArgumentEntry(false, null, value.radicand))
    } else {
        structRepr("math.root", ArgumentEntry(false, null, value.index), ArgumentEntry(false, null, value.radicand))
    }

    fun reprOf(value: TMathAlignPoint): String = "$ & $.body"

    fun reprOf(value: TStyled): String {
        return "{ " + value.styles.value.joinToString("; ") { it.repr() } + "; ${value.child.repr()}; }"
    }

    fun reprOf(value: TFunction, contextual: Boolean = false): String = when (value) {
        is TElement -> reprOf(value)
        is TWith -> "(" + value.origin.repr() + ").with(.." + value.args.repr() + ")"
        is TNativeFunc -> value.name.value
        is TClosure -> {
            StringBuilder().apply {
                val captured = value.captured
                if (!captured.isNullOrEmpty()) {
                    append("{ ")
                    for ((name, v) in captured) {
                        append("let $name = ")
                        append(v.repr())
                        append("; ")
                    }
                }
                if (contextual) append(value.node.value)
                else append(reworkClosure(value.node.value))

                if (!value.captured.isNullOrEmpty()) append("}")
            }.toString()
        }

        else -> throw AssertionError(value)
    }

    private fun reworkClosure(text: String): String {
        val compiles = try {
            SANDBOX_COMPILER.evalDetached("let $text; 1")
            true
        } catch (e: TypstCompilerException) {
            false
        }

        if (compiles) {
            val ident = TYPST_IDENT.matchAt(text, 0).let { it ?: throw AssertionError(text) }.value
            return "{ let $text ; $ident }"
        } else {
            return text
        }
        return ""
    }

    fun elementRepr(s: String, vararg entries: ArgumentEntry): String {

        val lbl = entries.find { it.name == "label" }?.value as TLabel?
        val repr = structRepr(s, *entries.filter { it.name != "label" }.toTypedArray())

        return if (lbl == null) repr
        else "[#($repr)#${lbl.repr()}]"
    }

    fun setRepr(s: String, vararg entries: ArgumentEntry): String {
        return "set " + structRepr(s, *entries) // TODO
    }

    fun reprOf(rule: TShowRule): String {
        val start = StringBuilder().apply {
            append("show")
            if (rule.selector is TSelector) {
                append(" ")
                append(rule.selector.repr())
            }
            append(": ")
        }.toString()

        return when (val transform = rule.transform) {
            is TArray<*> -> {
                transform.joinToString("; ") {
                    start + it.repr()
                }
            }

            else -> {
                start + rule.transform.repr()
            }
        }
    }

    fun reprOf(context: TContext) = "context " + reprOf(context.func, contextual = true)

    fun reprOf(context: TStyleDeprecated) = "style(" + context.func.repr() + ")"
    fun reprOf(update: TCounterUpdate): String =
        "counter(" + update.key.repr() + ").update(" + update.update.repr() + ")"

    fun reprOf(step: TCounterStep): String =
        "counter(" + step.key.repr() + ").step(level:" + step.level.repr() + ")"

    fun reprOf(update: TStateUpdate): String = "state(" + update.key.repr() + ").update(" + update.update.repr() + ")"

    fun reprOf(bytes: ByteArray): String = "bytes((" +
            bytes.joinToString(", ") { String.format("0x%02x", it) } +
            "))"

    //    fun reprOf(elem: TSymbolElem) : String = "$\\${elem.text.strValue}$.body"
    fun reprOf(elem: TSymbolElem): String = "symbol(${elem.text.repr()})"
}

private val xid_start = "[\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}" +
        "\\u1885" +
        "\\u1886" +
        "\\u2118" +
        "\\u212e" +
        "\\u309b" +
        "\\u309c]"

private val xid_continue = "[\\p{L}\\p{Pc}\\p{Nd}\\p{Nl}\\p{Mn}\\p{Mc}" +
        "\\p{Cf}\\u0000-\\u0008\\u000E-\\u001B\\u007F-\\u009F" + // Ignorable
        "\\u00b7\\u0387\\u1369\\u136a\\u136b\\u136c\\u136d\\u136e\\u136f\\u1370\\u1371\\u19da\\u2118\\u212e\\u309b\\u309c" +
        "]"

val TYPST_IDENT = Regex("($xid_start|_)($xid_continue|_|-)*")


private val SANDBOX_COMPILER by lazy {
    WorldBasedTypstCompiler(TypstSharedLibrary.anyInstance(), DetachedWorld())
}