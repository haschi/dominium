package org.github.haschi.haushaltsbuch.modell.core.values

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException

import java.util.UUID

@JsonSerialize(using = Aggregatkennung.AggregatkennungSerialisierer::class)
@JsonDeserialize(using = Aggregatkennung.AggregatkennungDeserialisierer::class)
data class Aggregatkennung(val id: UUID) {

    class AggregatkennungDeserialisierer : StdScalarDeserializer<Aggregatkennung>
    {
        constructor() : super(Aggregatkennung::class.java)

        constructor(vc: Class<*>) : super(vc)

        @Throws(IOException::class)
        override fun deserialize(
                jsonParser: JsonParser,
                deserializationContext: DeserializationContext): Aggregatkennung
        {

            val uuid = jsonParser.text

            return Aggregatkennung(UUID.fromString(uuid))
        }
    }

    class AggregatkennungSerialisierer @JvmOverloads constructor(t: Class<Aggregatkennung>? = null) : StdSerializer<Aggregatkennung>(t) {

        @Throws(IOException::class)
        override fun serialize(
                value: Aggregatkennung,
                jgen: JsonGenerator,
                provider: SerializerProvider) {
            jgen.writeString(value.id.toString())
        }
    }

    companion object {
        fun neu(): Aggregatkennung
        {
            return Aggregatkennung(UUID.randomUUID())
        }

        fun aus(id: String): Aggregatkennung
        {
            return Aggregatkennung(UUID.fromString(id))
        }
    }
}
