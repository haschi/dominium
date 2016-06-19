package com.github.haschi.dominium.infrastructure;

public interface ApplicationService<T> {

    void ausführen(T command) throws KonkurrierenderZugriff;
}
