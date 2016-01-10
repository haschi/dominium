package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.JpaEreignisstrom;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import org.apache.commons.lang3.NotImplementedException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

public final class HibernateHaushaltsbuchRepository implements HaushaltsbuchRepository {

    private final EntityManager entityManager;

    @Inject
    public HibernateHaushaltsbuchRepository(final EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public void leeren() {
        throw new NotImplementedException("Nicht implementiert.");
    }

    @Override
    public Haushaltsbuch findBy(final UUID identitätsmerkmal) {
        return null;
    }

    @Override
    public void add(final Haushaltsbuch haushaltsbuch) {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(this.streamName(haushaltsbuch));
        this.entityManager.persist(ereignisstrom);

        haushaltsbuch.getÄnderungen().forEach(this.entityManager::persist);
    }

    private String streamName(final Haushaltsbuch haushaltsbuch) {
        return String.format("%s-%s", Haushaltsbuch.class.getName(), haushaltsbuch.getIdentitätsmerkmal());
    }

    @Override
    public void save(final Haushaltsbuch haushaltsbuch) {
        throw new NotImplementedException("Nicht implementiert");
    }
}
