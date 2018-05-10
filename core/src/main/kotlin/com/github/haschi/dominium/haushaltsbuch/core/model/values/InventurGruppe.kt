package com.github.haschi.dominium.haushaltsbuch.core.model.values

enum class InventurGruppe(
        val kategorien: Array<Kategorie>)
{
    Anlagevermögen(arrayOf(
            Kategorie("Sonstiges"))),

    Umlaufvermögen(arrayOf(
            Kategorie("Sonstiges"))),

    Schulden(arrayOf(
            Kategorie("Sonstiges"))),
}