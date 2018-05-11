package com.github.haschi.dominium.haushaltsbuch.core.model.commands

class UngültigeKategorie(private val kategorie: String) : Exception("Ungültige Kategorie '$kategorie'")
