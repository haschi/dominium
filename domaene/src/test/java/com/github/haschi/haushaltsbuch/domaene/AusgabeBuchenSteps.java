package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Saldo;
import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.abfrage.SaldoAbfrage;
import com.github.haschi.haushaltsbuch.api.kommando.BucheTilgung;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Habensaldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import com.github.haschi.haushaltsbuch.api.kommando.BucheAusgabe;
import com.github.haschi.haushaltsbuch.domaene.testsupport.Kontostand;
import com.github.haschi.haushaltsbuch.domaene.testsupport.MoneyConverter;

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

    @Inject Event<BucheTilgung> tilgungBuchen;

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

    @Wenn("^ich meine Tilgung von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_tilgung_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto) {

        final BucheTilgung bucheTilgung = new BucheTilgung(
                this.welt.getAktuelleHaushaltsbuchId(),
                sollkonto,
                habenkonto,
                währungsbetrag);

        this.tilgungBuchen.fire(bucheTilgung);
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
        switch (kontostand.kontoart) {
            case Aktiv: return new Sollsaldo(kontostand.betrag);
            case Ertrag: return new Habensaldo(kontostand.betrag);
            case Aufwand: return new Sollsaldo(kontostand.betrag);
            case Passiv: return new Sollsaldo(kontostand.betrag);
            default: throw new IllegalArgumentException("kontostand");
        }
    }
}
