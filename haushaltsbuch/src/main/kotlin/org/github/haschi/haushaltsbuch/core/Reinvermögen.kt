package org.github.haschi.haushaltsbuch.core

data class Reinvermögen(
        val summeDesVermögens: Währungsbetrag,
        val summeDerSchulden: Währungsbetrag)
{
    val reinvermögen: Währungsbetrag
        get() = Währungsbetrag(summeDesVermögens.wert.subtract(summeDerSchulden.wert))
}
