package org.ldemetrios.kvasir.highlight

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor

fun String.escapeForID() = this
    .replace(" ", "_")
    .replace("//", "__")
    .replace("+", "and")
    .uppercase()

class TextAttributeHelper(
    val displayName: String,
    val parent: TextAttributesKey?,
    val tag: String
) {
    val id: String = "KVASIR_" + displayName.escapeForID()
    val key = parent?.let { createTextAttributesKey(id, it) } ?: createTextAttributesKey(id)
    val array = arrayOf(key)
    val descriptor = AttributesDescriptor(displayName, key)
}

private val collection = mutableListOf<TextAttributeHelper>()
val additional = mutableMapOf<String, TextAttributesKey>()

private fun attribute(
    displayName: String,
    parent: TextAttributesKey,
    tag: String = displayName.split("//").last().replace(" ", "_").lowercase()
): TextAttributeHelper {
    val helper = TextAttributeHelper(displayName, parent, tag)
    collection.add(helper)
    additional[tag] = helper.key
    return helper
}

private fun attribute(
    displayName: String,
    tag: String = displayName.split("//").last().replace(" ", "_").lowercase()
): TextAttributeHelper {
    val helper = TextAttributeHelper(displayName, null, tag)
    collection.add(helper)
    additional[tag] = helper.key
    return helper
}

val KEYWORD = attribute("Code//Keyword", DefaultLanguageHighlighterColors.KEYWORD, "kw")
val OPERATOR = attribute("Code//Operator", DefaultLanguageHighlighterColors.OPERATION_SIGN, "op")
val NUMERIC_LITERAL = attribute("Code//Numeric literal", DefaultLanguageHighlighterColors.NUMBER, "num")
val STRINGS = attribute("Code//String", DefaultLanguageHighlighterColors.STRING)
val MATHS = attribute("Math//Math", DefaultLanguageHighlighterColors.STRING)

val BAD_CHARACTER = attribute("Bad character", HighlighterColors.BAD_CHARACTER, "bad")
val COMMENT = attribute("Comment", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
val RAWS = attribute("Raw", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
val LABELS = attribute("Label", DefaultLanguageHighlighterColors.LABEL)

val ESCAPES = attribute("Markup//Escape", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)
val REFERENCES = attribute("Markup//Reference", DefaultLanguageHighlighterColors.LABEL)
val SHORTHANDS = attribute("Markup//Shorthand", DefaultLanguageHighlighterColors.KEYWORD)
val LINKS = attribute("Markup//Link", DefaultLanguageHighlighterColors.HIGHLIGHTED_REFERENCE)

val EMPH = attribute("Markup//Emphasis", "em")
val STRONG = attribute("Markup//Strong")
val EMPH_STRONG = attribute("Markup//Emphasis + Strong", "emst")
val HEADING = attribute("Markup//Heading", "h")
val EMPH_HEADING = attribute("Markup//Emphasis + Heading", "emh")
val STRONG_HEADING = attribute("Markup//Strong + Heading", "sth")
val EMPH_STRONG_HEADING = attribute("Markup//Emphasis + Strong + Heading", "emsth")
val TERM = attribute("Markup//Term", "t")
val EMPH_TERM = attribute("Markup//Emphasis + Term", "emt")
val STRONG_TERM = attribute("Markup//Strong + Term", "stt")
val EMPH_STRONG_TERM = attribute("Markup//Emphasis + Strong + Term", "emstt")

val RAINBOW = (1..12).map{
    attribute("Rainbow//Color $it", "rc$it")
}

val RAINBOW_BACK_WEAK = (1..12).map {
    attribute("Rainbow background weak//Color $it", "rcbw$it")
}

val RAINBOW_BACK_STRONG = (1..12).map{
    attribute("Rainbow background strong//Color $it", "rcbs$it")
}

val TEST_KEY = attribute("Test")

operator fun List<TextAttributeHelper>.invoke(i: Int) = this[/*(i * 2)*/ i % size]

val descriptors = collection.map { it.descriptor }.toTypedArray()
