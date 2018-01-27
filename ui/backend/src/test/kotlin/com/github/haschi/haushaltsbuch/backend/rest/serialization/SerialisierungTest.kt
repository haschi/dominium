package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jackson.JsonLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.core.ResolvableType
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DisplayName("Serialisierbarkeit der Wertobjekte prüfen")
@JsonTest
class SerialisierungTest
{
    @Autowired
    private lateinit var mapper: ObjectMapper;

    @TestTemplate
    @DisplayName("prüfen, ob POKO korrekt serialisiert wird")
    @ExtendWith(
            TestfallEröffnungsbilanzkontoErstellt::class,
            HaushaltsbuchführungBegonnenTestfall::class,
            ErfasseInventarTestfall::class,
            BeginneHaushaltsbuchführungTestfall::class,
            InventarErfasstTestfall::class,
            BeendeInventurTestfall::class,
            BeginneInventurTestfall::class)

    fun serialisierung(testfall: Testfall) {

        val tester:JacksonTester<Any> = JacksonTester(
                this.javaClass,
                ResolvableType.forClass(testfall.poko.javaClass),
                mapper)

        with(testfall)
        {
            assertThat(tester.write(poko))
                    .isEqualToJson(json)

            assertThat(tester.write(poko))

        }
    }

    @TestTemplate
    @DisplayName("prüfe, ob JSON Objekt korrekt deserialisiert wird")
    @ExtendWith(
            TestfallEröffnungsbilanzkontoErstellt::class,
            HaushaltsbuchführungBegonnenTestfall::class,
            BeendeInventurTestfall::class,
            BeginneInventurTestfall::class)
    fun deserialisierung(testfall: Testfall)
    {
        val tester: JacksonTester<Any> = JacksonTester(
                this.javaClass,
                ResolvableType.forClass(testfall.poko.javaClass),
                mapper)

        with(testfall)
        {
            assertThat(tester.parse(json)).isEqualTo(poko)
        }
    }

