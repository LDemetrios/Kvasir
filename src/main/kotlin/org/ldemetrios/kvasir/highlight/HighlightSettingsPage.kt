package org.ldemetrios.kvasir.highlight

import com.intellij.codeInsight.template.impl.MacroTokenType.STRING_LITERAL
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import org.ldemetrios.kvasir.language.T_ICON
import org.ldemetrios.tyko.compiler.SyntaxMode
import javax.swing.Icon

class HighlightSettingsPage : HighlightSettingsPageCommon(SyntaxMode.Markup)

open class HighlightSettingsPageCommon(val mode: SyntaxMode) : ColorSettingsPage {
    override fun getIcon(): Icon {
        return T_ICON
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return TypstLexicalHighlighter(mode)
    }

    override fun getDemoText(): String {
        return """
        This <strong>*is*</strong> just <em>_formatting_</em>. It's <strong>*</strong><emst>_composable_</emst><strong>*</strong>.
        
        <h>= Headings</h>
        
        <t>/ Term:</t> this is.

        (Unfortunately, color settings page doesn't support custom effects mixing)
        
        There are <label><labels></label> and <reference>@references</reference>. Shorthands are highlighted <shorthand>---</shorthand> like this.
        
        <rc8>-</rc8> List
            <rc4>-</rc4> markers,
          <rc4>-</rc4> as well as
            <rc11>+</rc11> enum
          <rc4>+</rc4> markers,
            <rc11>+</rc11> are highlighted
              <rc7>+</rc7> based on their level
        
        <kw>#let</kw> f = <num>1</num>
        <rc7>#[</rc7>The color of the hashes depends on the context<rc7>];</rc7> and so is the color of the semicolons.
        
        // These are comments.
        /*
            These are as well.
        */
        
        Rainbowifying can be disabled in plugin's settings.
        
        (This demonstrates the colors, not the highlighting.)
        <rc1>1</rc1> <rc2>2</rc2> <rc3>3</rc3> <rc4>4</rc4> <rc5>5</rc5> <rc6>6</rc6> <rc7>7</rc7> <rc8>8</rc8> <rc9>9</rc9> <rc10>10</rc10> <rc11>11</rc11> <rc12>12</rc12>
        
        Кириллица тоже прекрасно работает!
        <kw>#</kw>let и-юникод-идентификаторы = [seem to work as well ]

        <kw>#</kw>for i in range<rc7>(</rc7><num>5</num><rc7>)</rc7> <rc3>{</rc3>
            for x in range<rc9>(</rc9>i<rc9>)</rc9> <rc5>{</rc5>
                for y in range<rc11>(</rc11>x<rc11>)</rc11> <rc7>{</rc7>
                    <rc2>[</rc2>1<rc2>]</rc2>
                <rc7>}</rc7>
            <rc5>}</rc5>
        <rc3>}</rc3>
        
        <string>#"string with <escape>\n</escape> escapes"</string>, links: <link>https://typst.app/</link>
        
        <math>${'$'} A_n^d m a t h${'$'}</math>
        """.trimIndent()
    }

    fun mix(vararg keys: TextAttributeHelper) = TextAttributesKey.createTextAttributesKey(
        "tmp${System.nanoTime()}",
        DelegatingAttributes {
            mergeAttributes(*keys.reversed().map { it.key.resolve() }.toTypedArray())!!
        }
    )

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> {
        return mapOf(
            "strong" to STRONG.key,
            "em" to EMPH.key,
//            "emst" to mix(STRONG, EMPH),
            "h" to HEADING.key,
//            "emh" to mix(EMPH, HEADING),
//            "emsth" to mix(EMPH, STRONG, HEADING),
            "t" to TERM.key,
//            "emt" to mix(EMPH, TERM),
//            "stt" to mix(STRONG, TERM),
//            "emstt" to mix(TERM, EMPH, STRONG),
            "label" to LABELS.key,
            "reference" to REFERENCES.key,
            "shorthand" to SHORTHANDS.key,
            "kw" to KEYWORD.key,
            "math" to MATHS.key,
            "num" to NUMERIC_LITERAL.key,
            "string" to STRINGS.key,
            "escape" to ESCAPES.key,
            "link" to LINKS.key
        ) + (1..12).associate { "rc$it" to RAINBOW[it - 1].key }
    }

    override fun getAttributeDescriptors(): Array<out AttributesDescriptor> {
        return descriptors
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "Typst"
    }
}