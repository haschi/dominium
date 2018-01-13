package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanzkonto
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DisplayName("JSON Serialisierung des Ereignisses Eröffnungsbilanzkonto erstellt")
@JsonTest
class EröffnungsbilanzkontoErstelltTest
{
    @Autowired
    private lateinit var json: JacksonTester<EröffnungsbilanzkontoErstellt>

    @Test
    fun `Serialisierung des Ereignisses prüfen`()
    {
        val ereignis = EröffnungsbilanzkontoErstellt(
                Eröffnungsbilanzkonto(
                        soll = listOf(Buchung("Sollbuchung", 12.00.euro())),
                        haben = listOf(Buchung("Habenbuchung", 14.00.euro()))))

        assertThat(this.json.write(ereignis))
                .isEqualToJson("""{
                |  "eröffnungsbilanzkonto" : {
                |    "soll" : [ {
                |      "buchungstext" : "Sollbuchung",
                |      "betrag" : "12,00 EUR"
                |    } ],
                |    "haben" : [ {
                |      "buchungstext" : "Habenbuchung",
                |      "betrag" : "14,00 EUR"
                |    } ]
                |  }
                |}""".trimMargin())
    }

    @Test
    fun `Deserialisierung des Ereignisses prüfen`()
    {
        val content = """{
                |  "eröffnungsbilanzkonto" : {
                |    "soll" : [ {
                |      "buchungstext" : "Sollbuchung",
                |      "betrag" : "12,00 EUR"
                |    } ],
                |    "haben" : [ {
                |      "buchungstext" : "Habenbuchung",
                |      "betrag" : "14,00 EUR"
                |    } ]
                |  }
                |}""".trimMargin()

        assertThat(json.parse(content))
                .isEqualTo(
                        EröffnungsbilanzkontoErstellt(
                                Eröffnungsbilanzkonto(
                                        soll = listOf(Buchung("Sollbuchung", 12.00.euro())),
                                        haben = listOf(Buchung("Habenbuchung", 14.00.euro())))))
    }
}