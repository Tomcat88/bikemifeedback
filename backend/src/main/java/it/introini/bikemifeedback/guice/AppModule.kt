package it.introini.bikemifeedback.guice

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.client.MongoDatabase
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import it.introini.bikemifeedback.config.Config
import it.introini.bikemifeedback.config.Parameter
import it.introini.bikemifeedback.rigs.stations.StationsStateCodec
import org.bson.codecs.configuration.CodecRegistries

open class AppModule : AbstractModule() {
    companion object: AppModule()
    override fun configure() { /* NOP */ }

    @Provides @Singleton fun vertx() = Vertx.currentContext().owner()
    @Provides @Singleton @Inject fun router(vertx: Vertx) = Router.router(vertx)

    @Singleton @Provides @Inject fun mongoClient(config: Config): MongoClient {
        val host = config.getString(Parameter.MONGO_DB_HOST)
        val port = config.getInt(Parameter.MONGO_DB_PORT)
        val options = MongoClientOptions.builder()
                                        .codecRegistry(CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                                                       CodecRegistries.fromCodecs(StationsStateCodec)))
                                        .build()
        return MongoClient("$host:$port", options)
    }

    @Singleton @Provides @Inject fun mongoDb(config: Config, mongoClient: MongoClient): MongoDatabase {
        return mongoClient.getDatabase(config.getString(Parameter.MONGO_DB_NAME))
    }


}