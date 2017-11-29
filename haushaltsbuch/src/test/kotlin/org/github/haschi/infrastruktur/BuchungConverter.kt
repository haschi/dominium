package org.github.haschi.infrastruktur

import cucumber.api.Transformer
import org.github.haschi.haushaltsbuch.api.Buchung
import org.github.haschi.haushaltsbuch.api.Währungsbetrag
import java.util.regex.Pattern

class BuchungConverter : Transformer<Buchung>()
{
    override fun transform(s: String): Buchung
    {
        val pattern = Pattern.compile("^(.*) (-?(?:\\d{1,3}\\.)?\\d{1,3},\\d{2} EUR)$")
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