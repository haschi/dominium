package com.github.haschi.haushaltsbuch.testing.sandbox

import io.vertx.core.json.JsonObject
import org.assertj.core.api.Assertions.assertThat
import org.github.haschi.haushaltsbuch.api.BeendeInventur
import org.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung
import org.github.haschi.haushaltsbuch.api.BeginneInventur
import org.github.haschi.haushaltsbuch.api.Buchung
import org.github.haschi.haushaltsbuch.api.ErfasseInventar
import org.github.haschi.haushaltsbuch.api.ErfasseSchulden
import org.github.haschi.haushaltsbuch.api.Eröffnungsbilanzkonto
import org.github.haschi.haushaltsbuch.api.EröffnungsbilanzkontoErstellt
import org.github.haschi.haushaltsbuch.api.Inventar
import org.github.haschi.haushaltsbuch.api.LeseInventar
import org.github.haschi.haushaltsbuch.api.Währungsbetrag
import org.github.haschi.haushaltsbuch.api.euro
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import java.util.stream.Stream

@DisplayName("Serialisierbarkeit der Wertobjekte prüfen")
class SerialisierungTest
{
    data class Testfall
    (
        val poko: Any,
        val json: String
    )

    @TestTemplate
    @DisplayName("prüfen, ob POKO korrekt serialisiert wird")
    @ExtendWith(
            SerialisierteAnweisungenAnbieter::class,
            SerialisierteAbfragenAnbieter::class,
            SerialisierteEreignisseAnbieter::class)
    fun serialisierung(testfall: Testfall) {

        assertThat(JsonObject.mapFrom(testfall.poko).encodePrettily())
                .isEqualTo(testfall.json)
    }

    class SerialisierteEreignisseAnbieter : TestfallAnbieter<Testfall>(Testfall::class)
    {
        override fun testfälle(): Stream<Testfall> =
                Stream.of(Testfall(EröffnungsbilanzkontoErstellt(Eröffnungsbilanzkonto(
                        soll = listOf(Buchung("Sollbuchung", 12.00.euro())),
                        haben = listOf(Buchung("Habenbuchung", 14.00.euro())))),
                        """{
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
                            |}""".trimMargin()))

    }

    class SerialisierteAnweisungenAnbieter : TestfallAnbieter<Testfall>(Testfall::class)
    {
        private val id ="2c618058-09b8-4cde-b3fa-edcceadbd908"

        override fun testfälle(): Stream<Testfall> =
            Stream.of(
                    Testfall(BeendeInventur(Aggregatkennung.aus(id)),
                            """{
                                |  "von" : "$id"
                                |}""".trimMargin()),
                    Testfall(
                            BeginneInventur(Aggregatkennung.aus(id)),
                            """{
                                |  "id" : "$id"
                                |}""".trimMargin()),
                    Testfall(
                            BeginneHaushaltsbuchführung(Aggregatkennung.aus(id), Inventar.leer),
                            """{
                                    |  "id" : "$id",
                                    |  "inventar" : {
                                    |    "anlagevermoegen" : [ ],
                                    |    "umlaufvermoegen" : [ ],
                                    |    "schulden" : [ ],
                                    |    "reinvermoegen" : {
                                    |      "summeDerSchulden" : "0,00 EUR",
                                    |      "summeDesVermögens" : "0,00 EUR",
                                    |      "reinvermögen" : "0,00 EUR"
                                    |    }
                                    |  }
                                    |}""".trimMargin()),
                    Testfall(
                            BeginneInventur(Aggregatkennung.aus(id)),
                            """{
                               |  "id" : "$id"
                               |}""".trimMargin()),
                    Testfall(
                            ErfasseInventar(Aggregatkennung.aus(id), Inventar.leer),
                            """{
                                |  "für" : "$id",
                                |  "inventar" : {
                                |    "anlagevermoegen" : [ ],
                                |    "umlaufvermoegen" : [ ],
                                |    "schulden" : [ ],
                                |    "reinvermoegen" : {
                                |      "summeDerSchulden" : "0,00 EUR",
                                |      "summeDesVermögens" : "0,00 EUR",
                                |      "reinvermögen" : "0,00 EUR"
                                |    }
                                |  }
                                |}""".trimMargin()),
                    Testfall(
                            ErfasseSchulden(
                                    inventurkennung = Aggregatkennung.aus(id),
                                    position = "Autokredit",
                                    währungsbetrag = Währungsbetrag.NullEuro),
                            """{
                                |  "inventurkennung" : "$id",
                                |  "position" : "Autokredit",
                                |  "währungsbetrag" : "0,00 EUR"
                                |}""".trimMargin())
                    )
    }

    class SerialisierteAbfragenAnbieter : TestfallAnbieter<Testfall>(Testfall::class)
    {
        val inventurId = UUID.randomUUID().toString();

        override fun testfälle(): Stream<Testfall> =
                Stream.of(
                        Testfall(LeseInventar(Aggregatkennung.aus(inventurId)),
                                """{
                                    |  "ausInventur" : "$inventurId"
                                    |}""".trimMargin())
                )

    }
}