package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoMitAnfangsbestandAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchAggregatKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class BuchenSteps {

    private final HaushaltsbuchAggregatKontext kontext;

    @Inject public BuchenSteps(final HaushaltsbuchAggregatKontext kontext) {
        this.kontext = kontext;
    }

    @Angenommen("^mein Haushaltsbuch besitzt folgende Konten:$")
    public void mein_Haushaltsbuch_besitzt_folgende_Konten(final List<Kontostand> kontostände) { // NOPMD Datenfluss

        this.kontext.kommandoAusführen(new HaushaltsbuchführungBeginnenKommando());

        for (final Kontostand kontostand : kontostände) {
            this.kontext.kommandoAusführen(new KontoMitAnfangsbestandAnlegenKommando( // NOPMD
                    this.kontext.aktuelleHaushaltsbuchId(),
                    kontostand.kontoname,
                    kontostand.kontoart,
                    kontostand.betrag));
        }
    }

    @Wenn("^ich das Konto \"([^\"]*)\" mit einem Anfangsbestand von (-?\\d+,\\d{2} [A-Z]{3}) anlege$")
    public void wenn_ich_das_Konto_mit_einem_Anfangsbestand_anlege(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final UUID haushaltsbuchId = this.kontext.aktuelleHaushaltsbuchId();
        this.kontext.kommandoAusführen(new KontoMitAnfangsbestandAnlegenKommando(
                haushaltsbuchId,
                kontoname,
                Kontoart.Aktiv,
                betrag));
    }

    @Dann("^(?:werde ich|ich werde) die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt haben$")
    public void werde_ich_die_Buchung_mit_der_Fehlermeldung_abgelehnt_haben(final BuchungWurdeAbgelehnt fehlermeldung) {

        final List<Domänenereignis<Haushaltsbuch>> stream = this.kontext.getStream(
                this.kontext.aktuelleHaushaltsbuchId());

        assertThat(stream).contains(fehlermeldung); // NOPMD LoD TODO
    }

    @Dann("^(?:ich werde|werde ich) den Buchungssatz \"([^\"]*)\" angelegt haben$")
    public void ich_werde_den_Buchungssatz_angelegt_haben(final String erwarteterBuchungssatz) {

        final List<Domänenereignis<Haushaltsbuch>> stream = this.kontext.getStream(
                this.kontext.aktuelleHaushaltsbuchId());

        final List<Buchungssatz> buchungssätze = stream.stream()
                .filter(ereignis -> ereignis instanceof BuchungWurdeAusgeführt)
                .map(ereignis -> (BuchungWurdeAusgeführt) ereignis)
                .map(BuchungWurdeAusgeführt::getBuchungssatz)
                .collect(Collectors.toList());

        assertThat(buchungssätze.toString()).contains(erwarteterBuchungssatz); // NOPMD LoD TODO
    }
}
