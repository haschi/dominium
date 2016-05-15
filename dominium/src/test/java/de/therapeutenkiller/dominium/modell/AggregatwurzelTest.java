package de.therapeutenkiller.dominium.modell;

import com.mscharhag.oleaster.runner.OleasterRunner;
import de.therapeutenkiller.dominium.testdomäne.TestAggregat;
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss;
import de.therapeutenkiller.dominium.testdomäne.ZustandWurdeGeändert;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.UUID;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OleasterRunner.class)
public class AggregatwurzelTest {

    private TestAggregat subjectUnderTest;

    {
        final UUID identitätsmerkmal = UUID.randomUUID();
        beforeEach(() -> this.subjectUnderTest = new TestAggregat(identitätsmerkmal, Version.NEU));

        describe("Eine Aggregatwurzel", () -> {

            it("hat ein Identitätsmerkmal", () -> {
                assertThat(this.subjectUnderTest.getIdentitätsmerkmal()).isEqualTo(identitätsmerkmal);
            });

            it("hat zu Beginn keine Änderungen", () -> {
                assertThat(this.subjectUnderTest.getÄnderungen()).isEmpty();
            });

            it("kann einen Schnappschuss erstellen", () -> {
                assertThat(this.subjectUnderTest.schnappschussErstellen())
                    .isEqualTo(TestAggregatSchnappschuss.builder()
                    .identitätsmerkmal(identitätsmerkmal)
                    .version(0L) // TODO sollte 1 sein!
                    .payload(0L)
                    .build());
            });

            it("hat eine Initialversion = 1", () -> {
                assertThat(this.subjectUnderTest.getInitialversion())
                    .isEqualTo(0L); // TODO sollte 1 sein.
            });

            Arrays.asList(
                new ZustandWurdeGeändert[]{new ZustandWurdeGeändert(42L)},
                new ZustandWurdeGeändert[]{
                    new ZustandWurdeGeändert(43L),
                    new ZustandWurdeGeändert(44L),
                    new ZustandWurdeGeändert(45)})
                .forEach(testfall -> {
                    final String description = String.format("mit %d Änderungen nacheinander", testfall.length);
                    describe(description, () -> {
                        beforeEach(() -> {
                            Arrays.asList(testfall).forEach(ereignis -> {
                                this.subjectUnderTest.zustandÄndern(ereignis.getPayload());
                            });
                        });

                        it("besitzt " + testfall.length + " Änderungen", () -> {
                            assertThat(this.subjectUnderTest.getÄnderungen()).containsExactly(testfall);
                        });

                        it("wendet Änderungen auf eigenen Zustand an", () -> {
                            assertThat(this.subjectUnderTest.schnappschussErstellen())
                                .isEqualTo(TestAggregatSchnappschuss.builder()
                                    .version(testfall.length)
                                    .identitätsmerkmal(identitätsmerkmal)
                                    .payload(testfall[testfall.length - 1].getPayload())
                                    .build());
                        });
                    });

                    final String description2 = String.format("mit %d Aktualisierungen", testfall.length);
                    describe(description2, () -> {
                        beforeEach(() -> {
                            this.subjectUnderTest.aktualisieren(Arrays.asList(testfall));
                        });

                        it("erhöht die Version des Aggregats", () -> {
                            assertThat(this.subjectUnderTest.getVersion())
                                .isEqualTo(testfall.length);
                        });

                        it("setzt die Initialversion des Aggregats", () -> {
                            assertThat(this.subjectUnderTest.getInitialversion())
                                .isEqualTo(this.subjectUnderTest.getVersion());
                        });

                        it("hat keine Änderungen", () -> {
                            assertThat(this.subjectUnderTest.getÄnderungen())
                                .isEmpty();
                        });
                    });
                });

            describe("aus einem Schnappschuss wiederhergestellt", () -> {
                final UUID anderesIdentitätsmerkmal = UUID.randomUUID();

                beforeEach(() -> {
                    final TestAggregatSchnappschuss schnappschuss = TestAggregatSchnappschuss.builder()
                        .identitätsmerkmal(anderesIdentitätsmerkmal)
                        .payload(4711L)
                        .version(123L)
                        .build();

                    this.subjectUnderTest = new TestAggregat(
                        anderesIdentitätsmerkmal,
                        new Version(schnappschuss.getVersion()));

                    this.subjectUnderTest.wiederherstellenAus(schnappschuss);
                });

                it("erhält die Version aus dem Schnappschuss", () -> {
                    assertThat(this.subjectUnderTest.getVersion())
                        .isEqualTo(123L);
                });

                it("erhält das Identitätsmerkmal aus dem Schnappschuss", () -> {
                    assertThat(this.subjectUnderTest.getIdentitätsmerkmal())
                        .isEqualTo(anderesIdentitätsmerkmal);
                });
            });
        });
    }
}
