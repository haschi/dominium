package de.therapeutenkiller.dominium.persistenz;

public final class KonkurrierenderZugriff extends Exception {

    private static final long serialVersionUID = 1080076135868416928L;

    public KonkurrierenderZugriff(final long erwarteteVersion, final long aktuelleVersion) {
        super(String.format("Erwartete Version: %d, Aktuelle Version: %d%n", erwarteteVersion, aktuelleVersion));
    }
}
