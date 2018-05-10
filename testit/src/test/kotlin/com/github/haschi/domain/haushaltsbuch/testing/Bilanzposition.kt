package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Gruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter

data class Bilanzposition(
        val seite: String,
        private val gruppe: String,
        val kategorie: String,
        val posten: String,
        @XStreamConverter(MoneyConverter::class)
        val betrag: Währungsbetrag)
{
    fun bilanzgruppe(): Gruppe
    {
        val elemente = Regex("([A-Z])\\s(.*)").find(gruppe)
        val buchstabe = elemente!!.groupValues[1][0]
        val bezeichnung = elemente.groupValues[2]
        return Gruppe(buchstabe, bezeichnung)
    }
}