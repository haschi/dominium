package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.EreignisstromNichtVorhanden;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestSchnappschuss;
import de.therapeutenkiller.testing.DatenbankRegel;
import de.therapeutenkiller.testing.TestUhr;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public final class SchnappschussAusEinemEreignislagerHolenTest {

    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private JpaEreignislager<TestAggregat, Long> store;
    private TestUhr uhr = new TestUhr();

    @SuppressWarnings("LawOfDemeter")
    private TestSchnappschuss alterSchnappschuss = new TestSchnappschuss(/*
            TestSchnappschuss.createInitializer()
                    .identitätsmerkmal(1L)
                    .version(1L)
                    .zustand(42L)*/);

    @SuppressWarnings("LawOfDemeter")
    private TestSchnappschuss neuerSchnappschuss = new TestSchnappschuss(/*
            TestSchnappschuss.createInitializer()
                    .identitätsmerkmal(1L)
                    .version(2L)
                    .zustand(43L)*/);

    @Before
    public void angenommen_ich_habe_schnappschüsse_in_einem_ereignislager() throws EreignisstromNichtVorhanden {
        this.store = new JpaEreignislager<>(this.datenbankRegel.getEntityManager(), this.uhr);
        this.store.neuenEreignisstromErzeugen("test-strom", new ArrayList<>());

        final LocalDateTime jetzt = LocalDateTime.now();
        this.uhr.stellen(jetzt.minusMinutes(1L));
        this.store.schnappschussHinzufügen("test-strom", this.alterSchnappschuss);

        this.uhr.stellen(jetzt.plusMinutes(1L));
        this.store.schnappschussHinzufügen("test-strom", this.neuerSchnappschuss);
    }

    @Test
    public void wenn_ich_den_neuesten_schnappschuss_aus_einem_nicht_vorhandenen_ereignisstrom_lese() {
        final Throwable thrown = catchThrowable(() -> {
            this.store.getNeuesterSchnappschuss("nicht-vorhanden");
        });

        this.dann_werde_ich_eine_EreignisstromNichtVorhanden_ausnahme_erhalten(thrown);
    }

    private void dann_werde_ich_eine_EreignisstromNichtVorhanden_ausnahme_erhalten(final Throwable thrown) {
        assertThat(thrown).isExactlyInstanceOf(EreignisstromNichtVorhanden.class);
    }

    @Test
    public void wenn_ich_den_neusten_schnappschuss_anfordere() throws EreignisstromNichtVorhanden {
        final Schnappschuss<TestAggregat, Long> schnappschuss =
                this.store.getNeuesterSchnappschuss("test-strom").get();

        this.dann_werde_ich_den_jüngsten_schnappschuss_erhalten(schnappschuss);
    }

    private void dann_werde_ich_den_jüngsten_schnappschuss_erhalten(
            final Schnappschuss<TestAggregat, Long> schnappschuss) {
        assertThat(schnappschuss).isEqualTo(this.neuerSchnappschuss);
    }
}
