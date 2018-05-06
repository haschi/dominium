package com.github.haschi.dominium.haushaltsbuch.core.model.values

data class Kategorie(val kategorie: String)
{
    companion object
    {
        val keine: Kategorie
            get() = Kategorie("")
    }
}