package com.github.haschi.dominium.haushaltsbuch.core.model.commands

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurGruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Kategorie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class ErfasseInventar(
        @TargetAggregateIdentifier val id: Aggregatkennung,
        val anlagevermoegen: Vermoegenswerte,
        val umlaufvermoegen: Vermoegenswerte,
        val schulden: Schulden)
{
    init
    {
        kategoriePrüfen(anlagevermoegen, InventurGruppe.Anlagevermögen)
        kategoriePrüfen(umlaufvermoegen, InventurGruppe.Umlaufvermögen)

        schulden.forEach {
            if (!InventurGruppe.Schulden.kategorien.contains(Kategorie(it.kategorie)))
            {
                throw UngültigeKategorie(it.kategorie)
            }
        }

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