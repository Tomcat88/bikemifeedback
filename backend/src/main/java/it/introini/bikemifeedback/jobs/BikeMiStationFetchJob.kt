package it.introini.bikemifeedback.jobs

import io.vertx.core.Handler
import io.vertx.core.impl.StringEscapeUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.pmw.tinylog.Logger
import java.nio.charset.Charset


open class BikeMiStationFetchJob: Handler<Long> {
    companion object: BikeMiStationFetchJob() {
        const val BIKE_STATION_URL = "https://www.bikemi.com/it/mappa-stazioni.aspx"
        const val MARKER_REGEX = "GoogleMap.addMarker\\((.*)\\);"
    }


    override fun handle(event: Long) {
        val document = Jsoup.connect(BIKE_STATION_URL)
                            .timeout(5000)
                            .userAgent("Mozilla")
                            .get()
        val stations = document.select("script").filter {
            it.html().startsWith ("GoogleMap.create('station-map', '');")
        }.map { it.html().split("\r\n").map { it.trim() } }.firstOrNull() ?: emptyList()

        val regex = Regex(MARKER_REGEX)
        val matches = stations.mapNotNull { regex.find(it)?.groups?.get(1)?.value }
                              .map        { it.split(",").map { it.replace("'", "").replace("\r\n", "").trim() } }
                              .mapIndexed { i, split -> StationIn(StringEscapeUtils.unescapeJava(split[0]), split[1].toDouble(), split[2].toDouble(), StringEscapeUtils.unescapeJava(split[3]), Jsoup.parse(StringEscapeUtils.unescapeJava(split[4])), i + 1) }
        matches.forEach {
            Logger.info("Stazione: ${it.number} -- ${it.name}, Bici: ${it.getAvailableBikes()}, Bici elettriche: ${it.getAvailableEBikes()}, Stalli: ${it.getAvailableRacks()}")
        }
    }
}

data class StationIn(val iconPath:String, val lat: Double, val lon: Double, val name: String, val htmlPopUp: Document, val number: Int) {

    fun getAvailableBikes():  Int = htmlPopUp.select("tbody>tr")[0].select("td")[1].text()?.toInt() ?: 0
    fun getAvailableEBikes(): Int = htmlPopUp.select("tbody>tr")[1].select("td")[1].text()?.toInt() ?: 0
    fun getAvailableRacks():  Int = htmlPopUp.select("tbody>tr")[0].select("td")[2].text()?.toInt() ?: 0

}