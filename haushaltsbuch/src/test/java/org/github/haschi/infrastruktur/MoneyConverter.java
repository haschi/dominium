package org.github.haschi.infrastruktur;

import cucumber.api.Transformer;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;
import org.github.haschi.haushaltsbuch.api._Währungsbetrag;

public class MoneyConverter extends Transformer<Währungsbetrag>
{

    public Währungsbetrag transform(final String währungsbetrag)
    {
        return Währungsbetrag.parse(währungsbetrag);
    }
}
