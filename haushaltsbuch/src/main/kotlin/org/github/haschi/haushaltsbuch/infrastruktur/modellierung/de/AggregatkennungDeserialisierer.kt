package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de

import java.io.IOException
import java.util.UUID

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer

class AggregatkennungDeserialisierer : StdScalarDeserializer<Aggregatkennung> {
    constructor() : super(Aggregatkennung::class.java) {

    }

    constructor(vc: Class<*>) : super(vc) {}

    @Throws(IOException::class)
    override fun deserialize(
            jsonParser: JsonParser,
            deserializationContext: DeserializationContext): Aggregatkennung {

        val uuid = jsonParser.text

        return Aggregatkennung(UUID.fromString(uuid))
    }
}