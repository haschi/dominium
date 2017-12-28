package com.github.haschi.haushaltsbuch.testing.sandbox

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Klasse A serialisieren und deserialisieren")
class KlasseATest
{
    @Test
    @DisplayName("Klasse A serialisieren")
    fun klasse_a_serialisieren()
    {
        val klasse = AussereKlasse(
                KlasseA("Hello World"),
                KlasseB(42),
                KlasseC(true),
                KlasseD(12.34),
                KlasseE("A", "B"))

        assertThat(JsonObject.mapFrom(klasse).encode())
                .isEqualTo("""{"a":"Hello World","b":42,"c":true,"d":12.34,"e":["A","B"]}""")
    }

    @Test
    @DisplayName("Klasse A deserialisieren")
    fun klasse_a_deserialisieren()
    {
        Json.mapper.registerKotlinModule()

        val json = json {
            obj(
                    "a" to "Hello World",
                    "b" to 42,
                    "c" to true,
                    "d" to 12.34,
                    "e" to array("A", "B"))
        }

        assertThat(json.mapTo(AussereKlasse::class.java))
                .isEqualTo(AussereKlasse(
                        KlasseA("Hello World"),
                        KlasseB(42),
                        KlasseC(true),
                        KlasseD(12.34),
                        KlasseE("A", "B")))
    }
}
