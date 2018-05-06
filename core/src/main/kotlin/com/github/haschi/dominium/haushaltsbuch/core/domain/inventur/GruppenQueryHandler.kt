package com.github.haschi.dominium.haushaltsbuch.core.domain.inventur

import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurGruppe
import org.axonframework.queryhandling.QueryHandler

object Ansicht
{
    val umlaufvermoegen: InventurGruppe = InventurGruppe.Umlaufvermögen
    val anlagevermoegen: InventurGruppe = InventurGruppe.Anlagevermögen
    val schulden: InventurGruppe = InventurGruppe.Schulden
}

class GruppenQueryHandler
{
    @QueryHandler
    fun falls(query: LeseInventurGruppen): Ansicht
    {
        return Ansicht
    }
}