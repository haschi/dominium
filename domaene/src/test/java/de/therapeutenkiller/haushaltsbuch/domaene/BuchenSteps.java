package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;
import de.therapeutenkiller.haushaltsbuch.api.kommando.LegeKontoMitAnfangsbestandAn;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public final class BuchenSteps {

    @Inject
    private DieWelt welt;

    @Inject
    private Event<LegeKontoMitAnfangsbestandAn> kontoMitAnfangsbestandAnlegen;

    @Inject
    private Event<BeginneHaushaltsbuchführung> haushaltsbuchführungBeginnen;

    @Angenommen("^mein Haushaltsbuch besitzt folgende Konten:$")
    public void mein_Haushaltsbuch_besitzt_folgende_Konten(final List<Kontostand> kontostände) {

        final UUID identitätsmerkmal = UUID.randomUUID();
        this.haushaltsbuchführungBeginnen.fire(new BeginneHaushaltsbuchführung(identitätsmerkmal));
        this.welt.setAktuelleHaushaltsbuchId(identitätsmerkmal);

        kontostände.stream()
            .map(Kontostand.alsKontoMitKontostandAnlegenKommando(identitätsmerkmal))
            .forEach(kommando -> this.kontoMitAnfangsbestandAnlegen.fire(kommando));
    }

    @Wenn("^ich das Konto \"([^\"]*)\" mit einem Anfangsbestand von (-?\\d+,\\d{2} [A-Z]{3}) anlege$")
    public void wenn_ich_das_Konto_mit_einem_Anfangsbestand_anlege(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final LegeKontoMitAnfangsbestandAn kommando = new LegeKontoMitAnfangsbestandAn(
                this.welt.getAktuelleHaushaltsbuchId(),
                kontoname,
                Kontoart.Aktiv,
                betrag);

        this.kontoMitAnfangsbestandAnlegen.fire(kommando);
    }

    @Dann("^(?:werde ich|ich werde) die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt haben$")
    public void werde_ich_die_Buchung_mit_der_Fehlermeldung_abgelehnt_haben(final BuchungWurdeAbgelehnt fehlermeldung) {

        final List<Domänenereignis<Haushaltsbuch>> stream = this.welt.getStream(
                this.welt.getAktuelleHaushaltsbuchId());

        assertThat(stream).contains(fehlermeldung);
    }

    @Dann("^(?:ich werde|werde ich) den Buchungssatz \"([^\"]*)\" angelegt haben$")
    public void ich_werde_den_Buchungssatz_angelegt_haben(final String erwarteterBuchungssatz) {

        final List<Domänenereignis<Haushaltsbuch>> stream = this.welt.getStream(
                this.welt.getAktuelleHaushaltsbuchId());

        final List<Buchungssatz> buchungssätze = stream.stream()
                .filter(ereignis -> ereignis instanceof BuchungWurdeAusgeführt)
                .map(ereignis -> (BuchungWurdeAusgeführt) ereignis)
                .map(BuchungWurdeAusgeführt::getBuchungssatz)
                .collect(Collectors.toList());

        assertThat(buchungssätze.toString()).contains(erwarteterBuchungssatz);
    }
}
