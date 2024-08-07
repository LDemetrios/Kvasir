package org.ldemetrios.kvasir.highlight

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import org.ldemetrios.kvasir.language.TypstFileType.Companion.T_ICON
import javax.swing.Icon


class HighlightSettingsPage : ColorSettingsPage {
    override fun getIcon(): Icon {
        return T_ICON
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return TypstLexicalHighlighter()
    }

    override fun getDemoText(): String {
        return """
        This <strong>*is*</strong> just <em>_formatting_</em>. It's <strong>*</strong><emst>_composable_</emst><strong>*</strong>.

        <h>= Heading </h><emh>_with </emh><emsth>*additional*</emsth><emh> formatting_</emh>

        <t>/ This is term:</t> just go with it.
        <t>/ </t><emt>_Formatted_</emt><t> </t><stt>*term*</stt><t>, if </t><emt>_</emt><emstt>*you*</emstt><emt>_</emt><t> need:</t> also works.

        That's pretty much all with markup, #strike[but there are plans.]

        There are <label><labels></label> and <reference>@references</reference>. Shorthands are highlighted <shorthand>---</shorthand> like this.

        <rc3>-</rc3> Resistance?
            <rc5>-</rc5> From my own warship!
          <rc5>-</rc5> Demon hordes, take flight. And *eviscerate* them!
            <rc7>-</rc7> Whom to root for. The lines have certainly blured.
               <rc9>+</rc9> List markers
             <rc9>+</rc9> are
        <rc3>+</rc3> colored
            <rc5>-</rc5> based on relative indents

        <kw>#</kw>let f = 1
        <rc3>#[</rc3>Color of the hash depends on the context!<rc3>]</rc3>

        // There seems to be no exponential overhead, but be careful
        #<rc3>(</rc3>x: <rc5>(</rc5>x: <rc7>(</rc7>x: <rc9>(</rc9>x: <rc11>(</rc11>x: <rc1>(</rc1>x: <rc3>(</rc3>x: <rc5>(</rc5>x: <rc7>(</rc7>x: <rc9>(</rc9>x: <rc11>(</rc11>x: <rc1>(</rc1>x: <rc3>(</rc3>x: <rc5>(</rc5>x: <rc7>(</rc7>x: <rc9>(</rc9>x: <rc11>(</rc11>x: <rc1>(</rc1>x: <rc3>(</rc3>x: <rc5>(</rc5>x: <rc7>(</rc7>x: <rc9>(</rc9>x: <rc11>(</rc11>x: <rc1>(</rc1>x: <rc3>(</rc3>x: <rc5>(</rc5>x<rc5>) =></rc5> y<rc3>) =></rc3> y<rc1>) =></rc1> y<rc11>) =></rc11> y<rc9>) =></rc9> y<rc7>) =></rc7> y<rc5>) =></rc5> y<rc3>) =></rc3> y<rc1>) =></rc1> y<rc11>) =></rc11> y<rc9>) =></rc9> y<rc7>) =></rc7> y<rc5>) =></rc5> y<rc3>) =></rc3> y<rc1>) =></rc1> y<rc11>) =></rc11> y<rc9>) =></rc9> y<rc7>) =></rc7> y<rc5>) =></rc5> y<rc3>) =></rc3> y<rc1>) =></rc1> y<rc11>) =></rc11> y<rc9>) =></rc9> y<rc7>) =></rc7> y<rc5>) =></rc5> y<rc3>)</rc3>
        /*
            Besides, these are comments
        */

        <kw>#</kw>let f<rc1>(</rc1>stuff<rc1>)</rc1> = not stuff

        Кириллица тоже прекрасно работает!
        <kw>#</kw>let и-юникод-идентификаторы =  <rc3>[</rc3>seem to work as well <rc3>]</rc3>

        لااㅤأستطيعㅤالتحققㅤمماㅤإذاㅤكانㅤيعملㅤبلغاتㅤأخرى.

        #method.call<rc1>[][]</rc1>.chain<rc3>()[]</rc3>.are-colored<rc5>()[][]</rc5>.as-well-as<rc7>[]</rc7>.functions<rc9>()[]</rc9><rc11>()[][]</rc11><rc1>()()[]</rc1><rc3>()</rc3><rc5>()</rc5>

        <kw>#</kw>for i in range<rc1>(</rc1>5<rc1>)</rc1> <rc3>{</rc3>
            for x in range<rc3>(</rc3>i<rc3>)</rc3> <rc5>{</rc5>
                for x in range<rc5>(</rc5>i<rc5>)</rc5> <rc7>{</rc7>
                    1
                <rc7>}</rc7>
            <rc5>}</rc5>
        <rc3>}</rc3>

        <math>${'$'}"There is no" m_u^c(h) support.for math btw, but </math><rc3>#[</rc3>Embedded <em>_expressions_</em> are supported here as well<rc3>]</rc3><math> ${'$'}</math>
        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> {
        return additional
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