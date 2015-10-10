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

/**
 * Created by mhaschka on 22.09.15.
 */
public class MyStepdefs {

  private Geldbörse geldbörse;

  @Angenommen("^ich habe eine leere Geldbörse$")
  public void ich_habe_eine_leere_Geldbörse() throws Throwable {
    this.geldbörse = Geldbörse.erzeugen();
  }

  @Wenn("^ich (\\d+\\,\\d{2}) (.*) in meine Geldbörse stecke$")
  public void ich_€_in_meine_Geldbörse_stecke(
      BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class)CurrencyUnit währung) throws Throwable {

    Money geld = Money.of(betrag, währung);
    this.geldbörse.hineinstecken(geld);
  }

  @Dann("^befinden sich (\\d+\\,\\d{2}) (.*) in meiner Geldbörse$")
  public void befinden_sich_€_in_meiner_Geldbörse(
      BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class)CurrencyUnit währung) throws Throwable {

    Money geld = Money.of(betrag, währung);
    assertThat(this.geldbörse.getInhalt()).isEqualTo(geld);
  }

  @Angenommen("^in meiner Geldbörse befinden sich (\\d+\\,\\d{2}) (.*)$")
  public void in_meiner_Geldbörse_befinden_sich_€(
      BigDecimal betrag,
      @Transform(CurrencyUnitConverter.class)CurrencyUnit währung) throws Throwable {
    this.geldbörse = Geldbörse.erzeugen();
    this.geldbörse.hineinstecken(Money.of(betrag, währung));
  }
}
