//package org.ldemetrios.kvasir.formatting
//
//import com.intellij.application.options.CodeStyleAbstractConfigurable
//import com.intellij.application.options.CodeStyleAbstractPanel
//import com.intellij.application.options.TabbedLanguageCodeStylePanel
//import com.intellij.psi.codeStyle.*
//import org.ldemetrios.kvasir.language.TypstLanguage
//
//
//class TypstCodeStyleSettings(settings: CodeStyleSettings) : CustomCodeStyleSettings("TypstCodeStyleSettings", settings)
//
//internal class TypstCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
//    override fun createCustomSettings(settings: CodeStyleSettings) = TypstCodeStyleSettings(settings)
//
//    override fun getConfigurableDisplayName(): String = "Typst"
//
//    override fun createConfigurable(
//        settings: CodeStyleSettings,
//        modelSettings: CodeStyleSettings
//    ): CodeStyleConfigurable {
//        return object : CodeStyleAbstractConfigurable(settings, modelSettings, this.configurableDisplayName) {
//            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
//                return SimpleCodeStyleMainPanel(currentSettings, settings)
//            }
//        }
//    }
//
//    private class SimpleCodeStyleMainPanel(currentSettings: CodeStyleSettings?, settings: CodeStyleSettings) :
//        TabbedLanguageCodeStylePanel(TypstLanguage.INSTANCE, currentSettings, settings)
//}
//
//internal class TypstLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
//    override fun getLanguage() = TypstLanguage.INSTANCE
//
//    override fun customizeSettings(
//        consumer: CodeStyleSettingsCustomizable,
//        settingsType: SettingsType
//    ) {
//        if (settingsType == SettingsType.INDENT_SETTINGS) {
//            consumer.showStandardOptions(
//                CodeStyleSettingsCustomizable.IndentOption.TAB_SIZE.name,
//                CodeStyleSettingsCustomizable.IndentOption.INDENT_SIZE.name,
//                CodeStyleSettingsCustomizable.IndentOption.CONTINUATION_INDENT_SIZE.name,
//            )
//        }
//
//        if (settingsType == SettingsType.WRAPPING_AND_BRACES_SETTINGS) {
//            consumer.showStandardOptions(
//                CodeStyleSettingsCustomizable.WrappingOrBraceOption.RIGHT_MARGIN.name
//            )
//        }
//    }
//
//    override fun getCodeSample(settingsType: SettingsType): String {
//        return """
//        #{
//            set text(fill:blue) if 1 + 2 == 3
//            f(long, set, of, arguments, that, apparently, needs, to, be, separated).at(least, I, think, so).You(think, thats, it, but, I, have, not, finished, yet)
//        }
//
//        Skip skríða á kjarkinum unnirnar\
//        Eyðifjǫll til vinjann’ þrifsamra\
//        Hver sólaruppras er nýtt upphaf\
//        Rís ok rík\
//        """.trimIndent()
//    }
//}