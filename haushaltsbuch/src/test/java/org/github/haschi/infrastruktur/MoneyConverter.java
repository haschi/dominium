package org.github.haschi.infrastruktur;

import cucumber.api.Transformer;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;

public final class MoneyConverter extends Transformer<Währungsbetrag>
{

    public Währungsbetrag transform(final String währungsbetrag)
    {
        return Währungsbetrag.währungsbetrag(währungsbetrag);
    }
}
