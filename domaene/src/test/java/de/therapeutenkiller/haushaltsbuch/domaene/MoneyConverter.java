package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;
import java.util.regex.Pattern;

public class MoneyConverter extends Transformer<Money> {

  private static final Pattern pattern = Pattern.compile("(-{0,1}\\d+,\\d{2}) ([A-Z]{3})");

  @Override
  public final Money transform(final String währungsbetrag) {
    if (StringUtils.isEmpty(währungsbetrag)) {
      throw new NullPointerException("Währungsbetrag ist leer");
    }

    final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
    final MonetaryAmount amount = format.parse(währungsbetrag);

    return Money.from(amount);
  }
}
