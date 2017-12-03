package com.github.haschi.domain.haushaltsbuch.testing

import cucumber.api.Transformer
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag

class MoneyConverter : Transformer<Währungsbetrag>()
{

    override fun transform(währungsbetrag: String): Währungsbetrag
    {
        return Währungsbetrag.währungsbetrag(währungsbetrag)
    }
}
