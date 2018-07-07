package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurGruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Kategorie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import kotlin.streams.toList


class InventarEingabe(
    val gruppe: InventurGruppe,
    val gruppe2: String,
    var kategorie: Kategorie,
    var position: String,
    var währungsbetrag: Währungsbetrag
)

val List<InventarEingabe>.umlaufvermögen: Vermoegenswerte
    get() = vermögenswerte(InventurGruppe.Umlaufvermögen)

val List<InventarEingabe>.anlagevermögen
    get() = vermögenswerte(InventurGruppe.Anlagevermögen)

fun List<InventarEingabe>.vermögenswerte(gruppe: InventurGruppe): Vermoegenswerte =
    Vermoegenswerte(
        this.stream()
            .filter { z -> z.gruppe2 == gruppe.name }
            .map({p -> Vermoegenswert(p.kategorie.kategorie, p.position, p.währungsbetrag)})
            .toList())

val List<InventarEingabe>.schulden
    get() = Vermoegenswerte(
            this.stream()
                    .filter { z -> z.gruppe == InventurGruppe.Schulden }
                    .map({ p -> Vermoegenswert(p.kategorie.kategorie, p.position, p.währungsbetrag) })
                    .toList())