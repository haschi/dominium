package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

import java.io.IOException

class AggregatkennungSerialisierer @JvmOverloads constructor(t: Class<Aggregatkennung>? = null) : StdSerializer<Aggregatkennung>(t) {

    @Throws(IOException::class)
    override fun serialize(
            value: Aggregatkennung,
            jgen: JsonGenerator,
            provider: SerializerProvider) {
        jgen.writeString(value.id.toString())
    }
}
