package org.ldemetrios.kvasir.highlight

import java.lang.reflect.InaccessibleObjectException
import java.lang.reflect.Modifier
import java.util.IdentityHashMap
import java.util.concurrent.ConcurrentSkipListMap
import kotlin.random.Random

fun Any.fields(): MutableMap<String, Any?> {
    if (this is Array<*>) {
        val fields = mutableMapOf<String, Any?>()
        fields["length"] = this.size
        for (i in this.indices) {
            fields[i.toString()] = this[i]
        }
        return fields
    }
    val fields = mutableMapOf<String, Any?>()
    var aClass: Class<Any>? = this.javaClass
    while (aClass != null) {
        for (field in aClass.declaredFields) {
            if (Modifier.isStatic(field.modifiers)) continue
            val value = try {
                field.isAccessible = true
                field.get(this)
            } catch (e: InaccessibleObjectException) {
                System.err.println("Unable to open: ${field.declaringClass.module.name}/${field.declaringClass.packageName}" )
                "_INACCESSIBLE"
            }
            fields[field.name] = value
        }
        aClass = aClass.superclass
    }
    return fields
}

fun parse(obj: Any) {
    data class ObjectData(val index: Long, val fields: MutableMap<String, Any?>)

    var l = 0L

    fun collectAll(obj: Any?, set: IdentityHashMap<Any, ObjectData>) {
        when (obj) {
            null, /*is String, */is Number, is Boolean -> return
        }
        if (obj in set) return
        val fields = obj!!.fields()
        set[obj] = ObjectData(l++, fields)
        for (value in fields.values) {
            try {
                collectAll(value, set)
            } catch(e: StackOverflowError) {
            }
        }
    }

    val set = IdentityHashMap<Any, ObjectData>()
    collectAll(obj, set)

    println("digraph obj {")
    println("    rankdir=LR;")
    println("    node [shape=record];")

    var ref = 0L
    val edges = mutableSetOf<Triple<Long, Long, Long>>()
     for ((node, data) in set) {
        val type = node.javaClass.name
        val id = data.index
        val fields = data.fields

        print("    node$id [label=\"<type> ${type.shorten()}")
        for ((key, value) in fields) {
            print(" | ")
            when (value) {
                null -> print("$key : null")
//                is String -> print("$key : \\\"${value.escape()}\\\"")
                is Number, is Boolean -> print("$key : $value")
                else -> {
                    val refId = ref++
                    print(" <ref$refId> $key ")
                    if (key !in listOf("before", "after"))
                        edges.add(Triple(id, refId, set[value]!!.index))
                }
            }
        }
        println("\"]")
    }

    println()
    for ((nodefrom, reffrom, to) in edges) {
        println("    node$nodefrom:ref$reffrom -> node$to") // :type
    }
    println("}")
    println("\n\n\n--- ${set.size} nodes ---")

}

fun String.escape() = this
    .replace("\\", "\\\\")
    .replace("\"", "\\\"")
    .replace("\n", "\\n")
    .replace("\r", "\\r")
    .replace("\t", "\\t")

fun splitCamelCase(input: String): List<String> {
    val regex = "(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])".toRegex()
    return regex.split(input).filter { it.isNotEmpty() }
}

fun String.shorten(): String {
    val pack = split(".").dropLast(1)
    val outer = split(".").last().split("$").dropLast(1)
    val inn = split(".").last().split("$").last()
    val syl = "[eyuioaEYUIOA]"
    val cons = "[qwrtpsdfghjklzxcvbnmQWRTPSDFGHJKLZXCVBNM]"
    return pack.joinToString("") { it[0].toString() + "." } +
            outer.joinToString("") {
                val words = splitCamelCase(it)
//                words.joinToString("") {
//                    (Regex("($syl|[0-9])*($cons(($syl|[0-9])*$cons){0,2})?").matchAt(it, 0)?.value ?: it)
//                } + "$"
                words.dropLast(1).joinToString("") { it[0].toString() } + words.last() + "$"
            } +
            inn
}
//
//fun main() {
//    val m = ConcurrentSkipListMap<String, String>(
//        java.util.Map.of(
//            "one", "один",
//            "two", "два",
//            "three", "три",
//            "four", "четыре",
//            "five", "пять",
//            "six", "шесть",
//            "seven", "семь",
//            "eight", "восемь",
//            "nine", "девять",
//            "ten", "десять",
////            "eleven" to "одиннадцать",
////            "twelve" to "двенадцать",
////            "thirteen" to "тринадцать",
////            "fourteen" to "четырнадцать",
////            "fifteen" to "пятнадцать",
////            "sixteen" to "шестнадцать",
////            "seventeen" to "семнадцать",
////            "eighteen" to "восемнадцать",
////            "nineteen" to "девятнадцать",
////            "twenty" to "двадцать"
//        )
//    )
//    parse(m)
//}