package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.KontoSaldieren;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.BuchungssatzHinzufügen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeNichtAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matthias on 23.12.15.
 */
@Singleton
public final class AusgabenBuchenSteps {

    private final KontoAnlegen kontoAnlegen;
    private final BuchungssatzHinzufügen buchungssatzHinzufügen;
    private final KontoSaldieren kontoSaldieren;
    private final HaushaltsbuchführungBeginnen haushaltsbuchfhrungBeginnen;
    private UUID haushaltsbuchId;
    private BuchungWurdeNichtAusgeführt buchungsWurdeNichtAusgeführt;

    @Inject
    AusgabenBuchenSteps(
            final HaushaltsbuchführungBeginnen haushaltsbuchfhrungBeginnen,
            final KontoAnlegen kontoAnlegen,
            final BuchungssatzHinzufügen buchungssatzHinzufügen,
            final KontoSaldieren kontoSaldieren) {
        this.haushaltsbuchfhrungBeginnen = haushaltsbuchfhrungBeginnen;
        this.kontoAnlegen = kontoAnlegen;

        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
        this.kontoSaldieren = kontoSaldieren;
    }

    public void haushaltsbuchbuchWurdeAngelegt(@Observes final HaushaltsbuchWurdeAngelegt ereignis) {
        this.haushaltsbuchId = ereignis.haushaltsbuch.getIdentität(); // NOPMD
    }

    public void buchungWurdeNichtAusgeführtEreignishandler(@Observes final BuchungWurdeNichtAusgeführt ereignis) {
        this.buchungsWurdeNichtAusgeführt = ereignis;
    }

    @Angenommen("^ich mein Haushaltsbuch besitzt folgende Konten:$")
    public void ichMeinHaushaltsbuchBesitztFolgendeKonten(final List<Kontostand> kontostände) { // NOPMD Dataflow

        this.haushaltsbuchfhrungBeginnen.ausführen();

        for (final Kontostand kontostand : kontostände) {
            this.kontoAnlegen.ausführen(this.haushaltsbuchId, kontostand.kontoname);

            this.buchungssatzHinzufügen.ausführen(
                    this.haushaltsbuchId,
                    "Anfangsbestand",
                    kontostand.kontoname,
                    kontostand.betrag);
        }
    }

    @Wenn("^ich (-{0,1}\\d+,\\d{2} [A-Z]{3}) vom \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void ichEurVomAnBuche(
            @Transform(MoneyConverter.class) final MonetaryAmount arg0,
            final String sollkonto,
            final String habenkonto)  {

        this.buchungssatzHinzufügen.ausführen(this.haushaltsbuchId, sollkonto, habenkonto, arg0);
    }

    @Dann("^werde ich folgende Kontostände erhalten:$")
    public void werdeIchFolgendeKontoständeErhalten(final List<Kontostand> kontostände) { // NOPMD Dataflow

        for (final Kontostand kontostand : kontostände) {
            final Saldo saldo = this.kontoSaldieren.ausführen(this.haushaltsbuchId, kontostand.kontoname);

            // TODO Besser in einem Konverter
            final Habensaldo erwartetesHabensaldo = new Habensaldo(kontostand.betrag); // NOPMD
            assertThat(saldo).isEqualTo(erwartetesHabensaldo); // NOPMD
        }
    }

    @Dann("^wird die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt$")
    public void wirdDieBuchungMitDerFehlermeldungAbgelehnt(final String fehlermeldung) {
        assertThat(this.buchungsWurdeNichtAusgeführt).isEqualTo(new BuchungWurdeNichtAusgeführt(fehlermeldung));
    }
}
