package it.introini.bikemifeedback.rigs.stations

import io.vertx.core.json.JsonObject
import it.introini.bikemifeedback.utils.GeoPoint
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.Document
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.DocumentCodec
import org.bson.codecs.EncoderContext


open class StationsStateCodec: Codec<StationsState> {

    companion object: StationsStateCodec()

    override fun encode(writer: BsonWriter, value: StationsState, encoderContext: EncoderContext) {
        DocumentCodec().encode(writer, Document(value.toJson().map), encoderContext)
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): StationsState {
        val document = DocumentCodec().decode(reader, decoderContext).toJson().let { JsonObject(it) }
        val id = document.getString("id")
        val moment = document.getInstant("moment")
        val array = document.getJsonArray("stations")
        val stations = (0 until array.size())
                .map { array.getJsonObject(it) }
                .map {
                    Station( it.getString("name"),
                            GeoPoint( it.getJsonObject("position").getDouble("lat"),
                                      it.getJsonObject("position").getDouble("lon") ),
                            it.getInteger("code"),
                            it.getInteger("bikes"),
                            it.getInteger("ebikes"),
                            it.getInteger("racks") )
                }
        return StationsState(id, moment, stations)
    }

    override fun getEncoderClass(): Class<StationsState> {
        return StationsState::class.java
    }
}