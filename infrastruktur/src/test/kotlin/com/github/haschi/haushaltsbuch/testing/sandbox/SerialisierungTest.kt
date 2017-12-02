package com.github.haschi.haushaltsbuch.testing.sandbox

import org.github.haschi.haushaltsbuch.api.BeginneInventur
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import java.util.stream.Stream

@DisplayName("Serialisierbarkeit der Wertobjekte prüfen")
class SerialisierungTest
{
    data class Testfall
    (
        val poko: Any,
        val json: String
    )

    @TestTemplate
    @DisplayName("prüfen, ob POKO korrekt serialisiert wird")
    @ExtendWith(SerialisierteAnweisungenAnbieter::class)
    fun serialisierung(testfall: Testfall) {

    }

    class SerialisierteAnweisungenAnbieter : TestfallAnbieter<Testfall>(Testfall::class)
    {
        override fun testfälle(): Stream<Testfall> =
            Stream.of(
                    Testfall(
                            BeginneInventur(Aggregatkennung.neu()),
                            """{"id": "12345 """))

    }
}