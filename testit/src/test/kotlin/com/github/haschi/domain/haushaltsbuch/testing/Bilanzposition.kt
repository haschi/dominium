package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Gruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

data class Bilanzposition(
        val seite: String,
        private val gruppe: String,
        val kategorie: String,
        val posten: String,
        val betrag: Währungsbetrag)
{
    val bilanzGruppe: Gruppe
        get()
        {
            val elemente = Regex("([A-Z])\\s(.*)").find(gruppe)
            val buchstabe = elemente!!.groupValues[1][0]
            val bezeichnung = elemente.groupValues[2]
            return Gruppe(buchstabe, bezeichnung)
        }
}