package it.introini.bikemifeedback.rigs.stations

import com.fasterxml.jackson.annotation.JsonProperty
import it.introini.bikemifeedback.utils.Encodable
import java.time.Instant
import java.util.*


data class StationsState(@JsonProperty val id: String,
                         @JsonProperty val moment: Instant,
                         @JsonProperty val stations: Collection<Station> ): Encodable {
    companion object {
        fun now(stations: Collection<Station>): StationsState = StationsState(UUID.randomUUID().toString(), Instant.now(), stations)
    }
}