package com.github.haschi.dominium.haushaltsbuch.core.model.values

import java.time.LocalDateTime

data class Inventar(
        val erstelltAm: LocalDateTime,
        val anlagevermoegen: Vermoegenswerte,
        val umlaufvermoegen: Vermoegenswerte,
        val schulden: Vermoegenswerte)
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
                    LocalDateTime.MIN,
                    Vermoegenswerte.keine,
                    Vermoegenswerte.keine,
                    Vermoegenswerte.keine)
    }
}
