package de.therapeutenkiller.dominium.modell;

public interface SchnappschussQuelle<Aggregat, Identitätsmerkmal> {
    Schnappschuss<Identitätsmerkmal> schnappschussErstellen();
}
