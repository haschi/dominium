package com.github.haschi.haushaltsbuch;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.github.haschi.haushaltsbuch.api.BeginneInventur;
import org.github.haschi.haushaltsbuch.api.Inventar;
import org.github.haschi.haushaltsbuch.api.Reinvermögen;
import org.github.haschi.haushaltsbuch.api.Schulden;
import org.github.haschi.haushaltsbuch.api.Vermögenswerte;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Inventar serialisieren")
public class InventarSerialisierenTest
{
    @Test
    @DisplayName("Deserialisieren eines vom Frontend gebauten Json Objekts")
    public void echtes_objekt_deserialisieren()
    {
        final String sampe = "{\"anlagevermoegen\": {\"wert\": []},\"umlaufvermoegen\":[],\"schulden\":[]}";
        final JsonObject json = new JsonObject(sampe);

        assertThat(json.mapTo(Inventar.class))
                .isEqualTo(Inventar.builder().build());
    }
    @Test
    @DisplayName("Vollständiges Inventar deserialisieren")
    public void deserialisieren()
    {
        final Inventar inventar = Inventar.builder()
                .anlagevermoegen(Vermögenswerte.leer())
                .umlaufvermoegen(Vermögenswerte.leer())
                .schulden(Schulden.leer())
                .build();

        final JsonObject jsonObject = JsonObject.mapFrom(inventar);
        System.out.println(jsonObject.encodePrettily());

        assertThat(jsonObject.mapTo(Inventar.class)).isEqualTo(inventar);
    }

    @Test
    @DisplayName("Reinvermögen serialisieren")
    public void reinvermogenSerialisieren()
    {
        final Reinvermögen reinvermögen = Reinvermögen.builder()
                .summeDesVermögens(Währungsbetrag.NullEuro())
                .summeDerSchulden(Währungsbetrag.NullEuro())
                .build();

        final JsonObject json = JsonObject.mapFrom(reinvermögen);
        System.out.println(json.encodePrettily());
        assertThatCode(() -> JsonObject.mapFrom(reinvermögen))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Reinvermögen deserialisieren")
    public void reinvermögen_deserialisieren()
    {
        final JsonObject json = new JsonObject()
                .put("summeDesVermoegens", "120,00 EUR")
                .put("summeDerSchulden", "80,00 EUR");

        System.out.println(json.encodePrettily());

        assertThatCode(() -> json.mapTo(Reinvermögen.class))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Beginne Inventur serialisieren")
    public void beginne_inventur_serialisieren()
    {
        final Aggregatkennung id = Aggregatkennung.neu();
        final BeginneInventur beginneInventur = BeginneInventur.of(id);

        System.out.println(JsonObject.mapFrom(beginneInventur).encodePrettily());

        assertThatCode(() -> JsonObject.mapFrom(beginneInventur))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Beginne Inventur deserialisieren")
    public void beginne_inventur_deserialisieren()
    {
        final JsonObject json = new JsonObject()
                .put("id", UUID.randomUUID().toString());

        System.out.println(json.encodePrettily());

        assertThatCode(() -> json.mapTo(BeginneInventur.class))
                .doesNotThrowAnyException();
    }
}
