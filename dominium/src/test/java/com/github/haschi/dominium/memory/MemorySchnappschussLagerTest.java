package com.github.haschi.dominium.memory;

import com.github.haschi.dominium.testdomaene.TestAggregat;
import com.github.haschi.dominium.testdomaene.TestAggregat.Snapshot;
import com.github.haschi.dominium.testdomaene.Testdaten;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OleasterRunner.class)
public class MemorySchnappschussLagerTest {

    private MemorySchnappschussLager<Snapshot, UUID> subjectUnderTest;

    {
        final UUID identitätsmerkmal = UUID.randomUUID();


        beforeEach(() -> this.subjectUnderTest = new MemorySchnappschussLager<>());

        describe("Ein Schnappschuss-Lager", () -> {

            describe("ohne Schnappschüsse für ein Aggregat", () -> {
                it("liefert keine Schnappschüsse", () ->
                    assertThat(this.subjectUnderTest.getNeuesterSchnappschuss(identitätsmerkmal))
                        .isEmpty());
            });

            Arrays.asList(
                new TestAggregat.Snapshot[] {Testdaten.schnappschüsse[0]},
                new Snapshot[] {
                    Testdaten.schnappschüsse[0],
                    Testdaten.schnappschüsse[1],
                    Testdaten.schnappschüsse[2]})
                .forEach(testfall -> {
                    final String description = String.format(
                        "mit %d Schnappschüssen für ein Aggregat",
                        testfall.length);

                    describe(description, () -> {
                        beforeEach(() ->
                            Arrays.asList(testfall).forEach(schnappschuss ->
                                this.subjectUnderTest.schnappschussHinzufügen(
                                    schnappschuss,
                                    identitätsmerkmal)));

                        it("liefert den zuletzt hinzugefügten Schnappschuss", () ->
                            assertThat(this.subjectUnderTest.getNeuesterSchnappschuss(identitätsmerkmal))
                                .isEqualTo(Optional.of(Arrays.asList(testfall).get(testfall.length - 1))));

                        describe("wenn es geleert wird", () -> {
                            beforeEach(() ->
                                this.subjectUnderTest.leeren());

                            it("liefert keine Schnappschüsse", () ->
                                assertThat(this.subjectUnderTest.getNeuesterSchnappschuss(identitätsmerkmal))
                                    .isEmpty());
                        });
                    });
                });
        });
    }
}
