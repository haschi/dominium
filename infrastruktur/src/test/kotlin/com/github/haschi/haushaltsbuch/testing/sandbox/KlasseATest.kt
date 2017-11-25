package com.github.haschi.haushaltsbuch.testing.sandbox

import io.vertx.core.json.JsonObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import java.util.UUID

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.Json

@DisplayName("Klasse A serialisieren und deserialisieren")
class KlasseATest
{
    internal var uuid = UUID.randomUUID()

    @Test
    @DisplayName("Klasse A serialisieren")
    fun klasse_a_serialisieren()
    {
        val x = AussereKlasse(
                KlasseA("Hello World"),
                KlasseB(42),
                KlasseC(true),
                KlasseD(12.34))

        val json = JsonObject.mapFrom(x)
        assertThat(json.encode())
                .isEqualTo("""{"a":"Hello World","b":42,"c":true,"d":12.34}""")

        println(json.encodePrettily())
    }

    @Test
    @DisplayName("Klasse A deserialisieren")
    fun klasse_a_deserialisieren()
    {
        val json = JsonObject()
                .put("a", "Hello World")
                .put("b", 42)
                .put("c", true)
                .put("d", 12.34)

        println(json.encodePrettily())

        Json.mapper.registerKotlinModule();


        assertThatCode { json.mapTo(AussereKlasse::class.java) }
                .doesNotThrowAnyException()

        assertThat(json.mapTo(AussereKlasse::class.java))
                .isEqualTo(AussereKlasse(
                        KlasseA("Hello World"),
                        KlasseB(42),
                        KlasseC(true),
                        KlasseD(12.34)))
    }
}
