package org.github.haschi.haushaltsbuch.api

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.apache.commons.lang3.StringUtils
import org.javamoney.moneta.Money
import java.util.*
import javax.money.Monetary
import javax.money.MonetaryAmount
import javax.money.format.MonetaryFormats

@JsonDeserialize(using = WährungsbetragDeserialisierer::class)
@JsonSerialize(using = WährungsbetragSerialisierer::class)
data class Währungsbetrag(val wert: MonetaryAmount) {

    override // @JsonProperty("betrag")
    fun toString(): String {
            val deutschesFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY)
            return deutschesFormat.format(wert)
    }

    companion object {
        fun währungsbetrag(betrag: String): Währungsbetrag {
            if (StringUtils.isEmpty(betrag)) {
                throw IllegalArgumentException("Währungsbetrag ist leer")
            }

            val analysieren = DeutschenWährungsbetragAnalysieren()
            val s = analysieren.aus(betrag)

            return Währungsbetrag(s)
        }

        val NullEuro: Währungsbetrag =
            Währungsbetrag(
                    Money.of(
                            0,
                            Monetary.getCurrency(Locale.GERMANY)))
    }
}
