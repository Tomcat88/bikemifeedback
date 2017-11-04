package it.introini.bikemifeedback.www.handlers

import com.google.inject.Inject
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import it.introini.bikemifeedback.rigs.stations.StationManager
import it.introini.bikemifeedback.www.Response


open class CurrentStateHandler: Handler<RoutingContext> {

    companion object: CurrentStateHandler()

    @Inject private lateinit var stationManager: StationManager

    override fun handle(event: RoutingContext) {
        val currentState = stationManager.getState()
        if (currentState == null) {
            event.response().statusCode = HttpResponseStatus.OK.code()
            event.response().end(Response.empty().encode())
        } else {
            event.response().statusCode = HttpResponseStatus.OK.code()
            event.response().end(Response.of(currentState).encode())
        }
    }
}