package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Dann;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeNichtAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungssatzWurdeErstellt;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class BuchenSteps {

    private BuchungWurdeNichtAusgeführt buchungsWurdeNichtAusgeführt;
    private BuchungssatzWurdeErstellt buchungssatzWurdeAngelegt;

    public void buchungWurdeNichtAusgeführtHandler(@Observes final BuchungWurdeNichtAusgeführt ereignis) {
        this.buchungsWurdeNichtAusgeführt = ereignis;
    }

    public void buchungssatzWurdeAngelegtHandler(@Observes final BuchungssatzWurdeErstellt buchungssatzWurdeAngelegt) {
        this.buchungssatzWurdeAngelegt = buchungssatzWurdeAngelegt;
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
