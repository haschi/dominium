package com.github.haschi.dominium.testdomäne;

import org.immutables.value.Value;

@Value.Immutable
public abstract class EinWertobjekt {

    public abstract String vorname();

    public abstract String nachname();
}
