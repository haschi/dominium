package de.therapeutenkiller.support;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public final class EntityManagerProducer {

    @PersistenceUnit
    private EntityManagerFactory factory; // NOPMD

    @Produces
    public EntityManager erzeugen() {
        return Persistence
            .createEntityManagerFactory("test")
            .createEntityManager();

        // return this.factory.createEntityManager();
    }

    public void dispose(@Disposes final EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
