package de.therapeutenkiller.dominium.memory;

import com.mscharhag.oleaster.runner.OleasterRunner;
import de.therapeutenkiller.dominium.testdomäne.TestAggregat;
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss;
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

    private MemorySchnappschussLager<TestAggregatSchnappschuss, TestAggregat, UUID> subjectUnderTest;

    {
        final UUID identitätsmerkmal = UUID.randomUUID();

        final TestAggregatSchnappschuss[] schnappschüsse = {
            TestAggregatSchnappschuss.builder()
                .version(1L)
                .payload(42L)
                .identitätsmerkmal(identitätsmerkmal)
                .build(),

            TestAggregatSchnappschuss.builder()
                .version(2L)
                .payload(43L)
                .identitätsmerkmal(identitätsmerkmal)
                .build(),

            TestAggregatSchnappschuss.builder()
                .version(3L)
                .payload(44L)
                .identitätsmerkmal(identitätsmerkmal)
                .build()
        };

        beforeEach(() -> this.subjectUnderTest = new MemorySchnappschussLager<>());

        describe("Ein Schnappschuss-Lager", () -> {

            describe("ohne Schnappschüsse für ein Aggregat", () -> {
                it("liefert keine Schnappschüsse", () -> {
                    assertThat(this.subjectUnderTest.getNeuesterSchnappschuss(identitätsmerkmal))
                        .isEmpty();
                });
            });

            Arrays.asList(
                new TestAggregatSchnappschuss[] {schnappschüsse[0]},
                new TestAggregatSchnappschuss[] {schnappschüsse[0], schnappschüsse[1], schnappschüsse[2]})
                .forEach(testfall -> {
                    final String description = String.format(
                        "mit %d Schnappschüssen für ein Aggregat",
                        testfall.length);

                    describe(description, () -> {
                        beforeEach(() -> {
                            Arrays.asList(testfall).forEach(schnappschuss -> {
                                this.subjectUnderTest.schnappschussHinzufügen(schnappschuss);
                            });
                        });

                        it("liefert den zuletzt hinzugefügten Schnappschuss", () -> {
                            assertThat(this.subjectUnderTest.getNeuesterSchnappschuss(identitätsmerkmal))
                                .isEqualTo(Optional.of(Arrays.asList(testfall).get(testfall.length - 1)));
                        });

                        describe("wenn es geleert wird", () -> {
                            beforeEach(() -> {
                                this.subjectUnderTest.leeren();
                            });

                            it("liefert keine Schnappschüsse", () -> {
                                assertThat(this.subjectUnderTest.getNeuesterSchnappschuss(identitätsmerkmal))
                                    .isEmpty();
                            });
                        });
                    });
                });
        });
    }
}
