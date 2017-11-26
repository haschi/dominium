package com.github.haschi.haushaltsbuch.testing.sandbox

import com.fasterxml.jackson.annotation.JsonCreator

// Beispiel für ein Wertobjekt, dass als einzigen Wert eine unveränderbare
// Liste enthält.
//
//
data class KlasseE(val l: List<String>) : List<String> by l
{
    @JsonCreator constructor(vararg s: String) : this(s.asList())
}