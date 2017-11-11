package it.introini.bikemifeedback.utils

import io.vertx.core.json.JsonObject


data class GeoPoint(val lat: Double, val lng: Double)

interface Encodable {
    fun toJson(): JsonObject = JsonObject.mapFrom(this)
    fun encode(): String = toJson().encode()
}
