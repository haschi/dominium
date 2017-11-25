package org.github.haschi.haushaltsbuch.api

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

import java.io.IOException

class WährungsbetragSerialisierer @JvmOverloads constructor(t: Class<Währungsbetrag>? = null) : StdSerializer<Währungsbetrag>(t) {

    @Throws(IOException::class)
    override fun serialize(
            value: Währungsbetrag,
            jgen: JsonGenerator,
            provider: SerializerProvider) {
        jgen.writeString(value.toString())
    }
}
