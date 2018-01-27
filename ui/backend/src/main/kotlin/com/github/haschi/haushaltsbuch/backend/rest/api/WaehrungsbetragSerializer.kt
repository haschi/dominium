package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import java.io.IOException

class WaehrungsbetragSerializer @JvmOverloads constructor(t: Class<Währungsbetrag>? = null)
    : StdSerializer<Währungsbetrag>(t)
{

    @Throws(IOException::class)
    override fun serialize(
            value: Währungsbetrag,
            generator: JsonGenerator,
            provider: SerializerProvider)
    {
        generator.writeString(value.toString())
    }
}