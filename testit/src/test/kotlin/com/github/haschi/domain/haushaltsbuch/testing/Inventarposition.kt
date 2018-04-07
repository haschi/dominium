package com.github.haschi.domain.haushaltsbuch.testing


import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schuld
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import kotlin.streams.toList

class Inventarposition {
    var gruppe: String? = null
    var position: String? = null
    @XStreamConverter(MoneyConverter::class)
    var währungsbetrag: Währungsbetrag? = null
}

fun List<Inventarposition>.vermögenswerte(bezeichnung: String): Vermoegenswerte =
    Vermoegenswerte(
        this.stream()
            .filter { z -> z.gruppe == bezeichnung }
            .map({p -> Vermoegenswert(p.position!!, p.währungsbetrag!!)})
            .toList())

fun List<Inventarposition>.schulden(bezeichnung: String): Schulden =
        Schulden(
                this.stream()
                        .filter { z -> z.gruppe == bezeichnung }
                        .map({p -> Schuld(p.position!!, p.währungsbetrag!!) })
                        .toList())
