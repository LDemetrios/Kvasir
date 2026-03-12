package org.ldemetrios.kvasir.preview.data

import org.ldemetrios.tyko.conversion.java2typst
import org.ldemetrios.tyko.conversion.typst2java
import org.ldemetrios.tyko.model.TValue
import java.lang.constant.ClassDesc
import java.lang.constant.DirectMethodHandleDesc
import java.lang.constant.MethodHandleDesc
import java.lang.constant.MethodTypeDesc
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

private data class ParsedSignature(
    val owner: ClassDesc,
    val name: String,
    val descriptor: String,
)

private fun parseClassDesc(raw: String): ClassDesc {
    return if (raw.startsWith("[") || (raw.startsWith("L") && raw.endsWith(";")) || raw.length == 1) {
        ClassDesc.ofDescriptor(raw)
    } else {
        val split = raw.lastIndexOf('.')
        if (split == -1) {
            ClassDesc.of(raw)
        } else {
            ClassDesc.of(raw.substring(0, split), raw.substring(split + 1))
        }
    }
}

private fun parseSignature(signature: String): ParsedSignature {
    val ownerSplit = signature.indexOf('#')
    require(ownerSplit > 0 && ownerSplit < signature.length - 1) {
        "Expected signature `owner#member:descriptor`, got `$signature`"
    }
    val memberSplit = signature.indexOf(':', ownerSplit + 1)
    require(memberSplit > ownerSplit + 1 && memberSplit < signature.length - 1) {
        "Expected signature `owner#member:descriptor`, got `$signature`"
    }
    return ParsedSignature(
        owner = parseClassDesc(signature.substring(0, ownerSplit)),
        name = signature.substring(ownerSplit + 1, memberSplit),
        descriptor = signature.substring(memberSplit + 1),
    )
}

private fun buildHandleDesc(signature: String, kind: DirectMethodHandleDesc.Kind): MethodHandleDesc {
    val parsed = parseSignature(signature)
    return when (kind) {
        DirectMethodHandleDesc.Kind.CONSTRUCTOR -> {
            val descriptor = MethodTypeDesc.ofDescriptor(parsed.descriptor)
            MethodHandleDesc.ofConstructor(parsed.owner, *descriptor.parameterArray())
        }

        DirectMethodHandleDesc.Kind.GETTER,
        DirectMethodHandleDesc.Kind.SETTER,
        DirectMethodHandleDesc.Kind.STATIC_GETTER,
        DirectMethodHandleDesc.Kind.STATIC_SETTER -> {
            MethodHandleDesc.ofField(
                kind,
                parsed.owner,
                parsed.name,
                ClassDesc.ofDescriptor(parsed.descriptor),
            )
        }

        else -> {
            MethodHandleDesc.ofMethod(
                kind,
                parsed.owner,
                parsed.name,
                MethodTypeDesc.ofDescriptor(parsed.descriptor),
            )
        }
    }
}

fun reflectiveCall(
    lookup: MethodHandles.Lookup,
    value: String,
    valueOf: DirectMethodHandleDesc.Kind,
    params: List<TValue>
): TValue {
    val descriptor = buildHandleDesc(value, valueOf)
    val handle = descriptor.resolveConstantDesc(lookup) as MethodHandle
    val parameterTypes = handle.type().parameterArray()
    require(parameterTypes.size == params.size) {
        "Expected ${parameterTypes.size} arguments for `$value`, got ${params.size}"
    }
    val converted = params.mapIndexed { idx, param ->
        typst2java(param, parameterTypes[idx])
    }
    return java2typst(handle.invokeWithArguments(converted))
}

object BytesClassLoader : ClassLoader() {
    fun define(name: String, b: ByteArray): Class<*>? {
        return defineClass(name, b, 0, b.size)
    }
}