package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Angenommen;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;
import de.therapeutenkiller.haushaltsbuch.api.kommando.LegeKontoAn;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.UUID;

public final class GemeinsameSteps {

    @Inject
    private DieWelt welt;

    @Inject
    private Event<BeginneHaushaltsbuchführung> haushaltsbuchführungBeginnen;

    @Inject
    private Event<LegeKontoAn> kontoAnlegen;

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        final UUID identitätsmerkmal = UUID.randomUUID();
        this.haushaltsbuchführungBeginnen.fire(new BeginneHaushaltsbuchführung(identitätsmerkmal));
        this.welt.setAktuelleHaushaltsbuchId(identitätsmerkmal);
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname) {

        final LegeKontoAn kommando = new LegeKontoAn(
                this.welt.getAktuelleHaushaltsbuchId(),
                kontoname,
                Kontoart.Aktiv);

        this.kontoAnlegen.fire(kommando);
    }
}
