package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Eröffnungsbilanz(
        val aktiva: Aktiva,
        val fehlbetrag: Währungsbetrag,
        val passiva: Passiva)
{
    init
    {
        if (aktiva.summe + fehlbetrag != passiva.summe){
            throw BilanzsummenNichtIdentisch()
        }
    }
}

