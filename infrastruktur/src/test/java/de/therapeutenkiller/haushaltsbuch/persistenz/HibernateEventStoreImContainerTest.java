package de.therapeutenkiller.haushaltsbuch.persistenz;

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
import java.io.File;

@RunWith(Arquillian.class)
public final class HibernateEventStoreImContainerTest {

    @Deployment
    public static WebArchive createDeployment() {

        final File[] aspectj = Maven.resolver().resolve("org.aspectj:aspectjrt:1.8.7").withoutTransitivity().asFile();
        final File[] aspekte = Maven.resolver().resolve("de.therapeutenkiller:aspekte:0.0.24").withoutTransitivity().asFile();
        final WebArchive jar =  ShrinkWrap.create(WebArchive.class)

                .addClass(Greeter.class)
                .addAsLibraries(aspectj)
                .addAsLibraries(aspekte)
      //          .addClass(HaushaltsbuchEventStore.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");


        System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    public Greeter greeter;

    //@Inject
    //HaushaltsbuchEventStore store;

    @Test
    public void kann_entity_manager_injizieren() {
        Assert.assertNotNull(this.greeter);
    }
}
