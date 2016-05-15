package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.modell.Version;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestSchnappschuss;
import de.therapeutenkiller.testing.DatenbankRegel;
import de.therapeutenkiller.testing.TestUhr;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussInEinemSchnappschussLagerAblegenTest {

    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private JpaSchnappschussLager<TestSchnappschuss> store;
    private final TestUhr uhr = new TestUhr();

    private static final long EREIGNIS_NUTZLAST = 42L;

    @SuppressWarnings("LawOfDemeter")
    private final TestSchnappschuss testSchnappschuss = TestSchnappschuss.builder()
            .identitätsmerkmal(UUID.randomUUID())
            .version(Version.NEU.nachfolger())
            .zustand(EREIGNIS_NUTZLAST)
            .get();

    @Before
    public void angenommen_ich_habe_einen_ereignisstrom_angelegt() {
        this.store = new JpaSchnappschussLager<>(this.datenbankRegel.getEntityManager(), this.uhr);
        this.uhr.stellen(LocalDateTime.now());
    }

    @Test
    public void wenn_ich_einen_schnappschuss_ablege() throws Exception {

        this.store.schnappschussHinzufügen(this.testSchnappschuss);
        this.datenbankRegel.getEntityManager().flush();
        this.datenbankRegel.getEntityManager().clear();

        this.dann_werde_ich_das_aggregat_mit_schnappschuss_wiederherstellen();
    }

    private void dann_werde_ich_das_aggregat_mit_schnappschuss_wiederherstellen() {

        final Schnappschuss<UUID> neuesterSchnappschuss = this.store.getNeuesterSchnappschuss(
            this.testSchnappschuss.getIdentitätsmerkmal())
            .orElseThrow(() -> new EntityNotFoundException("Schnappschuss nicht gefunden"));

        assertThat(neuesterSchnappschuss).isEqualTo(this.testSchnappschuss);
    }
}
