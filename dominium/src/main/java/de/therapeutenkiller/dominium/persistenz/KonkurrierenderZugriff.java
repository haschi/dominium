package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.memory.MemoryEreignisstrom;
import de.therapeutenkiller.dominium.modell.Aggregatwurzel;

public final class KonkurrierenderZugriff extends Exception {

    private final String ereignisstrom;
    private final long erwarteteVersion;
    private final long aktuelleVersion;

    public <A extends Aggregatwurzel<A, I>, I> KonkurrierenderZugriff(
            final MemoryEreignisstrom<A> ereignisstrom,
            final long erwarteteVersion) {
        this.erwarteteVersion = erwarteteVersion;
        this.ereignisstrom = ereignisstrom.toString();
        this.aktuelleVersion = ereignisstrom.getVersion();
    }

    @Override
    public String toString() {
        return String.format("Konkurrierender Zugriff für %s Version %d: erwartete Version %d",
                this.ereignisstrom,
                this.aktuelleVersion,
                this.erwarteteVersion);
    }
}
