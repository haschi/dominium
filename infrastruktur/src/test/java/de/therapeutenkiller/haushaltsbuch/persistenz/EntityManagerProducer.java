package de.therapeutenkiller.haushaltsbuch.persistenz;

import javax.annotation.Priority;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.interceptor.Interceptor;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Priority(Interceptor.Priority.APPLICATION + 10)
public final class EntityManagerProducer {

    @Produces
    public static EntityManager entityManagerErzeugen() {
        return Persistence.createEntityManagerFactory("test-infrastruktur").createEntityManager();
    }

    public void dispose(@Disposes final EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
