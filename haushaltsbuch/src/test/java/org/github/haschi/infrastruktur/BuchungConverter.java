package org.github.haschi.infrastruktur;

import cucumber.api.Transformer;
import org.github.haschi.haushaltsbuch.api.Buchung;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;
import org.github.haschi.haushaltsbuch.api._Buchung;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public final class BuchungConverter extends Transformer<Buchung>
{
    public Buchung transform(final String s)
    {
        final Pattern pattern = Pattern.compile("^(.*) (-?(?:\\d{1,3}\\.)?\\d{1,3},\\d{2} EUR)$");
        final Matcher matcher = pattern.matcher(s);

        assert matcher.matches() : "Keine Übereinstimmung: " + s;

        if (matcher.matches())
        {
            return Buchung.builder()
                    .buchungstext(matcher.group(1))
                    .betrag(Währungsbetrag.währungsbetrag(matcher.group(2)))
                    .build();
        }
        else
        {
            return _Buchung.leer();
        }
    }
}
