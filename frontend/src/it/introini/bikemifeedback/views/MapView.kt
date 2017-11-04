package it.introini.bikemifeedback.views

import it.introini.bikemifeedback.client.BikeMiFeedbackClient
import it.introini.bikemifeedback.client.StationsState
import kotlinx.coroutines.experimental.launch
import kotlinx.html.div
import kotlinx.html.span
import kotlinx.html.style
import react.RProps
import react.RState
import react.ReactComponentSpec
import react.dom.ReactDOMBuilder
import react.dom.ReactDOMComponent
import react.googlemaps.GoogleMap
import react.googlemaps.withGoogleMap
import runtime.wrappers.jsObjectOf

class MapViewProps: RProps()
class MapViewState(var currentState: StationsState? = null): RState
class MapView: ReactDOMComponent<MapViewProps, MapViewState>() {

    companion object: ReactComponentSpec<MapView, MapViewProps, MapViewState>

    init {
        state = MapViewState()
        refreshState()
    }

    private fun refreshState() {
        launch {
            val state = BikeMiFeedbackClient.getCurrentState()
            this.setState {
                currentState = state
            }
        }
    }

    override fun ReactDOMBuilder.render() {
        div {
            span {
                +"Fetched state (${state.currentState?.moment})"
            }
            withGoogleMap {
                containerElement = div {
                    key = "delay"
                    style = jsObjectOf("height" to "400px")
                }
                mapElement = div {
                    key = "delay"
                    style = jsObjectOf("height" to "100%")
                }
                GoogleMap {
                    defaultZoom = 8
                    defaultCenter = jsObjectOf("lat" to 45.464192, "lon" to 9.189974)
                }
            }
        }
    }
}