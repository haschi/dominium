package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignisstrom;
import de.therapeutenkiller.haushaltsbuch.domaene.support.EventSerializer;
import de.therapeutenkiller.haushaltsbuch.domaene.support.EventWrapper;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.TestEreignis;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
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
import java.util.UUID;

@RunWith(Arquillian.class)
public final class HibernateEventStoreImContainerTest {

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

        final WebArchive jar =  ShrinkWrap.create(WebArchive.class)

                .addClass(Greeter.class)
                .addClass(Domänenereignis.class)
                .addClass(TestEreignis.class)
                .addClass(Wertobjekt.class)
                .addClass(Ereignisstrom.class)
                .addClass(EventWrapper.class)
                .addClass(EventSerializer.class)
                .addAsLibraries(aspectj)
                .addAsLibraries(aspekte)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");


        System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    public Greeter greeter;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserTransaction userTransaction;

    //@Inject
    //HaushaltsbuchEventStore store;

    @Test
    public void kann_entity_manager_injizieren() {
        Assert.assertNotNull(this.greeter);
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

        final TestEreignis ereignis = new TestEreignis("Matthias", "Haschka");
        final Ereignisstrom strom = new Ereignisstrom("Test-Strom");
        final EventWrapper<UUID> wrapper = strom.registerEvent(ereignis);

        this.entityManager.persist(wrapper);

        this.userTransaction.commit();
        this.entityManager.clear();

        final List<EventWrapper> ergebnis = this.entityManager
                .createQuery("SELECT e from EventWrapper e ", EventWrapper.class)
                .getResultList();

        Assert.assertEquals(ergebnis.size(), 1);
    }
}
