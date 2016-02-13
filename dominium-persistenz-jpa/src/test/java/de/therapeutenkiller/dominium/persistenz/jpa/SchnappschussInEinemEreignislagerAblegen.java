package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestSchnappschuss;
import de.therapeutenkiller.testing.DatenbankRegel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussInEinemEreignislagerAblegen {
    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private HibernateEventStore<TestAggregat, Long> store;
    private TestUhr uhr = new TestUhr();

    private TestSchnappschuss testSchnappschuss = new TestSchnappschuss(
            TestSchnappschuss.createInitializer()
            .identitätsmerkmal(1L)
            .version(1L)
            .zustand(42L));

    @Before
    public void angenommen_ich_habe_einen_ereignisstrom_angelegt() {
        this.store = new HibernateEventStore<>(this.datenbankRegel.getEntityManager(), this.uhr);
        this.store.neuenEreignisstromErzeugen("test-strom", new ArrayList<>());
    }

    @Test
    public void wenn_ich_einen_schnappschuss_ablege() throws IOException {

        this.uhr.stellen(LocalDateTime.now());
        this.store.schnappschussHinzufügen("test-strom", this.testSchnappschuss);
        this.dann_werde_ich_das_aggregat_mit_schnappschuss_wiederherstellen();
    }

    private void dann_werde_ich_das_aggregat_mit_schnappschuss_wiederherstellen() {

        final Optional<Schnappschuss<TestAggregat, Long>> neuesterSchnappschuss = this.store.getNeuesterSchnappschuss(
                "test-strom");

        assertThat(neuesterSchnappschuss).isEqualTo(this.testSchnappschuss);
    }
}
