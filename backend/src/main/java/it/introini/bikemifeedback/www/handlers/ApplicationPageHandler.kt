package it.introini.bikemifeedback.www.handlers

import io.netty.handler.codec.http.HttpHeaderNames
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import kotlinx.html.*
import kotlinx.html.stream.createHTML


open class ApplicationPageHandler: Handler<RoutingContext> {
    companion object : ApplicationPageHandler()

    override fun handle(event: RoutingContext) {
        event.response().putHeader(HttpHeaderNames.CONTENT_TYPE, "text/html")
        event.response().end(buildString {
            appendln("<!DOCTYPE html>")
            appendln(createHTML(true).html {
                head {
                    link(rel = "stylesheet", href = "https://fonts.googleapis.com/css?family=Roboto:300,400,500")
                }
                body(classes = "no-margin") {
                    div { id = "app" }
                    script(src = "http://localhost:8084/frontend/frontend.bundle.js")
                }
            }.toString())
        })
    }
}