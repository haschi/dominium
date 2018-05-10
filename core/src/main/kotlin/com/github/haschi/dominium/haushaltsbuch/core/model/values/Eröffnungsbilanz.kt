package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Eröffnungsbilanz(val aktiva: Aktiva, private val passiva: Passiva)
{
    init
    {
        if (aktiva.summe != passiva.summe){
            throw BilanzsummenNichtIdentisch()
        }
    }
}

