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
