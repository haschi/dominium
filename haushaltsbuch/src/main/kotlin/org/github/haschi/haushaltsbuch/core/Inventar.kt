package org.github.haschi.haushaltsbuch.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.github.haschi.haushaltsbuch.api.Reinvermögen
import org.github.haschi.haushaltsbuch.api.Schulden
import org.github.haschi.haushaltsbuch.api.Vermoegenswerte
import org.github.haschi.haushaltsbuch.api.Währungsbetrag

@JsonSerialize(`as` = Inventar::class)
@JsonDeserialize(`as` = Inventar::class)
@JsonIgnoreProperties(value= "reinvermoegen", allowGetters=true)
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
