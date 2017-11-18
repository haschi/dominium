package com.github.haschi.haushaltsbuch.testing.sandbox;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Klasse A serialisieren und deserialisieren")
public class KlasseATest
{
    UUID uuid = UUID.randomUUID();

    @Test
    @DisplayName("Klasse A serialisieren")
    public void klasse_a_serialisieren()
    {
        final AussereKlasse x = AussereKlasse.builder()
                .a(KlasseA.of("Hello World"))
                .b(KlasseB.of(42))
                .c(KlasseC.of(true))
                .d(KlasseD.of(12.34))
                .build();

        final JsonObject json = JsonObject.mapFrom(x);
        assertThat(json.encode())
                .isEqualTo("{\"a\":\"Hello World\",\"b\":42,\"c\":" + true + ",\"d\":12.34}");

        System.out.println(json.encodePrettily());
    }

    @Test
    @DisplayName("Klasse A deserialisieren")
    public void klasse_a_deserialisieren()
    {
        final JsonObject json = new JsonObject()
                .put("a", "Hello World")
                .put("b", 42)
                .put("c", true)
                .put("d", 12.34);

        System.out.println(json.encodePrettily());

        assertThatCode(() -> json.mapTo(AussereKlasse.class))
                .doesNotThrowAnyException();

        assertThat(json.mapTo(AussereKlasse.class))
                .isEqualTo(AussereKlasse.builder()
                                   .a(KlasseA.of("Hello World"))
                                    .b(KlasseB.of(42))
                        .c(KlasseC.of(true))
                        .d(KlasseD.of(12.34))
                                   .build());
    }
}
