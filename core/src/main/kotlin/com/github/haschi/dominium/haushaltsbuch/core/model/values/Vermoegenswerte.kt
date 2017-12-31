package com.github.haschi.dominium.haushaltsbuch.core.model.values

import org.javamoney.moneta.function.MonetaryFunctions
import javax.money.MonetaryAmount

data class Vermoegenswerte(private val l: List<Vermoegenswert>) : List<Vermoegenswert> by l
{

    constructor(vararg ls: Vermoegenswert) : this(ls.asList())

    val summe: Währungsbetrag
        get() = Währungsbetrag(
                this.stream()
                        .map<MonetaryAmount> { m -> m.waehrungsbetrag.wert }
                        .reduce(MonetaryFunctions.sum())
                        .orElse(Währungsbetrag.NullEuro.wert))

    companion object
    {
        val keine: Vermoegenswerte
            get() = Vermoegenswerte(emptyList())
    }
}
