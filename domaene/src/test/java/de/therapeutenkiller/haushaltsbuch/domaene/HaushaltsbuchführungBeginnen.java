package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mhaschka on 10.10.15.
 */
public class HaushaltsbuchführungBeginnen {

  private Haushaltsbuch haushaltsbuch;

  @Wenn("^ich mit der Haushaltsbuchführung beginne$")
  public final void ich_mit_der_Haushaltsbuchführung_beginne() {

    this.haushaltsbuch = new Haushaltsbuch();
  }

  @Dann("^wird mein ausgewiesenes Gesamtvermögen (-{0,1}\\d+\\,\\d{2}) (.*) betragen$")
  public final void wird_mein_ausgewiesenes_Gesamtvermögen_betragen(
      final BigDecimal betrag,
      final String währung) {

    final Money erwartetesGesamtvermögen = Money.of(betrag, währung);

    assertThat(this.haushaltsbuch.gesamtvermögenBerechnen())
        .isEqualTo(erwartetesGesamtvermögen);
  }

  @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
  public final void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
    this.haushaltsbuch = new Haushaltsbuch();
  }

  @Wenn("^ich dem Haushaltsbuch mein Konto \"([^\"]*)\" mit einem Bestand von (-{0,1}\\d+\\,"
      + "\\d{2}) (.*) hinzufüge$")
  public final void ich_dem_Haushaltsbuch_mein_Konto_mit_einem_Bestand_von_hinzufüge(
      final String kontoname,
      final BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) {

    final Money anfangsbestand = Money.of(betrag, währung);
    final Konto konto = new Konto(kontoname, anfangsbestand);
    this.haushaltsbuch.neuesKontoHinzufügen(konto);
  }

  @Angenommen("^mein ausgewiesenes Gesamtvermögen beträgt (-{0,1}\\d+\\,\\d{2}) (.*)$")
  public final void mein_ausgewiesenes_Gesamtvermögen_beträgt_anfängliches_Gesamtvermögen(
      final BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) {

    this.haushaltsbuch = new Haushaltsbuch();
    final Money anfänglichesGesamtvermögen = Money.of(betrag, währung);
    final Konto einKonto = new Konto("anfängliches Geldvermögen", anfänglichesGesamtvermögen);
    this.haushaltsbuch.neuesKontoHinzufügen(einKonto);
  }

  @Wenn("^ich ein Konto \"([^\"]*)\" mit einem Bestand von (-{0,1}\\d+\\,\\d{2}) (.*) der "
      + "Haushaltsbuchführung hinzufüge$")
  public final void
      ich_ein_Konto_mit_einem_Bestand_von_Kontobestand_der_Haushaltsbuchführung_hinzufüge(
      final String kontoname,
      final BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) {

    final Money anfangsbestand = Money.of(betrag, währung);
    final Konto neuesKonto = new Konto(kontoname, anfangsbestand);
    this.haushaltsbuch.neuesKontoHinzufügen(neuesKonto);
  }
}
