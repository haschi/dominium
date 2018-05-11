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
        anlagevermoegen.forEach {
            if (!InventurGruppe.Anlagevermögen.kategorien.contains(Kategorie(it.kategorie)))
            {
                throw UngültigeKategorie(it.kategorie)
            }
        }

        umlaufvermoegen.forEach {
            if (!InventurGruppe.Umlaufvermögen.kategorien.contains(Kategorie(it.kategorie)))
            {
                throw UngültigeKategorie(it.kategorie)
            }
        }

        schulden.forEach {
            if (!InventurGruppe.Schulden.kategorien.contains(Kategorie(it.kategorie)))
            {
                throw UngültigeKategorie(it.kategorie)
            }
        }

    }
}