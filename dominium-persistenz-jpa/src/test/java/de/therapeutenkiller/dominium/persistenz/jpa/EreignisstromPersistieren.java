package de.therapeutenkiller.dominium.persistenz.jpa;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SuppressWarnings("checkstyle:designforextension")
@RunWith(CdiTestRunner.class)
@Transactional
public class EreignisstromPersistieren  {

    @Inject
    private EntityManager entityManager;

    @Test
    public void ereignisströme_können_persistiert_werden() {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom("test-strom");
        ereignisstrom.setVersion(42L);

        this.entityManager.persist(ereignisstrom);
        this.entityManager.flush();
        this.entityManager.clear();

        final JpaEreignisstrom materialisiert = this.entityManager.find(JpaEreignisstrom.class, "test-strom");
        assertThat(materialisiert).isEqualTo(ereignisstrom);
    }

    @Test
    public void name_des_stroms_muss_eindeutig_sein() {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom("test-strom");
        this.entityManager.persist(ereignisstrom);

        final JpaEreignisstrom doppelt = new JpaEreignisstrom("test-strom");

        assertThatExceptionOfType(EntityExistsException.class)
            .isThrownBy(() -> { this.entityManager.persist(doppelt); })
            .withMessageStartingWith(
                    "A different object with the same identifier value was already associated with the session");
    }

    @Test
    public void ereignisströme_mit_gleichem_namen_können_nicht_gespeichert_werden() {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom("test-strom");
        this.entityManager.persist(ereignisstrom);
        this.entityManager.flush();
        this.entityManager.clear();

        final JpaEreignisstrom doppelt = new JpaEreignisstrom("test-strom");

        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> { this.entityManager.persist(doppelt); })
                .withMessageStartingWith(
                        "A different object with the same identifier value was already associated with the session");
    }
}
