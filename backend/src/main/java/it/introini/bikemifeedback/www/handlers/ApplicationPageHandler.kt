package it.introini.bikemifeedback.www.handlers

import io.vertx.core.Handler
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.impl.Utils


open class ApplicationPageHandler: Handler<RoutingContext> {
    companion object : ApplicationPageHandler()

    override fun handle(event: RoutingContext) {
        val index = Utils.readResourceToBuffer("index.html")
        event.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/html").end(index)
    }
}