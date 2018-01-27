package com.github.haschi.haushaltsbuch.infrastruktur.rest

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import java.io.IOException

class WährungsbetragSerialisierer @JvmOverloads constructor(t: Class<Währungsbetrag>? = null)
    : StdSerializer<Währungsbetrag>(t)
{

    @Throws(IOException::class)
    override fun serialize(
            value: Währungsbetrag,
            jgen: JsonGenerator,
            provider: SerializerProvider)
    {
        jgen.writeString(value.toString())
    }
}