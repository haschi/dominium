package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.ZustandWurdeGeändert;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("checkstyle:designforextension")
@RunWith(CdiTestRunner.class)
@Transactional
public class EreignisseImEreignislagerAblegen {

    class JpaDomänenereignisUmschlagTestAggregat extends JpaDomänenereignisUmschlag<TestAggregat>{}

    @Inject
    EntityManager entityManager;

    @Test
    public void sdfsdf() {

        final TestAggregat aggregat = new TestAggregat(1L);
        aggregat.einenZustandÄndern(42L);
        aggregat.einenZustandÄndern(43L);

        final HibernateEventStore<TestAggregat, Long> store = new HibernateEventStore<>(this.entityManager);

        store.neuenEreignisstromErzeugen("test-strom", aggregat.getÄnderungen());

        this.entityManager.flush();
        this.entityManager.clear();

        final JpaEreignisstrom materialisierterEreignisstrom = this.entityManager.find(JpaEreignisstrom.class, "test-strom");
        final JpaEreignisstrom x = new JpaEreignisstrom("test-strom");
        x.setVersion(3L);
        assertThat(materialisierterEreignisstrom).isEqualTo(x);

        final TypedQuery<JpaDomänenereignisUmschlag> query = this.entityManager.createQuery(
                "SELECT umschlag from JpaDomänenereignisUmschlag umschlag "
                + "WHERE umschlag.meta.name = :name",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("name", "test-strom");
        final List<JpaDomänenereignisUmschlag> resultList = query.getResultList();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList.stream().map(JpaDomänenereignisUmschlag<TestAggregat>::öffnen).collect(Collectors.toList()))
                .containsExactly(new ZustandWurdeGeändert(42L), new ZustandWurdeGeändert(43L));
    }
}
