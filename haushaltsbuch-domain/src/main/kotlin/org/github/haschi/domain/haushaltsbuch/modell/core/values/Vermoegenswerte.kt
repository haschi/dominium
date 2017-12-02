package org.github.haschi.domain.haushaltsbuch.modell.core.values

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue
import org.javamoney.moneta.function.MonetaryFunctions
import javax.money.MonetaryAmount

data class Vermoegenswerte @JsonCreator constructor (@get:JsonIgnore private val l: List<Vermoegenswert>) : List<Vermoegenswert> by l {

    constructor(vararg ls: Vermoegenswert): this(ls.asList())

    @JsonValue fun wert(): List<Vermoegenswert> = l

    val summe: Währungsbetrag
        get() = Währungsbetrag(
                this.stream()
                        .map<MonetaryAmount> { m -> m.währungsbetrag.wert }
                        .reduce(MonetaryFunctions.sum())
                        .orElse(Währungsbetrag.NullEuro.wert))


    companion object {
        val keine: Vermoegenswerte
            get() = Vermoegenswerte(emptyList())
    }
}
