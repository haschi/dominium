package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import java.util.UUID

class AggregatkennungDeserializer : JsonDeserializer<Aggregatkennung>()
{
    override fun deserialize(p: JsonParser, context: DeserializationContext)
            : Aggregatkennung
    {
        return Aggregatkennung(UUID.fromString(p.text))
    }
}