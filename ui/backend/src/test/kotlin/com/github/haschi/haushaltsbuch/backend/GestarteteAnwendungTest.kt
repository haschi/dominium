package com.github.haschi.haushaltsbuch.backend

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

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
}