    @TestTemplate
    @DisplayName("prüfe, ob serialisierte Objekte dem JSON Schema entsprechen")
    @ExtendWith(
            BeendeInventurTestfall::class,
            HaushaltsbuchführungBegonnenTestfall::class)
    fun schematest(testfall: Testfall)
    {
        with(testfall) {

                val schema = JsonLoader.fromString(schema)


                val tester:JacksonTester<Any> = JacksonTester(
                        this.javaClass,
                        ResolvableType.forClass(testfall.poko.javaClass),
                        mapper)


//
//            val factory = JsonSchemaFactory.byDefault()
//            val x = factory.getJsonSchema(schema)
//
//            val validator = factory.validator
//            validator.validate(schema, data)
        }
    }

//    class SerialisierteEreignisseAnbieter : TestfallAnbieter<Testfall<*>>(Testfall::class)
//    {
//        private val haushaltsbuchId = UUID.randomUUID().toString()
//
//        override fun testfaelle(): Stream<Testfall<*>> =
//                Stream.of(
//                        Testfall(
//                                clazz = EröffnungsbilanzkontoErstellt::class.java,
//                                poko = EröffnungsbilanzkontoErstellt(
//                                        Eröffnungsbilanzkonto(
//                                                soll = listOf(Buchung("Sollbuchung", 12.00.euro())),
//                                                haben = listOf(Buchung("Habenbuchung", 14.00.euro())))),
//                                json = """{
//                                    |  "eröffnungsbilanzkonto" : {
//                                    |    "soll" : [ {
//                                    |      "buchungstext" : "Sollbuchung",
//                                    |      "betrag" : "12,00 EUR"
//                                    |    } ],
//                                    |    "haben" : [ {
//                                    |      "buchungstext" : "Habenbuchung",
//                                    |      "betrag" : "14,00 EUR"
//                                    |    } ]
//                                    |  }
//                                    |}""".trimMargin()),
//                        Testfall(
//                                HaushaltsbuchführungBegonnen(Aggregatkennung.aus(haushaltsbuchId)),
//                                """{
//                                    |  "id" : "$haushaltsbuchId"
//                                    |}""".trimMargin()),
//                        Testfall(
//                                InventarErfasst(
//                                        Inventar(
//                                                anlagevermoegen = Vermoegenswerte(
//                                                        Vermoegenswert("Aktiendepot", 123.45.euro())),
//                                                umlaufvermoegen = Vermoegenswerte(
//                                                        Vermoegenswert("Geldbörse", 15.67.euro())),
//                                                schulden = Schulden(
//                                                        Schuld("Autokredit", 10100.00.euro())))),
//                                """{
//                                    |  "inventar" : {
//                                    |    "anlagevermoegen" : [ {
//                                    |      "position" : "Aktiendepot",
//                                    |      "waehrungsbetrag" : "123,45 EUR"
//                                    |    } ],
//                                    |    "umlaufvermoegen" : [ {
//                                    |      "position" : "Geldbörse",
//                                    |      "waehrungsbetrag" : "15,67 EUR"
//                                    |    } ],
//                                    |    "schulden" : [ {
//                                    |      "position" : "Autokredit",
//                                    |      "waehrungsbetrag" : "10.100,00 EUR"
//                                    |    } ],
//                                    |    "reinvermoegen" : {
//                                    |      "summeDesVermoegens" : "139,12 EUR",
//                                    |      "summeDerSchulden" : "10.100,00 EUR",
//                                    |      "reinvermoegen" : "-9.960,88 EUR"
//                                    |    }
//                                    |  }
//                                    |}""".trimMargin()),
//                        Testfall(InventurBeendet(),"{ }"),
//                        Testfall(
//                                SchuldErfasst("Autokredit", 12345.67.euro()),
//                                """{
//                                    |  "position" : "Autokredit",
//                                    |  "betrag" : "12.345,67 EUR"
//                                    |}""".trimMargin()),
//                        Testfall(
//                                UmlaufvermögenErfasst("Girokonto", 12.34.euro()),
//                                """{
//                                    |  "position" : "Girokonto",
//                                    |  "betrag" : "12,34 EUR"
//                                    |}""".trimMargin()))
//
//    }
//
//    class SerialisierteAnweisungenAnbieter : TestfallAnbieter<Testfall<*>>(Testfall::class)
//    {
//        private val id ="2c618058-09b8-4cde-b3fa-edcceadbd908"
//
//        override fun testfaelle(): Stream<Testfall> =
//            Stream.of(
//                    Testfall(BeendeInventur(Aggregatkennung.aus(id)),
//                            """{
//                                |  "von" : "$id"
//                                |}""".trimMargin()),
//                    Testfall(
//                            BeginneInventur(Aggregatkennung.aus(id)),
//                            """{
//                                |  "id" : "$id"
//                                |}""".trimMargin()),
//                    Testfall(
//                            BeginneHaushaltsbuchführung(Aggregatkennung.aus(id), Inventar.leer),
//                            """{
//                                    |  "id" : "$id",
//                                    |  "inventar" : {
//                                    |    "anlagevermoegen" : [ ],
//                                    |    "umlaufvermoegen" : [ ],
//                                    |    "schulden" : [ ],
//                                    |    "reinvermoegen" : {
//                                    |      "summeDesVermoegens" : "0,00 EUR",
//                                    |      "summeDerSchulden" : "0,00 EUR",
//                                    |      "reinvermoegen" : "0,00 EUR"
//                                    |    }
//                                    |  }
//                                    |}""".trimMargin()),
//                    Testfall(
//                            BeginneInventur(Aggregatkennung.aus(id)),
//                            """{
//                               |  "id" : "$id"
//                               |}""".trimMargin()),
//                    Testfall(
//                            ErfasseInventar(Aggregatkennung.aus(id), Inventar.leer),
//                            """{
//                                |  "id" : "$id",
//                                |  "inventar" : {
//                                |    "anlagevermoegen" : [ ],
//                                |    "umlaufvermoegen" : [ ],
//                                |    "schulden" : [ ],
//                                |    "reinvermoegen" : {
//                                |      "summeDesVermoegens" : "0,00 EUR",
//                                |      "summeDerSchulden" : "0,00 EUR",
//                                |      "reinvermoegen" : "0,00 EUR"
//                                |    }
//                                |  }
//                                |}""".trimMargin()),
//                    Testfall(
//                            ErfasseSchulden(
//                                    inventurkennung = Aggregatkennung.aus(id),
//                                    position = "Autokredit",
//                                    waehrungsbetrag = Währungsbetrag.NullEuro),
//                            """{
//                                |  "inventurkennung" : "$id",
//                                |  "position" : "Autokredit",
//                                |  "waehrungsbetrag" : "0,00 EUR"
//                                |}""".trimMargin())
//                    )
//    }
//
//    class SerialisierteAbfragenAnbieter : TestfallAnbieter<Testfall<*>>(Testfall::class)
//    {
//        private val inventurId = UUID.randomUUID().toString()
//
//        override fun testfaelle(): Stream<Testfall> =
//                Stream.of(
//                        Testfall(LeseInventar(Aggregatkennung.aus(inventurId)),
//                                """{
//                                    |  "id" : "$inventurId"
//                                    |}""".trimMargin())
//                )
//
//    }
}