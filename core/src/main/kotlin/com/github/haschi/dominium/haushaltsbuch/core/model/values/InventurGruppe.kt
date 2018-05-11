package com.github.haschi.dominium.haushaltsbuch.core.model.values

enum class InventurGruppe(val kategorien: Array<Kategorie>)
{
    Anlagevermögen(arrayOf(
            Kategorie("Fuhrpark"),
            Kategorie("Grundstücke und Gebäude"),
            Kategorie("Wertpapiere"),
            Kategorie("Sonstiges"))),

    Umlaufvermögen(arrayOf(
            Kategorie("Bargeld"),
            Kategorie("Forderungen"),
            Kategorie("Bankguthaben"),
            Kategorie("Sonstiges"))),

    Schulden(arrayOf(
            Kategorie("Hypotheken"),
            Kategorie("Darlehen"),
            Kategorie("Sonstiges"))),
}