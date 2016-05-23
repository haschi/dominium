package com.github.haschi.dominium.testdomäne;

import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Immutable;

@Immutable
public interface AnderesWertobjekt {
    @Auxiliary String strasse();

    String postleitzahl();

    String ort();
}
