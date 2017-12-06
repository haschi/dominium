package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.*

class AggregatkennungDeserializer : JsonDeserializer<Aggregatkennung>()
{
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Aggregatkennung
    {
        return Aggregatkennung(UUID.fromString(p.text))
    }
}