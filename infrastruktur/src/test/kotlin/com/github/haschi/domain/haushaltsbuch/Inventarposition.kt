package com.github.haschi.domain.haushaltsbuch


import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schuld
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schulden
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswert
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswerte
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import com.github.haschi.domain.haushaltsbuch.testing.MoneyConverter
import kotlin.streams.toList

class Inventarposition {
    var gruppe: String? = null
    var untergruppe: String? = null
    var position: String? = null
    @XStreamConverter(MoneyConverter::class)
    var währungsbetrag: Währungsbetrag? = null
}

fun List<Inventarposition>.vermögenswerte(bezeichnung: String): Vermoegenswerte =
    Vermoegenswerte(
        this.stream()
            .filter { z -> z.untergruppe == bezeichnung }
            .map({p -> Vermoegenswert(p.position!!, p.währungsbetrag!!)})
            .toList())

fun List<Inventarposition>.schulden(bezeichnung: String): Schulden =
        Schulden(
                this.stream()
                        .filter { z -> z.untergruppe == bezeichnung }
                        .map({p -> Schuld(p.position!!, p.währungsbetrag!!) })
                        .toList())
