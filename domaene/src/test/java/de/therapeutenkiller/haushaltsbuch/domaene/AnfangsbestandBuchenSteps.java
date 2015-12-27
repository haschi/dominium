package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.KontoSaldieren;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Sollsaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.AnfangsbestandBuchen;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HabensaldoConverter;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchführungBeginnenKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.SollsaldoConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class AnfangsbestandBuchenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final AnfangsbestandBuchen anfangsbestandBuchen;
    private final KontoSaldieren kontoSaldieren;

    @Inject
    public AnfangsbestandBuchenSteps(
            final HaushaltsbuchführungBeginnenKontext kontext,
            final AnfangsbestandBuchen anfangsbestandBuchen,
            final KontoSaldieren kontoSaldieren) {
        this.kontext = kontext;
        this.anfangsbestandBuchen = anfangsbestandBuchen;
        this.kontoSaldieren = kontoSaldieren;
    }

    @Wenn("^ich auf das Konto \"([^\"]*)\" den Anfangsbestand von (-{0,1}\\d+,\\d{2} [A-Z]{3}) buche$")
    public void ichAufDasKontoDenAnfangsbestandVonEurBuche(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {
        this.anfangsbestandBuchen.ausführen(this.kontext.aktuellesHaushaltsbuch(), kontoname, betrag);
    }

    @Dann("^wird das Konto \"([^\"]*)\" ein Sollsaldo von (-{0,1}\\d+,\\d{2} [A-Z]{3}) haben$")
    public void wirdDasKontoEinSollsaldoVonEurHaben(
            final String kontoname,
            @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo) {

        final Saldo tatsächlicherSaldo = this.kontoSaldieren.ausführen(
                this.kontext.aktuellesHaushaltsbuch(),
                kontoname);

        assertThat(tatsächlicherSaldo).isEqualTo(erwarteterSaldo); // NOPMD LoD OK für AssertJ
    }

    @Dann("^wird das Konto \"([^\"]*)\" ein Habensaldo von (-{0,1}\\d+,\\d{2} [A-Z]{3}) haben$")
    public void wirdDasKontoEinHabensaldoVonEurHaben(
            final String kontoname,
            @Transform(HabensaldoConverter.class) final Habensaldo erwarteterSaldo) {

        final Saldo tatsächlicherSaldo = this.kontoSaldieren.ausführen(
                this.kontext.aktuellesHaushaltsbuch(),
                kontoname);

        assertThat(tatsächlicherSaldo).isEqualTo(erwarteterSaldo); // NOPMD LoD OK für AssertJ
    }

    @Und("^ich habe auf das Konto \"([^\"]*)\" den Anfangsbestand von (-{0,1}\\d+,\\d{2} [A-Z]{3}) gebucht$")
    public void ichHabeAufDasKontoDenAnfangsbestandGebucht(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {

        this.anfangsbestandBuchen.ausführen(this.kontext.aktuellesHaushaltsbuch(), kontoname, währungsbetrag);
    }

    @Wenn("^ich weitere (-{0,1}\\d+,\\d{2} [A-Z]{3}) auf das Konto \"([^\"]*)\" als Anfangsbestand buche$")
    public void ichWeiteresGeldAufDasKontoAlsAnfangsbestandBuche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String kontoname) throws Throwable {

        this.anfangsbestandBuchen.ausführen(this.kontext.aktuellesHaushaltsbuch(), kontoname, währungsbetrag);
    }
}
