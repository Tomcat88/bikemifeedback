package it.introini.bikemifeedback.app

import com.google.inject.Inject
import com.google.inject.Injector
import io.vertx.core.Future
import io.vertx.core.Vertx
import it.introini.bikemifeedback.jobs.BikeMiStationFetchJob
import java.time.Duration


class App @Inject constructor(private val vertx: Vertx,
                                      val injector: Injector) {

    fun startup(startFuture: Future<Void>) {
        startupJobs()
        startFuture.complete()
    }

    private fun startupJobs(): Future<Void> {
        val future = Future.future<Void>()

        vertx.setPeriodic(Duration.ofMinutes(2).toMillis(), injector.getInstance(BikeMiStationFetchJob::class.java))
        return future
    }
}