package com.github.haschi.dominium.testdomaene;

import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Immutable;

@Immutable
public interface AnderesWertobjekt {
    @Auxiliary String strasse();

    String postleitzahl();

    String ort();
}
