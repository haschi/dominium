package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregatEreignisziel;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.ZustandWurdeGeändert;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:designforextension")
@RunWith(CdiTestRunner.class)
@Transactional
public class DomänenereignisUmschlagPersistierenTest {

    @Inject
    EntityManager entityManager;

    private UUID id = UUID.randomUUID();

    @Test
    public void domänenereignis_umschlag_kann_persistiert_werden() {
        final Domänenereignis<TestAggregatEreignisziel> ereignis = new ZustandWurdeGeändert(42L);
        final JpaEreignisMetaDaten<UUID> meta = new JpaEreignisMetaDaten<>(this.id, 1L);
        final JpaDomänenereignisUmschlag<TestAggregatEreignisziel> umschlag = new JpaDomänenereignisUmschlag<>(
                ereignis,
                meta);

        this.entityManager.persist(umschlag);

        this.entityManager.flush();
        this.entityManager.clear();

        final JpaDomänenereignisUmschlag materialisiert = this.entityManager.find(
                JpaDomänenereignisUmschlag.class, meta);

        assertThat(materialisiert).isEqualTo(umschlag);
    }
}
