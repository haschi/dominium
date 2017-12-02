package org.github.haschi.haushaltsbuch.core.values

data class Buchung(val buchungstext: String, val betrag: Währungsbetrag) {

    companion object {

        val leer: Buchung
        get() = Buchung(String.empty, Währungsbetrag.NullEuro)
    }
}

val String.Companion.empty: String
    get() = ""
