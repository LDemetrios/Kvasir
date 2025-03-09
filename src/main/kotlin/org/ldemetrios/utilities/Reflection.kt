package org.ldemetrios.utilities

//import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler
//import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger
import org.ldemetrios.utilities.constants.NEWLINE_PATTERN
import java.io.File
import java.io.IOException
import java.lang.reflect.Executable
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.nio.file.Files

fun Class<*>.superclassList(): List<Class<*>> {
    val res = mutableListOf<Class<*>>(this)
    var clazz: Class<*>? = this
    do {
        clazz = clazz?.superclass
        if (clazz != null) res.add(clazz)
    } while (clazz != Any::class.java && clazz != null)
    return res
}

fun juniorestCommonSuperclass(c1: Class<*>, c2: Class<*>): Class<*>? {
    if (c1 == Void::class.java) return c2
    if (c2 == Void::class.java) return c1

    val super1 = c1.superclassList()
    val super2 = c2.superclassList()
    for (clazz in super1) if (clazz in super2) return clazz
    return null //Stub, will never be reached
}

fun Field.isPublic() = Modifier.isPublic(modifiers)
fun Field.isPrivate() = Modifier.isPrivate(modifiers)
fun Field.isProtected() = Modifier.isProtected(modifiers)
fun Field.isStatic() = Modifier.isStatic(modifiers)
fun Field.isFinal() = Modifier.isFinal(modifiers)
fun Field.isVolatile() = Modifier.isVolatile(modifiers)
fun Field.isTransient() = Modifier.isTransient(modifiers)

fun Executable.isPublic() = Modifier.isPublic(modifiers)
fun Executable.isPrivate() = Modifier.isPrivate(modifiers)
fun Executable.isProtected() = Modifier.isProtected(modifiers)
fun Executable.isStatic() = Modifier.isStatic(modifiers)
fun Executable.isFinal() = Modifier.isFinal(modifiers)
fun Executable.isSynchronized() = Modifier.isSynchronized(modifiers)
fun Executable.isNative() = Modifier.isNative(modifiers)
fun Executable.isAbstract() = Modifier.isAbstract(modifiers)

fun Class<*>.isPublic() = Modifier.isPublic(modifiers)
fun Class<*>.isPrivate() = Modifier.isPrivate(modifiers)
fun Class<*>.isProtected() = Modifier.isProtected(modifiers)
fun Class<*>.isStatic() = Modifier.isStatic(modifiers)
fun Class<*>.isFinal() = Modifier.isFinal(modifiers)
fun Class<*>.isAbstract() = Modifier.isAbstract(modifiers)

private fun addPath(list: MutableList<in File>, path: String) {
    val file = File(path)
    if (file.exists()) {
        list.add(file)
    } else {
        println("warn: missing '$path', ignored")
    }
}

//fun decompile(source: File, destination: File) {
//    val logger = object : IFernflowerLogger() {
//        override fun writeMessage(p0: String?, p1: Severity?) = Unit
//        override fun writeMessage(p0: String?, p1: Severity?, p2: Throwable?) = Unit
//    }
//    val decompiler = ConsoleDecompiler(destination, mapOf(), logger)
//    decompiler.addSource(source)
//    decompiler.decompileContext()
//}

//fun classText(clazz: Class<*>): String {
//    val path: String = clazz.name.replace('.', '/') + ".class"
//    val stream = ClassLoader.getSystemResourceAsStream(path) ?: throw IOException("Class not found: ${clazz.name}")
//
//    val bytes = stream.readAllBytes()
//
//    val tmp = Files.createTempFile("func", "classfile.class").toFile()
//    tmp.createNewFile()
//    tmp.writeBytes(bytes)
//
//    val dest = Files.createTempDirectory("decomp")
//    decompile(tmp, dest.toFile())
//
//    return dest.toFile().listFiles()!!.single().readText()
//}

//fun debugToString(x: Any?): String {
//    if (x == null) return "null"
//    if (x is kotlin.jvm.internal.FunctionReference || x is kotlin.jvm.internal.Lambda<*>) {
//        return functionToString(x)
//    }
//    val str = x.toString()
//    val clazz = x.javaClass.name
//    if (str.startsWith(clazz) && str.drop(clazz.length).matches(Regex("@[0-9a-f]{8}"))) {
//        return x.toString() // TODO
//    } else {
//        return x.toString()
//    }
//}

//fun <A, B, C> comp(f: (A) -> B, g: (B) -> C): (A) -> C = { g(f(it)) }
//
//fun main() {
//    println(functionToString { it: Int ->
//        var res = 1
//        for (i in 1..it) res *= i
//        res
//    })
//    println("=========")
//    println(functionToString(1::toString))
//    println("=========")
//    println(functionToString(comp({ it: Int -> it + 1 }, { it * 2 })))
//}
//
//fun functionToString(x: Any): String {
//    val text = classText(x.javaClass)
//        .split(NEWLINE_PATTERN)
//        .split(limit = 2) { it == "@Metadata(" }[1]
//        .split(limit = 2) { it == ")" }[1]
//        .joinToString("\n")
//
//    val body = text
//        .split("invoke", limit = 2)[1]
//        .split("{", limit = 2)
//        .let {
//            val args = it[0]
//            val body = "{" + it[1]
//            "   " + args + "-> " + body.trim().removeSuffix("}").trim()
//        }
//
//    val captures = x.javaClass.declaredFields.filter {
//        !it.isStatic()
//    }.mapTo(mutableListOf()) {
//        it.isAccessible = true
//        it.name to it.get(x)
//    }
//
//    // TODO Also capture inner classes representing inner lambdas
//
//    if (x.javaClass.superclass.simpleName == "FunctionReferenceImpl") {
//        captures.add("receiver" to (x as kotlin.jvm.internal.CallableReference).boundReceiver)
//    }
//
//    return if (captures.isEmpty()) body else
//        "$body\n     where\n" +
//                captures.joinToString("\n") {
//                    "      " + it.first + " = " + debugToString(it.second)
//                }
//}
//
