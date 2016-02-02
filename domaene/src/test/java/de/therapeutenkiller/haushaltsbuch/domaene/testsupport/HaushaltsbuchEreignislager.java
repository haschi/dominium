package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.dominium.jpa.HibernateEventStore;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

public final class HaushaltsbuchEreignislager extends HibernateEventStore<Haushaltsbuch, UUID> {

    @Inject
    public HaushaltsbuchEreignislager(final EntityManager entityManager) {
        super(entityManager);
    }
}
