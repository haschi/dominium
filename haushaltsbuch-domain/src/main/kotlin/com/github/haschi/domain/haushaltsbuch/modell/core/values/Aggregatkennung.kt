package com.github.haschi.domain.haushaltsbuch.modell.core.values

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import java.io.IOException

import java.util.UUID

data class Aggregatkennung(val id: UUID) {

    companion object {
        fun neu(): Aggregatkennung
        {
            return Aggregatkennung(UUID.randomUUID())
        }

        fun aus(id: String): Aggregatkennung
        {
            return Aggregatkennung(UUID.fromString(id))
        }

        val nil: Aggregatkennung = Aggregatkennung(UUID(0,0))
    }
}
