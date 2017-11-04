package react.googlemaps

import react.RProps
import react.ReactExternalComponentSpec

private val reactGoogleMaps: dynamic = runtime.wrappers.require("react-google-maps")


class GoogleMapProps(
        var defaultZoom: Int? = null,
        var defaultCenter: Any? = null
): RProps()

object GoogleMap: ReactExternalComponentSpec<GoogleMapProps>(reactGoogleMaps.GoogleMap)

class WithGoogleMapProps(
        var containerElement: Any? = null,
        var mapElement: Any? = null
): RProps()

object withGoogleMap: ReactExternalComponentSpec<WithGoogleMapProps>(reactGoogleMaps.withGoogleMap)