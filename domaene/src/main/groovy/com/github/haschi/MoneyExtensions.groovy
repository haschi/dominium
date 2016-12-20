package com.github.haschi

import org.javamoney.moneta.Money

import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.money.MonetaryAmount

// TODO: Refactor: DRY
// TODO: Refactor in eigenes Modul
class MoneyExtensions {

    static MonetaryAmount getEuro(BigDecimal betrag) {

        CurrencyUnit currency = Monetary.getCurrency("EUR")
        Money.of(betrag, currency)
    }

    static MonetaryAmount getMark(BigDecimal betrag) {
        CurrencyUnit currency = Monetary.getCurrency("DEM")
        Money.of(betrag, currency)
    }

    static MonetaryAmount getDollar(BigDecimal betrag) {
        CurrencyUnit currency = Monetary.getCurrency("USD")
        Money.of(betrag, currency)
    }
}