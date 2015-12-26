package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.KontoSaldieren;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Sollsaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.BuchungssatzHinzufügen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungWurdeNichtAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class AusgabeBuchenSteps {

    private final KontoAnlegen kontoAnlegen;
    private final BuchungssatzHinzufügen buchungssatzHinzufügen;
    private final KontoSaldieren kontoSaldieren;
    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final HaushaltsbuchführungBeginnen haushaltsbuchfhrungBeginnen;
    private BuchungWurdeNichtAusgeführt buchungsWurdeNichtAusgeführt;

    @Inject
    AusgabeBuchenSteps(
            final HaushaltsbuchführungBeginnenKontext kontext,
            final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen,
            final KontoAnlegen kontoAnlegen,
            final BuchungssatzHinzufügen buchungssatzHinzufügen,
            final KontoSaldieren kontoSaldieren) {
        this.kontext = kontext;
        this.haushaltsbuchfhrungBeginnen = haushaltsbuchführungBeginnen;
        this.kontoAnlegen = kontoAnlegen;

        this.buchungssatzHinzufügen = buchungssatzHinzufügen;
        this.kontoSaldieren = kontoSaldieren;
    }

    public void buchungWurdeNichtAusgeführtEreignishandler(@Observes final BuchungWurdeNichtAusgeführt ereignis) {
        this.buchungsWurdeNichtAusgeführt = ereignis;
    }

    @Angenommen("^mein Haushaltsbuch besitzt folgende Konten:$")
    public void ichMeinHaushaltsbuchBesitztFolgendeKonten(final List<Kontostand> kontostände) { // NOPMD Dataflow

        this.haushaltsbuchfhrungBeginnen.ausführen();

        for (final Kontostand kontostand : kontostände) {
            this.kontoAnlegen.ausführen(this.kontext.aktuellesHaushaltsbuch(), kontostand.kontoname);

            this.buchungssatzHinzufügen.ausführen(
                    this.kontext.aktuellesHaushaltsbuch(),
                    "Anfangsbestand",
                    kontostand.kontoname,
                    kontostand.betrag);
        }
    }

    @Wenn("^ich meine Ausgabe von (-{0,1}\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_ausgabe_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto)  {

        this.buchungssatzHinzufügen.ausführen(
                this.kontext.aktuellesHaushaltsbuch(),
                sollkonto,
                habenkonto,
                währungsbetrag);
    }

    @Dann("^werde ich folgende Kontostände erhalten:$")
    public void werdeIchFolgendeKontoständeErhalten(final List<Kontostand> kontostände) { // NOPMD Dataflow

        for (final Kontostand kontostand : kontostände) {
            final Saldo saldo = this.kontoSaldieren.ausführen(
                    this.kontext.aktuellesHaushaltsbuch(),
                    kontostand.kontoname);

            // TODO Besser in einem Konverter
            final Saldo erwartetesSaldo = saldoFürKonto(kontostand);
            assertThat(saldo).isEqualTo(erwartetesSaldo); // NOPMD
        }
    }

    private static Saldo saldoFürKonto(final Kontostand kontostand) {
        if (kontostand.kontoart.equals(Kontoart.Aktiv)) { //NOPMD LoD TODO
            return new Habensaldo(kontostand.betrag); // NOPMD
        } else if (kontostand.kontoart.equals(Kontoart.Ertrag)) { //NOPMD LoD TODO
            return new Sollsaldo(kontostand.betrag); //NOPMD LoD TODO
        } else if (kontostand.kontoart.equals(Kontoart.Aufwand)) { //NOPMD LoD TODO
            return new Habensaldo(kontostand.betrag); //NOPMD LoD TODO
        }
        return null;
    }

    @Dann("^werde ich die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt haben$")
    public void wirdDieBuchungMitDerFehlermeldungAbgelehnt(final String fehlermeldung) {
        assertThat(this.buchungsWurdeNichtAusgeführt).isEqualTo(new BuchungWurdeNichtAusgeführt(fehlermeldung));
    }
}
