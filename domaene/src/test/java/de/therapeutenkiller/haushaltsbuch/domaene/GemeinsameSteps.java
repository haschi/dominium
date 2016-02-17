package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchAggregatKontext;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GemeinsameSteps {

    private final HaushaltsbuchAggregatKontext kontext;

    @Before()
    public void containerInitialisieren() {
    }

    @Inject
    public GemeinsameSteps(final HaushaltsbuchAggregatKontext kontext) {
        this.kontext = kontext;
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        this.kontext.kommandoAusführen(new HaushaltsbuchführungBeginnenKommando());
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname) {
        this.kontext.kommandoAusführen(new KontoAnlegenKommando(
                this.kontext.aktuelleHaushaltsbuchId(),
                kontoname,
                Kontoart.Aktiv));
    }
}
