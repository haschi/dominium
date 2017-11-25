package org.github.haschi.haushaltsbuch.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(`as` = Reinvermögen::class)
@JsonDeserialize(`as` = Reinvermögen::class)
data class Reinvermögen(
        @JsonProperty(value = "summeDesVermoegens")
        val summeDesVermögens: Währungsbetrag,

        @JsonProperty(value = "summeDerSchulden")
        val summeDerSchulden: Währungsbetrag) {
    val reinvermögen: Währungsbetrag
        get() = Währungsbetrag(summeDesVermögens.wert.subtract(summeDerSchulden.wert))

}
