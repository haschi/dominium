package de.therapeutenkiller.haushaltsbuch

import org.javamoney.moneta.Money

import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.money.MonetaryAmount

/**
 * Created by matthias on 21.12.15.
 */
class MoneyExtensions {

    static def MonetaryAmount getEuro(BigDecimal betrag) {

        CurrencyUnit currency = Monetary.getCurrency("EUR")
        Money.of(betrag, currency)
    }

    static def MonetaryAmount getMark(BigDecimal betrag) {
        CurrencyUnit currency = Monetary.getCurrency("DEM")
        Money.of(betrag, currency)
    }

    static def MonetaryAmount getDollar(BigDecimal betrag) {
        CurrencyUnit currency = Monetary.getCurrency("USD")
        Money.of(betrag, currency)
    }
}
