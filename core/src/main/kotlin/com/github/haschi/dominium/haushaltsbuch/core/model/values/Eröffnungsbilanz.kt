package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Aktiva(
        val anlagevermoegen: Vermoegenswerte,
        val umlaufvermoegen: Vermoegenswerte)
{
    val summe: Währungsbetrag
        get() = anlagevermoegen.summe + umlaufvermoegen.summe
}

data class Passiva(
        val eigenkapital: Vermoegenswerte,
        val fremdkapital: Vermoegenswerte)
{
    val summe: Währungsbetrag
        get() = eigenkapital.summe + fremdkapital.summe
}

data class Eröffnungsbilanz(
        val aktiva: Aktiva,
        val passiva: Passiva)