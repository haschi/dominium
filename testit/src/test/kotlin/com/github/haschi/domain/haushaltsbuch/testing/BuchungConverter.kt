package com.github.haschi.domain.haushaltsbuch.testing

import cucumber.api.Transformer
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import java.util.regex.Pattern

class BuchungConverter : Transformer<Buchung>()
{

    override fun transform(s: String): Buchung
    {
        val pattern = Pattern.compile(MoneyConverter.regex)
        val matcher = pattern.matcher(s)

        assert(matcher.matches()) { "Keine Übereinstimmung: " + s }

        return if (matcher.matches())
        {
            Buchung(
                    buchungstext = matcher.group(1),
                    betrag = Währungsbetrag.währungsbetrag(matcher.group(2)))
        } else
        {
            Buchung.leer
        }
    }
}

