package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregatEreignis;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregatEreignisziel;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestSchnappschuss;
import de.therapeutenkiller.testing.DatenbankRegel;
import de.therapeutenkiller.testing.TestUhr;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussAusEinemEreignislagerHolenTest {

    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private JpaEreignislager<TestAggregatEreignis, TestAggregatEreignisziel> store;
    private TestUhr uhr = new TestUhr();
    private UUID id = UUID.randomUUID();

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
    public void angenommen_ich_habe_schnappschüsse_in_einem_ereignislager() throws AggregatNichtGefunden {
        this.store = new JpaEreignislager<>(this.datenbankRegel.getEntityManager());
        this.store.neuenEreignisstromErzeugen(this.id, new ArrayList<>());

        final LocalDateTime jetzt = LocalDateTime.now();
        this.uhr.stellen(jetzt.minusMinutes(1L));
        // this.store.schnappschussHinzufügen(this.id, this.alterSchnappschuss);

        this.uhr.stellen(jetzt.plusMinutes(1L));
        // this.store.schnappschussHinzufügen(this.id, this.neuerSchnappschuss);
    }

    @Test
    public void wenn_ich_den_neuesten_schnappschuss_aus_einem_nicht_vorhandenen_ereignisstrom_lese() {
        // final Throwable thrown = catchThrowable(() -> {
        //    this.store.getNeuesterSchnappschuss(UUID.randomUUID());
        // });

        /// this.dann_werde_ich_eine_EreignisstromNichtVorhanden_ausnahme_erhalten(thrown);
    }

    private void dann_werde_ich_eine_EreignisstromNichtVorhanden_ausnahme_erhalten(final Throwable thrown) {
        assertThat(thrown).isExactlyInstanceOf(AggregatNichtGefunden.class);
    }

    @Test
    public void wenn_ich_den_neusten_schnappschuss_anfordere() throws AggregatNichtGefunden {
        // final Schnappschuss<TestAggregat, UUID> schnappschuss =
        //        this.store.getNeuesterSchnappschuss(this.id).get();

        // this.dann_werde_ich_den_jüngsten_schnappschuss_erhalten(schnappschuss);
    }

    private void dann_werde_ich_den_jüngsten_schnappschuss_erhalten(
            final Schnappschuss<TestAggregat, UUID> schnappschuss) {
        assertThat(schnappschuss).isEqualTo(this.neuerSchnappschuss);
    }
}
