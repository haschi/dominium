package com.github.haschi.haushaltsbuch

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import org.assertj.core.api.Assertions.assertThatCode
import org.github.haschi.haushaltsbuch.api.Vermoegenswert
import org.github.haschi.haushaltsbuch.api.Vermoegenswerte
import org.github.haschi.haushaltsbuch.api.Währungsbetrag
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

@DisplayName("Vermoegenswerte serialisieren")
class VermoegenswerteSerialisierenTest
{
    @TestFactory
    fun serialisieren(): List<DynamicNode>
    {
        Json.mapper.registerKotlinModule()

        data class Wrapper(val anlagevermoegen: Vermoegenswerte)
        data class Testfall(val bezeichnung: String, val testwert: Wrapper)

        return listOf(
                Testfall("2 Vermögenswerte",
                        Wrapper(
                                Vermoegenswerte(
                                        Vermoegenswert(
                                                "Girtokonto",
                                                Währungsbetrag.NullEuro),
                                        Vermoegenswert(
                                                "Sparbuch",
                                                Währungsbetrag.NullEuro)
                                ))),
                Testfall("1 Vermögenswert",
                        Wrapper(
                                Vermoegenswerte(
                                        Vermoegenswert(
                                                "Girokonto",
                                                Währungsbetrag.NullEuro))
                        )),
                Testfall("Kein Vermögenswert",
                        Wrapper(Vermoegenswerte())))
                .map { testfall ->
                    dynamicTest(testfall.bezeichnung, {
                        assertThatCode { JsonObject.mapFrom(testfall.testwert) }
                                .doesNotThrowAnyException()
                        println(JsonObject.mapFrom(testfall.testwert).encodePrettily())
                    })
                }
    }
}