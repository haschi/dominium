package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoAn;
import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import cucumber.api.PendingException;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;

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

    @Dann("^werde ich einen Pending Step haben$")
    public void werdeIchEinenPendingStepHaben() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
