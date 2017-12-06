package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import java.io.IOException

class WährungsbetragDeserialisierer : StdScalarDeserializer<Währungsbetrag>
{
    constructor() : super(Währungsbetrag::class.java)

    constructor(vc: Class<*>) : super(vc)

    @Throws(IOException::class)
    override fun deserialize(
            jsonParser: JsonParser,
            deserializationContext: DeserializationContext): Währungsbetrag
    {

        val betrag = jsonParser.text

        return Währungsbetrag.währungsbetrag(betrag)
    }
}