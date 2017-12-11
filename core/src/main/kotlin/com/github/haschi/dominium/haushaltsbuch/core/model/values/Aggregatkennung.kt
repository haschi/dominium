package com.github.haschi.dominium.haushaltsbuch.core.model.values

import java.util.*

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
