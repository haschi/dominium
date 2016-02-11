package de.therapeutenkiller.dominium.persistenz.jpa;

import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;
import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SuppressWarnings("checkstyle:designforextension")
public class EntityManagerProducer {

    @Inject
    @PersistenceUnitName("test")
    private EntityManagerFactory factory;

    @Produces
    @Default
    @RequestScoped
    // @TransactionScoped
    public EntityManager entityManager() {
        return this.factory.createEntityManager();
    }

    public void close(@Disposes @Any final EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}

