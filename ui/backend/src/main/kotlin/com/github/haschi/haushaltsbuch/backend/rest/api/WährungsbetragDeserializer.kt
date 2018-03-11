package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import java.io.IOException

class WährungsbetragDeserializer
    : StdScalarDeserializer<Währungsbetrag>(Währungsbetrag::class.java)
{
    @Throws(IOException::class)
    override fun deserialize(
            jsonParser: JsonParser,
            deserializationContext: DeserializationContext): Währungsbetrag
    {

        val betrag = jsonParser.text

        return Währungsbetrag.währungsbetrag(betrag)
    }
}