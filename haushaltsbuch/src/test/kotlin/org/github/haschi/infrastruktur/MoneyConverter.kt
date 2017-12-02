package org.github.haschi.infrastruktur

import cucumber.api.Transformer
import org.github.haschi.haushaltsbuch.modell.core.values.Währungsbetrag

class MoneyConverter : Transformer<Währungsbetrag>()
{

    override fun transform(währungsbetrag: String): Währungsbetrag
    {
        return Währungsbetrag.währungsbetrag(währungsbetrag)
    }
}
