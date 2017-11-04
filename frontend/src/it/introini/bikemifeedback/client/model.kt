package it.introini.bikemifeedback.client


data class StationsState ( val id: String,
                           val moment: String,
                           val stations: Collection<Station> )

data class Station ( val name:     String,
                     val position: GeoPoint,
                     val code:     Int,
                     val bikes:    Int,
                     val ebikes:   Int,
                     val racks:    Int )

data class GeoPoint ( val lat: Double,
                      val lon: Double )