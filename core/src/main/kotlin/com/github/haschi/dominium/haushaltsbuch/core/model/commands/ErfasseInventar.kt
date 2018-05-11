package com.github.haschi.dominium.haushaltsbuch.core.model.commands

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurGruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Kategorie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class ErfasseInventar(
        @TargetAggregateIdentifier val id: Aggregatkennung,
        val anlagevermoegen: Vermoegenswerte,
        val umlaufvermoegen: Vermoegenswerte,
        val schulden: Vermoegenswerte)
{
    init
    {
        kategoriePrüfen(anlagevermoegen, InventurGruppe.Anlagevermögen)
        kategoriePrüfen(umlaufvermoegen, InventurGruppe.Umlaufvermögen)
        kategoriePrüfen(schulden, InventurGruppe.Schulden)
    }

    private fun kategoriePrüfen(vermoegenswerte: Vermoegenswerte, gruppe: InventurGruppe)
    {
        vermoegenswerte.forEach {
            if (!gruppe.kategorien.contains(Kategorie(it.kategorie)))
            {
                throw UngültigeKategorie(it.kategorie)
            }
        }
    }
}