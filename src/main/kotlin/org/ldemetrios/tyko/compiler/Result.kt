package org.ldemetrios.tyko.compiler

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

@Serializable(with = RResultSerializer::class)
sealed class RResult<out T, out E> {
    data class Ok<T>(val value: T) : RResult<T, Nothing>()
    data class Err<E>(val error: E) : RResult<Nothing, E>()

    fun isOk(): Boolean = this is Ok
    fun isErr(): Boolean = this is Err

    fun getOrNull(): T? = (this as? Ok)?.value
    fun errorOrNull(): E? = (this as? Err)?.error

    fun unwrap(): T = getOrNull()!!

    inline fun <R> map(transform: (T) -> R): RResult<R, E> = when (this) {
        is Ok -> Ok(transform(value))
        is Err -> this
    }

    inline fun <R> mapErr(transform: (E) -> R): RResult<T, R> = when (this) {
        is Ok -> this
        is Err -> Err(transform(error))
    }

    inline fun <R> join(ifOk : (T) -> R, ifErr : (E) -> R) = when(this) {
        is Ok -> ifOk(value)
        is Err -> ifErr(error)
    }
}

inline fun <T, E, R> RResult<T, E>.andThen(transform: (T) -> RResult<R, E>): RResult<R, E> = when (this) {
    is RResult.Ok -> transform(value)
    is RResult.Err -> this
}

class RResultSerializer<T, E>(
    private val tSerializer: KSerializer<T>,
    private val eSerializer: KSerializer<E>
) : KSerializer<RResult<T, E>> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("RResult") {
            element("Ok", tSerializer.descriptor, isOptional = true)
            element("Err", eSerializer.descriptor, isOptional = true)
        }

    override fun serialize(encoder: Encoder, value: RResult<T, E>) {
        val jsonEncoder = encoder as? JsonEncoder ?: error("Only JsonEncoder is supported")
        val jsonElement = when (value) {
            is RResult.Ok -> JsonObject(mapOf("Ok" to jsonEncoder.json.encodeToJsonElement(tSerializer, value.value)))
            is RResult.Err -> JsonObject(mapOf("Err" to jsonEncoder.json.encodeToJsonElement(eSerializer, value.error)))
        }
        jsonEncoder.encodeJsonElement(jsonElement)
    }

    override fun deserialize(decoder: Decoder): RResult<T, E> {
        val jsonDecoder = decoder as? JsonDecoder ?: error("Only JsonDecoder is supported")
        val jsonElement = jsonDecoder.decodeJsonElement()

        require(jsonElement is JsonObject) { "Expected JSON object" }

        return when {
            "Ok" in jsonElement -> RResult.Ok(jsonDecoder.json.decodeFromJsonElement(tSerializer, jsonElement["Ok"]!!))
            "Err" in jsonElement -> RResult.Err(jsonDecoder.json.decodeFromJsonElement(eSerializer, jsonElement["Err"]!!))
            else -> error("Invalid RResult format")
        }
    }
}
