package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptor;
import java.util.Optional;
import java.util.UUID;

@Priority(Interceptor.Priority.APPLICATION + 10)
@Singleton
public final class HibernateHaushaltsbuchRepository implements HaushaltsbuchRepository {

    HaushaltsbuchEventStore eventStore;

    @Inject
    public HibernateHaushaltsbuchRepository(final HaushaltsbuchEventStore eventStore) {

        this.eventStore = eventStore;
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
        this.eventStore.neuenEreignisstromErzeugen(
                this.streamName(haushaltsbuch),
                haushaltsbuch.getÄnderungen());
    }

    private String streamName(final Haushaltsbuch haushaltsbuch) {
        return String.format("%s-%s",
                Haushaltsbuch.class.getName(),
                haushaltsbuch.getIdentitätsmerkmal());
    }

    @Override
    public void save(final Haushaltsbuch haushaltsbuch) {
        this.eventStore.ereignisseDemStromHinzufügen(
                this.streamName(haushaltsbuch),
                haushaltsbuch.getÄnderungen(),
                Optional.of(haushaltsbuch.getVersion()));
    }
}
