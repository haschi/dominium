package de.therapeutenkiller.support;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public final class EntityManagerProducer {

    // @PersistenceUnit
    // private EntityManagerFactory factory; // NOPMD

    @Produces
    public EntityManager erzeugen() {
        return Persistence
                .createEntityManagerFactory("test")
                .createEntityManager();

        // return this.factory.createEntityManager();
    }
}
