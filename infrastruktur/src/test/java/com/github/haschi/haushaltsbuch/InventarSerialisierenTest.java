package com.github.haschi.haushaltsbuch;

import io.vertx.core.json.JsonObject;
import javaslang.Tuple;
import org.github.haschi.haushaltsbuch.api.Inventar;
import org.github.haschi.haushaltsbuch.api.Reinvermögen;
import org.github.haschi.haushaltsbuch.api.Vermögenswerte;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.text.MessageFormat;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("Inventar serialisieren")
public class InventarSerialisierenTest
{
    @Test
    @DisplayName("Vollständiges Inventar deserialisieren")
    public void deserialisieren()
    {
        final Inventar inventar = Inventar.builder()
                .anlagevermoegen(Vermögenswerte.leer())
                .umlaufvermoegen(Vermögenswerte.leer())
                // .schulden(Schulden.leer())
                .build();

        final JsonObject jsonObject = JsonObject.mapFrom(inventar);
        System.out.println(jsonObject.encodePrettily());
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
                .put("summeDesVermoegens", new JsonObject().put("betrag", "120,00 EUR"))
                .put("summeDerSchulden", new JsonObject().put("betrag", "80,00 EUR"));


        System.out.println(json.encodePrettily());

        assertThatCode(() -> json.mapTo(Reinvermögen.class))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Währungsbetrag serialisieren")
    public void währungsbetragSerialisieren()
    {
        final Währungsbetrag währungsbetrag = Währungsbetrag.NullEuro();

        System.out.println(MessageFormat.format("Währungsbetrag {0}", JsonObject.mapFrom(währungsbetrag).encodePrettily
                ()));

        assertThatCode(() -> JsonObject.mapFrom(währungsbetrag))
                .doesNotThrowAnyException();
    }

    @DisplayName("Währungsbetrag deserialisieren")
    @TestFactory
    public Stream<DynamicTest> währungsbetrag_deserialisieren()
    {

        return Stream.of("0,00 EUR", "123,56 EUR")
                .map(s -> Tuple.of(s, Währungsbetrag.währungsbetrag(s)))
                .map(input -> dynamicTest(
                        MessageFormat.format("Währungsbetrag {0} deserialisieren", input._1()),
                        () -> {

                            final JsonObject entries = new JsonObject();
                            entries.put("betrag", input._1());

                            assertThatCode(() -> entries.mapTo(Währungsbetrag.class))
                                    .doesNotThrowAnyException();

                            assertThat(entries.mapTo(Währungsbetrag.class))
                                    .isEqualTo(input._2());
                        }));
    }
}
