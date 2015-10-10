package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transformer;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * Created by mhaschka on 10.10.15.
 */
public class CurrencyUnitConverter extends Transformer<CurrencyUnit> {

  @Override public CurrencyUnit transform(String währung) {
    return Monetary.getCurrency(währung);
  }
}
