package de.therapeutenkiller.haushaltsbuch.domaene.support;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

// E: Snapshot-Typ, A: Aggregat-Typ
interface EreignisLager<E, A> {

    void createNewStream(String streamName, Collection<Domänenereignis<A>> domainEvents);

    void appendEventsToStream(
            String streamName,
            Collection<Domänenereignis<A>> domainEvents,
            Optional<Integer> expectedVersion);

    List<Domänenereignis<A>> getStream(String streamName, int fromVersion, int toVersion);

    void addSnapshot(String streamName, E snapshot);

    @DarfNullSein
    E getLatestSnapshot(String streamName);

    /**
     * Liefert das Ereignis, mit dem ein Aggregat erzeugt worden ist.
     * @param streamName Name des Streams für das Aggregat
     * @return Das Initialereignis.
     */
    Domänenereignis<A> getInitialEvent(String streamName);
}
