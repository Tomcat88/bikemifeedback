package it.introini.bikemifeedback.rigs.stations.impl

import com.google.inject.Inject
import com.mongodb.client.MongoDatabase
import it.introini.bikemifeedback.rigs.stations.Station
import it.introini.bikemifeedback.rigs.stations.StationManager
import it.introini.bikemifeedback.rigs.stations.StationsState
import org.bson.Document


class StationManagerImpl @Inject constructor(mongoDb: MongoDatabase): StationManager {

    private val currentState = mongoDb.getCollection("current_states", StationsState::class.java)
    private val olderStates = mongoDb.getCollection("older_states", StationsState::class.java)

    override fun addState(stations: Collection<Station>) {
        getState()?.let { olderStates.insertOne(it) }
        currentState.deleteOne(Document())
        currentState.insertOne(StationsState.now(stations))
    }

    override fun getState(): StationsState? {
        return currentState.find().first()
    }

}