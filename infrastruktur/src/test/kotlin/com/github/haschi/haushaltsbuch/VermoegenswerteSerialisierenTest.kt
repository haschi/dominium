package com.github.haschi.haushaltsbuch

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import org.github.haschi.haushaltsbuch.api.Vermoegenswert
import org.github.haschi.haushaltsbuch.api.Vermoegenswerte
import org.github.haschi.haushaltsbuch.api.Währungsbetrag
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Vermoegenswerte serialisieren")
class VermoegenswerteSerialisierenTest
{

    @Test
    fun serialisieren()
    {
        val vermoegenswert = Vermoegenswerte(listOf(
                Vermoegenswert("Girtokonto", Währungsbetrag.NullEuro),
                Vermoegenswert("Sparbuch", Währungsbetrag.NullEuro))
        )

        Json.mapper.registerKotlinModule()
        val json = JsonObject.mapFrom(Vermoegenswerte.keine)
        println(json.encodePrettily())
    }
}