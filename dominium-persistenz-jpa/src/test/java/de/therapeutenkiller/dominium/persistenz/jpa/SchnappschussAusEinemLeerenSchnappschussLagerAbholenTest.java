package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestSchnappschuss;
import de.therapeutenkiller.testing.DatenbankRegel;
import de.therapeutenkiller.testing.TestUhr;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussAusEinemLeerenSchnappschussLagerAbholenTest {

    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private JpaSchnappschussLager<TestSchnappschuss, TestAggregat> store;
    private final TestUhr uhr = new TestUhr();
    private final UUID id = UUID.randomUUID();

    @Before
    public void angenommen_ich_habe_ein_ereignislager_ohne_schnappschüsse_angelegt() {
        this.store = new JpaSchnappschussLager<>(
            this.datenbankRegel.getEntityManager(),
            this.uhr);
    }

    @Test
    public void wenn_ich_den_neuesten_schnappschuss_anfordere() throws AggregatNichtGefunden {
        final Optional<TestSchnappschuss> schnappschuss =
                this.store.getNeuesterSchnappschuss(this.id);

        this.dann_werde_ich_keinen_schnappschuss_erhalten(schnappschuss.orElse(TestSchnappschuss.LEER));
    }

    private void dann_werde_ich_keinen_schnappschuss_erhalten(
            final Schnappschuss<TestAggregat, UUID> schnappschuss) {

        assertThat(schnappschuss).isEqualTo(TestSchnappschuss.LEER);
    }
}
