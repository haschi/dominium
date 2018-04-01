package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Passiva(
        val eigenkapital: Vermoegenswerte,
        val fremdkapital: Vermoegenswerte)
{
    val summe: Währungsbetrag
        get() = eigenkapital.summe + fremdkapital.summe
}