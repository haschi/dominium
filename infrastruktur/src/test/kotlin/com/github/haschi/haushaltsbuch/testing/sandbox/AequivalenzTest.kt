package com.github.haschi.haushaltsbuch.testing.sandbox

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Äquivalenzregeln der Wertobjekte prüfen")
class AequivalenzTest
{
    @Test
    fun klasseA()
    {
        EqualsVerifier.forClass(KlasseA::class.java).verify()
    }

    @Test
    fun klasseE()
    {
        EqualsVerifier.forClass(KlasseE::class.java).verify()
    }
}