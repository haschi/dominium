package org.github.haschi.haushaltsbuch.infrastruktur;

import cucumber.api.Transformer;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;

public class MoneyConverter extends Transformer<Währungsbetrag>
{
    @Override
    public final Währungsbetrag transform(final String währungsbetrag)
    {
        return Währungsbetrag.parse(währungsbetrag);
    }
}
