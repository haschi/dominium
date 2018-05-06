package com.github.haschi.domain.haushaltsbuch.testing

data class Kategorie(val kategorie: String)
{
    companion object
    {
        val keine: Kategorie
            get() = Kategorie("")
    }
}