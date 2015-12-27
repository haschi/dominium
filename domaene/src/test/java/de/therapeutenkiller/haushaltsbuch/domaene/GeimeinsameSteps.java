package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Angenommen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchführungBeginnenKontext;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GeimeinsameSteps {

    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;
    private final KontoAnlegen kontoAnlegen;
    private final HaushaltsbuchführungBeginnenKontext kontext;

    @Inject
    public GeimeinsameSteps(
            final HaushaltsbuchführungBeginnenKontext kontext,
            final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen,
            final KontoAnlegen kontoAnlegen,
            final Logger logger) {
        this.kontext = kontext;

        logger.info("Hello World.");
        logger.warn("Dies ist eine Warnung!");

        this.kontoAnlegen = kontoAnlegen;
        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        this.haushaltsbuchführungBeginnen.ausführen();
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname) {
        this.kontoAnlegen.ausführen(this.kontext.aktuellesHaushaltsbuch(), kontoname);
    }
}
