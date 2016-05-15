package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregatEreignis;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.TestAggregatEreignisziel;
import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.ZustandWurdeGeändert;
import de.therapeutenkiller.testing.DatenbankRegel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public final class EreignisseInEinemNeuenEreignislagerAblegenTest {

    @Rule
    public final DatenbankRegel datenbankRegel = new DatenbankRegel();

    private static final long[] EREIGNIS_NUTZLAST = {42L, 43L};

    private final UUID id = UUID.randomUUID();

    @Before
    public void wenn_ein_neues_ereignislager_mit_ereignissen_angelegt_wird() {

        final TestAggregat aggregat = new TestAggregat(this.id, 0L);
        aggregat.einenZustandÄndern(EREIGNIS_NUTZLAST[0]);
        aggregat.einenZustandÄndern(EREIGNIS_NUTZLAST[1]);

        final EntityManager entityManager = this.datenbankRegel.getEntityManager();

        final JpaEreignislager<TestAggregatEreignisziel> store = new JpaEreignislager<>(
                entityManager
        );

        store.neuenEreignisstromErzeugen(this.id, aggregat.getÄnderungen());
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void dann_wird_die_versionsnummer_des_ereignisstroms_erhöht() {
        final EntityManager entityManager = this.datenbankRegel.getEntityManager();

        final JpaEreignisstrom rematerialisiert = entityManager.find(JpaEreignisstrom.class, this.id);
        final JpaEreignisstrom einEreignisstrom = new JpaEreignisstrom(this.id);
        einEreignisstrom.setVersion(2L);
        assertThat(rematerialisiert).isEqualTo(einEreignisstrom);
    }

    @Test
    public void dann_werden_die_ereignisse_gespeichert() {
        final EntityManager entityManager = this.datenbankRegel.getEntityManager();

        final TypedQuery<JpaDomänenereignisUmschlag> query = entityManager.createQuery(
                "SELECT umschlag from JpaDomänenereignisUmschlag umschlag "
                        + "WHERE umschlag.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "ORDER BY umschlag.meta.version",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("identitätsmerkmal", this.id);

        final List<TestAggregatEreignis> domänenereignisse = query.getResultList().stream()
                .map(JpaDomänenereignisUmschlag<TestAggregatEreignis>::öffnen)
                .collect(Collectors.toList());

        assertThat(domänenereignisse)
            .containsExactly(
                new ZustandWurdeGeändert(EREIGNIS_NUTZLAST[0]),
                new ZustandWurdeGeändert(EREIGNIS_NUTZLAST[1]));
    }
}
