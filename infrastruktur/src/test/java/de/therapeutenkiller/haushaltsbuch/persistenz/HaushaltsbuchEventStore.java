package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.jpa.HibernateEventStore;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class HaushaltsbuchEventStore extends HibernateEventStore<HaushaltsbuchSchnappschuss, Haushaltsbuch> {

    @Inject
    public HaushaltsbuchEventStore(final EntityManager entityManager) {
        super(entityManager);
    }
}
