package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne.TestAggregat;
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne.ZustandWurdeGeändert;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

@RunWith(CdiRunner.class)
@SuppressWarnings("checkstyle:designforextension")
public class HibernateEventStoreOhneContainerTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private ZustandWurdeGeändert geändert;

    @Produces
    public EntityManager createEntityManager() {
        return Persistence
            .createEntityManagerFactory("test")
            .createEntityManager();
    }

    @Test
    public void entityManager_wird_injiziiert() {
        Assert.assertFalse(this.entityManager == null);
    }

    @Test
    @Transactional
    public void geändert_wird_injiziiert() {
        Assert.assertFalse(this.geändert == null);
    }

    @Test
    public void ereignis_persistieren() {

        final ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(42L);
        final JpaEreignisstrom<TestAggregat> strom = new JpaEreignisstrom<>("Test-Strom");

        final JpaDomänenereignisUmschlag<TestAggregat> umschlag = (JpaDomänenereignisUmschlag<TestAggregat>)
                strom.registerEvent(ereignis);

        final String id = umschlag.getIdentitätsmerkmal();
        this.entityManager.persist(umschlag);

        final DomänenereignisUmschlag umschlag1 = this.entityManager.find(
                JpaDomänenereignisUmschlag.class,
                id);

        Assert.assertNotNull(umschlag1);
    }
}
