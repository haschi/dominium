package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Angenommen;
import de.therapeutenkiller.haushaltsbuch.domaene.api.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.api.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchAggregatKontext;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GeimeinsameSteps {

    private final HaushaltsbuchAggregatKontext kontext;

    @Inject
    public GeimeinsameSteps(
            final HaushaltsbuchAggregatKontext kontext,
            final Logger logger) {
        this.kontext = kontext;

        logger.info("Hello World.");
        logger.warn("Dies ist eine Warnung!");

    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        this.kontext.kommandoAusführen(new HaushaltsbuchführungBeginnenKommando());
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname) {
        this.kontext.kommandoAusführen(new KontoAnlegenKommando(
                this.kontext.aktuellesHaushaltsbuch(),
                kontoname));
    }
}
