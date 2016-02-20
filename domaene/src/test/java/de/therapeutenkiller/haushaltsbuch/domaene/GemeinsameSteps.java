package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class GemeinsameSteps {

    private final DieWelt kontext;

    @Before()
    public void containerInitialisieren() {
    }

    @Inject
    public GemeinsameSteps(final DieWelt kontext) {
        this.kontext = kontext;
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        final UUID identitätsmerkmal = UUID.randomUUID();
        this.kontext.kommandoAusführen(new HaushaltsbuchführungBeginnenKommando(identitätsmerkmal));
        this.kontext.setAktuelleHaushaltsbuchId(identitätsmerkmal);
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname) {
        this.kontext.kommandoAusführen(new KontoAnlegenKommando(
                this.kontext.getAktuelleHaushaltsbuchId(),
                kontoname,
                Kontoart.Aktiv));
    }
}
