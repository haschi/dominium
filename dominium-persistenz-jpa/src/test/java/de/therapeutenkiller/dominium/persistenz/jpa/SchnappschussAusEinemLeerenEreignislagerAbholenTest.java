package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregatEreignisziel;
import de.therapeutenkiller.testing.DatenbankRegel;
import de.therapeutenkiller.testing.TestUhr;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussAusEinemLeerenEreignislagerAbholenTest {

    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private JpaEreignislager<TestAggregat, TestAggregatEreignisziel> store;
    private TestUhr uhr = new TestUhr();
    private UUID id = UUID.randomUUID();

    @Before
    public void angenommen_ich_habe_ein_ereignislager_ohne_schnappschüsse_angelegt() {
        this.store = new JpaEreignislager<>(this.datenbankRegel.getEntityManager(), this.uhr);
        this.store.neuenEreignisstromErzeugen(this.id, new ArrayList<>());
    }

    @Test
    public void wenn_ich_den_neuesten_schnappschuss_anfordere() throws AggregatNichtGefunden {
        final Optional<Schnappschuss<TestAggregat, UUID, TestAggregatEreignisziel>> schnappschuss =
                this.store.getNeuesterSchnappschuss(this.id);

        this.dann_werde_ich_keinen_schnappschuss_erhalten(schnappschuss);
    }

    private void dann_werde_ich_keinen_schnappschuss_erhalten(
            final Optional<Schnappschuss<TestAggregat, UUID, TestAggregatEreignisziel>> schnappschuss) {

        assertThat(schnappschuss).isEmpty();
    }
}
