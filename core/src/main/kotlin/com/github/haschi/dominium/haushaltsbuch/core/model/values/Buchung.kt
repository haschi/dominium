package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Buchung(val buchungstext: String, val betrag: Währungsbetrag)
{

    companion object
    {

        val leer: Buchung
            get() = Buchung(empty, Währungsbetrag.NullEuro)
    }
}

val empty: String
    get() = ""
