package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.EreignisstromNichtVorhanden;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;
import de.therapeutenkiller.testing.DatenbankRegel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussAusEinemLeerenEreignislagerAbholen {

    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private HibernateEventStore<TestAggregat, Long> store;
    private TestUhr uhr = new TestUhr();

    @Before
    public void angenommen_ich_habe_ein_ereignislager_ohne_schnappschüsse_angelegt() {
        this.store = new HibernateEventStore<>(this.datenbankRegel.getEntityManager(), this.uhr);
        this.store.neuenEreignisstromErzeugen("test-strom", new ArrayList<>());
    }

    @Test
    public void wenn_ich_den_neuesten_schnappschuss_anfordere() throws EreignisstromNichtVorhanden {
        final Optional<Schnappschuss<TestAggregat, Long>> schnappschuss =
                this.store.getNeuesterSchnappschuss("test-strom");

        this.dann_werde_ich_keinen_schnappschuss_erhalten(schnappschuss);
    }

    private void dann_werde_ich_keinen_schnappschuss_erhalten(
            final Optional<Schnappschuss<TestAggregat, Long>> schnappschuss) {

        assertThat(schnappschuss).isEmpty();
    }
}
