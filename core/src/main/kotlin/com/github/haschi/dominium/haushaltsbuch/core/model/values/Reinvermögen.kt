package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Reinvermögen(
        val summeDesVermoegens: Währungsbetrag,
        val summeDerSchulden: Währungsbetrag)
{
    val reinvermoegen: Währungsbetrag
        get() = Währungsbetrag(summeDesVermoegens.wert.subtract(summeDerSchulden.wert))
}
