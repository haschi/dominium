package com.github.haschi.haushaltsbuch.infrastruktur.rest

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import java.io.IOException

class AggregatkennungSerializer : JsonSerializer<Aggregatkennung>()
{

    @Throws(IOException::class)
    override fun serialize(
            value: Aggregatkennung,
            generator: JsonGenerator,
            provider: SerializerProvider)
    {
        generator.writeString(value.id.toString())
    }
}