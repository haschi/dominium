package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.Optional;

public interface SchnappschussLager<S extends Schnappschuss<A, I>, A, I> {
    Optional<S> getNeuesterSchnappschuss(I id);

    void schnappschussHinzufügen(S testSchnappschuss);
}

