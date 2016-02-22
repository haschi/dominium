package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.haushaltsbuch.abfrage.SaldoAbfrage;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Sollsaldo;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HabensaldoConverter;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.SollsaldoConverter;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class AnfangsbestandBuchenSteps {

    @Inject
    private DieWelt welt;

    @Inject
    private SaldoAbfrage kontoSaldieren;

    @Inject
    private Event<AnfangsbestandBuchenKommando> bucheAnfangsbestand;

    @Wenn("^ich auf das Konto \"([^\"]*)\" (?:den Anfangsbestand von) (-?\\d+,\\d{2} [A-Z]{3}) buche$")
    public void wenn_ich_auf_das_Konto_den_Anfangsbestand_buche(
            final String konto,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final AnfangsbestandBuchenKommando befehl = new AnfangsbestandBuchenKommando(
                this.welt.getAktuelleHaushaltsbuchId(),
                konto,
                betrag);

        this.bucheAnfangsbestand.fire(befehl);
    }

    @Dann("^(?:werde ich|ich werde) auf dem Konto \"([^\"]*)\" ein Sollsaldo von (-?\\d+,\\d{2} [A-Z]{3}) haben$")
    public void dann_werde_ich_auf_dem_Konto_ein_Sollsaldo_haben(
            final String konto,
            @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo)
            throws AggregatNichtGefunden {

        final Saldo saldo = this.kontoSaldieren.abfragen(
                this.welt.getAktuelleHaushaltsbuchId(),
                konto);

        assertThat(saldo).isEqualTo(erwarteterSaldo);
    }

    @Dann("^(?:werde ich|ich werde) auf dem Konto \"([^\"]*)\" ein Habensaldo von (-?\\d+,\\d{2} [A-Z]{3}) haben$")
    public void dann_werde_ich_auf_dem_Konto_ein_Habensaldo_haben(
            final String konto,
            @Transform(HabensaldoConverter.class) final Habensaldo erwarteterSaldo)
            throws AggregatNichtGefunden {

        final Saldo saldo = this.kontoSaldieren.abfragen(
                this.welt.getAktuelleHaushaltsbuchId(),
                konto);

        assertThat(saldo).isEqualTo(erwarteterSaldo);
    }

    @Und("^ich habe auf das Konto \"([^\"]*)\" den Anfangsbestand von (-?\\d+,\\d{2} [A-Z]{3}) gebucht$")
    public void angenommen_ich_habe_auf_das_Konto_den_Anfangsbestand_gebucht(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {

        final AnfangsbestandBuchenKommando befehl = new AnfangsbestandBuchenKommando(
                this.welt.getAktuelleHaushaltsbuchId(),
                kontoname,
                währungsbetrag);

        this.bucheAnfangsbestand.fire(befehl);
    }

    @Wenn("^ich weitere (-?\\d+,\\d{2} [A-Z]{3}) auf das Konto \"([^\"]*)\" als Anfangsbestand buche$")
    public void wenn_ich_weitere_Euro_auf_das_Konto_als_Anfangsbestand_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String kontoname) throws Throwable {

        this.welt.kommandoAusführen(new AnfangsbestandBuchenKommando(
                this.welt.getAktuelleHaushaltsbuchId(),
                kontoname,
                währungsbetrag));
    }
}
