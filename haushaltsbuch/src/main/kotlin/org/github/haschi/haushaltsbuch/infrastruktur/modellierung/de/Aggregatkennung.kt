package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import java.util.UUID

@JsonSerialize(using = AggregatkennungSerialisierer::class)
@JsonDeserialize(using = AggregatkennungDeserialisierer::class)
data class Aggregatkennung(val id: UUID) {

    companion object {
        fun neu(): Aggregatkennung {
            return Aggregatkennung(UUID.randomUUID())
        }

        fun aus(id: String): Aggregatkennung {
            return Aggregatkennung(UUID.fromString(id))
        }
    }
}
