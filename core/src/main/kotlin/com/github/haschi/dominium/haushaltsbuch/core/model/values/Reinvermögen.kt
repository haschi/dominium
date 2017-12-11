package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Reinvermögen(
        val summeDesVermögens: Währungsbetrag,
        val summeDerSchulden: Währungsbetrag)
{
    val reinvermögen: Währungsbetrag
        get() = Währungsbetrag(summeDesVermögens.wert.subtract(summeDerSchulden.wert))
}
