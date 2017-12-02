package com.github.haschi.haushaltsbuch

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.github.haschi.haushaltsbuch.api.commands.BeginneInventur
import org.github.haschi.haushaltsbuch.core.Inventar
import org.github.haschi.haushaltsbuch.core.Reinvermögen
import org.github.haschi.haushaltsbuch.core.Schulden
import org.github.haschi.haushaltsbuch.core.Vermoegenswerte
import org.github.haschi.haushaltsbuch.core.Währungsbetrag
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Inventar serialisieren")
@Disabled
class InventarSerialisierenTest
{
    @Test
    @DisplayName("Deserialisieren eines vom Frontend gebauten Json Objekts")
    fun echtes_objekt_deserialisieren()
    {
        val sample = json { obj(
                "anlagevermoegen" to array(),
                "umlaufvermoegen" to array(),
                "schulden" to array()
        ) }

        assertThat(sample.mapTo(Inventar::class.java))
                .isEqualTo(Inventar.leer)
    }

    @Test
    @DisplayName("Vollständiges Inventar deserialisieren")
    fun deserialisieren()
    {
        val inventar = Inventar(
                anlagevermoegen = Vermoegenswerte.keine,
                umlaufvermoegen = Vermoegenswerte.keine,
                schulden = Schulden.keine)

        val jsonObject = JsonObject.mapFrom(inventar)
        println(jsonObject.encodePrettily())

        assertThat(jsonObject.mapTo(Inventar::class.java)).isEqualTo(inventar)
    }

    @Test
    @DisplayName("Reinvermögen serialisieren")
    fun reinvermogenSerialisieren()
    {
        val reinvermögen = Reinvermögen(
                summeDesVermögens = Währungsbetrag.NullEuro,
                summeDerSchulden = Währungsbetrag.NullEuro)

        val json = JsonObject.mapFrom(reinvermögen)
        println(json.encodePrettily())
        assertThatCode { JsonObject.mapFrom(reinvermögen) }
                .doesNotThrowAnyException()
    }

    @Test
    @DisplayName("Reinvermögen deserialisieren")
    fun reinvermögen_deserialisieren()
    {
        val json = JsonObject()
                .put("summeDesVermoegens", "120,00 EUR")
                .put("summeDerSchulden", "80,00 EUR")

        println(json.encodePrettily())

        assertThatCode { json.mapTo(Reinvermögen::class.java) }
                .doesNotThrowAnyException()
    }

    @Test
    @DisplayName("Beginne Inventur serialisieren")
    fun beginne_inventur_serialisieren()
    {
        val id = Aggregatkennung.neu()
        val beginneInventur = BeginneInventur(id)

        println(JsonObject.mapFrom(beginneInventur).encodePrettily())

        assertThatCode { JsonObject.mapFrom(beginneInventur) }
                .doesNotThrowAnyException()
    }

    @Test
    @DisplayName("Beginne Inventur deserialisieren")
    fun beginne_inventur_deserialisieren()
    {
        val id = UUID.randomUUID();

        val json = JsonObject()
                .put("id", id.toString())

        println(json.encodePrettily())

        assertThatCode { json.mapTo(BeginneInventur::class.java) }
                .doesNotThrowAnyException()

        assertThat(json.mapTo(BeginneInventur::class.java))
                .isEqualTo(BeginneInventur(Aggregatkennung(id)))
    }

    companion object
    {
        @JvmStatic
        @BeforeAll
        fun setup()
        {
            Json.mapper.registerKotlinModule();
        }
    }
}
