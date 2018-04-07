package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Aktiva(
        val anlagevermoegen: Vermoegenswerte,
        val umlaufvermoegen: Vermoegenswerte,
        val fehlbetrag: Vermoegenswerte)
{
    val summe: Währungsbetrag
        get() =
             anlagevermoegen.summe +
                     umlaufvermoegen.summe +
                     fehlbetrag.summe
}