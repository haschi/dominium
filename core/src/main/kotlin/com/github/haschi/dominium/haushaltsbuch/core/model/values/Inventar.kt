package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Inventar(
        val anlagevermoegen: Vermoegenswerte,
        val umlaufvermoegen: Vermoegenswerte,
        val schulden: Schulden)
{

    val reinvermoegen: Reinvermögen
        get() = Reinvermögen(
                Währungsbetrag(
                        anlagevermoegen.summe.wert.add(
                                umlaufvermoegen.summe.wert)),
                schulden.summe)


    companion object
    {
        val leer: Inventar
            get() = Inventar(
                    Vermoegenswerte.keine,
                    Vermoegenswerte.keine,
                    Schulden.keine)
    }
}
