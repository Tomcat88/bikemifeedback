package it.introini.bikemifeedback.app

import com.google.inject.Inject
import io.vertx.core.Future
import io.vertx.core.Vertx
import it.introini.bikemifeedback.jobs.BikeMiStationFetchJob
import java.time.Duration


class App @Inject constructor(private val vertx: Vertx) {

    fun startup(startFuture: Future<Void>) {
        startupJobs()
        startFuture.complete()
    }

    private fun startupJobs(): Future<Void> {
        val future = Future.future<Void>()

        vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), BikeMiStationFetchJob)
        return future
    }
}