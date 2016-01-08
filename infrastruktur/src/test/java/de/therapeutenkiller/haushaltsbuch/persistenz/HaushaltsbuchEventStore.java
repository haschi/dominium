package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSnapshot;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class HaushaltsbuchEventStore extends HibernateEventStore<HaushaltsbuchSnapshot, Haushaltsbuch> {

    @Inject
    public HaushaltsbuchEventStore(final EntityManager entityManager) {
        super(entityManager);
    }
}
