package it.introini.bikemifeedback.rigs.stations

import com.google.inject.ImplementedBy
import it.introini.bikemifeedback.rigs.stations.impl.StationManagerImpl


@ImplementedBy(StationManagerImpl::class)
interface StationManager {
    fun addState(stations: Collection<Station>)
    fun getState(): StationsState?
}