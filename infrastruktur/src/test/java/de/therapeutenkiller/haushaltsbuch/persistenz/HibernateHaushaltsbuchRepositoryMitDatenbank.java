package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import junit.framework.Assert;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@RunWith(CdiTestRunner.class)
public final class HibernateHaushaltsbuchRepositoryMitDatenbank {

    @Inject
    private EntityManager entityManager; // NOPMD

    @Inject
    private HaushaltsbuchRepository repository; // NOPMD

    @Test
    @Transactional
    public void haushaltsbuch_wird_persistiert() {
        Assert.assertNotNull(this.repository);
    }
}
