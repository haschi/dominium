package com.github.haschi.haushaltsbuch.testing.sandbox

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.jupiter.api.Test

class ÄquivalenzTest
{
    @Test
    fun klasse_a()
    {
        EqualsVerifier.forClass(KlasseA::class.java).verify()
    }

    @Test
    fun klasse_e()
    {
        EqualsVerifier.forClass(KlasseE::class.java).verify()
    }
}