package com.github.haschi.domain.haushaltsbuch.testing

import cucumber.api.Transformer
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro

class MoneyConverter : Transformer<Währungsbetrag>()
{

    override fun transform(währungsbetrag: String): Währungsbetrag
    {
        if (währungsbetrag.isEmpty())
            return 0.0.euro()

        return Währungsbetrag.währungsbetrag(währungsbetrag)
    }
}
