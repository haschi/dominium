package com.github.haschi.dominium.haushaltsbuch.core.model.values

import org.javamoney.moneta.function.MonetaryFunctions
import javax.money.MonetaryAmount

data class Schulden(private val l: List<Schuld>) : List<Schuld> by l
{

    constructor(vararg ls: Schuld) : this(ls.asList())

    val summe: Währungsbetrag
        get() = Währungsbetrag(
                this.stream()
                        .map<MonetaryAmount> { m -> m.währungsbetrag.wert }
                        .reduce(MonetaryFunctions.sum())
                        .orElse(Währungsbetrag.NullEuro.wert))

    companion object
    {
        val keine: Schulden
            get() = Schulden(emptyList())
    }
}

