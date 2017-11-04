package it.introini.bikemifeedback.client

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.await
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import kotlin.browser.window
import kotlin.js.json

val baseApi = "/bikemifeedback/api/v1"

open class BikeMiFeedbackClient {
    companion object: BikeMiFeedbackClient()

    suspend fun getCurrentState(): StationsState? {
        return async {
            getAndParseResult("$baseApi/stations/current", null, null, this::parseStationsState)
        }.await()
    }

    private fun parseStationsState(json: dynamic): StationsState? {
        if (json == null) return null
        val stationsArray: Array<dynamic> = json.stations
        return StationsState(
                json.id,
                json.moment,
                stationsArray.mapNotNull(this::parseStatsion)
        )
    }

    private fun parseStatsion(json: dynamic): Station? {
        if (json == null) return null

        return Station(
                json.name,
                GeoPoint(json.lat, json.lon),
                json.code,
                json.bikes,
                json.ebikes,
                json.racks
        )
    }

}

suspend fun <T> getAndParseResult(url: String, auth: Pair<String, String>?, body: dynamic, parse: (dynamic) -> T): T =
        requestAndParseResult("GET", url, auth, body, parse)


suspend fun <T> requestAndParseResult(method: String, url: String, auth: Pair<String, String>?, body: dynamic, parse: (dynamic) -> T): T {
    val headers = arrayOf(
            auth,
            "Accept" to "application/json"
    ).filterNotNull()
     .toTypedArray()
    val response = window.fetch(url, object: RequestInit {
        override var method: String? = method
        override var body: dynamic = body
        override var credentials: RequestCredentials? = "same-origin".asDynamic()
        override var headers: dynamic = json(*headers)
    }).await()
    val json = response.json().await()
    console.info("response from $method $url", json)
    return parse(json)
}
