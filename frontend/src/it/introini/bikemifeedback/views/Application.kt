package it.introini.bikemifeedback.views

import kotlinx.html.div
import react.RState
import react.ReactComponentEmptyProps
import react.ReactComponentSpec
import react.dom.ReactDOM
import react.dom.ReactDOMBuilder
import react.dom.ReactDOMComponent
import react.dom.render
import react.materialui.MaterialUiMuiThemeProvider
import kotlin.browser.document
import kotlin.browser.window


fun main(args: Array<String>) {
    runtime.wrappers.require("bikemifeedback.css")
    val injectTapEventPlugin = runtime.wrappers.require("react-tap-event-plugin")
    injectTapEventPlugin()
    ReactDOM.render(document.getElementById("app")) {
        div {
            MaterialUiMuiThemeProvider {
                Application {}
            }
        }
    }
}

class ApplicationState(var view: View): RState

class Application: ReactDOMComponent<ReactComponentEmptyProps, ApplicationState>() {

    companion object: ReactComponentSpec<Application, ReactComponentEmptyProps, ApplicationState>

    init {
        state = ApplicationState(View.Map)
        changeUrl(View.Map)
    }

    private fun changeUrl(view: View) {
        window.history.pushState(null, "BikeMI feeback", "/bmf${view.path}")
    }


    override fun ReactDOMBuilder.render() {
        div("content") {
            when (state.view) {
                View.Map -> MapView {}
            }
        }
    }
}

enum class View(val path: String) {
    Map("/map");

    companion object {
        fun parse(path: String): View? = View.values().find { it.path == path }
    }
}
