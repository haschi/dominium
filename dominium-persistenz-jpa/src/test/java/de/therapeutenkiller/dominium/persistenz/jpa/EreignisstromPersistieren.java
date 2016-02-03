package de.therapeutenkiller.dominium.persistenz.jpa;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:designforextension")
@RunWith(CdiTestRunner.class)
@Transactional
public class EreignisstromPersistieren  {

    @Inject
    private EntityManager entityManager;

    @Test
    public void kann_persistiert_werden2() {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom("test-strom");
        // ereignisstrom.setVersion(42L);

        this.entityManager.persist(ereignisstrom);
        this.entityManager.flush();
        this.entityManager.clear();

        final JpaEreignisstrom materialisiert = this.entityManager.find(JpaEreignisstrom.class, "test-strom");
        assertThat(materialisiert).isEqualTo(ereignisstrom);
    }
}
