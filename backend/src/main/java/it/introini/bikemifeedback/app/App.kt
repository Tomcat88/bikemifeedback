package it.introini.bikemifeedback.app

import com.google.inject.Inject
import com.google.inject.Injector
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import it.introini.bikemifeedback.config.Config
import it.introini.bikemifeedback.config.Parameter
import it.introini.bikemifeedback.config.Parameter.*
import it.introini.bikemifeedback.jobs.BikeMiStationFetchJob
import it.introini.bikemifeedback.www.RouteBinder
import org.pmw.tinylog.Logger
import java.time.Duration


class App @Inject constructor( private val vertx: Vertx,
                               private val injector: Injector,
                               private val routeBinder: RouteBinder,
                               private val router: Router,
                               private val config: Config ) {

    fun startup(startFuture: Future<Void>) {
        startupJobs().compose { startupWeb() }.setHandler {
            if (it.succeeded()) {
                startFuture.complete()
            } else {
                startFuture.fail(it.cause())
            }
        }
    }

    private fun startupJobs(): Future<Void> {
        vertx.setPeriodic(Duration.ofMinutes(2).toMillis(), injector.getInstance(BikeMiStationFetchJob::class.java))
        return Future.succeededFuture()
    }

    private fun startupWeb(): Future<Void> {
        val future = Future.future<Void>()
        val port = config.getInt(HTTP_PORT)
        val httpServer = vertx.createHttpServer()
        httpServer.requestHandler{ router.accept(it) }
        httpServer.listen(port) {
            if (it.succeeded()) {
                routeBinder.bindRoutes()
                Logger.info("Http server up! listening on port $port")
                future.complete()
            } else {
                Logger.error("Something went wrong while starting http server: ${it.cause().message}",it.cause())
                future.fail(it.cause())
            }
        }
        return future
    }
}