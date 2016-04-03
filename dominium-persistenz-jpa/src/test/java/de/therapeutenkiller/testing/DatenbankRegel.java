package de.therapeutenkiller.testing;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Die Regel bewirkt in einem JUnit Test, dass alle Testmethoden
 * innerhalb einer Datenbank-Transaktion ausgeführt werden.
 */
public class DatenbankRegel implements TestRule {
    private String persistenceUnit = "test";
    private boolean rollback = false;

    private final ThreadLocal<EntityManager> em = new ThreadLocal<>();

    public final Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
                final EntityManager entityManager = emf.createEntityManager();
                DatenbankRegel.this.em.set(entityManager);
                try {
                    DatenbankRegel.this.transaction(x -> {
                        base.evaluate();
                    });
                } finally {
                    entityManager.close();
                    emf.close();
                    DatenbankRegel.this.em.remove();
                }
            }
        };
    }

    public final EntityManager getEntityManager() {
        return this.em.get();
    }

    public DatenbankRegel persistenceUnit(String name) {
        this.persistenceUnit = name;
        return this;
    }

    public DatenbankRegel rollback() {
        this.rollback = true;
        return this;
    }

    public final void transaction(final ThrowingConsumer<EntityManager> task) {
        final EntityManager entityManager = this.em.get();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        boolean cancelled = false;
        try {
            task.accept(entityManager);
        } catch (final RuntimeException e) {
            cancelled = true;
            transaction.rollback();
            throw e;
        } finally {
            if (!cancelled) {
                if (this.rollback) {
                    transaction.rollback();
                } else {
                    transaction.commit();
                }
            }
        }
    }
}
