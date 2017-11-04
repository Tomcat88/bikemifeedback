package it.introini.bikemifeedback.www

import it.introini.bikemifeedback.rigs.stations.Station
import it.introini.bikemifeedback.rigs.stations.StationsState
import it.introini.bikemifeedback.utils.Encodable
import java.time.Instant


class Response( val moment: Instant,
                val stations: Collection<Station>): Encodable {
    companion object {
        fun of(state: StationsState) = Response(state.moment, state.stations)
        fun empty() = Response(Instant.now(), emptyList())
    }

}