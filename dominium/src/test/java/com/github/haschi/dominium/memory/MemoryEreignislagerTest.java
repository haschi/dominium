package com.github.haschi.dominium.memory;

import com.github.haschi.coding.aspekte.ArgumentIstNullException;
import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.modell.Versionsbereich;
import com.github.haschi.dominium.persistenz.EreignisstromWurdeNichtGefunden;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import com.github.haschi.dominium.testdomäne.TestAggregatEreignisZiel;
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert;

@RunWith(OleasterRunner.class)
public class MemoryEreignislagerTest {

    private MemoryEreignislager<UUID, TestAggregatEreignisZiel> lager;

    {
        final UUID identitätsmerkmal = UUID.randomUUID();

        beforeEach(() -> this.lager = new MemoryEreignislager<>());

        describe("Ein Ereignislager", () -> {

            it("liefert eine leere Ereignisliste für unbekannte Ereignisströme",  () ->
                assertThat(this.lager.getEreignisliste(identitätsmerkmal, Versionsbereich.ALLE_VERSIONEN))
                    .isEmpty());

            it("besitzt keine Ereignisströme", () ->
                assertThat(this.lager.getEreignisströme().collect(Collectors.toSet()))
                    .isEmpty());
        });

        describe("mit Ereignisstrom", () -> {
            final ZustandWurdeGeändert[] domänenereignisse = {
                ZustandWurdeGeändert.of(42L),
                ZustandWurdeGeändert.of(43L)
            };

            beforeEach(() -> this.lager.neuenEreignisstromErzeugen(
                identitätsmerkmal,
                Arrays.asList(domänenereignisse)));

            it("kann geleert werden", () -> {
                this.lager.clear();
                assertThat(this.lager.getEreignisströme().collect(Collectors.toSet())).isEmpty();
            });

            it("ist nicht leer", () ->
                assertThat(this.lager.getEreignisströme().collect(Collectors.toSet()))
                    .isNotEmpty());

            it("kann Ereignisstrom nicht doppelt anlegen", () ->
                assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> this.lager.neuenEreignisstromErzeugen(
                        identitätsmerkmal, Collections.emptyList())));

            it("enthält Ereignisströme, falls angelegt", () ->
                assertThat(this.lager.existiertEreignisStrom(identitätsmerkmal))
                    .isTrue());

            it("enthält keinen Ereignisstrom, falls nicht angelegt", () ->
                assertThat(this.lager.existiertEreignisStrom(UUID.randomUUID()))
                    .isFalse());

            it("liefert Ereignisliste seiner Ereignisströme", () ->
                assertThat(this.lager.getEreignisliste(identitätsmerkmal, Versionsbereich.ALLE_VERSIONEN))
                    .containsExactly(domänenereignisse));

            it("liefert Ereignisliste für Versionsbereich seiner Ereignisströme", () ->
                assertThat(this.lager.getEreignisliste(identitätsmerkmal,
                        Versionsbereich.von(new Version(2L)).bis(new Version(2L))))
                    .containsExactly(domänenereignisse[1]));

            describe("Ereignisse hinzufügen mit gültiger Versionsnummer", () -> {
                final ZustandWurdeGeändert[] weitereEreignisse = {
                    ZustandWurdeGeändert.of(44L),
                    ZustandWurdeGeändert.of(45L)
                };

                final long version = 2;

                beforeEach(() -> this.lager.ereignisseDemStromHinzufügen(
                    identitätsmerkmal, version, Arrays.asList(weitereEreignisse)));

                it("fügt Ereignisse dem Ereignisstrom hinzu",() ->
                    assertThat(this.lager.getEreignisliste(identitätsmerkmal, Versionsbereich.ALLE_VERSIONEN))
                        .containsExactly(
                            domänenereignisse[0],
                            domänenereignisse[1],
                            weitereEreignisse[0],
                            weitereEreignisse[1]));

                it("benötigt Identitätsmerkmal", () ->
                    assertThatExceptionOfType(ArgumentIstNullException.class)
                        .isThrownBy(() -> this.lager.ereignisseDemStromHinzufügen(null, version, new ArrayList<>())));

                it("benötigt neue Ereignisse", () ->
                    assertThatExceptionOfType(ArgumentIstNullException.class)
                        .isThrownBy(() -> this.lager.ereignisseDemStromHinzufügen(identitätsmerkmal, version, null)));
            });

            describe("Ereignisse hinzufügen mit ungültiger Versionsnummer", () ->
                Arrays.asList(-1, 1, 3, 100).forEach(version ->
                    it("Erzeugt eine Ausnahme für erwartete Version " + version.toString(), () ->
                        assertThatExceptionOfType(KonkurrierenderZugriff.class)
                            .isThrownBy(() -> this.lager.ereignisseDemStromHinzufügen(
                                identitätsmerkmal, version, Collections.emptyList())))));
        });

        describe("ohne Ereignisstrom", () ->
            it("kann keine Ereignisse hinzufügen", () ->
                assertThatExceptionOfType(EreignisstromWurdeNichtGefunden.class).isThrownBy(() ->
                    this.lager.ereignisseDemStromHinzufügen(UUID.randomUUID(), 2, Collections.emptyList()))));
    }
}
