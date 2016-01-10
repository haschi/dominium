package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.EreignisLager;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Ereignisstrom;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Umschlag;
import org.apache.commons.lang3.NotImplementedException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class HibernateEventStore<E, A> implements EreignisLager<E, A> {

    public final EntityManager entityManager;

    @Inject
    public HibernateEventStore(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public final void createNewStream(final String streamName, final Collection<Domänenereignis<A>> domainEvents) {
        final Ereignisstrom ereignisstrom = new Ereignisstrom(streamName);
        this.entityManager.persist(ereignisstrom);
        this.appendEventsToStream(streamName, domainEvents, Optional.empty());
    }

    @Override
    public final void appendEventsToStream(
            final String streamName,
            final Collection<Domänenereignis<A>> domainEvents,
            final Optional<Integer> expectedVersion) {

        final Ereignisstrom<A> strom = (Ereignisstrom<A>)this.entityManager.find(Ereignisstrom.class, streamName);

        if (expectedVersion.isPresent()) {
            this.checkForConcurrencyError(expectedVersion.get(), strom);
        }

        for (final Domänenereignis<A> ereignis : domainEvents) {
            final Umschlag<A> wrappedEvent = strom.registerEvent(ereignis);
            this.entityManager.persist(wrappedEvent);
        }
    }

    private void checkForConcurrencyError(final int expectedVersion, final Ereignisstrom stream) {
        final int lastUpdatedVersion = stream.getVersion();

        if (lastUpdatedVersion != expectedVersion) {
            final String error = String.format("Expected: %d. Found: %d", expectedVersion, lastUpdatedVersion);
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public final List<Domänenereignis<A>> getStream(
            final String streamName,
            final int fromVersion,
            final int toVersion) {
        /*final TypedQuery<EventWrapper<A>> query = this.entityManager.createQuery(
                "SELECT c FROM EventWrapper c", HaushaltsbuchEreignis.class);


        final List<EventWrapper<A>> wrapperList = query.getResultList();
        final List<Domänenereignis<A>> ld = wrapperList.stream()
                .map(wrapper -> EventSerializer.deserialize(wrapper.ereignis))
                .collect(Collectors.toList());*/
        return null;
    }

    @Override
    public final void addSnapshot(final String streamName, final E snapshot) {
        throw new NotImplementedException("Nicht implementiert.");
    }

    @Override
    public final E getLatestSnapshot(final String streamName) {
        return null;
    }

    @Override
    public final Domänenereignis<A> getInitialEvent(final String streamName) {
        return null;
    }
}
