package de.therapeutenkiller.dominium.persistenz;

public final class KonkurrierenderZugriff extends Exception {
    public KonkurrierenderZugriff(final long erwarteteVersion, final long aktuelleVersion) {
        super(String.format("Erwartete Version: %d, Aktuelle Version: %d%n", erwarteteVersion, aktuelleVersion));
    }
}
