package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import org.javamoney.moneta.Money;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public class HaushaltsbuchführungBeginnen {

    @Inject
    private HaushaltsbuchRepository repository;

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public final void ich_mit_der_Haushaltsbuchführung_beginne() {
        this.repository.hinzufügen(new Haushaltsbuch());
    }

    @Dann("^wird mein ausgewiesenes Gesamtvermögen (-{0,1}\\d+,\\d{2} [A-Z]{3}) betragen$")
    public final void wird_mein_ausgewiesenes_Gesamtvermögen_betragen(
        @Transform(MoneyConverter.class) MonetaryAmount währungsbetrag) {

        Haushaltsbuch haushaltsbuch = this.repository.besorgen();

        final MonetaryAmount actual = haushaltsbuch.gesamtvermögenBerechnen();
        assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Dann("^wird ein neues Haushaltsbuch mit einem Gesamtvermögen von (-{0,1}\\d+,\\d{2} [A-Z]{3}) angelegt worden sein$")
    public final void dann_wird_ein_neues_haushaltsbuch_angelegt_worden_sein(
        @Transform(MoneyConverter.class) MonetaryAmount währungsbetrag)  {

        Haushaltsbuch haushaltsbuch = this.repository.besorgen();
        assertThat(haushaltsbuch.gesamtvermögenBerechnen()).isEqualTo(währungsbetrag);
    }

    @Und("^mein Anfangsbestand wird (-{0,1}\\d+,\\d{2} [A-Z]{3}) betragen$")
    public final void mein_Anfangsbestand_wird_Geld_betragen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) throws Throwable {

        Haushaltsbuch haushaltsbuch = this.repository.besorgen();
        final Konto anfangsbestand = haushaltsbuch.kontoSuchen("Anfangsbestand");
        final MonetaryAmount kontostand = haushaltsbuch.kontostandBerechnen(anfangsbestand);

        assertThat(kontostand).isEqualTo(währungsbetrag); // NOPMD
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public final void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        this.repository.hinzufügen(new Haushaltsbuch());
    }

    @Wenn("^ich dem Haushaltsbuch mein Konto \"([^\"]*)\" mit einem Bestand von "
        + "(-{0,1}\\d+,\\d{2}) (.*) hinzufüge$")
    public final void ich_dem_Haushaltsbuch_mein_Konto_mit_einem_Bestand_von_hinzufüge(
        final String kontoname,
        final BigDecimal betrag,
        @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) {

        final Money anfangsbestand = Money.of(betrag, währung);
        final Konto konto = new Konto(kontoname, anfangsbestand);
        Haushaltsbuch haushaltsbuch = this.repository.besorgen();

        haushaltsbuch.neuesKontoHinzufügen(konto, anfangsbestand);
    }

    @Angenommen("^mein ausgewiesenes Gesamtvermögen beträgt (-{0,1}\\d+,\\d{2}) (.*)$")
    public final void mein_ausgewiesenes_Gesamtvermögen_beträgt_anfängliches_Gesamtvermögen(
        final BigDecimal betrag,
        @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) {

        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch();
        final Money gesamtvermögen = Money.of(betrag, währung);
        final Konto einKonto = new Konto("anfängliches Geldvermögen", gesamtvermögen);
        haushaltsbuch.neuesKontoHinzufügen(einKonto, gesamtvermögen);

        this.repository.hinzufügen(haushaltsbuch);
    }

    @Wenn("^ich ein Konto \"([^\"]*)\" mit einem Bestand von (-{0,1}\\d+,\\d{2}) (.*) der "
        + "Haushaltsbuchführung hinzufüge$")
    public final void
        ich_ein_Konto_mit_einem_Bestand_von_Kontobestand_der_Haushaltsbuchführung_hinzufüge(
            final String kontoname,
            final BigDecimal betrag,
            @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) {
        final Money anfangsbestand = Money.of(betrag, währung);
        final Konto neuesKonto = new Konto(kontoname, anfangsbestand);

        final Haushaltsbuch haushaltsbuch = this.repository.besorgen();

        haushaltsbuch.neuesKontoHinzufügen(neuesKonto, anfangsbestand);
    }
}
