package de.therapeutenkiller.dominium.memory;

import com.mscharhag.oleaster.runner.OleasterRunner;
import de.therapeutenkiller.dominium.testdomäne.TestAggregat;
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss;
import org.junit.runner.RunWith;

import java.util.UUID;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.before;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OleasterRunner.class)
public class AggregatAusSchnappschussWiederherstellen {
    private MemorySchnappschussLager<TestAggregat, UUID> lager;

    {
        describe("Ein MemorySchnappschussLager", () -> {

            final UUID identitätsmerkmal = UUID.randomUUID();

            final TestAggregatSchnappschuss schnappschuss = TestAggregatSchnappschuss.builder()
                .identitätsmerkmal(identitätsmerkmal)
                .payload(42L)
                .version(1L)
                .build();


            describe("dass leer ist", () -> {
                before(() -> {
                    this.lager = new MemorySchnappschussLager<>();
                });

                it("kann keinen Schnappschuss wiederherstellen", () -> {
                    assertThat(this.lager.getNeuesterSchnappschuss(identitätsmerkmal)).isEmpty();
                });
            });

            describe("mit mindestens einem Schnappschuss", () -> {

                before(() -> {
                    this.lager = new MemorySchnappschussLager<>();
                    this.lager.schnappschussHinzufügen(schnappschuss);
                });

                it("kann neuesten Schnappschuss wiederherstellen", () -> {
                    assertThat(this.lager.getNeuesterSchnappschuss(identitätsmerkmal).get())
                        .isEqualTo(schnappschuss);
                });
            });
        });
    }
}
