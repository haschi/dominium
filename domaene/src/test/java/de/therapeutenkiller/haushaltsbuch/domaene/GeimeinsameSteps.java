package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Angenommen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GeimeinsameSteps {

    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;

    @Inject
    public GeimeinsameSteps(
        final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen,
        final Logger logger) {

        logger.info("Hello World.");
        logger.warn("Dies ist eine Warnung!");
        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        this.haushaltsbuchführungBeginnen.ausführen();
    }
}
