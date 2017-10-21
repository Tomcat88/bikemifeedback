package it.introini.bikemifeedback.rigs.stations

import com.fasterxml.jackson.annotation.JsonProperty
import it.introini.bikemifeedback.jobs.StationIn
import it.introini.bikemifeedback.utils.Encodable
import it.introini.bikemifeedback.utils.GeoPoint


data class Station(@JsonProperty val name:     String,
                   @JsonProperty val position: GeoPoint,
                   @JsonProperty val code:     Int,
                   @JsonProperty val bikes:    Int,
                   @JsonProperty val ebikes:   Int,
                   @JsonProperty val racks:    Int      ): Encodable {

    constructor(stationIn: StationIn) : this( stationIn.name,
                                              GeoPoint(stationIn.lat, stationIn.lon),
                                              stationIn.number,
                                              stationIn.getAvailableBikes(),
                                              stationIn.getAvailableEBikes(),
                                              stationIn.getAvailableRacks() )
}