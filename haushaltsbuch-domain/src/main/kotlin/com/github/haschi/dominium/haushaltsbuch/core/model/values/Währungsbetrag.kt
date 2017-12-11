package com.github.haschi.dominium.haushaltsbuch.core.model.values

import org.javamoney.moneta.Money
import java.util.*
import javax.money.Monetary
import javax.money.MonetaryAmount
import javax.money.format.MonetaryAmountFormat
import javax.money.format.MonetaryFormats

data class Währungsbetrag(val wert: MonetaryAmount)
{
    internal class DeutschenWährungsbetragAnalysieren {

        private val format: MonetaryAmountFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY)

        fun aus(währungsbetrag: String): MonetaryAmount
        {
            return this.format.parse(währungsbetrag)
        }
    }

    override
    fun toString(): String
    {
        val deutschesFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY)
        return deutschesFormat.format(wert)
    }

    companion object
    {
        fun währungsbetrag(betrag: String): Währungsbetrag
        {
            if (betrag.isEmpty())
            {
                throw IllegalArgumentException("Währungsbetrag ist leer")
            }

            return Währungsbetrag(DeutschenWährungsbetragAnalysieren().aus(betrag))
        }

        val NullEuro: Währungsbetrag =
                Währungsbetrag(
                        Money.of(
                                0,
                                Monetary.getCurrency(Locale.GERMANY)))

        fun euro(betrag: Double): Währungsbetrag
        {
            return Währungsbetrag(Money.of(betrag, Monetary.getCurrency(Locale.GERMANY)))
        }
    }

    operator fun plus(y: Währungsbetrag): Währungsbetrag
    {
        return Währungsbetrag(this.wert.add(y.wert))
    }
}

fun Double.euro(): Währungsbetrag = Währungsbetrag.euro(this)
