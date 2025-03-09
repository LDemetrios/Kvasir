package org.ldemetrios.kvasir.highlight

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.markup.AttributesFlyweight
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.editor.markup.TextAttributesEffectsBuilder
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.sun.jna.Platform
import org.ldemetrios.kvasir.preview.ui.clip
import java.awt.Color
import java.awt.Font
import java.util.function.BiConsumer

class TextAttributeHelper(val displayName: String, val parent: TextAttributesKey?) {
    val id: String = "KVASIR_" + displayName
        .replace(" ", "_")
        .replace("//", "__")
        .replace("+", "and")
        .uppercase()
    val key: TextAttributesKey = parent?.let { createTextAttributesKey(id, it) } ?: createTextAttributesKey(id)
    val array = arrayOf(key)
    val descriptor = AttributesDescriptor(displayName, key)
}

private val collection = mutableListOf<TextAttributeHelper>()

private fun attribute(
    displayName: String,
    parent: TextAttributesKey? = null,
): TextAttributeHelper {
    val helper = TextAttributeHelper(displayName, parent)
    collection.add(helper)
    return helper
}

val KEYWORD = attribute("Code//Keyword", DefaultLanguageHighlighterColors.KEYWORD)
val KEYWORD_LITERAL = attribute("Code//Keyword Literal", KEYWORD.key)
val OPERATOR = attribute("Code//Operator", DefaultLanguageHighlighterColors.OPERATION_SIGN)
val NUMERIC_LITERAL = attribute("Code//Numeric literal", DefaultLanguageHighlighterColors.NUMBER)
val STRINGS = attribute("Code//String", DefaultLanguageHighlighterColors.STRING)

val COMMENT = attribute("Comment", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
val RAWS = attribute("Raw", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
val LABELS = attribute("Label", DefaultLanguageHighlighterColors.LABEL)

val ESCAPES = attribute("Markup//Escape", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)
val REFERENCES = attribute("Markup//Reference", DefaultLanguageHighlighterColors.LABEL)
val SHORTHANDS = attribute("Markup//Shorthand", DefaultLanguageHighlighterColors.KEYWORD)
val LINKS = attribute("Markup//Link", DefaultLanguageHighlighterColors.HIGHLIGHTED_REFERENCE)

val EMPH = attribute("Markup//Emphasis")
val STRONG = attribute("Markup//Strong")
val HEADING = attribute("Markup//Heading")
val TERM = attribute("Markup//Term")
val MATHS = attribute("Math//Math", DefaultLanguageHighlighterColors.STRING)

val RAINBOW = (1..12).map {
    attribute("Rainbow//Color $it")
}

val RAINBOW_BACK_WEAK = (1..12).map {
    attribute("Rainbow background weak//Color $it")
}

val RAINBOW_BACK_STRONG = (1..12).map {
    attribute("Rainbow background strong//Color $it")
}

val TEST_KEY = attribute("Test")

operator fun List<TextAttributeHelper>.invoke(i: Int) = this[/*(i * 2)*/ i % size]

val descriptors = collection.map { it.descriptor }.toTypedArray()

fun mergeAttributes(vararg attrs: TextAttributes): TextAttributes? {
    if (attrs.isEmpty()) return null

    if (attrs.size == 1) return attrs[0]

    val result = attrs[0].clone()

    result.fontType = attrs.map { it.fontType }.reduce(::mergeFontType)

    result.foregroundColor =
        mixColors(attrs.mapNotNull { it.foregroundColor }, attrs.size, defaultScheme.defaultForeground)
    result.backgroundColor =
        mixColors(attrs.mapNotNull { it.backgroundColor }, attrs.size, defaultScheme.defaultBackground)

    val builder = TextAttributesEffectsBuilder.create(attrs[0])
    attrs.drop(1).fold(builder) { it, next -> it.coverWith(next) }.applyTo(result)
    return result
}

fun mergeAttributesNoMix(vararg attrs: TextAttributes): TextAttributes? {
    if (attrs.isEmpty()) return null
    return attrs.reduce { a, b ->
        val result = a.clone()
        result.fontType = mergeFontType(a.fontType, b.fontType)
        result.foregroundColor = b.foregroundColor ?: a.foregroundColor
        result.backgroundColor = b.backgroundColor ?: a.backgroundColor
        TextAttributesEffectsBuilder.create(a)
            .coverWith(b)
            .applyTo(result)
        result
    }
}

fun mixColors(components: List<Color>, n: Int, default: Color): Color {
    val redDiff = components.sumOf { it.red - default.red }
    val greenDiff = components.sumOf { it.green - default.green }
    val blueDiff = components.sumOf { it.blue - default.blue }
    val n = components.size // Experimental change
    val red = default.red + redDiff // * (n) / (n + 1)
    val green = default.green + greenDiff // * (n) / (n + 1)
    val blue = default.blue + blueDiff // * (n) / (n + 1)
    return Color(red.clip(0, 255), green.clip(0, 255), blue.clip(0, 255))
}

fun mergeFontType(a: Int, b: Int): Int {
    val bold = (a and Font.BOLD) or (b and Font.BOLD)
    val italic = (a and Font.ITALIC) xor (b and Font.ITALIC)
    return bold or italic
}

fun TextAttributesKey.resolve() = defaultScheme.getAttributes(this)!!

class DelegatingAttributes(private val func: () -> TextAttributes) : TextAttributes() {
    override fun getForegroundColor(): Color = func().foregroundColor
    override fun getBackgroundColor(): Color = func().backgroundColor
    override fun getEffectColor(): Color = func().effectColor
    override fun getErrorStripeColor(): Color? = func().errorStripeColor
    override fun getEffectType(): EffectType? = func().effectType
    override fun getFontType(): Int = func().fontType
    override fun forEachAdditionalEffect(consumer: BiConsumer<in EffectType, in Color>) =
        func().forEachAdditionalEffect(consumer)

    override fun forEachEffect(consumer: BiConsumer<in EffectType, in Color>) = func().forEachEffect(consumer)
    override fun getFlyweight(): AttributesFlyweight = func().flyweight
}

val defaultScheme get() = EditorColorsManager.getInstance().globalScheme
