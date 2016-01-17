package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.DomänenereignisUmschlag;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignisstrom;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
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


        // System.out.println(jar.toString(true));
        return ShrinkWrap.create(WebArchive.class)

                .addClass(Greeter.class)
                .addClass(Domänenereignis.class)
                .addClass(Wertobjekt.class)
                .addClass(JpaEreignisstrom.class)
                .addClass(JpaDomänenereignisUmschlag.class)
                .addClass(EventSerializer.class)
                .addClass(DomänenereignisUmschlag.class)
                .addClass(Ereignisstrom.class)
                .addAsLibraries(aspectj)
                .addAsLibraries(aspekte)
                .addAsLibraries(commons)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @Ignore
    public void kann_EreignisWrapper_persistieren()
            throws SystemException,
            NotSupportedException,
            HeuristicRollbackException,
            HeuristicMixedException,
            RollbackException {
        this.userTransaction.begin();
        this.entityManager.joinTransaction();

        // final EreignisWurdeGeworfen ereignis = new EreignisWurdeGeworfen("Matthias", "Haschka");
        final JpaEreignisstrom<UUID> strom = new JpaEreignisstrom<>("Test-Strom");
        // final DomänenereignisUmschlag<UUID> wrapper = strom.registerEvent(ereignis);

        // this.entityManager.persist(wrapper);

        this.userTransaction.commit();
        this.entityManager.clear();

        final List<JpaDomänenereignisUmschlag> ergebnis = this.entityManager
                .createQuery("SELECT e from JpaDomänenereignisUmschlag e ", JpaDomänenereignisUmschlag.class)
                .getResultList();

        Assert.assertEquals(ergebnis.size(), 1);
    }
}
