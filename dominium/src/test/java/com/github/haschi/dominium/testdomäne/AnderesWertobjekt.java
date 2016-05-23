package com.github.haschi.dominium.testdomäne;

import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class AnderesWertobjekt {
    @Auxiliary public abstract String strasse();
    public abstract String postleitzahl();
    public abstract String ort();
}
