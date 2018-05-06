package com.github.haschi.domain.haushaltsbuch.testing


import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schuld
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import kotlin.streams.toList

@XStreamConverter(InventarpositionConverter::class)
class Inventarposition(
    val gruppe: String,
    var kategorie: Kategorie,
    var position: String,
    var währungsbetrag: Währungsbetrag
)

fun List<Inventarposition>.vermögenswerte(bezeichnung: String): Vermoegenswerte =
    Vermoegenswerte(
        this.stream()
            .filter { z -> z.gruppe == bezeichnung }
            .map({p -> Vermoegenswert(p.kategorie.kategorie, p.position!!, p.währungsbetrag!!)})
            .toList())

fun List<Inventarposition>.schulden(bezeichnung: String): Schulden =
        Schulden(
                this.stream()
                        .filter { z -> z.gruppe == bezeichnung }
                        .map({p -> Schuld(p.position!!, p.währungsbetrag!!) })
                        .toList())
