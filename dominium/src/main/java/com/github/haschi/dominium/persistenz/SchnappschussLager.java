package com.github.haschi.dominium.persistenz;

import com.github.haschi.dominium.modell.Schnappschuss;

import java.util.Optional;

public interface SchnappschussLager<S extends Schnappschuss, I> {
    Optional<S> getNeuesterSchnappschuss(I id);

    void schnappschussHinzufügen(S testSchnappschuss, final I identitätsmerkmal);
}

