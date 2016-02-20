package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.persistenz.Repository;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptor;
import java.util.UUID;

@Priority(Interceptor.Priority.APPLICATION + 10)
@Singleton
public final class HibernateHaushaltsbuchRepository
        extends Repository<Haushaltsbuch, UUID>
        implements HaushaltsbuchRepository {

    @Inject
    public HibernateHaushaltsbuchRepository(final HaushaltsbuchEreignislager eventStore) {
        super(eventStore);
    }

    @Override
    protected Haushaltsbuch neuesAggregatErzeugen(final UUID identitätsmerkmal) {
        return new Haushaltsbuch(identitätsmerkmal);
    }

    @Override
    protected String streamNameFor(final UUID identitätsmerkmal) {
        return String.format("%s-%s",
                Haushaltsbuch.class.getName(),
                identitätsmerkmal);
    }
}
