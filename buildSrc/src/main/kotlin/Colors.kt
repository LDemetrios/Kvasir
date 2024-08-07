package org.ldemetrios.kvasir.build

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import java.awt.Color
import java.awt.Font
import java.io.File

const val variants = 12

fun StringBuilder.rainbowList(
    name: String,
    fontType: Int,
    colorWhere: String,
    saturation: Double,
    brightness: Double,
) {
    for (i in 0 until variants) {
        val clr = Color.getHSBColor(1.0f / variants * i, saturation.toFloat(), brightness.toFloat())
        val hex = clr.rgb.let { it and 0xffffff }.toString(16).run { "0".repeat(6 - length) + this }
        append(
            """
        |    <option name="${name}_${i + 1}">
        |        <value>
        |            <option name="$colorWhere" value="$hex"/>
        |            <option name="FONT_TYPE" value="$fontType"/>
        |        </value>
        |    </option>
        |
        """.trimMargin()
        )
    }
}

fun main(){
    writeColorsXml(
        File("resources/commons.json"),
        File("resources/high-contrast.json"),
        File("src/main/resources/kvasir-color-schemes/high-contrast-test.xml")
    )
}

fun writeColorsXml(commonJson: File, specificJson: File, outputXml: File) {
    val sb = StringBuilder()

    sb.append("<?xml version='1.0'?>\n<list>\n")

    val common = JsonParser.parseString(commonJson.readText()) as JsonObject
    val specific = JsonParser.parseString(specificJson.readText()) as JsonObject

    fun JsonObject.walk(path: List<String>): JsonElement? {
        var result: JsonElement? = this
        for (key in path) {
            result = when (result) {
                is JsonObject -> result.get(key)
                is JsonArray -> result.get(key.toInt())
                else -> null
            }
        }
        return result
    }

    fun key(path: String): JsonElement? {
        var result: JsonElement? = JsonPrimitive("$" + path)
        while (
            result.run {
                this is JsonPrimitive &&
                        this.isString &&
                        this.asString.startsWith("$") &&
                        this.asString.length > 1
            }
        ) {
            val address = (result as JsonPrimitive).asString.drop(1).split(".")

            result = specific.walk(address) ?: common.walk(address)
        }
        return result
    }

    fun option(address: String) {
        sb.append(
            """
            |    <option name="KVASIR_${
                address
                    .replace("-", "_")
                    .replace(".", "__")
                    .uppercase()
            }">
            |        <value>
            |
        """.trimMargin()
        )

        fun part(p: String) {
            key("$address.$p")?.run {
                val format = when (this) {
                    is JsonPrimitive -> when {
                        isString -> asString
                        isNumber -> asNumber
                        isBoolean -> asBoolean
                        else -> throw AssertionError()
                    }
                    else -> throw AssertionError()
                }.run { "\"$this\"" }
                sb.append("            <option name=\"")
                sb.append(p.uppercase().replace("-", "_"))
                sb.append("\" value=$format/>\n")
            }
        }
        part("foreground")
        part("background")
        part("effect-type")
        part("effect-color")
        part("font-type")

        sb.append(
            """
            |        </value>
            |    </option>
            |
            """.trimMargin()
        )
    }

    listOf(
        "raw",
        "markup.reference",
        "label",
        "markup.link",
        "markup.emphasis",
        "markup.strong",
        "markup.emphasis-and-strong",
        "markup.heading",
        "markup.emphasis-and-heading",
        "markup.strong-and-heading",
        "markup.emphasis-and-strong-and-heading",
        "markup.term",
        "markup.emphasis-and-term",
        "markup.strong-and-term",
        "markup.emphasis-and-strong-and-term",
    ).forEach(::option)


    sb.rainbowList(
        "KVASIR_RAINBOW__COLOR", // 0.9, 1.0
        Font.BOLD, "FOREGROUND",
        key("rainbow.foreground-saturation")!!.asNumber.toDouble(),
        key("rainbow.foreground-brightness")!!.asNumber.toDouble(),
    )
    sb.rainbowList(
        // 1.0, 1.0
        "KVASIR_RAINBOW_BACKGROUND_STRONG__COLOR",
        Font.PLAIN,
        "BACKGROUND",
        key("rainbow.background-strong-saturation")!!.asNumber.toDouble(),
        key("rainbow.background-strong-brightness")!!.asNumber.toDouble(),
    )
    sb.rainbowList(
        // 1.0, 0.25
        "KVASIR_RAINBOW_BACKGROUND_WEAK__COLOR",
        Font.PLAIN,
        "BACKGROUND",
        key("rainbow.background-weak-saturation")!!.asNumber.toDouble(),
        key("rainbow.background-weak-brightness")!!.asNumber.toDouble(),
    )
    sb.append("</list>\n")

    outputXml.writeText(sb.toString())
}