package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.io.IOException

class AggregatkennungSerialisierer : JsonSerializer<Aggregatkennung>() {

    @Throws(IOException::class)
    override fun serialize(
            value: Aggregatkennung,
            jgen: JsonGenerator,
            provider: SerializerProvider) {
        jgen.writeString(value.id.toString())
    }
}