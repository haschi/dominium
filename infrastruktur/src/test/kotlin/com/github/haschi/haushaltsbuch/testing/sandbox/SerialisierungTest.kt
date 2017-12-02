package com.github.haschi.haushaltsbuch.testing.sandbox

import io.vertx.core.json.JsonObject
import org.assertj.core.api.Assertions.assertThat
import org.github.haschi.haushaltsbuch.api.BeendeInventur
import org.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung
import org.github.haschi.haushaltsbuch.api.BeginneInventur
import org.github.haschi.haushaltsbuch.core.Buchung
import org.github.haschi.haushaltsbuch.api.ErfasseInventar
import org.github.haschi.haushaltsbuch.api.ErfasseSchulden
import org.github.haschi.haushaltsbuch.core.Eröffnungsbilanzkonto
import org.github.haschi.haushaltsbuch.api.EröffnungsbilanzkontoErstellt
import org.github.haschi.haushaltsbuch.api.HaushaltsbuchführungBegonnen
import org.github.haschi.haushaltsbuch.core.Inventar
import org.github.haschi.haushaltsbuch.api.InventarErfasst
import org.github.haschi.haushaltsbuch.api.InventurBeendet
import org.github.haschi.haushaltsbuch.api.LeseInventar
import org.github.haschi.haushaltsbuch.core.Schuld
import org.github.haschi.haushaltsbuch.api.SchuldErfasst
import org.github.haschi.haushaltsbuch.core.Schulden
import org.github.haschi.haushaltsbuch.api.UmlaufvermögenErfasst
import org.github.haschi.haushaltsbuch.core.Vermoegenswert
import org.github.haschi.haushaltsbuch.core.Vermoegenswerte
import org.github.haschi.haushaltsbuch.core.Währungsbetrag
import org.github.haschi.haushaltsbuch.core.euro
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

        with(testfall) {
            assertThat(JsonObject.mapFrom(poko).encodePrettily())
                    .isEqualTo(json)
        }
    }

    class SerialisierteEreignisseAnbieter : TestfallAnbieter<Testfall>(Testfall::class)
    {
        private val haushaltsbuchId = UUID.randomUUID().toString()

        override fun testfälle(): Stream<Testfall> =
                Stream.of(
                        Testfall(
                                EröffnungsbilanzkontoErstellt(
                                        Eröffnungsbilanzkonto(
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
                                    |}""".trimMargin()),
                        Testfall(
                                HaushaltsbuchführungBegonnen(Aggregatkennung.aus(haushaltsbuchId)),
                                """{
                                    |  "id" : "$haushaltsbuchId"
                                    |}""".trimMargin()),
                        Testfall(
                                InventarErfasst(
                                        Inventar(
                                                anlagevermoegen = Vermoegenswerte(
                                                        Vermoegenswert("Aktiendepot", 123.45.euro())),
                                                umlaufvermoegen = Vermoegenswerte(
                                                        Vermoegenswert("Geldbörse", 15.67.euro())),
                                                schulden = Schulden(
                                                        Schuld("Autokredit", 10100.00.euro())))),
                                """{
                                    |  "inventar" : {
                                    |    "anlagevermoegen" : [ {
                                    |      "position" : "Aktiendepot",
                                    |      "währungsbetrag" : "123,45 EUR"
                                    |    } ],
                                    |    "umlaufvermoegen" : [ {
                                    |      "position" : "Geldbörse",
                                    |      "währungsbetrag" : "15,67 EUR"
                                    |    } ],
                                    |    "schulden" : [ {
                                    |      "position" : "Autokredit",
                                    |      "währungsbetrag" : "10.100,00 EUR"
                                    |    } ],
                                    |    "reinvermoegen" : {
                                    |      "summeDesVermögens" : "139,12 EUR",
                                    |      "summeDerSchulden" : "10.100,00 EUR",
                                    |      "reinvermögen" : "-9.960,88 EUR"
                                    |    }
                                    |  }
                                    |}""".trimMargin()),
                        Testfall(InventurBeendet(),"{ }"),
                        Testfall(
                                SchuldErfasst("Autokredit", 12345.67.euro()),
                                """{
                                    |  "position" : "Autokredit",
                                    |  "betrag" : "12.345,67 EUR"
                                    |}""".trimMargin()),
                        Testfall(
                                UmlaufvermögenErfasst("Girokonto", 12.34.euro()),
                                """{
                                    |  "position" : "Girokonto",
                                    |  "betrag" : "12,34 EUR"
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
                                    |      "summeDesVermögens" : "0,00 EUR",
                                    |      "summeDerSchulden" : "0,00 EUR",
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
                                |      "summeDesVermögens" : "0,00 EUR",
                                |      "summeDerSchulden" : "0,00 EUR",
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
        private val inventurId = UUID.randomUUID().toString()

        override fun testfälle(): Stream<Testfall> =
                Stream.of(
                        Testfall(LeseInventar(Aggregatkennung.aus(inventurId)),
                                """{
                                    |  "ausInventur" : "$inventurId"
                                    |}""".trimMargin())
                )

    }
}