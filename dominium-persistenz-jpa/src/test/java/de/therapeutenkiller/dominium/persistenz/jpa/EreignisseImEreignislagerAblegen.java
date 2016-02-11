package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.ZustandWurdeGeändert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public final class EreignisseImEreignislagerAblegen {

    @Rule
    public final DatenbankRegel dbt = new DatenbankRegel();

    @Before
    public void wenn_ein_neues_ereignislager_mit_ereignissen_angelegt_wird() {

        final TestAggregat aggregat = new TestAggregat(1L);
        aggregat.einenZustandÄndern(42L);
        aggregat.einenZustandÄndern(43L);

        final EntityManager entityManager = this.dbt.getEntityManager();

        final HibernateEventStore<TestAggregat, Long> store = new HibernateEventStore<>(entityManager);
        store.neuenEreignisstromErzeugen("test-strom", aggregat.getÄnderungen());
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void dann_wird_die_versionsnummer_des_ereignisstroms_erhöht() {
        final EntityManager entityManager = this.dbt.getEntityManager();

        final JpaEreignisstrom materialisierterEreignisstrom = entityManager.find(JpaEreignisstrom.class, "test-strom");
        final JpaEreignisstrom x = new JpaEreignisstrom("test-strom");
        x.setVersion(3L);
        assertThat(materialisierterEreignisstrom).isEqualTo(x);

    }

    @Test
    public void dann_werden_die_ereignisse_gespeichert() {
        final EntityManager entityManager = this.dbt.getEntityManager();

        final TypedQuery<JpaDomänenereignisUmschlag> query = entityManager.createQuery(
                "SELECT umschlag from JpaDomänenereignisUmschlag umschlag "
                        + "WHERE umschlag.meta.name = :name "
                        + "ORDER BY umschlag.meta.version",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("name", "test-strom");
        final List<JpaDomänenereignisUmschlag> resultList = query.getResultList();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList.stream().map(JpaDomänenereignisUmschlag<TestAggregat>::öffnen).collect(Collectors.toList()))
                .containsExactly(new ZustandWurdeGeändert(42L), new ZustandWurdeGeändert(43L));
    }
}
