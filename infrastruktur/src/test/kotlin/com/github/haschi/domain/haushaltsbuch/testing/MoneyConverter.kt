package com.github.haschi.domain.haushaltsbuch.testing

import cucumber.api.Transformer
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

class MoneyConverter : Transformer<Währungsbetrag>()
{

    override fun transform(währungsbetrag: String): Währungsbetrag
    {
        return Währungsbetrag.währungsbetrag(währungsbetrag)
    }
}
