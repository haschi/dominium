package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

public interface Schnappschuss<A extends Aggregatwurzel<A, T>, T> {
    T getIdentitätsmerkmal();

    long getVersion();

    /**
     * Erzeugt aus dem Schnappschuss ein Aggregat.
     * TODO: Die Methode prüfen, in wie weit LoD durch Anwendung verletzt wird.
     *
     * @return Ein Aggregat
     */
    A materialisieren();
}
