package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeNichtAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungssatzWurdeErstellt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchführungBeginnenKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class BuchenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;
    private final KontoAnlegen kontoAnlegen;
    private BuchungWurdeNichtAusgeführt buchungsWurdeNichtAusgeführt;
    private BuchungssatzWurdeErstellt buchungssatzWurdeAngelegt;

    @Inject public BuchenSteps(
            final HaushaltsbuchführungBeginnenKontext kontext,
            final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen,
            final KontoAnlegen kontoAnlegen) {
        this.kontext = kontext;
        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
        this.kontoAnlegen = kontoAnlegen;
    }

    public void buchungWurdeNichtAusgeführtHandler(@Observes final BuchungWurdeNichtAusgeführt ereignis) {
        this.buchungsWurdeNichtAusgeführt = ereignis;
    }

    public void buchungssatzWurdeAngelegtHandler(@Observes final BuchungssatzWurdeErstellt buchungssatzWurdeAngelegt) {
        this.buchungssatzWurdeAngelegt = buchungssatzWurdeAngelegt;
    }

    @Angenommen("^mein Haushaltsbuch besitzt folgende Konten:$")
    public void mein_Haushaltsbuch_besitzt_folgende_Konten(final List<Kontostand> kontostände) { // NOPMD Dataflow

        this.haushaltsbuchführungBeginnen.ausführen();

        for (final Kontostand kontostand : kontostände) {
            this.kontoAnlegen.ausführen(
                    this.kontext.aktuellesHaushaltsbuch(),
                    kontostand.kontoname,
                    kontostand.betrag);
        }
    }

    @Dann("^(?:werde ich|ich werde) die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt haben$")
    public void werde_ich_die_Buchung_mit_der_Fehlermeldung_abgelehnt_haben(final String fehlermeldung) {
        assertThat(this.buchungsWurdeNichtAusgeführt).isEqualTo(new BuchungWurdeNichtAusgeführt(fehlermeldung));
    }

    @Dann("^(?:ich werde|werde ich) den Buchungssatz \"([^\"]*)\" angelegt haben$")
    public void ich_werde_den_Buchungssatz_angelegt_haben(final String erwarteterBuchungssatz) {
        final Buchungssatz aktuellerBuchungssatz = this.buchungssatzWurdeAngelegt.getBuchungssatz();
        assertThat(aktuellerBuchungssatz.toString()).isEqualTo(erwarteterBuchungssatz); // NOPMD LoD TODO
    }
}
