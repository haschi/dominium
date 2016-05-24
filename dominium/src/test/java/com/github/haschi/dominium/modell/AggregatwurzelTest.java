package com.github.haschi.dominium.modell;

import com.github.haschi.dominium.testdomäne.TestAggregat;
import com.github.haschi.dominium.testdomäne.TestAggregatSchnappschuss;
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.UUID;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OleasterRunner.class)
public class AggregatwurzelTest {

    private static final long[] EREIGNIS_NUTZLAST = {42L, 43L, 44L, 45L, 4711L};
    private static final Version VERSION = new Version(123L);

    private TestAggregat subjectUnderTest;

    {
        final UUID identitätsmerkmal = UUID.randomUUID();
        beforeEach(() -> this.subjectUnderTest = new TestAggregat(identitätsmerkmal, Version.NEU));

        describe("Eine Aggregatwurzel", () -> {

            it("hat ein Identitätsmerkmal", () ->
                assertThat(this.subjectUnderTest.getIdentitätsmerkmal())
                    .isEqualTo(identitätsmerkmal));

            it("hat zu Beginn keine Änderungen", () ->
                assertThat(this.subjectUnderTest.getÄnderungen())
                    .isEmpty());

            it("kann einen Schnappschuss erstellen", () ->
                assertThat(this.subjectUnderTest.schnappschussErstellen())
                    .isEqualTo(TestAggregatSchnappschuss.builder()
                    .identitätsmerkmal(identitätsmerkmal)
                    .version(Version.NEU)
                    .payload(0L)
                    .build()));

            it("hat eine Initialversion = 1", () ->
                assertThat(this.subjectUnderTest.getInitialversion())
                    .isEqualTo(Version.NEU));

            Arrays.asList(
                new ZustandWurdeGeändert[]{ZustandWurdeGeändert.of(EREIGNIS_NUTZLAST[0])},
                new ZustandWurdeGeändert[]{
                    ZustandWurdeGeändert.of(EREIGNIS_NUTZLAST[1]),
                    ZustandWurdeGeändert.of(EREIGNIS_NUTZLAST[2]),
                    ZustandWurdeGeändert.of(EREIGNIS_NUTZLAST[3])})
                .forEach(testfall -> {
                    final String description = String.format("mit %d Änderungen nacheinander", testfall.length);
                    describe(description, () -> {
                        beforeEach(() ->
                            Arrays.asList(testfall).forEach(ereignis ->
                                this.subjectUnderTest.zustandÄndern(ereignis.payload())));

                        it("besitzt " + testfall.length + " Änderungen", () ->
                            assertThat(this.subjectUnderTest.getÄnderungen()).containsExactly(testfall));

                        it("wendet Änderungen auf eigenen Zustand an", () ->
                            assertThat(this.subjectUnderTest.schnappschussErstellen())
                                .isEqualTo(TestAggregatSchnappschuss.builder()
                                    .version(new Version(testfall.length))
                                    .identitätsmerkmal(identitätsmerkmal)
                                    .payload(testfall[testfall.length - 1].payload())
                                    .build()));
                    });

                    final String description2 = String.format("mit %d Aktualisierungen", testfall.length);
                    describe(description2, () -> {
                        beforeEach(() ->
                            this.subjectUnderTest.aktualisieren(Arrays.asList(testfall)));

                        it("erhöht die Version des Aggregats", () ->
                            assertThat(this.subjectUnderTest.getVersion())
                                .isEqualTo(new Version(testfall.length)));

                        it("setzt die Initialversion des Aggregats", () ->
                            assertThat(this.subjectUnderTest.getInitialversion())
                                .isEqualTo(this.subjectUnderTest.getVersion()));

                        it("hat keine Änderungen", () ->
                            assertThat(this.subjectUnderTest.getÄnderungen())
                                .isEmpty());
                    });
                });

            describe("aus einem Schnappschuss wiederhergestellt", () -> {
                final UUID anderesIdentitätsmerkmal = UUID.randomUUID();

                beforeEach(() -> {
                    final TestAggregatSchnappschuss schnappschuss = TestAggregatSchnappschuss.builder()
                        .identitätsmerkmal(anderesIdentitätsmerkmal)
                        .payload(EREIGNIS_NUTZLAST[4])
                        .version(VERSION)
                        .build();

                    this.subjectUnderTest = new TestAggregat(anderesIdentitätsmerkmal, schnappschuss.getVersion());

                    this.subjectUnderTest.wiederherstellenAus(schnappschuss);
                });

                it("erhält die Version aus dem Schnappschuss", () ->
                    assertThat(this.subjectUnderTest.getVersion())
                        .isEqualTo(VERSION));

                it("erhält das Identitätsmerkmal aus dem Schnappschuss", () ->
                    assertThat(this.subjectUnderTest.getIdentitätsmerkmal())
                        .isEqualTo(anderesIdentitätsmerkmal));
            });
        });
    }
}
