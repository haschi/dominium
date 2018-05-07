package com.github.haschi.dominium.haushaltsbuch.core.model.values

enum class InventurGruppe(
        val bezeichnung: String,
        val kategorien: Array<Kategorie>)
{
    Anlagevermögen("Anlagevermögen", arrayOf(
            Kategorie("Sonstiges"))),

    Umlaufvermögen("Umlaufvermögen", arrayOf(
            Kategorie("Sonstiges"))),

    Schulden("Schulden", arrayOf(
            Kategorie("Sonstiges"))),
}