package it.introini.bikemifeedback.www

import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpMethod.GET
import io.vertx.ext.web.RoutingContext
import it.introini.bikemifeedback.www.handlers.CurrentStateHandler


enum class Route(val method: HttpMethod, val endpoint: String, val handler: Class<out Handler<RoutingContext>>) {
    CURRENT_STATE(GET, "/stations/current", CurrentStateHandler::class.java)
}