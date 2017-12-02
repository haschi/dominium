package org.github.haschi.haushaltsbuch.api

import javax.money.MonetaryAmount
import javax.money.format.MonetaryAmountFormat
import javax.money.format.MonetaryFormats
import java.util.Locale

internal class DeutschenWährungsbetragAnalysieren {

    private val format: MonetaryAmountFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY)

    fun aus(währungsbetrag: String): MonetaryAmount {
        return this.format.parse(währungsbetrag)
    }
}
