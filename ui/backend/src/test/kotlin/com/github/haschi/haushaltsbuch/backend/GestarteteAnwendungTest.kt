package com.github.haschi.haushaltsbuch.backend

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@Disabled
class GestarteteAnwendungTest
{
    @Test
    fun `sollte Anwendungskern beenden`()
    {
        val anwendung: Anwendung = mock(Anwendung::class.java)
        val gestarteteAnwendung = GestarteteAnwendung(anwendung)

        gestarteteAnwendung.shutdown()

        verify(anwendung).stop()
    }

    @BeforeEach
    fun ausgabeKlauen()
    {
        System.setOut(PrintStream(output))
    }

    @Test
    fun `sollte die Log-Ausgabe 'Anwendungskern wird beendet' beim Beenden ausgeben`()
    {
        val anwendung = mock(Anwendung::class.java)

        val sut = GestarteteAnwendung(anwendung)
        sut.shutdown()

        assertThat(output.toString())
                .contains("Anwendungskern wird beendet")

        assertThat(output.toString())
                .contains("ENDE")
    }

    @Test
    fun `sollte die Log-Ausgabe 'ENDE' beim Beenden ausgeben`()
    {
        val anwendung = mock(Anwendung::class.java)

        val sut = GestarteteAnwendung(anwendung)
        sut.shutdown()

        assertThat(output.toString())
                .contains("ENDE")
    }


    @AfterEach
    fun ausgabeZurueckgeben()
    {
        System.setOut(System.out)
    }

    companion object
    {
        val output = ByteArrayOutputStream()
    }
}