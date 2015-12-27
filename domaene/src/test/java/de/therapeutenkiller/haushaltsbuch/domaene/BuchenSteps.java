package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchAggregatKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class BuchenSteps {

    private final HaushaltsbuchAggregatKontext kontext;
    private final KontoAnlegen kontoAnlegen;
    private BuchungWurdeAbgelehnt buchungsWurdeNichtAusgeführt;
    private BuchungWurdeAusgeführt buchungssatzWurdeAngelegt;

    @Inject public BuchenSteps(
            final HaushaltsbuchAggregatKontext kontext,
            final KontoAnlegen kontoAnlegen) {
        this.kontext = kontext;
        this.kontoAnlegen = kontoAnlegen;
    }

    public void buchungWurdeNichtAusgeführtHandler(@Observes final BuchungWurdeAbgelehnt ereignis) {
        this.buchungsWurdeNichtAusgeführt = ereignis;
    }

    public void buchungssatzWurdeAngelegtHandler(@Observes final BuchungWurdeAusgeführt buchungssatzWurdeAngelegt) {
        this.buchungssatzWurdeAngelegt = buchungssatzWurdeAngelegt;
    }

    @Angenommen("^mein Haushaltsbuch besitzt folgende Konten:$")
    public void mein_Haushaltsbuch_besitzt_folgende_Konten(final List<Kontostand> kontostände) { // NOPMD Dataflow

        this.kontext.kommandoAusführen(new HaushaltsbuchführungBeginnenKommando());

        for (final Kontostand kontostand : kontostände) {
            this.kontoAnlegen.ausführen(
                    this.kontext.aktuellesHaushaltsbuch(),
                    kontostand.kontoname,
                    kontostand.betrag);
        }
    }

    @Wenn("^ich das Konto \"([^\"]*)\" mit einem Anfangsbestand von (-?\\d+,\\d{2} [A-Z]{3}) anlege$")
    public void wenn_ich_das_Konto_mit_einem_Anfangsbestand_anlege(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        this.kontoAnlegen.ausführen(haushaltsbuchId, kontoname, betrag);
    }

    @Dann("^(?:werde ich|ich werde) die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt haben$")
    public void werde_ich_die_Buchung_mit_der_Fehlermeldung_abgelehnt_haben(final String fehlermeldung) {
        assertThat(this.buchungsWurdeNichtAusgeführt).isEqualTo(new BuchungWurdeAbgelehnt(fehlermeldung));
    }

    @Dann("^(?:ich werde|werde ich) den Buchungssatz \"([^\"]*)\" angelegt haben$")
    public void ich_werde_den_Buchungssatz_angelegt_haben(final String erwarteterBuchungssatz) {
        final Buchungssatz aktuellerBuchungssatz = this.buchungssatzWurdeAngelegt.getBuchungssatz();
        assertThat(aktuellerBuchungssatz.toString()).isEqualTo(erwarteterBuchungssatz); // NOPMD LoD TODO
    }
}
