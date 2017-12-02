package com.github.haschi.haushaltsbuch

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.github.haschi.haushaltsbuch.core.Vermoegenswert
import org.github.haschi.haushaltsbuch.core.Vermoegenswerte
import org.github.haschi.haushaltsbuch.core.Währungsbetrag
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

data class Wrapper @JsonCreator constructor(val anlagevermoegen: Vermoegenswerte) {
}

@DisplayName("Vermoegenswerte serialisieren")
@Disabled
class VermoegenswerteSerialisierenTest
{
    @TestFactory
    fun serialisieren(): List<DynamicNode>
    {
        Json.mapper.registerKotlinModule()
        Json.mapper

        data class Testfall(val bezeichnung: String, val testwert: Wrapper, val json: String)

        return listOf(
                Testfall("2 Vermögenswerte",
                        Wrapper(
                                Vermoegenswerte(
                                        Vermoegenswert(
                                                "Girokonto",
                                                Währungsbetrag.NullEuro),
                                        Vermoegenswert(
                                                "Sparbuch",
                                                Währungsbetrag.NullEuro)
                                )),
                        """
                            {
                              "anlagevermoegen" : [ {
                                "position" : "Girokonto",
                                "währungsbetrag" : "0,00 EUR"
                              }, {
                                "position" : "Sparbuch",
                                "währungsbetrag" : "0,00 EUR"
                              } ]
                            }
                            """.trimIndent()),
                Testfall("1 Vermögenswert",
                        Wrapper(
                                Vermoegenswerte(
                                        Vermoegenswert(
                                                "Girokonto",
                                                Währungsbetrag.NullEuro))),
                        """
                            {
                              "anlagevermoegen" : [ {
                                "position" : "Girokonto",
                                "währungsbetrag" : "0,00 EUR"
                              } ]
                            }
                        """.trimIndent()),
                Testfall("Kein Vermögenswert",
                        Wrapper(Vermoegenswerte()),
                        """
                          {
                            "anlagevermoegen" : [ ]
                          }
                        """.trimIndent()))
                .map { testfall ->
                    dynamicContainer(testfall.bezeichnung, Stream.of(
                        dynamicTest("Serialisierung: ${testfall.bezeichnung}", {
                            assertThatCode { JsonObject.mapFrom(testfall.testwert) }
                                    .doesNotThrowAnyException()

                            println(JsonObject.mapFrom(testfall.testwert).encodePrettily())

                            assertThat(JsonObject.mapFrom(testfall.testwert).encodePrettily())
                                    .isEqualTo(testfall.json)
                        }),
                            dynamicTest("Deserialisierung: ${testfall.bezeichnung}", {

                                assertThat(Json.mapper.canDeserialize(Json.mapper.constructType(Vermoegenswerte::class.java)))
                                        .isTrue()

                                assertThat(JsonObject(testfall.json).mapTo(Wrapper::class.java))
                                        .isEqualTo(testfall.testwert)
                            })));
                }
    }
}