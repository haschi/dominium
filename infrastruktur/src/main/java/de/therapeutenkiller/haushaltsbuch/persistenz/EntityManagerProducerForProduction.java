package de.therapeutenkiller.haushaltsbuch.persistenz;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings("checkstyle:designforextension")
public class EntityManagerProducerForProduction {

    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    public EntityManager erzeugen() {
        return this.entityManager;
    }
}
