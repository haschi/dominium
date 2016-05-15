package de.therapeutenkiller.dominium.memory;

import com.mscharhag.oleaster.runner.OleasterRunner;
import de.therapeutenkiller.dominium.testdomäne.TestAggregat;
import de.therapeutenkiller.dominium.testmittel.Testdaten;
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

            describe("dass leer ist", () -> {
                before(() -> this.lager = new MemorySchnappschussLager<>());

                it("kann keinen Schnappschuss wiederherstellen", () ->
                    assertThat(this.lager.getNeuesterSchnappschuss(identitätsmerkmal)).isEmpty());
            });

            describe("mit mindestens einem Schnappschuss", () -> {

                before(() -> {
                    this.lager = new MemorySchnappschussLager<>();
                    this.lager.schnappschussHinzufügen(Testdaten.getSchnappschuss(identitätsmerkmal));
                });

                it("kann neuesten Schnappschuss wiederherstellen", () ->
                    assertThat(this.lager.getNeuesterSchnappschuss(identitätsmerkmal)
                            .orElseThrow(IllegalStateException::new))
                        .isEqualTo(Testdaten.getSchnappschuss(identitätsmerkmal)));
            });
        });
    }
}
