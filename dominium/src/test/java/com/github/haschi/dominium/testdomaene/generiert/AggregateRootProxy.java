package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;

public interface AggregateRootProxy<T, I> {
    I getId();

    Version getVersion();

    Iterable<T> getUncommittedChanges();

    void markChangesAsCommitted();

    void wiederherstellen(final Iterable<T> ereignisse);
}
