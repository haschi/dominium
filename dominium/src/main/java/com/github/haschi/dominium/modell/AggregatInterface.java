package com.github.haschi.dominium.modell;

import java.util.List;

public interface AggregatInterface<T> {

    void aktualisieren(List<Domänenereignis<T>> stream);

    long getVersion();

    void setInitialversion(long initialversion);

    long getInitialversion();
}
