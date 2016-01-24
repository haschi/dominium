package de.therapeutenkiller.haushaltsbuch.persistenz; // NOPMD

import de.therapeutenkiller.dominium.aggregat.Aggregatwurzel;
import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.aggregat.Entität;
import de.therapeutenkiller.dominium.aggregat.Wertobjekt;
import de.therapeutenkiller.dominium.aggregat.testdomäne.TestAggregat;
import de.therapeutenkiller.dominium.aggregat.testdomäne.ZustandWurdeGeändert;
import de.therapeutenkiller.dominium.jpa.EventSerializer;
import de.therapeutenkiller.dominium.jpa.JpaDomänenereignisUmschlag;
import de.therapeutenkiller.dominium.jpa.JpaEreignisstrom;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;
import de.therapeutenkiller.dominium.lagerung.Ereignisstrom;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.io.File;
import java.util.List;

@RunWith(Arquillian.class)
@Ignore
public final class HibernateEventStoreImContainerTest {

    @PersistenceContext
    private EntityManager entityManager;

    // @Inject
    private UserTransaction userTransaction;

    //@Inject
    //HaushaltsbuchEventStore store;

    @Deployment
    public static WebArchive createDeployment() {

        final File[] aspectj = Maven.resolver()
                .resolve("org.aspectj:aspectjrt:1.8.7")
                .withoutTransitivity()
                .asFile();

        final File[] aspekte = Maven.resolver()
                .resolve("de.therapeutenkiller:aspekte:0.0.24")
                .withoutTransitivity()
                .asFile();

        final File[] commons = Maven.resolver()
                .resolve("org.apache.commons:commons-lang3:3.4")
                .withoutTransitivity()
                .asFile();

        final File[] deltaspikeApi = Maven.resolver()
                .resolve("org.apache.deltaspike.core:deltaspike-core-api:1.5.2")
                .withTransitivity()
                .asFile();

        final File[] deltaspikeImpl = Maven.resolver()
                .resolve("org.apache.deltaspike.core:deltaspike-core-impl:1.5.2")
                .withTransitivity()
                .asFile();

        // System.out.println(jar.toString(true));
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Domänenereignis.class)
                .addClass(Wertobjekt.class)
                .addClass(JpaEreignisstrom.class)
                .addClass(JpaDomänenereignisUmschlag.class)
                .addClass(EventSerializer.class)
                .addClass(DomänenereignisUmschlag.class)
                .addClass(Ereignisstrom.class)
                .addClass(ZustandWurdeGeändert.class)
                .addClass(TestAggregat.class)
                .addClass(Aggregatwurzel.class)
                .addClass(Entität.class)
                .addAsLibraries(aspectj)
                .addAsLibraries(aspekte)
                .addAsLibraries(commons)
                .addAsLibraries(deltaspikeApi)
                .addAsLibraries(deltaspikeImpl)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    public void es_gibt_einen_entity_manager() {
        Assert.assertNotNull(this.entityManager);
    }

    class TestAggregatDomänenereignisUmschlag extends JpaDomänenereignisUmschlag<TestAggregat> {
    }

    @Test
    public void kann_EreignisWrapper_persistieren()
            throws SystemException,
            NotSupportedException,
            HeuristicRollbackException,
            HeuristicMixedException,
            RollbackException {
        this.userTransaction.begin();
        this.entityManager.joinTransaction();

        final ZustandWurdeGeändert ereignis = new ZustandWurdeGeändert(42L);
        final JpaEreignisstrom<TestAggregat> strom = new JpaEreignisstrom<>("Test-Strom");
        final DomänenereignisUmschlag<TestAggregat> umschlag = strom.registerEvent(ereignis);

        this.entityManager.persist(umschlag);
        this.entityManager.flush();

        this.userTransaction.commit();
        this.entityManager.clear();

        final List<JpaDomänenereignisUmschlag> ergebnis = this.entityManager
                .createQuery("SELECT e from JpaDomänenereignisUmschlag e ", JpaDomänenereignisUmschlag.class)
                .getResultList();

        Assert.assertEquals(ergebnis.size(), 1);
    }
}
