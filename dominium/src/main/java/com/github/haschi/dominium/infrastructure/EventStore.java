package com.github.haschi.dominium.infrastructure;

import com.github.haschi.dominium.modell.Version;

/**
 * Created by matthias on 18.06.16.
 */
public interface EventStore<T, I> {

    void saveEvents(I identitätsmerkmal,
                    Iterable<T> changes,
                    Version version) throws KonkurrierenderZugriff;

    EventStream<T> getEventsForAggregate(I identitätsmerkmal);
}
