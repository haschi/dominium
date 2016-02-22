package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.haushaltsbuch.abfrage.SaldoAbfrage;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Sollsaldo;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheAusgabe;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class AusgabeBuchenSteps {

    @Inject
    private SaldoAbfrage kontoSaldieren;

    @Inject
    private DieWelt welt;

    @Inject
    private Event<BucheAusgabe> ausgabeBuchen;

    @Wenn("^ich meine Ausgabe von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_ausgabe_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto)  {

        final BucheAusgabe bucheAusgabe = new BucheAusgabe(
                this.welt.getAktuelleHaushaltsbuchId(),
                sollkonto,
                habenkonto,
                währungsbetrag
        );

        this.ausgabeBuchen.fire(bucheAusgabe);
    }

    @Dann("^werde ich folgende Kontostände erhalten:$")
    public void dann_werde_ich_folgende_Kontostände_erhalten(final List<Kontostand> kontostände)
            throws AggregatNichtGefunden {

        for (final Kontostand kontostand : kontostände) {
            final Saldo saldo = this.kontoSaldieren.abfragen(
                    this.welt.getAktuelleHaushaltsbuchId(),
                    kontostand.kontoname);

            // TODO Besser in einem Konverter
            final Saldo erwartetesSaldo = saldoFürKonto(kontostand);
            assertThat(saldo).isEqualTo(erwartetesSaldo); // NOPMD
        }
    }

    private static Saldo saldoFürKonto(final Kontostand kontostand) {
        if (kontostand.kontoart.equals(Kontoart.Aktiv)) { //NOPMD LoD TODO
            return new Sollsaldo(kontostand.betrag); // NOPMD
        } else if (kontostand.kontoart.equals(Kontoart.Ertrag)) { //NOPMD LoD TODO
            return new Habensaldo(kontostand.betrag); //NOPMD LoD TODO
        } else if (kontostand.kontoart.equals(Kontoart.Aufwand)) { //NOPMD LoD TODO
            return new Sollsaldo(kontostand.betrag); //NOPMD LoD TODO
        }
        return null;
    }
}
