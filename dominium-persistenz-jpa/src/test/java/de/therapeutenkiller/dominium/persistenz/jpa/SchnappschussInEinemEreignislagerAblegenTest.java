package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregatEreignis;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregatEreignisziel;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestSchnappschuss;
import de.therapeutenkiller.testing.DatenbankRegel;
import de.therapeutenkiller.testing.TestUhr;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussInEinemEreignislagerAblegenTest {
    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private JpaEreignislager<TestAggregatEreignis, TestAggregatEreignisziel> store;
    private TestUhr uhr = new TestUhr();
    private UUID id = UUID.randomUUID();

    @SuppressWarnings("LawOfDemeter")
    private TestSchnappschuss testSchnappschuss = new TestSchnappschuss(/*
            TestSchnappschuss.createInitializer()
            .identitätsmerkmal(1L)
            .version(1L)
            .zustand(42L)*/);

    @Before
    public void angenommen_ich_habe_einen_ereignisstrom_angelegt() {
        this.store = new JpaEreignislager<>(this.datenbankRegel.getEntityManager(), this.uhr);
        this.store.neuenEreignisstromErzeugen(this.id, new ArrayList<>());

        this.uhr.stellen(LocalDateTime.now());
    }

    @Test
    public void wenn_ich_einen_schnappschuss_ablege() throws IOException, AggregatNichtGefunden {

        //this.store.schnappschussHinzufügen(this.id, this.testSchnappschuss);
        this.dann_werde_ich_das_aggregat_mit_schnappschuss_wiederherstellen();
    }

    private void dann_werde_ich_das_aggregat_mit_schnappschuss_wiederherstellen() throws AggregatNichtGefunden {

        //final Schnappschuss<TestAggregat, UUID> neuesterSchnappschuss =
        //        this.store.getNeuesterSchnappschuss(this.id).get();

        //assertThat(neuesterSchnappschuss).isEqualTo(this.testSchnappschuss);
    }

    @Test
    public void wenn_ich_einen_schnappschuss_in_einem_nicht_vorhandenen_ereignisstrom_ablege() throws IOException {
        //final Throwable thrown = catchThrowable(() -> {
        //    this.store.schnappschussHinzufügen(UUID.randomUUID(), this.testSchnappschuss);
        //});

        //this.dann_werde_ich_eine_ereignisstromNichtVorhanden_ausnahme_erhalten(thrown);
    }

    private void dann_werde_ich_eine_ereignisstromNichtVorhanden_ausnahme_erhalten(final Throwable thrown) {
        assertThat(thrown)
                .isExactlyInstanceOf(AggregatNichtGefunden.class);
    }
}
