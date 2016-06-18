package com.github.haschi.dominium.infrastructure;

import com.github.haschi.dominium.modell.Version;

public abstract class Repository<T, I, P extends AggregateRootProxy<T, I>> {

    protected final EventStore<T, I> storage;

    public Repository(final EventStore<T, I> storage) {
        super();
        this.storage = storage;
    }

    public final P getById(final I identitätsmerkmal) {
        final EventStream<T> stream = this.storage.getEventsForAggregate(identitätsmerkmal);
        final P proxy = this.create(identitätsmerkmal, stream.version());

        proxy.wiederherstellen(stream.events());

        return proxy;
    }

    public abstract P create(I identitätsmerkmal, Version version);

    public final void save(final P aggregat) throws KonkurrierenderZugriff {
        final Iterable<T> changes = aggregat.getUncommittedChanges();
        this.storage.saveEvents(aggregat.getId(), changes, aggregat.getVersion());
        aggregat.markChangesAsCommitted();
    }
}
