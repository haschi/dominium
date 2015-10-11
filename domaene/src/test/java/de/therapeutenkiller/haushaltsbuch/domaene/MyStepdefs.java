package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MyStepdefs {

  private Geldbörse geldbörse;

  @Angenommen("^ich habe eine leere Geldbörse$")
  public final void ich_habe_eine_leere_Geldbörse() throws Throwable {
    this.geldbörse = Geldbörse.erzeugen();
  }

  @Wenn("^ich (\\d+\\,\\d{2}) (.*) in meine Geldbörse stecke$")
  public final void ich_in_meine_Geldbörse_stecke(
      final BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) throws Throwable {

    final Money geld = Money.of(betrag, währung);
    this.geldbörse.hineinstecken(geld);
  }

  @Dann("^befinden sich (\\d+\\,\\d{2}) (.*) in meiner Geldbörse$")
  public final void befinden_sich_in_meiner_Geldbörse(
      final BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) throws Throwable {

    final Money geld = Money.of(betrag, währung);
    assertThat(this.geldbörse.getInhalt()).isEqualTo(geld);
  }

  @Angenommen("^in meiner Geldbörse befinden sich (\\d+\\,\\d{2}) (.*)$")
  public final void in_meiner_Geldbörse_befinden_sich(
      final BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class) final CurrencyUnit währung) throws Throwable {
    this.geldbörse = Geldbörse.erzeugen();
    this.geldbörse.hineinstecken(Money.of(betrag, währung));
  }
}
