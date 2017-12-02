package org.github.haschi.haushaltsbuch.core

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.github.haschi.haushaltsbuch.api.DeutschenWährungsbetragAnalysieren
import org.javamoney.moneta.Money
import java.io.IOException
import java.util.*
import javax.money.Monetary
import javax.money.MonetaryAmount
import javax.money.format.MonetaryFormats

@JsonDeserialize(using = Währungsbetrag.WährungsbetragDeserialisierer::class)
@JsonSerialize(using = Währungsbetrag.WährungsbetragSerialisierer::class)
data class Währungsbetrag(val wert: MonetaryAmount)
{

    class WährungsbetragDeserialisierer : StdScalarDeserializer<Währungsbetrag>
    {
        constructor() : super(Währungsbetrag::class.java)

        constructor(vc: Class<*>) : super(vc)

        @Throws(IOException::class)
        override fun deserialize(
                jsonParser: JsonParser,
                deserializationContext: DeserializationContext): Währungsbetrag
        {

            val betrag = jsonParser.text

            return währungsbetrag(betrag)
        }
    }

    class WährungsbetragSerialisierer @JvmOverloads constructor(t: Class<Währungsbetrag>? = null) : StdSerializer<Währungsbetrag>(t) {

        @Throws(IOException::class)
        override fun serialize(
                value: Währungsbetrag,
                jgen: JsonGenerator,
                provider: SerializerProvider) {
            jgen.writeString(value.toString())
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
