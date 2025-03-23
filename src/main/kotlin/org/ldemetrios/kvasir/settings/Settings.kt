package org.ldemetrios.kvasir.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.panel
import org.jetbrains.annotations.Nls
import javax.swing.JComponent
import javax.swing.JPanel
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bindSelected
import javax.swing.JSpinner
import kotlin.Double
import kotlin.Int

internal class JBTextFieldCell(val component: JBTextField) :
    Parameter<JBTextField, String>(component, { it.text }, { holder, value -> holder.text = value })


internal class JBTextFieldIntCell(val component: JBTextField) :
    Parameter<JBTextField, Int>(component, { it.text.toInt() }, { holder, value -> holder.text = value.toString() })

internal class JBTextFieldDoubleCell(val component: JSpinner) :
    Parameter<JSpinner, Double>(component, { it.value as Double }, { holder, value -> holder.value = value })

internal class JBCheckBoxCell(val component: JBCheckBox) :
    Parameter<JBCheckBox, Boolean>(component, { it.isSelected }, { holder, value -> holder.isSelected = value })

class AppSettingsComponent {
    // TODO I desperately want some reflective sssstuff here...
    internal fun <T : JComponent> Cell<T>.putTo(property: KMutableProperty<T>): Cell<T> {
        property.setter.call(this.component)
        return this
    }

    //    lateinit var scopeParenthesisComponent: JBCheckBox
//    lateinit var scopeCodeBlocksComponent: JBCheckBox
//    lateinit var scopeContentBlocksComponent: JBCheckBox
//    lateinit var scopeDelimitedComponent: JBCheckBox
    lateinit var textWidthComponent: JBTextField
    lateinit var tabSizeComponent: JBTextField
    lateinit var rainbowComponent: JBCheckBox
    lateinit var unclippedScrollingComponent: JBCheckBox
    lateinit var scrollingCoeffComponent: JSpinner
    lateinit var zoomingCoeffComponent: JSpinner
    var panel: JPanel = panel {
//        row {
//            checkBox("Highlight background for parenthesis (expressions, args, arrays, dicts)").putTo(::scopeParenthesisComponent)
//        }
//        row {
//            checkBox("Highlight background for code blocks (Experimental!)").putTo(::scopeCodeBlocksComponent)
//        }
//        row {
//            checkBox("Highlight background for content blocks (Experimental!)").putTo(::scopeContentBlocksComponent)
//        }
//        row {
//            checkBox("Highlight background for delimiter pairs in math").putTo(::scopeDelimitedComponent)
//        }
        row {
            label("Text width (in characters):")
            intTextField(0 until Int.MAX_VALUE, 1).putTo(::textWidthComponent)
        }
        row {
            label("Tabulation size:")
            intTextField(0 until 100, 1).putTo(::tabSizeComponent)
        }
        row {
            checkBox("Rainbowify brackets (applies after document modification)").putTo(::rainbowComponent)
        }
        row {
            checkBox("Allow scrolling beyond document boundaries").putTo(::unclippedScrollingComponent)
        }
        row {
            label("Scrolling speed:")
            spinner((.0..100.0), .1).putTo(::scrollingCoeffComponent)
        }
        row {
            label("Zoom speed:")
            spinner((.0..100.0), .1).putTo(::zoomingCoeffComponent)
        }
    }

    //    var scopeParenthesis by JBCheckBoxCell(scopeParenthesisComponent)
//    var scopeCodeBlocks by JBCheckBoxCell(scopeCodeBlocksComponent)
//    var scopeContentBlocks by JBCheckBoxCell(scopeContentBlocksComponent)
//    var scopeDelimited by JBCheckBoxCell(scopeDelimitedComponent)
    var textWidth: Int by JBTextFieldIntCell(textWidthComponent)
    var tabSize: Int by JBTextFieldIntCell(tabSizeComponent)
    var rainbow: Boolean by JBCheckBoxCell(rainbowComponent)
    var unclippedScrolling: Boolean by JBCheckBoxCell(unclippedScrollingComponent)
    var scrollingCoeff: Double
        get() = (scrollingCoeffComponent.value as Double).also { println("Getting scrolling $it") }
        set(value) {
            scrollingCoeffComponent.value = value.also { println("Setting scrolling $it") }
        }
    var zoomingCoeff: Double by JBTextFieldDoubleCell(zoomingCoeffComponent)
    var state: AppSettingsState
        get() = AppSettingsState(
//            scopeParenthesis,
//            scopeCodeBlocks,
//            scopeContentBlocks,
//            scopeDelimited,
            false, false, false, false,
            textWidth,
            tabSize,
            rainbow,
            unclippedScrolling,
            scrollingCoeff,
            zoomingCoeff,
        )
        set(value) {
//            scopeParenthesis = value.scopeParenthesis
//            scopeCodeBlocks = value.scopeCodeBlocks
//            scopeContentBlocks = value.scopeContentBlocks
//            scopeDelimited = value.scopeDelimited
            textWidth = value.textWidth
            tabSize = value.tabSize
            rainbow = value.rainbow
            unclippedScrolling = value.unclippedScrolling
            scrollingCoeff = value.scrollingCoeff
            zoomingCoeff = value.zoomingCoeff
        }
}

data class AppSettingsState(
    var scopeParenthesis: Boolean = false,
    var scopeCodeBlocks: Boolean = false,
    var scopeContentBlocks: Boolean = false,
    var scopeDelimited: Boolean = false,
    var textWidth: Int = 120,
    var tabSize: Int = 4,
    var rainbow: Boolean = true,
    var unclippedScrolling: Boolean = false,
    var scrollingCoeff: Double = 1.0,
    var zoomingCoeff: Double = 1.0,
)


// ---------------- Boilerplate ----------------

internal open class Parameter<H, T>(val holder: H, val get: (H) -> T, val set: (H, T) -> Unit) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get(holder)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        set(holder, value)
    }
}

@State(name = "org.ldemetrios.kvasir.settings.AppSettings", storages = [Storage("KvasirPluginSettings.xml")])
internal class AppSettings : PersistentStateComponent<AppSettingsState> {
    private var state = AppSettingsState()

    override fun getState(): AppSettingsState {
        return state
    }

    override fun loadState(state: AppSettingsState) {
        this.state = state
    }

    companion object {
        val instance: AppSettings
            get() = ApplicationManager.getApplication().getService<AppSettings>(AppSettings::class.java)
    }
}

internal class AppSettingsConfigurable : Configurable {
    private var component: AppSettingsComponent? = null

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "Kvasir Settings"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return component!!.panel
    }

    override fun createComponent(): JComponent? {
        component = AppSettingsComponent()
        return component!!.panel
    }

    override fun isModified(): Boolean {
        return component!!.state != AppSettings.instance.state
    }

    override fun apply() {
        AppSettings.instance.loadState(component!!.state)
    }

    override fun reset() {
        component!!.state = AppSettings.instance.state
    }

    override fun disposeUIResources() {
        component = null
    }
}
