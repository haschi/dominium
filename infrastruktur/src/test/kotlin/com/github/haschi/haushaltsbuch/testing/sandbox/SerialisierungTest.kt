package com.github.haschi.haushaltsbuch.testing.sandbox

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneHaushaltsbuchführung
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseSchulden
import com.github.haschi.dominium.haushaltsbuch.core.model.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.dominium.haushaltsbuch.core.model.events.HaushaltsbuchführungBegonnen
import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventarErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventurBeendet
import com.github.haschi.dominium.haushaltsbuch.core.model.events.SchuldErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.events.UmlaufvermögenErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanzkonto
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schuld
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro
import com.github.haschi.haushaltsbuch.infrastruktur.rest.HaushaltsbuchModule
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID
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

        Json.mapper.registerKotlinModule()
        Json.mapper.registerModule(HaushaltsbuchModule())


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
                                    |      "waehrungsbetrag" : "123,45 EUR"
                                    |    } ],
                                    |    "umlaufvermoegen" : [ {
                                    |      "position" : "Geldbörse",
                                    |      "waehrungsbetrag" : "15,67 EUR"
                                    |    } ],
                                    |    "schulden" : [ {
                                    |      "position" : "Autokredit",
                                    |      "waehrungsbetrag" : "10.100,00 EUR"
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
                            ErfasseSchulden(
                                    inventurkennung = Aggregatkennung.aus(id),
                                    position = "Autokredit",
                                    währungsbetrag = Währungsbetrag.NullEuro),
                            """{
                                |  "inventurkennung" : "$id",
                                |  "position" : "Autokredit",
                                |  "waehrungsbetrag" : "0,00 EUR"
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
                                    |  "id" : "$inventurId"
                                    |}""".trimMargin())
                )

    }
}