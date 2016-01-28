package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.jpa.JpaDomänenereignisUmschlag;
import de.therapeutenkiller.dominium.jpa.JpaEreignisstrom;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregat;
import de.therapeutenkiller.dominium.modell.testdomäne.ZustandWurdeGeändert;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RunWith(CdiTestRunner.class)
@SuppressWarnings("checkstyle:designforextension")
public class HibernateEventStoreOhneContainerTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private ZustandWurdeGeändert geändert;

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
    @Transactional
    public void ereignis_persistieren() {

        final ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(42L);
        final JpaEreignisstrom<TestAggregat> strom = new JpaEreignisstrom<>("Test-Strom");

        final JpaDomänenereignisUmschlag<TestAggregat> umschlag = (JpaDomänenereignisUmschlag<TestAggregat>)
                strom.registerEvent(ereignis);

        final String identitätsmerkmal  = umschlag.getIdentitätsmerkmal();
        this.entityManager.persist(umschlag);

        final DomänenereignisUmschlag umschlag1 = this.entityManager.find(
                JpaDomänenereignisUmschlag.class,
                identitätsmerkmal);

        Assert.assertNotNull(umschlag1);
    }
}
