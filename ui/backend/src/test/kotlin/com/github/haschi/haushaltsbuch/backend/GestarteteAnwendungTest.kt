package com.github.haschi.haushaltsbuch.backend

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class GestarteteAnwendungTest
{
    @Test
    fun `sollte beendet werden`()
    {
        val anwendung: Anwendung = mock(Anwendung::class.java)
        val gestarteteAnwendung = GestarteteAnwendung(anwendung)
        gestarteteAnwendung.shutdown()

        verify(anwendung).stop()
    }
}